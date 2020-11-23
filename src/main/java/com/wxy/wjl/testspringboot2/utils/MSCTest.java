package com.wxy.wjl.testspringboot2.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class MSCTest {

    public static void main(String[] args) {
        String data="{\"eventId\":\"sas.kafkapubtpc2.balanceChangeNotify\",\"txDt\":\"20201117\",\"txTm\":\"052941\",\"acDt\":\"20201117\",\"mblNo\":\"254732087022\",\"eventTime\":\"20201117052941\",\"evtId\":\"SAS_5100_01\",\"sysCnl\":\"CCS\",\"busRefNo\":\"SAS5020201117000000000002519056\",\"reqJrnNo\":\"SAS5020201117000000000002519056\",\"reqBusNo\":\"20201117000000000016909641\",\"busKey\":\"busKey\",\"content\":null,\"txnCd\":\"sendByEvtCode\",\"ptrId\":\"SFC\",\"prdGrpCd\":\"MSHWARI\",\"langTyp\":\"EN\",\"acNo\":\"1060030601452878\",\"txAmt\":\"6.66\",\"prdSubCtg\":\"01\",\"prdCd\":\"SAVGEN01\",\"ccy\":\"KES\",\"curAcBal\":\"506.66\",\"uavBal\":\"0\",\"notTxAvaBal\":\"0\",\"bal\":\"506.66\",\"ciNo\":\"110001050280205\",\"usrNo\":\"1101050284205\",\"oppCurAcBal\":\"0\",\"oppUavBal\":\"0\",\"oppNotTxAvaBal\":\"0\",\"oppBal\":\"0\",\"acBalLmt\":\"0\",\"fstDepFlg\":\"\",\"acTyp\":\"106\",\"kycLvl\":\"0\",\"txSts\":\"S\"}";
        JSONObject dataJsonObject = JSON.parseObject(data);
        JSONObject constParamObject = JSONObject.parseObject("{\"txCd\":\"depositEngagement\"}");
        if (constParamObject != null) {
            //设置常量值
            for (Map.Entry<String, Object> entry : constParamObject.entrySet()) {
                Object o = getJSONObject(dataJsonObject, entry.getKey());
                if (o == null) {
                    setJSONObject(dataJsonObject, entry.getKey(), entry.getValue());
                    System.out.println("设置常量值");
                }
            }
        }
        System.out.println("最终数据="+JSON.toJSONString(dataJsonObject));
    }



    private static Object getJSONObject(JSONObject obj, String key) {
        int idx = StringUtils.indexOf(key, ".");
        if (idx != -1) {
            obj = obj.getJSONObject(key.substring(0, idx));
            key = key.substring(idx + 1);
            return getJSONObject(obj, key);
        } else {
            return obj.get(key);
        }
    }

    private static void setJSONObject(JSONObject obj, String key, Object value) {
        int idx = StringUtils.indexOf(key, ".");
        if (idx != -1) {
            String key1 = key.substring(0, idx);
            JSONObject obj1 = obj.getJSONObject(key1);
            if (obj1 == null) {
                obj1 = new JSONObject();
                obj.put(key1, obj1);
            }
            key = key.substring(idx + 1);
            setJSONObject(obj1, key, value);
        } else {
            obj.put(key, value);
        }
    }
}
