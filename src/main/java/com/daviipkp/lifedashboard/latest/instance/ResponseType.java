package com.daviipkp.lifedashboard.latest.instance;

public enum ResponseType {

    ERROR("error"),
    SUCCESS("success"),
    WARNING("warning");

    private String type;

    ResponseType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
