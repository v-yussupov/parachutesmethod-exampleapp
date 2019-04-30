package org.parachutesmethod.models;

public class RequestPOJO {
    String request;

    public RequestPOJO() {
    }

    public RequestPOJO(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}