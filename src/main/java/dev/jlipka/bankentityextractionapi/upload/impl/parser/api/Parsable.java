package dev.jlipka.bankentityextractionapi.upload.impl.parser.api;

public interface Parsable<SOURCE, TARGET> {
    TARGET parse(SOURCE source);
}
