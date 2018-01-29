package io.redspark.excelvalidator.model.fields.cep;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import io.redspark.excelvalidator.utils.ValidationUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class CepValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;

        if(!ValidationUtils.isValidCEP(value)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_CEP_INVALID.getText();
        }

        return this;
    }

}
