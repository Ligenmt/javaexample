package com.ligen.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author macbookpro
 */
public class BigDecimalSerializer
        extends StdScalarSerializer<BigDecimal>
        implements ContextualSerializer {
    private final static Map<Integer, BigDecimalSerializer> cache = new ConcurrentHashMap<>();
    /**
     * Static instance that is only to be used for {@link Number}.
     */
    private static final long serialVersionUID = 5467668946514982811L;

    private final int scale;

    public BigDecimalSerializer() {
        super(BigDecimal.class, false);
        this.scale = -1;
    }

    public BigDecimalSerializer(int scale) {
        super(BigDecimal.class, false);
        this.scale = scale;
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator g, SerializerProvider provider) throws IOException {
        // should mostly come in as one of these two:
        if (scale >= 0) {
            value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
//        g.writeNumber(value);
        g.writeString(value.toPlainString());

    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return createSchemaNode("string", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        Class<?> h = handledType();
        if (h == BigDecimal.class) {
            visitFloatFormat(visitor, typeHint, JsonParser.NumberType.BIG_DECIMAL);
        } else {
            // otherwise bit unclear what to call... but let's try:
                /*JsonNumberFormatVisitor v2 =*/
            visitor.expectNumberFormat(typeHint);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializers,
                                              BeanProperty property) throws JsonMappingException
    {

        if (property == null) {
            return this;
        }
        JsonFormat.Value format = findFormatOverrides(serializers, property, handledType());
        if (format == null) {
            return this;
        }
        if (NumberUtils.isCreatable(format.getPattern())) {
            Integer scale = NumberUtils.toInt(format.getPattern());
            if (scale < 0) {
                return this;
            }
            if (cache.containsKey(scale)) {
                return cache.get(scale);
            }
            BigDecimalSerializer serializer = new BigDecimalSerializer(scale);
            cache.put(scale, serializer);
            return serializer;
        }
        // Simple case first: serialize as numeric timestamp?
        return this;

    }

}