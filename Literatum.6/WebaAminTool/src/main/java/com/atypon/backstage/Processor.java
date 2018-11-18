package com.atypon.backstage;

public interface Processor<T> {
    void process(T data) throws ProcessingException;
}
