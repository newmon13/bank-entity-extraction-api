package dev.jlipka.bankentityextractionapi.upload.impl.parser.impl;

import dev.jlipka.bankentityextractionapi.bank.api.model.Bank;
import dev.jlipka.bankentityextractionapi.upload.impl.parser.api.Parsable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class ExcelBankParser implements Parsable<Row, Bank> {
    @Override
    public Bank parse(Row cells) {
        Map<Integer, String> cellValues = extractCellValues(cells.cellIterator());
        return Bank.builder()
                .countryCode(getCellValue(cellValues, BankColumn.COUNTRY_CODE))
                .swiftCode(getCellValue(cellValues, BankColumn.SWIFT_CODE))
                .codeType(getCellValue(cellValues, BankColumn.CODE_TYPE))
                .name(getCellValue(cellValues, BankColumn.NAME))
                .address(getCellValue(cellValues, BankColumn.ADDRESS))
                .townName(getCellValue(cellValues, BankColumn.TOWN_NAME))
                .countryName(getCellValue(cellValues, BankColumn.COUNTRY_NAME))
                .timeZone(getCellValue(cellValues, BankColumn.TIME_ZONE))
                .build();
    }

    private Map<Integer, String> extractCellValues(Iterator<Cell> rowCells) {
        Map<Integer, String> cellValues = new HashMap<>();

        while (rowCells.hasNext()) {
            Cell cell = rowCells.next();
            cellValues.put(cell.getColumnIndex(), cell.getStringCellValue());
        }

        return cellValues;
    }

    private String getCellValue(Map<Integer, String> cellValues, BankColumn column) {
        String value = cellValues.get(column.index);
        if (value == null || value.isBlank()) {
            return null;
        } else {
            return value.strip();
        }
    }

    private enum BankColumn {
        COUNTRY_CODE(0), SWIFT_CODE(1), CODE_TYPE(2), NAME(3), ADDRESS(4), TOWN_NAME(5), COUNTRY_NAME(6), TIME_ZONE(7);

        private final int index;

        BankColumn(int index) {
            this.index = index;
        }
    }
}