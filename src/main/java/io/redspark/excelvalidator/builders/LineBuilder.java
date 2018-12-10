package io.redspark.excelvalidator.builders;

import io.redspark.excelvalidator.model.Cell;
import io.redspark.excelvalidator.model.Line;
import io.redspark.excelvalidator.model.Table;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class LineBuilder {

    private Table table;
    private Row row;
    private List<CellBuilder> cells = new ArrayList<>();

    public static LineBuilder builder(Table table){
        return new LineBuilder(table);
    }

    public LineBuilder row(Row row){
        this.row = row;
        return this;
    }

    public LineBuilder cells(List<CellBuilder> cells){
        this.cells = cells;
        return this;
    }

    public LineBuilder(Table table) {
        this.table = table;

    }

    public Line build() {
        Line line = new Line(this.table, this.row);
        this.cells.forEach(cellBuilder -> {
            String valor = this.table.getAdapter().getCellValue(row, cellBuilder.getIndex(), cellBuilder.getClassType());
            Cell cell = null;
            cell = cellBuilder.value(valor).line(line).build();
            line.addCell(cell);
        });
        return line;

    }

}
