package io.redspark.excelvalidator.enums;

public enum EnumValidations {

    MESSAGE_ID_NOT_NUMERIC("O ID deve ser um número"),
    MESSAGE_CPF_INVALID("CPF inválido"),
    MESSAGE_GENERO_INVALID("O gênero deve ser M, F ou I"),
    MESSAGE_DATA_NASCIMENTO_INVALID("o formato da data deve ser ddmmyyyy ou dd/mm/yyyy"),
    MESSAGE_CEP_INVALID("CEP inválido"),
    MESSAGE_MONEY_INVALID("Valor inválido"),
    MESSAGE_EMAIL_INVALID("EMAIL inválido"),
    MESSAGE_DDD_INVALID("DDD inválido"),
    MESSAGE_MOBILE_INVALID("Número de celular inválido"),
    MESSAGE_CNPJ_INVALID("CNPJ inválido"),
    MESSAGE_STATUS_INVALID("Status inválido"),
    DETAIL("Detalhes: "),
    FIELD_REQUIRED("O campo %s é requerido"),
    CELL_ERROR("#Error");

    String text;
    
    EnumValidations(String t) {
        text = t;
    }
    
    public String getText() {
        return this.text;
    }
}