package io.redspark.excelvalidator.adapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
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

    public TableAdapterImpl(InputStream inputStream, boolean hasHeader) {
        this.hasHeader = hasHeader;
        try {
            workbook = new XSSFWorkbook(inputStream);
            content = workbook.getSheetAt(0);
            limparLinhasEmbranco(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limparLinhasEmbranco(XSSFSheet content2) {
        for (int i = 1; i < content.getLastRowNum(); i++) {
            if (isEmpty(content.getRow(i))) {
                content.shiftRows(i+1, content.getLastRowNum(), -1);
                break;
            }
        }
    }

    private boolean isEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            org.apache.poi.ss.usermodel.Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)
                return false;
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
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString().substring(0, 30);
        FileOutputStream fileOut = new FileOutputStream(path + myRandom + ".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        return myRandom + ".xlsx";
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

        if(this.hasHeader) {
            createHeaderCells(cells, table, iterator.next());
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
        return formatter.formatCellValue(currentRow.getCell(index));
    }

    @Override
    public void cellTint(Line line, CellStyle styleYellow, int index) {
        org.apache.poi.ss.usermodel.Cell cell = line.getRow().getCell(index);
        cell = cell == null ? line.getRow().createCell(index) : cell;
        cell.setCellStyle(styleYellow);
    }

}
