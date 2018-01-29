package io.redspark.excelvalidator.model.fields.id;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class NumberValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;

        if(!StringUtils.isNumeric(value)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_ID_NOT_NUMERIC.getText();
        }

        return this;
    }

}
