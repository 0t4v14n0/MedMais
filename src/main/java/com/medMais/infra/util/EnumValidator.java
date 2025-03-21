package com.medMais.infra.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class EnumValidator {

    // Metodo que valida se um valor do enum e valido
    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, E value) {
        if (value == null) return false;
        // Verifica se o valor existe no enum
        for (E enumValue : enumClass.getEnumConstants()) {
            if (enumValue == value) {
                return true;
            }
        }
        return false;
    }

    // Metodo para validar uma lista de valores do enum
    public static <E extends Enum<E>> boolean isValidEnumList(Class<E> enumClass, List<E> values) {
        if (values == null || values.isEmpty()) return false;
        return values.stream().allMatch(value -> isValidEnum(enumClass, value));
    }
}
