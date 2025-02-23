package dev.jlipka.bankentityextractionapi.upload.impl;

import dev.jlipka.bankentityextractionapi.bank.api.model.Bank;
import dev.jlipka.bankentityextractionapi.upload.api.ImportFacade;
import dev.jlipka.bankentityextractionapi.upload.api.dto.BankImportResult;
import dev.jlipka.bankentityextractionapi.upload.impl.consumer.api.Consumable;
import dev.jlipka.bankentityextractionapi.upload.impl.extractor.api.Extractable;
import dev.jlipka.bankentityextractionapi.upload.impl.parser.api.Parsable;
import dev.jlipka.bankentityextractionapi.upload.impl.persister.api.Persistable;
import dev.jlipka.bankentityextractionapi.upload.impl.uploader.api.Uploadable;
import dev.jlipka.bankentityextractionapi.upload.impl.validator.api.Validatable;
import dev.jlipka.bankentityextractionapi.upload.impl.validator.api.ValidationResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ExcelBankImportFacade implements ImportFacade {
    private final Uploadable uploader;
    private final Consumable<Sheet> consumer;
    private final Extractable<Sheet, Row> extractor;
    private final Parsable<Row, Bank> parser;
    private final Validatable<Bank> validator;
    private final Persistable<Bank> persister;

    public ExcelBankImportFacade(Uploadable uploader, Consumable<Sheet> consumer, Extractable<Sheet, Row> extractor,
                                 Parsable<Row, Bank> parser, Validatable<Bank> validator, Persistable<Bank> persister) {
        this.uploader = uploader;
        this.consumer = consumer;
        this.extractor = extractor;
        this.parser = parser;
        this.validator = validator;
        this.persister = persister;
    }


    @Override
    public BankImportResult importFile(MultipartFile file, boolean hasHeaderRow) {
        Path uploadedFilePath = uploader.upload(file);
        List<Sheet> sheets = consumer.read(uploadedFilePath);
        List<Row> rows = extractAll(sheets, hasHeaderRow);
        List<Bank> parsedRows = parseAll(rows);

        ValidationResult<Bank> validationResult = validator.validate(parsedRows);
        List<Bank> persistedBanks = persister.persist(validationResult.validEntities());

        return new BankImportResult(
                rows.size(),
                persistedBanks.size(),
                validationResult.validationErrors()
        );
    }

    private List<Row> extractAll(List<Sheet> sheets, boolean hasHeaderRow) {
        return sheets.stream()
                .flatMap(sheet -> extractor.extract(sheet, hasHeaderRow).stream())
                .collect(toList());
    }

    private List<Bank> parseAll(List<Row> rows) {
        return rows.stream()
                .map(parser::parse)
                .collect(toList());
    }
}
