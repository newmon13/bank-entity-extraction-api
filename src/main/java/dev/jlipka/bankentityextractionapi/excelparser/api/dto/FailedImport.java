package dev.jlipka.bankentityextractionapi.excelparser.api.dto;

import java.util.List;

public record FailedImport(Object entity, List<String> errors) {
}
