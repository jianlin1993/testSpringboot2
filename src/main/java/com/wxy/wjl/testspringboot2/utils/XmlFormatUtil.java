package com.wxy.wjl.testspringboot2.utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * xml 工具类
 */
public class XmlFormatUtil {


    /**
     * 格式化xml
     * @param xml
     * @return
     * @throws Exception
     */
    public static String formatXml(String xml) throws Exception{
        xml=xml.replaceAll("\n|\r|\t", "");
        Document document = null;
        try {
            document = getDocument(xml, null);
        } catch (DocumentException e) {
            throw e;
        }
        return formatXml(document);
    }
    public static  Document getDocument(String data, Map namespaces) throws DocumentException {

        Document doc = null;

        SAXReader reader = new SAXReader(new DocumentFactory());
        if (namespaces != null && !namespaces.isEmpty()) {
            reader.getDocumentFactory().setXPathNamespaceURIs(namespaces);
        }
        doc = reader.read(new StringReader(data));

        return doc;
    }
    public static  String formatXml(Document document) throws Exception{
        OutputFormat xmlFormat = new OutputFormat();
        // 设置换行
        xmlFormat.setNewlines(true);
        // 生成缩进
        xmlFormat.setIndent(true);
        // 使用4个空格进行缩进, 可以兼容文本编辑器
        xmlFormat.setIndent("\t");
        xmlFormat.setTrimText(false);
        xmlFormat.setNewLineAfterDeclaration(false);
        xmlFormat.setEncoding(StandardCharsets.UTF_8.toString());
        StringWriter writer = new StringWriter();
        // 格式化输出流
        XMLWriter xmlWriter = null;
        // 将document写入到输出流
        try{
            xmlWriter = new XMLWriter(writer, xmlFormat);
            xmlWriter.write(document);
        } catch (IOException e) {
            throw e;
        }finally {
            try {
                if (xmlWriter != null) {
                    xmlWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }

        return writer.toString();
    }


}
