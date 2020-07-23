package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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

    public static String doPostTimeOut(String params,String url,int timeOut) throws Exception{
        String httpRsp;
        CloseableHttpClient httpclient= HttpClients.createDefault();
        CloseableHttpResponse response=null;
        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        StringEntity stringEntity=new StringEntity(params,"UTF-8");
        httpPost.setEntity(stringEntity);
        HttpEntity entity=null;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeOut)
                .setSocketTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
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
