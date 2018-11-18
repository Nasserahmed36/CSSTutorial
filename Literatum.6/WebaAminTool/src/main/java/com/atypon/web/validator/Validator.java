package com.atypon.web.validator;

import java.util.List;

public abstract class Validator<T> {
    public abstract List<String> validate(T object);

    public boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}
