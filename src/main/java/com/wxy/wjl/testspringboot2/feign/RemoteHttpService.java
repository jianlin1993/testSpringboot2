package com.wxy.wjl.testspringboot2.feign;

import com.wxy.wjl.providerapi.entiy.ProviderReqBO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="remote",url = "${remote.url}")
@RequestMapping("/provider")
public interface RemoteHttpService {

    // 因为api里也引用了此mapping  此处改个名字
    @RequestMapping("testRemote2")
    String remoteService(@RequestBody ProviderReqBO providerReqBO);

}
