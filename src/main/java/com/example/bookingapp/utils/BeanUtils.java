package com.example.bookingapp.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@UtilityClass
@Slf4j
public class BeanUtils {

    @SneakyThrows
    public static void nonNullPropertiesCopy(Object from, Object to) {
        log.debug("nonNullPropertiesCopy() method is called");

        Field[] fields = from.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(from);

             if (value != null) {
                 field.set(to, value);
             }
        }
    }
}
