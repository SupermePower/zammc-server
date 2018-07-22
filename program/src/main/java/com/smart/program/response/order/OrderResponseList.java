package com.smart.program.response.order;

import java.util.List;

public class OrderResponseList {

    private List<OrderResponse> pay;

    private List<OrderResponse> finish;

    private List<OrderResponse> cancel;

    public OrderResponseList() {
    }

    public List<OrderResponse> getPay() {
        return pay;
    }

    public void setPay(List<OrderResponse> pay) {
        this.pay = pay;
    }

    public List<OrderResponse> getFinish() {
        return finish;
    }

    public void setFinish(List<OrderResponse> finish) {
        this.finish = finish;
    }

    public List<OrderResponse> getCancel() {
        return cancel;
    }

    public void setCancel(List<OrderResponse> cancel) {
        this.cancel = cancel;
    }
}
