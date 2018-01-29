package io.redspark.excelvalidator.model.fields.cpf;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class CpfColumn extends BaseCell {

    public CpfColumn() {
        super(Arrays.asList(new CpfNumberValidator()));
    }

}
