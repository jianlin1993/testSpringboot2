package com.wxy.wjl.testspringboot2.job.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;

/**
 * xxx
 *
 * @author xu_lw
 * @version 5.0.0
 * created  at 2020-02-21 09:03
 * copyright @2018 北京沐融信息科技股份有限公司
 */
public class OpsUtil {
	public static String getNodeId() {
		String nodeId = System.getProperty("ecp.app.nodeId");
		if (nodeId == null) {
			String hostAddress = MrNetUtil.getLocalhost().getHostAddress();
			nodeId = "sch-" + hostAddress;
		}
		return nodeId;
	}

	public static void sleepQuietly(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			;
		}
	}


	public static String getElementValue(Element root, String name) {
		int offset = 0;
		while( true ) {
			int idx = name.indexOf(".", offset);
			if( idx == -1 ) {
				return root.elementText(name.substring(offset, idx));
			} else {
				root = root.element(name.substring(offset, idx));
				if( root == null ) {
					return null;
				}
			}
			offset = idx + 1;
		}
	}
}
