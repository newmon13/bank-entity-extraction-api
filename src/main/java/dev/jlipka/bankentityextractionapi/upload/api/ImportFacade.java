package dev.jlipka.bankentityextractionapi.upload.api;

import dev.jlipka.bankentityextractionapi.upload.api.dto.BankImportResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImportFacade {
    BankImportResult importFile(MultipartFile file, boolean hasHeaderRow);
}
