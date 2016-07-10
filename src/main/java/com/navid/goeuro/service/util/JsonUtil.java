package com.navid.goeuro.service.util;

/**
 * Developed by Navid Ghahremani (ghahramani.navid@gmail.com)
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Utility class for testing REST controllers.
 */
public class JsonUtil {

    /**
     * Convert JSON to object.
     *
     * @param clazz   the class that you want
     * @param content the json string to convert
     * @return the JSON byte array
     * @throws IOException
     */
    public static <T> T convertJsonToObject(String content, Class<T> clazz) throws IOException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(content, clazz);
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert
     * @return the JSON byte array
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = getObjectMapper();

        return mapper.writeValueAsBytes(object);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    /**
     * Create a byte array with a specific size filled with specified data.
     *
     * @param size the size of the byte array
     * @param data the data to put in the byte array
     */
    public static byte[] createByteArray(int size, String data) {
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = Byte.parseByte(data, 2);
        }
        return byteArray;
    }
}
