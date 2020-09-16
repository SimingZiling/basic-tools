package org.yiming.networktools.response;

import java.io.Serializable;

/**
 * @author ：紫灵
 * @date ：Created in 2020/1/2 下午3:13
 * @modified ：回应数据
 */
public class ResponseData<T> implements Serializable {
    public ResponseData() {
    }

    private int code;//返回状态
    private T data;//返回数据
    private String msg;//返回信息

    public ResponseData(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public ResponseData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseData(ResponseCode reponseCode, String msg) {
        this.code = reponseCode.getCode();
        this.msg = msg;
    }

    public ResponseData(ResponseCode reponseCode, String msg, T data) {
        this.code = reponseCode.getCode();
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseData<T> success(String msg, T data){
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
        return this;
    }

//    public ResponseData<T> success(String msg){
//        return this.success(msg,null);
//    }

    public static ResponseData success(String msg){
        return new ResponseData().success(msg,null);
    }



//    public ResponseData<T> error(String msg){
//        return this.error(msg,null);
//    }

    public static ResponseData  error(String msg){
        return new ResponseData().error(msg,null);
    }

    public ResponseData<T> error(String msg, T data){
        this.code = ResponseCode.ERROR.getCode();
        this.msg = msg;
        this.data = data;
        return this;
    }

    public ResponseData<T> exception(String msg, T data){
        this.code = ResponseCode.EXCEPTION.getCode();
        this.msg = msg;
        this.data = data;
        return this;
    }

    public static ResponseData exception(String msg){
        return new ResponseData().exception(msg,null);
    }
}
