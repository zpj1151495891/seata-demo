package com.example.testbusinessservice.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.seata.rm.datasource.undo.parser.spi.JacksonSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class JsonDateTimeSerializer implements JacksonSerializer<LocalDateTime> {

    /**
     * the zoneId for LocalDateTime
     */
    private static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * customize serializer of java.time.LocalDateTime
     */
    private final JsonSerializer<LocalDateTime> localDateTimeSerializer = new LocalDateTimeSerializer();

    /**
     * customize deserializer of java.time.LocalDateTime
     */
    private final JsonDeserializer<LocalDateTime> localDateTimeDeserializer = new LocalDateTimeDeserializer();

    @Override
    public Class<LocalDateTime> type() {
        return LocalDateTime.class;
    }
 
    @Override
    public JsonSerializer<LocalDateTime> ser() {
        return localDateTimeSerializer;
    }
 
    @Override
    public JsonDeserializer<? extends LocalDateTime> deser() {
        return localDateTimeDeserializer;
    }

    /**
     * the class of serialize LocalDateTime type
     */
    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serializeWithType(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers,
                                      TypeSerializer typeSer) throws IOException {
            JsonToken valueShape = JsonToken.VALUE_NUMBER_INT;
            // if has microseconds, serialized as an array
            if (localDateTime.getNano() % 1000000 > 0) {
                valueShape = JsonToken.START_ARRAY;
            }

            WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen,
                    typeSer.typeId(localDateTime, valueShape));
            serialize(localDateTime, gen, serializers);
            typeSer.writeTypeSuffix(gen, typeIdDef);
        }

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            try {
                Instant instant = localDateTime.atZone(zoneId).toInstant();
                gen.writeNumber(instant.toEpochMilli());
                // if has microseconds, serialized as an array, write the nano to the array
                if (instant.getNano() % 1000000 > 0) {
                    gen.writeNumber(instant.getNano());
                }
            } catch (IOException e) {
                log.error("serialize java.time.LocalDateTime error : {}", e.getMessage(), e);
            }
        }
    }

    /**
     * the class of deserialize LocalDateTime type
     */
    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            try {
                Instant instant;
                if (p.isExpectedStartArrayToken()) {
                    ArrayNode arrayNode = p.getCodec().readTree(p);
                    long timestamp = arrayNode.get(0).asLong();
                    instant = Instant.ofEpochMilli(timestamp);
                    if (arrayNode.size() > 1) {
                        int nano = arrayNode.get(1).asInt();
                        instant = instant.plusNanos(nano % 1000000);
                    }
                } else {
                    long timestamp = p.getLongValue();
                    instant = Instant.ofEpochMilli(timestamp);
                }
                return LocalDateTime.ofInstant(instant, zoneId);
            } catch (Exception e) {
                log.error("deserialize java.time.LocalDateTime error : {}", e.getMessage(), e);
            }
            return null;
        }
    }
}