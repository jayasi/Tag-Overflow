package services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.Callback;
import models.Discussion;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Apoorv Singh on 4/12/2015.
 */

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate template;

    public <T> T getFromRedis(String key, Class<T> clazz, Callback<T> callback) throws IOException {
        ValueOperations<String, String> ops = template.opsForValue();
        String value = ops.get(key);
        ObjectMapper mapper = new ObjectMapper();
        if(value == null) {
            T valueHolder = callback.onKeyNotFound();
            String finalJSON = mapper.writeValueAsString(valueHolder);
            ops.set(key, finalJSON, 1, TimeUnit.MINUTES);
            return valueHolder;
        } else {
            if(value.charAt(0) == '[') {
                return mapper.readValue(value, new TypeReference<T>(){});
            } else {
                return (T) mapper.readValue(value, clazz);
            }
        }
    }

}
