package io.redspark.excelvalidator.model.fields.ddd;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class DDDValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;

        if(!ValidationUtils.isValidDDD(value)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_DDD_INVALID.getText();
        }

        return this;
    }

}
