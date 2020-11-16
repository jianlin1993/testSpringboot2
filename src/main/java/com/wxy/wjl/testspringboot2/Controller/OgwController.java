package com.wxy.wjl.testspringboot2.Controller;


import com.wxy.wjl.testspringboot2.utils.XmlFormatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ResponseBody
@Controller
@RequestMapping("/ogw")
public class OgwController {

    /**
     * 打印请求体的内容
     * @param request
     * @return
     */
    @RequestMapping("/ODout")
    public String getPid(HttpServletRequest request) throws Exception{
        ServletInputStream is = null;
        try {
            is = request.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            System.out.println(sb.toString());
            System.out.println("获取到的文本内容为：" + sb.toString());
            System.out.println("获取到的文本内容长度为：" + sb.toString().length());
            System.out.println("格式化后的报文内容为："+ XmlFormatUtil.formatXml(sb.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String data="";

        return data;
    }



}
