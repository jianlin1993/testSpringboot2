package com.wxy.wjl.testspringboot2.utils.zip;

import org.apache.commons.lang3.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtils {

    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();

    public static final String UTF_8 = "UTF-8";


    public static String compressToString(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return encoder.encodeToString(out.toByteArray());
    }


    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static String uncompressToString(String oriText) {
        if (StringUtils.isBlank(oriText)) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(decoder.decode(oriText));
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) throws IOException {
        String s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:saf=\"http://temenos.com/SAFAPP\">  \n" +
                "\t<soapenv:Header/>  \n" +
                "\t<soapenv:Body>  \n" +
                "\t\t<saf:SAFAPPAccountTxnDtlsWithPeriod>  \n" +
                "\t\t\t<!--Optional:-->  \n" +
                "\t\t\t<WebRequestCommon>             \n" +
                "\t\t\t\t<company>KE0010001</company>            \n" +
                "\t\t\t\t<password>Kenya123</password>            \n" +
                "\t\t\t\t<userName>bankOutbound</userName> \n" +
                "\t\t\t</WebRequestCommon>  \n" +
                "\t\t\t<!--Optional:-->  \n" +
                "\t\t\t<ENOFCBASAFAPPACCTXNDETAILSType>  \n" +
                "\t\t\t\t<enquiryInputCollection>  \n" +
                "\t\t\t\t\t<columnName>MOBILE.NO</columnName>  \n" +
                "\t\t\t\t\t<criteriaValue>254750000002</criteriaValue>  \n" +
                "\t\t\t\t\t<operand>EQ</operand>  \n" +
                "\t\t\t\t</enquiryInputCollection>  \n" +
                "\t\t\t\t<enquiryInputCollection>  \n" +
                "\t\t\t\t\t<columnName>ACCT.NO</columnName>  \n" +
                "\t\t\t\t\t<criteriaValue>1060030600810088</criteriaValue>  \n" +
                "\t\t\t\t\t<operand>EQ</operand>  \n" +
                "\t\t\t\t</enquiryInputCollection>  \n" +
                "\t\t\t\t<enquiryInputCollection>  \n" +
                "\t\t\t\t\t<columnName>VMT.REF</columnName>  \n" +
                "\t\t\t\t\t<criteriaValue>254750000002</criteriaValue>  \n" +
                "\t\t\t\t\t<operand>EQ</operand>  \n" +
                "\t\t\t\t</enquiryInputCollection>  \n" +
                "\t\t\t\t<enquiryInputCollection>  \n" +
                "\t\t\t\t\t<columnName>START.DATE</columnName>  \n" +
                "\t\t\t\t\t<criteriaValue>20000824</criteriaValue>  \n" +
                "\t\t\t\t\t<operand>EQ</operand>  \n" +
                "\t\t\t\t</enquiryInputCollection>  \n" +
                "\t\t\t\t<enquiryInputCollection>  \n" +
                "\t\t\t\t\t<columnName>END.DATE</columnName>  \n" +
                "\t\t\t\t\t<criteriaValue>20210000</criteriaValue>  \n" +
                "\t\t\t\t\t<operand>EQ</operand>  \n" +
                "\t\t\t\t</enquiryInputCollection>  \n" +
                "\t\t\t</ENOFCBASAFAPPACCTXNDETAILSType>  \n" +
                "\t\t</saf:SAFAPPAccountTxnDtlsWithPeriod>  \n" +
                "\t</soapenv:Body> \n" +
                "</soapenv:Envelope>\n";
        System.out.println("字符串长度："+s.length());
        System.out.println("压缩后：："+" 长度 ="+compressToString(s).length());
        System.out.println("解压后："+" 长度 ="+uncompressToString(compressToString(s)).length());
        //System.out.println("解压字符串后：："+uncompressToString(compress(s)).length());
    }
}
