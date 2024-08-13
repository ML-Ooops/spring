package org.example.mlooops.service;

public class ResponseData {
    private String result;

    public ResponseData(String result){
        this.result=result;
    }

    // Getters and Setters
    public String getOutput() {
        return result;
    }

    public void setOutput(String result) {
        this.result=result;
    }
}