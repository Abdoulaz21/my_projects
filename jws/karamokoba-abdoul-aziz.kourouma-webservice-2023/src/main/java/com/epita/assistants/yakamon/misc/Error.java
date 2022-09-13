package com.epita.assistants.yakamon.misc;

public enum Error {
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "Object not found"),
    DELETION_FAILED("DELETION_FAILED", "Deletion failed"),
    UPDATE_FAILED("UPDATE_FAILED", "Update failed"),
    CREATION_FAILED("CREATION_FAILED", "Object creation failed"),
    BAD_PARAMETER("BAD_PARAMETER", "Bad parameter"),
    BAD_BODY("BAD_BODY", "Bad body");

    private final String errorCode;
    private final String errorMessage;

    private Error(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
