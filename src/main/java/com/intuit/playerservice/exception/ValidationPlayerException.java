package com.intuit.playerservice.exception;

public class ValidationPlayerException extends Exception{

    public ValidationPlayerException (String msg,Exception e){
        super(msg,e);
    }
}
