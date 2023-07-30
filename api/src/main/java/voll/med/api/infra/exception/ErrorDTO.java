package voll.med.api.infra.exception;

import org.springframework.validation.FieldError;

public record ErrorDTO(String field, String message) {

    public ErrorDTO(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
