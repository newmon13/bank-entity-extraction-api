package dev.jlipka.bankentityextractionapi.upload.impl.uploader.api.error;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
}
