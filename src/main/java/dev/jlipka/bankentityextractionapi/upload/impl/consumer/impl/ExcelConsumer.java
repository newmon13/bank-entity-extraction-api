package dev.jlipka.bankentityextractionapi.upload.impl.consumer.impl;

import dev.jlipka.bankentityextractionapi.upload.impl.consumer.api.Consumable;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelConsumer implements Consumable<Sheet> {

    @Value("${app.storage.location}")
    private String storageLocation;

    @Override
    public List<Sheet> read(Path pathToFile) {
        String fileName = String.valueOf(pathToFile.getFileName());
        try (InputStream inputStream = getExcelInputStream(fileName)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            List<Sheet> sheets = new ArrayList<>();
            workbook.sheetIterator()
                    .forEachRemaining(sheets::add);
            return sheets;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            cleanupFile(fileName);
        }
    }

    private InputStream getExcelInputStream(String fileName) {
        try {
            return new FileInputStream(getFilePath(fileName).toFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getFilePath(String fileName) {
        Path storagePath = Path.of(storageLocation);
        return storagePath.resolve(fileName);
    }

    private void cleanupFile(String fileName) {
        if (fileName != null) {
            try {
                Files.deleteIfExists(Path.of(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}