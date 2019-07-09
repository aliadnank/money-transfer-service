package com.challenge.rest;

public class Response<T> {

    private T model;
    private Status status;
    private String message;

    public Response() {
    }

    public Response(Status status, T model, String message) {
        this.model = model;
        this.status = status;
        this.message = message;
    }


    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum Status {
        SUCCESS(100), FAILED(102);

        private int code;

       Status(int code) {
           this.code = code;
       }

       public int getCode() {
           return code;
       }
   }

    @Override
    public String toString() {
        return "Response{" +
                "model=" + model +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
