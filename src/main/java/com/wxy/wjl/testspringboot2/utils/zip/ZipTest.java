package com.wxy.wjl.testspringboot2.utils.zip;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class ZipTest {
    private String xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.cbagroup.com/lockbox\">\n" +
            "\t<soapenv:Body>\n" +
            "\t\t<loc:ActivateAndSaveLockBoxResponse>\n" +
            "\t\t\t<loc:STATUS>\n" +
            "\t\t\t\t<loc:SUCCESS_INDICATOR>Success</loc:SUCCESS_INDICATOR>\n" +
            "\t\t\t</loc:STATUS>\n" +
            "\t\t\t<loc:RESPONSE_CODE>SUCCESS</loc:RESPONSE_CODE>\n" +
            "\t\t\t<loc:CUSTOMER_MESSAGE>Success</loc:CUSTOMER_MESSAGE>\n" +
            "\t\t\t<loc:MSISDN>254110100390</loc:MSISDN>\n" +
            "\t\t\t<loc:TARGET_SAVINGS_AMOUNT>5000.00</loc:TARGET_SAVINGS_AMOUNT>\n" +
            "\t\t\t<loc:SAVINGS_AMOUNT>200.00</loc:SAVINGS_AMOUNT>\n" +
            "\t\t\t<loc:MESSAGE_ID>712Cd88r834mF00000DD</loc:MESSAGE_ID>\n" +
            "\t\t\t<loc:TRANSACTION_ID>OJD3SG313H</loc:TRANSACTION_ID>\n" +
            "\t\t\t<loc:TRANSACTION_RECEIPT_NO>OJD3SG313H</loc:TRANSACTION_RECEIPT_NO>\n" +
            "\t\t\t<loc:LOCK_BOX_DURATION>4</loc:LOCK_BOX_DURATION>\n" +
            "\t\t\t<loc:LOCK_BOX_BALANCE>200</loc:LOCK_BOX_BALANCE>\n" +
            "\t\t</loc:ActivateAndSaveLockBoxResponse>\n" +
            "\t</soapenv:Body>\n" +
            "</soapenv:Envelope>";

/*    @Test
    public void testGzip() throws Exception {

        System.out.println("xml1 = [" + xml1.length() + "]");
        byte[] bytes = GZipUtils.compress(xml1.getBytes());
        String xml2 = YGByteUtil.byteArrayToHex(bytes);
        System.out.println("xml2 = [" + xml2.length() + "][" + xml2 + "]");

        String xml3 = Base64.encodeBase64String(bytes);
        System.out.println("xml3 = [" + xml3.length() + "][" + xml3 + "]");

        byte[] xml4 = GZipUtils.decompress(YGByteUtil.hexToByteArray(xml2));
        System.out.println(new String (xml4));

    }

    @Test
    public void testZLib() throws Exception {
        System.out.println("xml1 = [" + xml1.length() + "]");
        byte[] bytes = ZLibUtils.compress(xml1.getBytes());
        String xml2 = YGByteUtil.byteArrayToHex(bytes);
        System.out.println("xml2 = [" + xml2.length() + "][" + xml2 + "]");

        String xml3 =Base64.encodeBase64String(bytes);
        System.out.println("xml3 = [" + xml3.length() + "][" + xml3 + "]");

        byte[] xml4 = ZLibUtils.decompress(YGByteUtil.hexToByteArray(xml2));
        System.out.println(new String (xml4));

    }*/

}
