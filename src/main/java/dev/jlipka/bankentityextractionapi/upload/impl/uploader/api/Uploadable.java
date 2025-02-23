package dev.jlipka.bankentityextractionapi.upload.impl.uploader.api;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface Uploadable  {
    Path upload(MultipartFile file);
}
