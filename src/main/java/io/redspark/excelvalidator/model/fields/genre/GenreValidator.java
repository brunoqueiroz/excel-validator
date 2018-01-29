package io.redspark.excelvalidator.model.fields.genre;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
public class GenreValidator implements Validator{

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {
        this.error = false;
        if(StringUtils.isEmpty(value) ||
                value.length() > 1  ||
                !StringUtils.containsAny(value, "M", "F", "I")){
            this.error = true;
            this.message = EnumValidations.MESSAGE_GENERO_INVALID.getText();
        }
        return this;
    }

}
