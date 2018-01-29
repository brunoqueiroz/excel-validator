package io.redspark.excelvalidator.model.fields.common;

import io.redspark.excelvalidator.enums.EnumValidations;
import io.redspark.excelvalidator.model.Cell;
import io.redspark.excelvalidator.model.Line;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class BaseCell implements Cell {

    protected List<Validator> validators;
    protected Line line;
    protected String value;
    protected Boolean error;
    protected String message;
    protected int index;
    protected Boolean required;
    protected String name;

    @Override
    public String getName() {
        String headerName = "";
        if(this.line.getTable().getAdapter().hasHeader()) {
            headerName = this.line.getTable().getAdapter().getHeaderNameOf(index);

        }else{
            headerName = this.getClass().getSimpleName().replace("Column", "").toLowerCase();
        }
        return headerName;
    }

    public BaseCell(List<Validator> validators) {
        this.validators = validators;
    }

    public void tintError(){
        this.line.getTable().getAdapter().cellTint(line, this.line.getTable().getAdapter().getCellStyle(), this.index);
    }

    public boolean validate() {
        return this.validators.stream().filter(v -> v.execute(this.value).getError()).count() == 0;
    }

    public boolean validateAndMarkError(){
        boolean validate = false;

       if(this.required && (this.value == null || this.value.isEmpty())) {
           String erro = String.format(EnumValidations.FIELD_REQUIRED.getText(), this.getName());
           this.error = true;
           this.message = erro;
           line.markLineWithError(erro);
       }else if(validate()){
           this.error = false;
           validate = true;
       }else{
            String errors = this.validators.stream().filter(validator -> validator.getError()).map(validator1 -> validator1.getMessage()).collect(Collectors.joining(" / "));
            this.error = true;
            this.message = errors;
            line.markLineWithError(errors);
       }
       return validate;
    }



}
