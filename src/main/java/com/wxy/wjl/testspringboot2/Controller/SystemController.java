package com.wxy.wjl.testspringboot2.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/system")
@Slf4j
public class SystemController {



	/**
	 * interrupt thread
	 * @return
	 */
	@RequestMapping("/interruptThread")
	public String interruptThread(@RequestParam("name") String name, @RequestParam("id") String id) {


		Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
		for( Thread thread : threads.keySet() ) {
			if(StringUtils.equals(name, thread.getName()) && NumberUtils.toLong(id) == thread.getId() ) {
				log.info("find thread:{}, {}; interrupt this thread", name, id);
				thread.interrupt();
				break;
			}
		}


		return "";
	}


	@Autowired
	private GitProperties git;

	@RequestMapping("/gitInfo")
	public String gitInfo() {


		return JSON.toJSONString(git);
	}


}
