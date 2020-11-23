package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.utils.HttpUtils;
import com.wxy.wjl.testspringboot2.utils.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 网络工具测试controller
 */
@ResponseBody
@Controller
@RequestMapping("/net")
@Slf4j
public class NetController {

    @RequestMapping("/getPid")
    public Integer getPid(){
        log.info("获取pid");

        return NetUtil.getPid();
    }

    @RequestMapping("/getLocalhostStr")
    public String getLocalhostStr(){
        return NetUtil.getLocalhostStr();
    }

    @RequestMapping("/http")
    public String doPostTimeOut() throws Exception{

        String data="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ic4=\"http://VMTComponentModel/InterfaceSpecification/Interfaces/C4/IC4FSP2VMT\" xmlns:c4=\"http://VMTComponentModel/InterfaceSpecification/Messages/C4/\">\n" +
                "        <soapenv:Header/>\n" +
                "        <soapenv:Body>\n" +
                "                <ic4:FSPInitiatedActivateNotification>\n" +
                "                        <ic4:request>\n" +
                "                                <c4:AdditionalInformation></c4:AdditionalInformation>\n" +
                "                                <c4:BankShortCode>262626</c4:BankShortCode>\n" +
                "                                <c4:CustomerId>\n" +
                "                                        <c4:MSISDN>254711121023</c4:MSISDN>\n" +
                "                                        <c4:VmtReferenceNumber>254711121023</c4:VmtReferenceNumber>\n" +
                "                                </c4:CustomerId>\n" +
                "                                <c4:MessageId>\n" +
                "                                        <c4:Id>2ca5de95-06c1-4153-ac13-0789772710bf</c4:Id>\n" +
                "                                        <c4:TimeStamp>2020-11-12T17:37:15.444+03:00</c4:TimeStamp>\n" +
                "                                </c4:MessageId>\n" +
                "                                <c4:TransactionId>20201112223228</c4:TransactionId>\n" +
                "                                <c4:TransactionReceiptNumber>20201112223228</c4:TransactionReceiptNumber>\n" +
                "                                <c4:TransactionTypeName>ActivateAccount</c4:TransactionTypeName>\n" +
                "                                <c4:AccountCreationTimeStamp>2020-11-12T17:37:15.444+03:00</c4:AccountCreationTimeStamp>\n" +
                "                                <c4:BankResponseCode>S0</c4:BankResponseCode>\n" +
                "                                <c4:SavingsAccountNumber>1060030601435892</c4:SavingsAccountNumber>\n" +
                "                                <c4:SimAppTransId>20201112223228</c4:SimAppTransId>\n" +
                "                        </ic4:request>\n" +
                "                </ic4:FSPInitiatedActivateNotification>\n" +
                "        </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return HttpUtils.doPostTimeOut(data,"http://localhost:8081/ogw/ODout",10);
    }
    @ResponseBody
    @RequestMapping("/testTimeout")
    public String testTimeout() throws Exception{
        Thread.sleep(100000);
        return "test";
    }

}
