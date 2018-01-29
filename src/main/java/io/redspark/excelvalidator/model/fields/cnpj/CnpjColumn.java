package io.redspark.excelvalidator.model.fields.cnpj;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class CnpjColumn extends BaseCell {

    public CnpjColumn() {
        super(Arrays.asList(new CnpjNumberValidator()));
    }

}
