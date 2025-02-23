package dev.jlipka.bankentityextractionapi.excelparser.api.error;

public class UnsupportedFileTypeException extends RuntimeException {
    public UnsupportedFileTypeException(String message) {
        super(message);
    }
}
