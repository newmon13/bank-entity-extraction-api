package dev.jlipka.bankentityextractionapi.upload.impl.extractor.impl;

import dev.jlipka.bankentityextractionapi.upload.impl.extractor.api.Extractable;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class ExcelRowExtractor implements Extractable<Sheet, Row> {

    public List<Row> extract(Sheet sheet, boolean hasHeaderRow) {
        return new ArrayList<>(extractSheet(sheet, hasHeaderRow));
    }

    private List<Row> extractSheet(Sheet sheet, boolean hasHeaderRow) {
        List<Row> rows = new ArrayList<>();
        Iterator<Row> rowIterator = eliminateBlankRows(sheet);
        if (hasHeaderRow) {
            skipHeaderRow(rowIterator);
        }

        while (rowIterator.hasNext()) {
            rows.add(rowIterator.next());
        }
        return rows;
    }

    private Iterator<Row> eliminateBlankRows(Sheet sheet) {
        return StreamSupport.stream(sheet.spliterator(), true)
                .filter(row -> StreamSupport.stream(row.spliterator(), false)
                        .anyMatch(cell -> cell != null && cell.getCellType() != CellType.BLANK && !cell.getStringCellValue()
                                .equals("<null>")))
                .iterator();
    }

    private void skipHeaderRow(Iterator<Row> rowIterator) {
        rowIterator.next();
    }
}
