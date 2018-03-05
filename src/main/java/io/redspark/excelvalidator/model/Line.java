package io.redspark.excelvalidator.model;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Line {

    @Getter
    private Table table;
    @Getter
    private Row row;
    @Getter
    private List<Cell> cells = new ArrayList<>();
    @Getter
    private Boolean error = false;
    private List<String> errors = new ArrayList<String>();

    public Line(Table table, Row row) {
        this.table = table;
        this.row = row;
    }

    public String getCellValueOf(int index){
        return StringUtils.trim(this.getCells().get(index).getValue());
    }

    public Line onlyDigits(int index){
        this.getCells().get(index).setValue(ValidationUtils.removeSpecialCharacters(this.getCells().get(index).getValue()));
        return this;
    }

    public Long getCellLongValueOf(int index){
        return Long.valueOf(this.getCells().get(index).getValue());
    }

    public Integer getCellIntegerValueOf(int index){
        return Integer.valueOf(this.getCells().get(index).getValue());
    }

    public String getCellStringValueOf(int index){
        return this.getCells().get(index).getValue();
    }

    public Optional<Date> getCellDateValueOf(int index, String format) throws ParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return Optional.of(simpleDateFormat.parse(this.getCells().get(index).getValue()));
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> getCellLocalDateOf(int index, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return Optional.of(LocalDate.parse(this.getCells().get(index).getValue(), formatter));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public void addCell(Cell cell){
        cells.add(cell);
    }

    public Boolean validate() {
        this.error = this.cells.stream().filter(celula -> !celula.validate()).count() > 0;
        return !this.error;
    }

    public Boolean validateAndMarkError() {
        boolean valid = this.cells.stream().filter(celula -> !celula.validateAndMarkError()).count() > 0;
        return valid;
    }

    public void createNewColumnWithErrorMessages() {
        short lastCell = row.getLastCellNum();
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(lastCell);
        String message = EnumValidations.DETAIL.getText() + getErrorString();
        if (this.errors != null && !this.errors.isEmpty()) {
            cell.setCellValue(message);
        }
    }

    public String getErrorString(){
        return this.errors.stream().collect(Collectors.joining(" / "));
    }

    public void tintAllCellsWithError(){
        this.cells.stream().filter(cell -> cell.getError()).forEach(celula -> celula.tintError());
    }

    public void markLineWithError(String message) {
        this.error = true;
        this.errors.add(message);
    }

}
