package org.yiming.networktools.response;

/**
 * @author ：紫灵
 * @date ：Created in 2020/1/2 下午3:17
 * @modified ：
 */
public enum ResponseCode {

    SUCCESS(0,"成功"),
    ERROR(1,"请求错误"),
    ABNORMAL_PERMISSIONS(3,"权限错误"),
    NOT_LOGGED_IN(31,"未登录"),
    EXCEPTION(2,"异常"),
    UNAUTHORIZED(20,"（未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。 "),;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
