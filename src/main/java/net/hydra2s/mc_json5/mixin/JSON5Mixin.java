package net.hydra2s.mc_json5.mixin;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import de.marhali.json5.Json5;
import de.marhali.json5.exception.Json5Exception;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registries;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.annotation.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import org.slf4j.Logger;

@Mixin(JsonHelper.class)
public class JSON5Mixin {
    @Unique private static final Json5 JSON5Reader = Json5.builder(options -> options.allowInvalidSurrogate().quoteSingle().trailingComma().build());
    @Unique private static final Json5 JSON5Writer = Json5.builder(options -> options.prettyPrinting().build());

    //
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = true) @Nullable
    public static <T> T deserialize(Gson gson, Reader reader, Class<T> type, boolean lenient) throws IOException {
        var json5 = IOUtils.toString(reader);

        // if still malformed, try parse as JSON5
        if (lenient) {
            try {
                json5 = JSON5Writer.serialize(JSON5Reader.parse(json5));
            } catch (IOException var7) {
                throw new Json5Exception(var7);
            }

            //
            //LOGGER.info("DEBUG JSON: ");
            //LOGGER.info(json5);
        }

        //
        try {
            // if still malformed, try parse as JSON5
            JsonReader jsonReader = new JsonReader(new StringReader(json5));
            jsonReader.setLenient(true);
            return gson.getAdapter(type).read(jsonReader);
        } catch (JsonParseException var5) {
            try {
                // if still malformed, try parse as JSON5
                try {
                    json5 = JSON5Writer.serialize(JSON5Reader.parse(json5));
                } catch(IOException var7) {
                    throw new Json5Exception(var7);
                }

                //
                //LOGGER.info("DEBUG JSON: ");
                //LOGGER.info(json5);

                // if still malformed, try parse as JSON5
                JsonReader jsonReader = new JsonReader(new StringReader(json5));
                jsonReader.setLenient(true);
                return gson.getAdapter(type).read(jsonReader);
            } catch (IOException var6) {
                throw new JsonParseException(var6);
            }
            //throw new JsonParseException(var5);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = true) @Nullable
    public static <T> T deserialize(Gson gson, Reader reader, TypeToken<T> typeToken, boolean lenient) throws IOException {
        var json5 = IOUtils.toString(reader);

        // if still malformed, try parse as JSON5
        if (lenient) {
            try {
                json5 = JSON5Writer.serialize(JSON5Reader.parse(json5));
            } catch (IOException var7) {
                throw new Json5Exception(var7);
            }

            //
            //LOGGER.info("DEBUG JSON: ");
            //LOGGER.info(json5);
        }

        //
        try {
            // if still malformed, try parse as JSON5
            JsonReader jsonReader = new JsonReader(new StringReader(json5));
            jsonReader.setLenient(true);
            return gson.getAdapter(typeToken).read(jsonReader);
        } catch (JsonParseException var5) {
            try {
                // if still malformed, try parse as JSON5
                try {
                    json5 = JSON5Writer.serialize(JSON5Reader.parse(json5));
                } catch(IOException var7) {
                    throw new Json5Exception(var7);
                }

                //
                //LOGGER.info("DEBUG JSON: ");
                //LOGGER.info(json5);

                // if still malformed, try parse as JSON5
                JsonReader jsonReader = new JsonReader(new StringReader(json5));
                jsonReader.setLenient(true);
                return gson.getAdapter(typeToken).read(jsonReader);
            } catch (IOException var6) {
                throw new JsonParseException(var6);
            }
            //throw new JsonParseException(var5);
        }
    }
}
