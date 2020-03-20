package org.yiming.networktools.response;

/**
 * @author ：紫灵
 * @date ：Created in 2020/1/2 下午3:13
 * @modified ：回应数据
 */
public class ResponseDate<T> {

    private int code;//返回状态
    private T data;//返回数据
    private String msg;//返回信息

    public ResponseDate(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public ResponseDate(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseDate(ResponseCode reponseCode, String msg) {
        this.code = reponseCode.getCode();
        this.msg = msg;
    }

    public ResponseDate(ResponseCode reponseCode, String msg, T data) {
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
}
