package dev.jlipka.bankentityextractionapi.upload.impl.parser.api.error;

import java.util.List;

public record FailedParse(Object entity, List<String> errors) {
}