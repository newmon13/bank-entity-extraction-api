package dev.jlipka.bankentityextractionapi.upload.impl.extractor.api;

import java.util.List;

public interface Extractable<SOURCE extends Iterable<?>, TARGET> {
    List<TARGET> extract(SOURCE source, boolean hasHeaderRow);
}