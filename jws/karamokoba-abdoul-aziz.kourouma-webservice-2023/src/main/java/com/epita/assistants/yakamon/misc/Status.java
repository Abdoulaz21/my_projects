package com.epita.assistants.yakamon.misc;

public enum Status {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    final private String status;
    private Status(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
