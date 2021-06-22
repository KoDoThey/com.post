package com.vcc.internship.restful;


public class ClientException extends Exception {
    private static final long serialVersionUID = 1L;

    public ClientException() {
        super();
    }
    public ClientException(String msg)   {
        super(msg);
    }
    public ClientException(String msg, Exception e)  {
        super(msg, e);
    }
}