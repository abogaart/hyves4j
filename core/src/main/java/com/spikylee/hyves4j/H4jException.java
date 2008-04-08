package com.spikylee.hyves4j;

public class H4jException extends Exception {
  
    private static final long serialVersionUID = 7958091410349084831L;
    
    private String errorCode;
    private String errorMessage;

    public H4jException(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public H4jException(String string, Exception e) {
        super(string, e);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
