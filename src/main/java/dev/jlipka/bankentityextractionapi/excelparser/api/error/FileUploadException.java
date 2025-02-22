package dev.jlipka.bankentityextractionapi.excelparser.api.error;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
