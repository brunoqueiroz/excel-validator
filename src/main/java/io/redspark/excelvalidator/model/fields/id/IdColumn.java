package io.redspark.excelvalidator.model.fields.id;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class IdColumn extends BaseCell {

    public IdColumn() {
        super(Arrays.asList(new NumberValidator()));
    }

}
