package io.redspark.excelvalidator.model.fields.common;

public interface Validator {

    Validator execute(String value);
    Boolean getError();
    String getMessage();


}
