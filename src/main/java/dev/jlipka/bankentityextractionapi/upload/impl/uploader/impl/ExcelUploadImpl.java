package dev.jlipka.bankentityextractionapi.upload.impl.uploader.impl;

import dev.jlipka.bankentityextractionapi.upload.impl.uploader.api.Uploadable;
import dev.jlipka.bankentityextractionapi.upload.impl.uploader.api.error.FileUploadException;
import dev.jlipka.bankentityextractionapi.upload.impl.uploader.api.validator.SpreadsheetFileValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ExcelUploadImpl implements Uploadable {

    private final SpreadsheetFileValidator fileValidator;
    @Value("${app.storage.location}")
    private String storageLocation;

    public ExcelUploadImpl(SpreadsheetFileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    @Override
    public Path upload(MultipartFile file) {
        validateFile(file);
        try {
            return Path.of(saveFile(file));
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file: " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (!fileValidator.supports(file.getClass())) {
            throw new FileUploadException("File type not supported");
        }

        Errors errors = new BeanPropertyBindingResult(file, "file");
        fileValidator.validate(file, errors);

        if (errors.hasErrors()) {
            throw new FileUploadException(errors.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". ")));
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Path.of(storageLocation);
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }
}
