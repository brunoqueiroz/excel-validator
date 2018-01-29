package io.redspark.excelvalidator.model.fields.mobilenumber;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class MobileNumberBrazilValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;

        if(!ValidationUtils.isValidCellNumber(value)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_MOBILE_INVALID.getText();
        }

        return this;
    }

}
