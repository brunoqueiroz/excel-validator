package io.redspark.excelvalidator.model;

import io.redspark.excelvalidator.adapter.TableAdapter;
import io.redspark.excelvalidator.builders.CellBuilder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Table {

    @Getter
    private final TableAdapter adapter;
    private final short indexDetail;
    @Getter
    private List<Line> lines = new ArrayList<Line>();

    @Getter
    private Boolean getHasErro;

    public Table(TableAdapter tableAdapter, List<CellBuilder> cells) {
        this(tableAdapter, cells, Short.valueOf("-2").shortValue());
    }

    public Table(TableAdapter tableAdapter, List<CellBuilder> cells, short indexDetail) {
       this.adapter = tableAdapter;
       this.lines = tableAdapter.getLines(this, cells);
       this.indexDetail = indexDetail;
    }

    public String getErrorString(){
        return this.getLines().stream().map(line -> line.getErrorString()).collect(Collectors.joining(" / "));
    }

    public boolean hasError(){
        return lines.stream().filter(line -> line.getError()).count() > 0;
    }

    public void validateFields() {
        lines.forEach(line -> {
            line.validateAndMarkError();
            line.createNewColumnWithErrorMessages(indexDetail);
            line.tintAllCellsWithError();
        });
    }

}
