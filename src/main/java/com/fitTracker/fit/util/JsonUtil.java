package com.fitTracker.fit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fitTracker.fit.exception.InternalServerException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@AllArgsConstructor
public class JsonUtil {
    private final String jsonFilePath = "/json";
    private final ObjectMapper objectMapper;

    public <T> T deserialize(String from, Class<T> tClass, boolean isPath) {
        if (isPath) {
            from = getJsonFileString(from);
        }
        return getObjectFromJson(from, tClass);
    }

    public <T> T deserialize(String from, Class<? extends Collection> classCollection, Class<?> clazz, boolean isPath) {
        if (isPath) {
            from = getJsonFileString(from);
        }
        return getListOfObjectsFromJson(from, classCollection, clazz);
    }

    public String serialize(Object jsonObject) {
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("Could not serialize JSON");
        }
    }

    public String getJsonFileString(String subPath) {
        Resource jsonFileResource = new ClassPathResource(jsonFilePath + "/" + subPath);
        try {
            return new String(jsonFileResource.getInputStream().readAllBytes(), UTF_8);
        } catch (IOException ex) {
            throw new InternalServerException("Could not read from JSON");
        }
    }

    private <T> T getObjectFromJson(String targetJson, Class<T> tClass) {
        try {
            return objectMapper.readValue(targetJson, tClass);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("Could not read object value from JSON for class: " + tClass.getName());
        }
    }

    private <T> T getListOfObjectsFromJson(String targetJson, Class<? extends Collection> classCollection, Class<?> tClass) {
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(classCollection, tClass);
        try {
            return objectMapper.readValue(targetJson, collectionType);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("Could not read value from JSON for collection of: " + tClass.getName());
        }
    }
}
