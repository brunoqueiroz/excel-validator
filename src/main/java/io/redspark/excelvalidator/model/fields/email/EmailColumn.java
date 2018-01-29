package io.redspark.excelvalidator.model.fields.email;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class EmailColumn extends BaseCell {

    public EmailColumn() {
        super(Arrays.asList(new EmailFormatValidator()));
    }

}
