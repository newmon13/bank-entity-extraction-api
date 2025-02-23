package dev.jlipka.bankentityextractionapi.upload.impl.validator.api;

import java.util.List;

public record ValidationResult<T>(List<T> validEntities, List<ValidationError<T>> validationErrors) {
}