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
    @Unique private static final Json5 JSON5Reader = Json5.builder(options -> options.allowInvalidSurrogate().quoteSingle().prettyPrinting().build());
    @Unique private static final Json5 JSON5Writer = Json5.builder(options -> options.build());

    /**
     * @author
     * @reason
     */
    @Overwrite @Nullable
    public static <T> T deserialize(Gson gson, Reader reader, Class<T> type, boolean lenient) {
        try {
            Reader reader_ = lenient ? new StringReader(JSON5Writer.serialize(JSON5Reader.parse(reader))) : reader;
            JsonReader jsonReader = new JsonReader(reader_);
            jsonReader.setLenient(true);
            return gson.getAdapter(type).read(jsonReader);
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
            Reader reader_ = lenient ? new StringReader(JSON5Writer.serialize(JSON5Reader.parse(reader))) : reader;
            JsonReader jsonReader = new JsonReader(reader_);
            jsonReader.setLenient(true);
            return gson.getAdapter(typeToken).read(jsonReader);
        } catch (IOException var5) {
            throw new JsonParseException(var5);
        }
    }
}
