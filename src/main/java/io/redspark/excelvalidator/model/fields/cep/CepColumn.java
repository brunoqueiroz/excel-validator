package io.redspark.excelvalidator.model.fields.cep;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class CepColumn extends BaseCell {

    public CepColumn() {
        super(Arrays.asList(new CepValidator()));
    }

}
