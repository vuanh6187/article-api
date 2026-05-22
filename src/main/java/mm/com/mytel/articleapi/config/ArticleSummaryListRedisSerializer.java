package mm.com.mytel.articleapi.config;

import mm.com.mytel.articleapi.dto.ArticleSummaryResponse;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class ArticleSummaryListRedisSerializer implements RedisSerializer<Object> {

    private static final TypeReference<List<ArticleSummaryResponse>> LIST_TYPE =
            new TypeReference<>() {};

    private final ObjectMapper objectMapper;

    public ArticleSummaryListRedisSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(Object value) throws SerializationException {
        if (value == null) {
            return new byte[0];
        }
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JacksonException e) {
            throw new SerializationException("Could not serialize article summaries", e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, LIST_TYPE);
        } catch (JacksonException e) {
            throw new SerializationException("Could not deserialize article summaries", e);
        }
    }
}
