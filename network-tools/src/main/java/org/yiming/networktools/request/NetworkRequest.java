package org.yiming.networktools.request;


import org.yiming.localtools.basicinfo.Charact;
import org.yiming.localtools.conversion.MapUtil;
import org.yiming.networktools.exception.NetworkToolsException;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * 网络请求
 */
public class NetworkRequest {

    public NetworkRequest(){}

    public NetworkRequest(NetworkRequest networkRequest){
        this.url = networkRequest.url;
        this.charact = networkRequest.charact;
        this.headerMap = networkRequest.headerMap;
        this.requestParam = networkRequest.requestParam;
        this.requestMethod = networkRequest.requestMethod;
        this.connectTimeout = networkRequest.connectTimeout;
        this.readTimeout = networkRequest.readTimeout;
        this.sslProtocol = networkRequest.sslProtocol;
        this.requestBody = networkRequest.requestBody;
    }

    private URL url;

    private Map<String,String> headerMap;

    private String requestParam;

    private String requestBody;

//    private Map<String,String> requestParam;
    private RequestMethod requestMethod;

    private Integer connectTimeout;// 连接超时时间

    private Integer readTimeout;// 返回超时时间

    private Charact charact;// 字符集

    private SSLProtocol sslProtocol;// ssl协议

    public URL getUrl() {
        return url;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public Charact getCharact() {
        return charact;
    }

    public SSLProtocol getSslProtocol() {
        return sslProtocol;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public static class Builder{
        private NetworkRequest networkRequest;

        public Builder(){
            networkRequest = new NetworkRequest();
        }

        public Builder addRequestUrl(String requestUrl) throws MalformedURLException {
            networkRequest.url  = new URL(requestUrl);
            return this;
        }

        public Builder addHeaderMap(Map<String,String> headerMap) {
            networkRequest.headerMap = headerMap;
            return this;
        }

        public Builder addRequestParam(String requestParam) {
            networkRequest.requestParam = requestParam;
            return this;
        }

        public Builder addRequestParam(Map<String,Object> requestParam) {
            networkRequest.requestParam = new MapUtil().mapToString(requestParam,"=","&");
            return this;
        }

        public Builder addRequestMethod(RequestMethod requestMethod) {
            networkRequest.requestMethod = requestMethod;
            return this;
        }

        public Builder addConnectTimeout(Integer connectTimeout) {
            networkRequest.connectTimeout = connectTimeout;
            return this;
        }

        public Builder addReadTimeout(Integer readTimeout) {
            networkRequest.readTimeout = readTimeout;
            return this;
        }

        public Builder addCharact(Charact charact) {
            networkRequest.charact = charact;
            return this;
        }

        public Builder addSslProtocol(SSLProtocol sslProtocol) {
            networkRequest.sslProtocol = sslProtocol;
            return this;
        }

        public Builder addRequestBody(String requestBody){
            networkRequest.requestBody = requestBody;
            return this;
        }

        public NetworkRequest build() throws NetworkToolsException {
            if(networkRequest.url == null){
                throw new NetworkToolsException("请求地址不能为空！");
            }
            if(networkRequest.requestMethod == null){
                networkRequest.requestMethod = RequestMethod.GET;
            }
            if(networkRequest.connectTimeout == null){
                networkRequest.connectTimeout = 5000;
            }
            if(networkRequest.readTimeout == null){
                networkRequest.readTimeout = 5000;
            }
            if(networkRequest.charact == null){
                networkRequest.charact = Charact.UTF8;
            }
            if(networkRequest.sslProtocol == null){
                networkRequest.sslProtocol = SSLProtocol.TLS;
            }
            return new NetworkRequest(networkRequest);
        }
    }

    public String sendRequest() throws NetworkToolsException {
//        URL url = new URL(requestUrl);
        if(url.getProtocol().equals("http")){
            return new HttpRequest().send();
        }else if(url.getProtocol().equals("https")){
            return new HttpsRequest().send();
        }
        throw new NetworkToolsException("不支持该请求协议！");
    }

    public class HttpRequest{
        public String send(){

//            System.out.println(NetworkRequest.this.url);
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            OutputStream outputStream = null;
            String result = null;

            try {
                // 建立远程请求url连接对象
//                URL url = new URL(requestUrl);
                connection = (HttpURLConnection) url.openConnection();
                // 设置请求方式
                connection.setRequestMethod(requestMethod.getRequestMethod());
                // 设置连接远程主机超时时间
                connection.setConnectTimeout(connectTimeout);
                //设置读取远程返回数据时间
                connection.setReadTimeout(readTimeout);

                if(requestMethod.equals(RequestMethod.POST)){
                    //默认值为false，当向远程服务器传数据/写数据时，需要设置为true
                    connection.setDoOutput(true);
                    //默认值为true，当向远程服务器读取数据时，设置为true，可有可无
                    connection.setDoInput(true);
                    if(requestParam != null && !requestParam.equals("")) {
                        // 通过连接对象获取一个输出流
                        OutputStream os = connection.getOutputStream();
                        //参数是键值队  , 不以"?"开始
                        os.write(requestParam.getBytes());
                        os.flush();
                    }
                }
                // 设置请求头
                if (headerMap != null && headerMap.size() > 0){
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }

                // 发送请求
                connection.connect();

                if (connection.getResponseCode() != HTTP_OK) {
//                    inputStream = connection.getErrorStream();
                    throw new NetworkToolsException("请求错误！错误代码：" + connection.getResponseCode());
                }
                // 通过connection连接获取输入流
                inputStream = connection.getInputStream();
                // 封装输入流，并指定字符集
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charact.getName()));
                // 存放数据
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                result = stringBuilder.toString();
            }catch (IOException | NetworkToolsException e){
                e.printStackTrace();
            }finally {
                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(connection != null){
                    connection.disconnect();
                }
            }
            return result;
        }
    }

