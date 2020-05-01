package br.com.ees.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtils {

    private static final ObjectMapper OM = new ObjectMapper();

    @SneakyThrows
    public static String name(Object value) {

        return OM.writeValueAsString(value);
    }
}
