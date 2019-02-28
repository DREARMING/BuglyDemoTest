package com.mvcoder.buglydemotest.paging;

public class Resource<T> {

    private Status status;

    private String message;

    private T data;

    public Resource(Status status, T data,  String msg) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    static <T> Resource<T> loading(String msg, T data){
        return new Resource<>(Status.LOADING, data,  msg);
    }

    static <T> Resource<T> success(T data){
        return new Resource<>(Status.SUCCESS, data,  null);
    }

    public static <T> Resource<T> error(String msg, T data){
        return new Resource<>(Status.ERROR, data,  msg);
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum Status{
        SUCCESS, ERROR, LOADING
    }

}
