package com.smart.program.response;

public class ResponseVO<T> {

    private int dealCode = 200;
    private String dealMsg;
    private T dealResult;

    public ResponseVO() {

    }

    public ResponseVO setResult(int dealCode, String dealMsg) {
        this.dealCode = dealCode;
        this.dealMsg = dealMsg;
        return this;
    }

    public ResponseVO<T> setResult(int dealCode, String dealMsg, T dealResult) {
        this.dealCode = dealCode;
        this.dealMsg = dealMsg;
        this.dealResult = dealResult;
        return this;
    }

    public int getDealCode() {
        return dealCode;
    }

    public void setDealCode(int dealCode) {
        this.dealCode = dealCode;
    }

    public String getDealMsg() {
        return dealMsg;
    }

    public void setDealMsg(String dealMsg) {
        this.dealMsg = dealMsg;
    }

    public T getDealResult() {
        return dealResult;
    }

    public void setDealResult(T dealResult) {
        this.dealResult = dealResult;
    }
}
