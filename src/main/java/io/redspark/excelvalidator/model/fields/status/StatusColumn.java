package io.redspark.excelvalidator.model.fields.status;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class StatusColumn extends BaseCell {

    public StatusColumn() {
        super(Arrays.asList(new StatusValidator()));
    }

}
