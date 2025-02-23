package dev.jlipka.bankentityextractionapi.upload.impl.validator.api;

import java.util.List;

public interface Validatable<T> {
    ValidationResult<T> validate(List<T> entities);
}
