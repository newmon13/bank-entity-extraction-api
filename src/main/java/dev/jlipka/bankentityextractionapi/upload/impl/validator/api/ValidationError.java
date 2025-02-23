package dev.jlipka.bankentityextractionapi.upload.impl.validator.api;

import java.util.List;

public record ValidationError<T>(T entity, List<String> validationMessages) {
}