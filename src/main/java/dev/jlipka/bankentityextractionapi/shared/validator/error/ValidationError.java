package dev.jlipka.bankentityextractionapi.shared.validator.error;

import lombok.Getter;

@Getter
public class ValidationError {
    private final String code;
    private final String message;
    private final String field;

    public ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
        this.field = null;
    }

    public ValidationError(String code, String message, String field) {
        this.code = code;
        this.message = message;
        this.field = field;
    }
}