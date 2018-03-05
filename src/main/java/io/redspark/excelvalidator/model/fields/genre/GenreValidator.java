package io.redspark.excelvalidator.model.fields.genre;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.fields.common.Validator;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
public class GenreValidator implements Validator{

    public static final String MALE = "M";
    public static final String FEMALE = "F";
    public static final String INDIFFERENT = "I";

    private Boolean error;
    private String message;

    @Override
    public Validator execute(String value) {

        String genre = value.trim();

        this.error = false;
        if(StringUtils.isEmpty(genre) ||
                genre.length() > 1  ||
                !StringUtils.containsAny(genre, MALE, FEMALE, INDIFFERENT)){
            this.error = true;
            this.message = EnumValidations.MESSAGE_GENERO_INVALID.getText();
        }
        return this;
    }

}
