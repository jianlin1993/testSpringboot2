/*
package com.wxy.wjl.testspringboot2.Controller;


import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("cache")
public class CacheController {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BillMapper billMapper;

    @ResponseBody
    @RequestMapping("bill/{jrnNo}")
    private Bill getCache1(@PathVariable String jrnNo){
        Bill bill = billMapper.getBill(Integer.parseInt(jrnNo));
        return bill;
    }


    @RequestMapping("/get")
    public void getCache() {
        Collection<String> names =  cacheManager.getCacheNames();
        for( String name : names ) {
            GuavaCache cache = (GuavaCache)cacheManager.getCache(name);
            System.out.println(name + ":" + cache.getNativeCache().asMap());
        }
    }

    @RequestMapping("/evict")
    public String evict(String name, String key) {
        GuavaCache cache = (GuavaCache)cacheManager.getCache(name);
        if( cache == null ) {
            return "not found cache:" + name;
        }
        cache.evict(key);
        return "succcess";
    }
}
*/
