package com.smart.program.common;

public class HttpResult {
    private int statusCode;
    private String content;

    public HttpResult() {
    }

    public HttpResult(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isOk() {
        return statusCode >= 200 && statusCode < 300;
    }
}