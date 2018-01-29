package io.redspark.excelvalidator.model.fields.email;

import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class EmailFormatValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;
        if (!ValidationUtils.validEmail(value)) {

            this.message = EnumValidations.MESSAGE_EMAIL_INVALID.getText();
            this.error = true;

        }
        return this;
    }

}
