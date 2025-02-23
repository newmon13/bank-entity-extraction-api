package dev.jlipka.bankentityextractionapi.upload;


import dev.jlipka.bankentityextractionapi.upload.api.dto.BankImportResult;
import dev.jlipka.bankentityextractionapi.upload.impl.ExcelBankImportFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BankImportController {

    private final ExcelBankImportFacade facade;

    public BankImportController(ExcelBankImportFacade facade) {
        this.facade = facade;
    }

    @PostMapping(value = "/excel/bank/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BankImportResult importFile(@RequestParam("file") MultipartFile file, @RequestParam("has_header_row") boolean hasHeaderRow) {
        return facade.importFile(file, hasHeaderRow);
    }
}