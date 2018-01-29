package io.redspark.excelvalidator.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

public class ValidationUtils {

    public static boolean validEmail(String emailString) {
        if(emailString != null && !emailString.isEmpty()) {
            return EmailValidator.getInstance().isValid(emailString);
        }
        return false;
    }
    
    public static boolean validCPF(String cpfString) {
        if(cpfString != null && !cpfString.isEmpty()) {
            CPFValidator cpfValidator = new CPFValidator();
            return cpfValidator.isEligible(cpfString);
        }
        return false;
    }
    
    public static boolean validCNPJ(String cnpjString) {
        if(cnpjString != null && !cnpjString.isEmpty()) {
            CNPJValidator cnpjValidator = new CNPJValidator();
            return cnpjValidator.isEligible(removeSpecialCharacters(cnpjString));
        }
        return false;
    }

    public static boolean isThisDateValid(String dateToValidate){
        if(dateToValidate == null){
            return false;
        }
        String onlyDigitsDate = removeSpecialCharacters(dateToValidate);

        if(onlyDigitsDate.length() > 8){
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(onlyDigitsDate);
            System.out.println(onlyDigitsDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidDDD(String ddd){

        int dddNumber = 0;

        if(StringUtils.isEmpty(ddd)) {
            return false;
        }

        try {
            dddNumber = Integer.parseInt(ddd);

        }catch (NumberFormatException e){
            return false;
        }

        return dddNumber > 0 && dddNumber <= 99;
    }

    public static boolean isValidCellNumber(String cellNumber){

        if(StringUtils.isEmpty(cellNumber)){
            return false;
        }

        return cellNumber.matches("^9[1-9][0-9]{7}$");

    }

    public static boolean isValidStatus(String status){

        if(StringUtils.isEmpty(status)){
            return false;
        }

        return status.matches("^[0-1]$");

    }

    public static boolean isValidCEP(String cep){
        String cepOnlyDigits = removeSpecialCharacters(cep);
        return !StringUtils.isEmpty(cepOnlyDigits) && cepOnlyDigits.length() == 8;
    }

    public static boolean isValidMoneyValue(String money){
        String result = removeCaractersForMoney(money);
        return !StringUtils.isEmpty(result) && StringUtils.split(result, ".").length < 3;
    }
    
    public static String removeSpecialCharacters(String valor) {
        if(StringUtils.isEmpty(valor)){
            return "";
        }
        return valor.replaceAll("[^0-9]", "");
    }

    public static String removeCaractersForMoney(String valor) {
        if(StringUtils.isEmpty(valor)){
            return "";
        }
        String result = "";
        result = valor.replaceAll("[^0-9.,]", "");
        result = result.replace(".", "").replace(",", ".");

        return result;
    }

}
