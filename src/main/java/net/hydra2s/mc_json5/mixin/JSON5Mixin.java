package net.hydra2s.mc_json5.mixin;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.marhali.json5.Json5;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registries;
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

@Mixin(JsonHelper.class)
public class JSON5Mixin {
    //@Shadow private static final Gson GSON = null;
    @Unique private static final Json5 JSON5 = Json5.builder(options -> options.allowInvalidSurrogate().quoteSingle().prettyPrinting().build());

    /**
     * @author
     * @reason
     */
    @Overwrite @Nullable
    public static <T> T deserialize(Gson gson, Reader reader, Class<T> type, boolean lenient) {
        try {
            if (!lenient) {
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(false);
                return gson.getAdapter(type).read(jsonReader);
            } else {
                return (T)(new Gson().fromJson(JSON5.serialize(JSON5.parse(reader)), type));
            }
        } catch (IOException var5) {
            throw new JsonParseException(var5);
        }
    }

    /**
     * @author
     * @reason
     */
    @Overwrite @Nullable
    public static <T> T deserialize(Gson gson, Reader reader, TypeToken<T> typeToken, boolean lenient) {
        try {
            if (!lenient) {
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(false);
                return gson.getAdapter(typeToken).read(jsonReader);
            } else {
                return (T)(new Gson().fromJson(JSON5.serialize(JSON5.parse(reader)), typeToken.getClass()));
            }
        } catch (IOException var5) {
            throw new JsonParseException(var5);
        }
    }
}
