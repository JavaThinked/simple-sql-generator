package com.javathinked.reflection.repository.exception;

public class TypeReflectionException extends RuntimeException {

    public TypeReflectionException() {
        super();
    }

    public TypeReflectionException(String message) {
        super(message);
    }

    public TypeReflectionException(Throwable cause) {
        super(cause);
    }

    public TypeReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
