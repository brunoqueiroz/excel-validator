package io.redspark.excelvalidator.model.fields.ddd;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class DDDColumn extends BaseCell {

    public DDDColumn() {
        super(Arrays.asList(new DDDValidator()));
    }

}
