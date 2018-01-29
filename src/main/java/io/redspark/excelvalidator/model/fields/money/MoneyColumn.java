package io.redspark.excelvalidator.model.fields.money;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class MoneyColumn extends BaseCell {

    public MoneyColumn() {
        super(Arrays.asList(new MoneyValidator()));
    }

}
