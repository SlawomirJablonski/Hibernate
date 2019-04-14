package com.kodilla.hibernate.manytomany.facade;

public class SearchingException extends Exception{

    public static String ERR_COMPANY_NOT_FOUND = "The searched company can not be found.";
    public static String ERR_EMPLOYEE_NOT_FOUND = "The searched employee can not be found.";

    public SearchingException(String message){
        super(message);
    }
}
