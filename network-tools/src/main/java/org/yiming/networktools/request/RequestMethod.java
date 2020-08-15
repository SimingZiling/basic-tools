package org.yiming.networktools.request;

/**
 * 请求方法
 */
public enum  RequestMethod {

    POST("POST"),GET("GET");

    private String requestMethod;

    public String getRequestMethod() {
        return requestMethod;
    }

    RequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
