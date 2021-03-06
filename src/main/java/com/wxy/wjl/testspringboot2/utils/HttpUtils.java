package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class HttpUtils {

    public static HttpClient login(String operNo, String operPwd) throws Exception{
        CloseableHttpResponse response=null;
        CloseableHttpClient httpclient=null;
        //此处需要使用自己的登陆url
        HttpPost httpPost=new HttpPost("loginUrl");
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        //设置JSON参数
        JSONObject params=new JSONObject();
        params.put("oper_no",operNo);
        params.put("oper_pwd",operPwd);
        StringEntity stringEntity=new StringEntity(params.toString(),"UTF-8");
        httpPost.setEntity(stringEntity);
        try{
            CookieStore cookieStore=new BasicCookieStore();
            httpclient= HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            response=httpclient.execute(httpPost);
            //获取状态码
            int statusCode=response.getStatusLine().getStatusCode();
            //返回非200，抛出异常错误
            if(HttpStatus.SC_OK != statusCode){
                throw new Exception("通讯异常，状态码非200，而为"+statusCode);
            }
        }catch (Exception e){
            throw e;
        }
        return httpclient;
    }




    //发送post请求，使用模拟登陆后的httpClient
    public static String doPost(HttpClient httpClient, String params, String url) throws Exception{
        String httpRsp;
        CloseableHttpClient httpclient=(CloseableHttpClient) httpClient;
        CloseableHttpResponse response=null;
        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        StringEntity stringEntity=new StringEntity(params,"UTF-8");
        httpPost.setEntity(stringEntity);
        HttpEntity entity=null;
        try{
            response =httpclient.execute(httpPost);
            //获取状态码
            int statusCode=response.getStatusLine().getStatusCode();
            //返回非200，抛出异常错误
            if(HttpStatus.SC_OK != statusCode){
                throw new Exception("通讯异常，状态码非200，而为"+statusCode);
            }
            entity =response.getEntity();
            //返回内容为空，抛出通讯异常错误
            if(null == entity){
                throw new Exception("通讯异常，返回为空");
            }
            httpRsp= EntityUtils.toString(entity,"UTF-8");
            if(null == httpRsp || "".equals(httpRsp.trim())){
                throw new Exception("通讯异常，返回报文为空");
            }
        }catch (Exception e){
            //其他异常，抛出异常错误
            throw e;
        }finally {
            try{
                if(null != response){
                    //销毁
                    EntityUtils.consume(response.getEntity());
                }
            }catch (IOException e){

            }
        }
        return httpRsp;
    }


    public static String doPost2(String params,String url) throws Exception{
        String httpRsp;
        CloseableHttpClient httpclient= HttpClients.createDefault();
        CloseableHttpResponse response=null;
        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        StringEntity stringEntity=new StringEntity(params,"UTF-8");
        httpPost.setEntity(stringEntity);
        HttpEntity entity=null;
        try{
            response =httpclient.execute(httpPost);
            //获取状态码
            int statusCode=response.getStatusLine().getStatusCode();
            //返回非200，抛出异常错误
            if(HttpStatus.SC_OK != statusCode){
                throw new Exception("通讯异常，状态码非200，而为"+statusCode);
            }
            entity =response.getEntity();
            //返回内容为空，抛出通讯异常错误
            if(null == entity){
                throw new Exception("通讯异常，返回为空");
            }
            System.out.println(entity);
            httpRsp= EntityUtils.toString(entity,"UTF-8");
            if(null == httpRsp || "".equals(httpRsp.trim())){
                throw new Exception("通讯异常，返回报文为空");
            }
        }catch (Exception e){
            //其他异常，抛出异常错误
            throw e;
        }finally {
            try{
                if(null != response){
                    //销毁
                    EntityUtils.consume(response.getEntity());
                }
            }catch (IOException e){

            }
        }
        return httpRsp;
    }

    /**
     * 带有超时时间的http请求 如果超时时间设置0  一直等待返回
     * @param params 请求参数(json格式) JSON.toJsonString
     * @param url   请求url
     * @param timeOut   超时时间 单位：秒
     * @return
     * @throws Exception
     */
    public static String doPostTimeOut(String params,String url,int timeOut) throws Exception{
        String httpRsp;
        CloseableHttpClient httpclient= HttpClients.createDefault();
        CloseableHttpResponse response=null;
        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        StringEntity stringEntity=new StringEntity(params,"UTF-8");
        httpPost.setEntity(stringEntity);
        HttpEntity entity=null;

        // setConnectTimeout 是设置连接到目标 URL 的等待时长，超过这个时间还没连上就抛出连接超时；
        //setConnectionRequestTimeout 是从connect Manager（连接池）获取连接的等待时长，这个版本是共享连接池的；
        //setSocketTimeout 是连接到目标URL 之后等待返回响应的时长，即超过这个时间就放弃本次调用并抛出 SocketTimeoutException:Read Time Out,实际时等待数据包的间隔
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeOut*1000)
                .setSocketTimeout(timeOut*1000)
                .setConnectionRequestTimeout(timeOut*1000)
                .build();
        httpPost.setConfig(requestConfig);
        try{
            response =httpclient.execute(httpPost);
            //获取状态码
            int statusCode=response.getStatusLine().getStatusCode();
            //返回非200，抛出异常错误
            if(HttpStatus.SC_OK != statusCode){
                throw new Exception("通讯异常，状态码非200，而为"+statusCode);
            }
            entity =response.getEntity();
            //返回内容为空，抛出通讯异常错误
            if(null == entity){
                throw new Exception("通讯异常，返回为空");
            }
            System.out.println(entity);
            httpRsp= EntityUtils.toString(entity,"UTF-8");
            if(null == httpRsp || "".equals(httpRsp.trim())){
                throw new Exception("通讯异常，返回报文为空");
            }
        }catch (SocketTimeoutException e){
            System.out.println("接收数据超时");
            throw e;
        }catch (ConnectTimeoutException e){
            System.out.println("连接超时");
            throw e;
        } catch (Exception e){
            //其他异常，抛出异常错误
            throw e;
        }finally {
            try{
                if(null != response){
                    //销毁
                    EntityUtils.consume(response.getEntity());
                }
            }catch (IOException e){

            }
        }
        return httpRsp;
    }


    /**
     * 处理字节流
     * @param params
     * @param url
     * @return
     * @throws Exception
     */
    public static String doPost3(String params,String url) throws Exception{
        String httpRsp;
        byte[] byteData;
        CloseableHttpClient httpclient= HttpClients.createDefault();
        CloseableHttpResponse response=null;
        HttpPost httpPost=new HttpPost(url);
        //如果是纯文本  使用text/xml
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        StringEntity stringEntity=new StringEntity(params,"UTF-8");
        httpPost.setEntity(stringEntity);
        HttpEntity entity=null;
        try{
            response =httpclient.execute(httpPost);
            //获取状态码
            int statusCode=response.getStatusLine().getStatusCode();
            //返回非200，抛出异常错误
            if(HttpStatus.SC_OK != statusCode){
                throw new Exception("通讯异常，状态码非200，而为"+statusCode);
            }
            entity =response.getEntity();
            //返回内容为空，抛出通讯异常错误
            if(null == entity){
                throw new Exception("通讯异常，返回为空");
            }
            System.out.println(entity);
            httpRsp= EntityUtils.toString(entity,"UTF-8");
             byteData = EntityUtils.toByteArray(response.getEntity());//拿到返回结果字节流

            if(null == httpRsp || "".equals(httpRsp.trim())){
                throw new Exception("通讯异常，返回报文为空");
            }
        }catch (Exception e){
            //其他异常，抛出异常错误
            throw e;
        }finally {
            try{
                if(null != response){
                    //销毁
                    EntityUtils.consume(response.getEntity());
                }
            }catch (IOException e){

            }
        }
        return httpRsp;
    }



}
