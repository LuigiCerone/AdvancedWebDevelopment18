package utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;

@Produces("application/json")
public class JacksonObjectMapper implements ContextResolver<ObjectMapper> {
    private ObjectMapper objectMapper;

    public JacksonObjectMapper() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new ISO8601DateFormat());
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.objectMapper;
    }
}