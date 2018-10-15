package io.redspark.excelvalidator.adapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.redspark.excelvalidator.builders.CellBuilder;
import io.redspark.excelvalidator.builders.LineBuilder;
import io.redspark.excelvalidator.model.Line;
import io.redspark.excelvalidator.model.Table;
import io.redspark.excelvalidator.model.fields.common.TextColumn;
import lombok.Getter;

public class TableAdapterImpl implements TableAdapter {

    private XSSFWorkbook workbook;
    private XSSFSheet content;
    private List<Line> lines = new ArrayList<>();
    private Row currentRow;
    private final boolean hasHeader;
    @Getter
    private Line header;
    private int headerLine;
    private int firstLine;

    public TableAdapterImpl(InputStream inputStream, boolean hasHeader) {
        this(inputStream, hasHeader, 0, 2);
    }

    public TableAdapterImpl(InputStream inputStream, boolean hasHeader, int headerLine, int firstLine) {
        this.hasHeader = hasHeader;
        try {
            workbook = new XSSFWorkbook(inputStream);
            content = workbook.getSheetAt(0);
            this.headerLine = headerLine;
            this.firstLine = firstLine;
            limparLinhasEmbranco(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limparLinhasEmbranco(XSSFSheet content2) {
        for (int i = this.firstLine; i < content.getLastRowNum(); i++) {
            if (isEmpty(content.getRow(i))) {
                content.shiftRows(i+1, content.getLastRowNum(), -1);
                break;
            }
        }
    }

    private boolean isEmpty(Row row) {
        if(row != null) {
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                org.apache.poi.ss.usermodel.Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)
                    return false;
            }
        }
        return true;
    }

    public CellStyle getCellStyle(){
        CellStyle styleYellow = workbook.createCellStyle();
        styleYellow.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        styleYellow.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return styleYellow;
    }

    @Override
    public String createSheet(String path) throws IOException {
        return createSheet(path, null);
    }

    @Override
    public String createSheet(String path, String name) throws IOException {

        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString().substring(0, 30);
        String fullname = myRandom + ".xlsx";

        if(name != null){
            fullname = name + ".xlsx" ;
        }
        FileOutputStream fileOut = new FileOutputStream(path + fullname);
        workbook.write(fileOut);
        fileOut.close();
        return fullname;
    }

    private void createHeaderCells(List<CellBuilder> cells, Table table, Row headerRow){
        List<CellBuilder> headerCells = new ArrayList<>();
        cells.stream().forEach(cellBuilder -> {
            headerCells.add(CellBuilder.builder(TextColumn.class, cellBuilder.getIndex()).required(false));
        });
        this.header = LineBuilder.builder(table).row(headerRow).cells(cells).build();
    }

    public Boolean hasHeader(){
        return this.getHeader() != null;
    }

    public String getHeaderNameOf(int index){
        if(hasHeader()){
            return this.getHeader().getCells().stream().filter(cell -> cell.getIndex() == index).findFirst().get().getValue();
        }
        return "";
    }

    public List<Line> getLines(Table table, List<CellBuilder> cells){
        Iterator<Row> iterator = content.iterator();

        int i = 0;
        for (Iterator<Row> it = iterator; it.hasNext(); i++) {
            Row row = iterator.next();
            if(hasHeader && i == this.headerLine){
                createHeaderCells(cells, table, row);
            }
            if(i == this.firstLine - 1){
                break;
            }
        }

        while (iterator.hasNext()) {
            currentRow = iterator.next();
            if(!isEmpty(currentRow)) {
                lines.add(LineBuilder.builder(table).row(currentRow).cells(cells).build());
            }
        }
        return lines;
    }

    @Override
    public String getCellValue(Row currentRow, int index) {
        DataFormatter formatter = new DataFormatter();
        Cell cell = currentRow.getCell(index);
        return formatter.formatCellValue(cell);
    }

    @Override
    public void cellTint(Line line, CellStyle styleYellow, int index) {
        org.apache.poi.ss.usermodel.Cell cell = line.getRow().getCell(index);
        cell = cell == null ? line.getRow().createCell(index) : cell;
        changeCelllToStringFormat(line, index, cell);
        cell.setCellStyle(styleYellow);

    }

    /**
     * Método que evita problemas quando a planilha está com formatação numérica.
     *
     * @param line
     * @param index
     * @param cell
     */
    private void changeCelllToStringFormat(Line line, int index, Cell cell) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(line.getCells().get(index).getValue());
    }

}
