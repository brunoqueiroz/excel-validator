package io.redspark.excelvalidator.model.fields.money;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class MoneyValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;

        if(!ValidationUtils.isValidMoneyValue(value)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_MONEY_INVALID.getText();
        }

        return this;
    }

}
