package io.redspark.excelvalidator.model.fields.status;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class StatusValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;

        if(!ValidationUtils.isValidStatus(value)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_STATUS_INVALID.getText();
        }

        return this;
    }

}
