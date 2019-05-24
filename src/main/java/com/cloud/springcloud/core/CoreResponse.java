package com.cloud.springcloud.core;

import java.io.Serializable;

/**
 * Response返回类 对象 ，支持泛型传入
 * @param <T>
 * @Auther lubing
 */
public class CoreResponse<T> extends  BaseResponse implements Serializable  {
    private  int   code  ;
    private  String  message ;

    private  Object  object  ;
    private  T  t  ;

    public CoreResponse(){ };


    public CoreResponse(int code, String message, Object object, T t) {
        this.message = message;
        this.code = code;
        this.object = object;
        this.t = t;
    }

    public CoreResponse(int code, String message, Object object) {
        this.message = message;
        this.code = code;
        this.object = object;
    }

    public CoreResponse(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "CoreResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", object=" + object +
                ", t=" + t +
                '}';
    }
}
