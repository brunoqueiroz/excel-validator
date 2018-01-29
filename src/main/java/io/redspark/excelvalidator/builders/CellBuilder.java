package io.redspark.excelvalidator.builders;

import java.util.List;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.Cell;
import io.redspark.excelvalidator.model.Line;
import io.redspark.excelvalidator.model.fields.common.BaseCell;
import io.redspark.excelvalidator.model.fields.common.TextColumn;
import io.redspark.excelvalidator.model.fields.common.Validator;
import lombok.Getter;

public class CellBuilder {

    private String value;
    private List<Validator> validators;
    @Getter
    private int index;
    private Class<? extends BaseCell> classType;
    private Line line;
    private Boolean required = true;

    public CellBuilder(Class classType, int index) {
        this.classType = classType;
        this.index = index;
    }

    public static CellBuilder builder(Class classType, int index) {
        return new CellBuilder(classType, index);
    }

    public CellBuilder value(String value) {
        this.value = value;
        return this;
    }

    public CellBuilder required(Boolean required) {
        this.required = required;
        return this;
    }

    public CellBuilder validators(List<Validator> validators) {
        this.validators = validators;
        return this;
    }

    public CellBuilder index(int index) {
        this.index = index;
        return this;
    }

    public Cell build() {
        BaseCell baseCell = null;
        try {
            baseCell = this.classType.newInstance();
            baseCell.setLine(this.line);
            baseCell.setValue(this.value);
            baseCell.setIndex(this.index);
            baseCell.setError(false);
            baseCell.setRequired(this.required);
            baseCell.setMessage("");
            if (validators != null && !validators.isEmpty()) {
                baseCell.setValidators(this.validators);
            }
        } catch (InstantiationException e) {
            baseCell = createErrorCell();

        } catch (IllegalAccessException e) {
            baseCell = createErrorCell();
        }
        return baseCell;

    }

    private BaseCell createErrorCell() {
        TextColumn textColumn = new TextColumn();
        textColumn.setError(true);
        textColumn.setIndex(this.index);
        textColumn.setLine(this.line);
        textColumn.setValue(EnumValidations.CELL_ERROR.getText());
        return textColumn;
    }

    public CellBuilder line(Line line) {
        this.line = line;
        return this;
    }
}
