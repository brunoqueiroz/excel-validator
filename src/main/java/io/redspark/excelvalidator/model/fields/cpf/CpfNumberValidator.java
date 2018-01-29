package io.redspark.excelvalidator.model.fields.cpf;

import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class CpfNumberValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;
        if (!ValidationUtils.validCPF(value)) {
            this.message = EnumValidations.MESSAGE_CPF_INVALID.getText();
            this.error = true;
        }
        return this;
    }

}
