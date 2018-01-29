package io.redspark.excelvalidator.model.fields.mobilenumber;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class MobileNumberBrazilColumn extends BaseCell {

    public MobileNumberBrazilColumn() {
        super(Arrays.asList(new MobileNumberBrazilValidator()));
    }

}
