package com.wxy.wjl.testspringboot2.Controller;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

@ResponseBody
@Controller
@RequestMapping("/v2")
public class Swagger2Controller {

    @RequestMapping("api-docs")
    public Object getJsonFromFile(@PathVariable String group){
        JSONObject json=null;
        if(StringUtils.equals("Gateway-openapi",group)){
            try {
                ClassPathResource classPathResource = new ClassPathResource("Gateway-openapi.json");
                InputStream inputStream = classPathResource.getInputStream();
                int iAvail = inputStream.available();
                byte[] bytes = new byte[iAvail];
                inputStream.read(bytes);
                String jsonString=new String(bytes);
                json=JSONObject.parseObject(jsonString);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return json;
    }


}
