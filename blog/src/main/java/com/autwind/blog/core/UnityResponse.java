package com.autwind.blog.core;

public class UnityResponse {
    private Integer code;
    private String massage;
    private String url;

    public UnityResponse(Integer code, String massage, String url) {
        this.code = code;
        this.massage = massage;
        this.url = url;
    }
    public UnityResponse(){

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
