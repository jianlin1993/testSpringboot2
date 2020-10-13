package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.utils.NetUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 网络工具测试controller
 */
@ResponseBody
@Controller
@RequestMapping("/net")
public class NetController {

    @RequestMapping("/getPid")
    public Integer getPid(){
        return NetUtil.getPid();
    }

    @RequestMapping("/getLocalhostStr")
    public String getLocalhostStr(){
        return NetUtil.getLocalhostStr();
    }
}
