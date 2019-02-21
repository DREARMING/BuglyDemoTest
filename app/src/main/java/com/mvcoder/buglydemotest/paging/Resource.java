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

    public static <T> Resource<T> loading(String msg, T data){
        return new Resource(Status.LOADING, data,  msg);
    }

    public static <T> Resource<T> success(T data){
        return new Resource(Status.SUCCESS, data,  null);
    }

    public static <T> Resource<T> error(String msg, T data){
        return new Resource(Status.ERROR, data,  msg);
    }


    enum Status{
        SUCCESS, ERROR, LOADING
    }

}
