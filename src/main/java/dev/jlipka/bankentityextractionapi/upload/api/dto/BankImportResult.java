package dev.jlipka.bankentityextractionapi.upload.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jlipka.bankentityextractionapi.bank.api.model.Bank;
import dev.jlipka.bankentityextractionapi.upload.impl.validator.api.ValidationError;

import java.util.List;

public record BankImportResult(
        @JsonProperty("entities_in_file") int entitiesInFile,
        @JsonProperty("saved_entities") int savedEntities,
        @JsonProperty("validation_errors") List<ValidationError<Bank>> validationErrors
) {
}
