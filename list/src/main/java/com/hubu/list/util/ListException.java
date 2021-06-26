package com.hubu.list.util;

public class ListException extends RuntimeException{
    private String message;
    public ListException(String message){
        super(message);
        this.message=message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
