package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {


    /**
     * 根据正则表达式提取字符串
     * @return
     */
    public static String getMatcherString(String regex, String source){
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        try{
            while (matcher.find()) {
                result = matcher.group(0);//只取第一组
            }
        }catch (Exception e){
            result="";
        }
        return result;
    }


    public static void main(String[] args) {
        String regex="<inb:StatusCode>[0-9A-Z]*</inb:StatusCode>";
        //String regex="StatusCode";
        String source="{\n" +
                "\t\"commFail\":true,\n" +
                "\t\"checkDt\":\"\",\n" +
                "\t\"authorizeDt\":\"\",\n" +
                "\t\"sendError\":true,\n" +
                "\t\"ptrId\":\"SFC\",\n" +
                "\t\"transactionRefId\":\"1\",\n" +
                "\t\"creditLimitStatus\":\"Active\",\n" +
                "\t\"checker\":\"\",\n" +
                "\t\"transactionTime\":\"20200827042744\",\n" +
                "\t\"requestRefId\":\"1\",\n" +
                "\t\"limitRefId\":\"0\",\n" +
                "\t\"inputter\":\"\",\n" +
                "\t\"creditLimit\":\"0\",\n" +
                "\t\"ptrJrnNo\":\"1\",\n" +
                "\t\"mpesaSubscriberId\":\"1\",\n" +
                "\t\"commSucc\":false,\n" +
                "\t\"reqBusNo\":\"\",\n" +
                "\t\"gda\":{\n" +
                "\t\t\"chkFlg\":\"\",\n" +
                "\t\t\"winBegTm\":\"\",\n" +
                "\t\t\"jrnSeq\":\"1\",\n" +
                "\t\t\"txDt\":\"20200827\",\n" +
                "\t\t\"curAcDtFlg\":\"\",\n" +
                "\t\t\"lastAcDt2Flg\":\"\",\n" +
                "\t\t\"canFlg\":\"\",\n" +
                "\t\t\"busTyp\":\"\",\n" +
                "\t\t\"jrnNo\":\"20200827000000000004411006\",\n" +
                "\t\t\"winMod\":\"\",\n" +
                "\t\t\"rvsFlg\":\"\",\n" +
                "\t\t\"nextAcDtFlg\":\"\",\n" +
                "\t\t\"oldJrnNo\":\"\",\n" +
                "\t\t\"busCnl\":\"SFLZ\",\n" +
                "\t\t\"commSts\":\"I\",\n" +
                "\t\t\"msgInf\":\"KYC Check failure with IPRS\",\n" +
                "\t\t\"reqBusNo\":\"\",\n" +
                "\t\t\"curAcDt\":\"20200827\",\n" +
                "\t\t\"inpOpr\":\"\",\n" +
                "\t\t\"txTm\":\"042744\",\n" +
                "\t\t\"winDt2\":\"\",\n" +
                "\t\t\"winDt1\":\"\",\n" +
                "\t\t\"reqId\":\"O!SFLZ!\",\n" +
                "\t\t\"trmId\":\"\",\n" +
                "\t\t\"txCd\":\"kycAuthFuliza\",\n" +
                "\t\t\"success\":false,\n" +
                "\t\t\"failure\":true,\n" +
                "\t\t\"wffFlg\":\"\",\n" +
                "\t\t\"nextAcDt\":\"\",\n" +
                "\t\t\"processing\":false,\n" +
                "\t\t\"winSts\":\"\",\n" +
                "\t\t\"reqJrnNo\":\"\",\n" +
                "\t\t\"commFail\":true,\n" +
                "\t\t\"sendError\":true,\n" +
                "\t\t\"msgTyp\":\"E\",\n" +
                "\t\t\"sysCnl\":\"IAS\",\n" +
                "\t\t\"cnlTxCd\":\"\",\n" +
                "\t\t\"vchNo\":\"1\",\n" +
                "\t\t\"msgDat\":\"IasFulizaKycAuthAction.java:209\",\n" +
                "\t\t\"errSysCnl\":\"\",\n" +
                "\t\t\"lastAcDtFlg\":\"\",\n" +
                "\t\t\"winStsTm\":\"\",\n" +
                "\t\t\"apCd\":\"\",\n" +
                "\t\t\"busKey\":\"{\\\"busOrdNo\\\":\\\"\\\",\\\"nodId\\\":\\\"ias_d_4\\\",\\\"reqBusNo\\\":\\\"test1\\\",\\\"rmk\\\":\\\"\\\",\\\"usrIdCipher\\\":\\\"254711111\\\",\\\"usrIdMask\\\":\\\"\\\",\\\"usrNo\\\":\\\"1100000066728\\\",\\\"vchNo\\\":\\\"1\\\"}\",\n" +
                "\t\t\"commSucc\":false,\n" +
                "\t\t\"commSts1\":\"INIT\",\n" +
                "\t\t\"verNo\":\"1.0.0\",\n" +
                "\t\t\"wffJrnNo\":\"\",\n" +
                "\t\t\"lastAcDt\":\"\",\n" +
                "\t\t\"winEndTm\":\"\",\n" +
                "\t\t\"msgCd\":\"E104\",\n" +
                "\t\t\"txnMod\":\"O\",\n" +
                "\t\t\"lastAcDt2\":\"\",\n" +
                "\t\t\"prdCd\":\"LN001\",\n" +
                "\t\t\"oprId\":\"\",\n" +
                "\t\t\"txTyp\":\"\",\n" +
                "\t\t\"acDt\":\"20200827\"\n" +
                "\t},\n" +
                "\t\"authorizer\":\"\",\n" +
                "\t\"prdGrpCd\":\"1\",\n" +
                "\t\"usrId\":\"254711111\",\n" +
                "\t\"serviceName\":\"OD_OPT_IN_CONFIRMATION\",\n" +
                "\t\"timeStamp\":\"20200827042744\",\n" +
                "\t\"compFlg\":\"\",\n" +
                "\t\"success\":false,\n" +
                "\t\"failure\":true,\n" +
                "\t\"crbStatus\":\"UNKNOWN\",\n" +
                "\t\"processing\":false,\n" +
                "\t\"inputDt\":\"\"\n" +
                "}";
        //System.out.println((getMatcherString(regex,source)).replace("<inb:StatusCode>","").replace("</inb:StatusCode>",""));
        JSONObject jsonObject=JSONObject.parseObject(source);
        JSONObject gda=jsonObject.getJSONObject("gda");
        String statusCode=gda.getString("msgCd");
        String statusDesc=gda.getString("msgInf");
        System.out.println(statusCode + "  "+statusDesc);
    }


}
