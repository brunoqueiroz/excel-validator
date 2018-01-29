package io.redspark.excelvalidator.model.fields.birthday;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class BirthdayColumn extends BaseCell {

    public BirthdayColumn() {
        super(Arrays.asList(new BirthdayValidator()));
    }

}
