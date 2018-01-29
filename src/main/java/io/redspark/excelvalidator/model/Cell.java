package io.redspark.excelvalidator.model;

import io.redspark.excelvalidator.model.fields.common.Validator;

import java.util.List;

public interface Cell {

    String getName();
    String getValue();
    int getIndex();
    boolean validate();
    boolean validateAndMarkError();
    void setValue(String value);
    String getMessage();
    Boolean getError();
    void tintError();
    List<Validator> getValidators();
    void setRequired(Boolean required);


}