    public class HttpsRequest{
        public String send(){
            HttpsURLConnection connection = null;
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            OutputStream outputStream = null;
            String result = null;
            try {

                SSLContext sslContext = SSLContext.getInstance(sslProtocol.getProtocol());
                // SSLContext初始化
                sslContext.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                // 建立远程请求url连接对象
//                URL url = new URL(requestUrl);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(sslSocketFactory);
                // 设置请求方式
                connection.setRequestMethod(requestMethod.getRequestMethod());
                // 设置连接远程主机超时时间
                connection.setConnectTimeout(connectTimeout);
                //设置读取远程返回数据时间
                connection.setReadTimeout(readTimeout);




                // 设置请求头
                if (headerMap != null && headerMap.size() > 0){
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        connection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }

                if(requestMethod.equals(RequestMethod.POST)){
                    //默认值为false，当向远程服务器传数据/写数据时，需要设置为true
                    connection.setDoOutput(true);
                    //默认值为true，当向远程服务器读取数据时，设置为true，可有可无
                    connection.setDoInput(true);
//                    if(requestParam != null && !requestParam.equals("")) {
//                        // 通过连接对象获取一个输出流
//                        OutputStream os = connection.getOutputStream();
//                        //参数是键值队  , 不以"?"开始
//                        os.write(requestParam.getBytes());
//                        os.flush();
//                    }
                    if (requestBody != null && !requestBody.equals("")) {
                        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                        dataOutputStream.write(requestBody.getBytes());
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    }
                }

                // 发送请求
                connection.connect();
//                System.out.println("+"+connection.getHeaderField( "location" ));
                if (connection.getResponseCode() != HTTP_OK) {
//                    inputStream = connection.getErrorStream();
                    throw new NetworkToolsException("请求错误！错误代码：" + connection.getResponseCode());
                }
                // 通过connection连接获取输入流
                inputStream = connection.getInputStream();
                // 封装输入流，并指定字符集
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charact.getName()));
                // 存放数据
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                }
                result = stringBuilder.toString();

            } catch (NoSuchAlgorithmException | IOException | KeyManagementException | NetworkToolsException e) {
                e.printStackTrace();
            }finally {
                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(connection != null){
                    connection.disconnect();
                }
            }
            return result;
        }

        /**
         * 默认信任管理器
         */
        private final class DefaultTrustManager implements X509TrustManager {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }
    }
}
