package io.redspark.excelvalidator.model.fields.genre;

import io.redspark.excelvalidator.model.fields.common.BaseCell;

import java.util.Arrays;


public class GenreColumn extends BaseCell {

    public GenreColumn() {
        super(Arrays.asList(new GenreValidator()));
    }

}
