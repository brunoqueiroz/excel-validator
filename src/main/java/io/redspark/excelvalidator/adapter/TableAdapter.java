package io.redspark.excelvalidator.adapter;

import io.redspark.excelvalidator.builders.CellBuilder;
import io.redspark.excelvalidator.model.Cell;
import io.redspark.excelvalidator.model.Line;
import io.redspark.excelvalidator.model.Table;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TableAdapter {

    List<Line> getLines(Table table, List<CellBuilder> cells);
    CellStyle getCellStyle();
    String createSheet(String path) throws IOException;
    Line getHeader();

    String createSheet(String path, String name) throws IOException;

    Boolean hasHeader();
    String getHeaderNameOf(int index);
    String getCellValue(Row currentRow, int index);
    void cellTint(Line line, CellStyle styleYellow, int index);

}
