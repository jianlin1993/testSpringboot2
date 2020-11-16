package com.wxy.wjl.testspringboot2.mytomcat;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 执行main方法，启动ServerSocket监听，读取web.xml的servlet配置与url映射，
 * 将socket请求转换成httpRequest与httpResponse，之后通过业务处理后返回。
 *
 */
public class MyStarter {

    // 自定义端口
    private static final Integer PORT = 8090;
    // 加载工程的URL-SERVLET映射
    public static Map<String, Object> servletMapping = new HashMap<String, Object>();

    /**
     *
     * function: ./start.sh
     * @param args
     * @author zhanghaolin
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            System.out.println("开始启动！");
            System.out.println("初始化中 ...");
            // 创建服务端口
            ServerSocket serverSocket = new ServerSocket(PORT);
            // 初始化(加载web.xml)
            init();
            System.out.println("启动完毕！");
            do {
                // 接收客户端连接
                Socket accept = serverSocket.accept();
                // 开启新线程让容器对连接进行处理
                Thread thread = new MyProcess(accept);
                thread.start();
            } while (Boolean.TRUE);	// 一直处于监听状态
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * function: 解析web.xml中配置的servlet
     */
    private static void init() {
        InputStream resourceAsStream = MyStarter.class.getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            for (int i = 0, length=elements.size(); i < length; i++) {
                Element element = elements.get(i);
                List<Element> es = element.elements();
                for (int j = 0, lgth=es.size(); j < lgth; j++) {
                    Element element2 = es.get(j);
                    String ename1 = element2.getName().toString();
                    if ("servlet-name".equals(ename1) && "servlet".equals(element.getName().toString())) {
                        String servletName = element2.getStringValue();
                        Element ele2 = element.element("servlet-class");
                        String classname = ele2.getStringValue();
                        List<Element> elements2 = rootElement.elements("servlet-mapping");
                        for (int k = 0, lk=elements2.size(); k < lk; k++) {
                            Element element4 = elements2.get(k);
                            List<Element> es3 = element4.elements();
                            for (int op = 0, opp=es3.size(); op < opp; op++) {
                                if ("servlet-name".equals(es3.get(op).getName().toString())
                                        && servletName.equals(es3.get(op).getStringValue())) {
                                    Element element7 = element4.element("url-pattern");
                                    String urlPattern = element7.getStringValue();
                                    servletMapping.put(urlPattern, (MyServlet) Class.forName(classname).newInstance());
                                    System.out.println("==> 加载 "+ classname + ":" +urlPattern);
                                }
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
