package dev.jlipka.bankentityextractionapi.upload.impl.parser.api.error;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class FailedParseException extends RuntimeException {
    private FailedParse failedParse;

    public FailedParseException(List<ObjectError> errors) {
        super(errors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", ")));
    }

    public FailedParseException(String message) {
        super(message);
    }

    public FailedParseException(FailedParse failedParse) {
        this.failedParse = failedParse;
    }

    public FailedParse getDetails() {
        return failedParse;
    }
}