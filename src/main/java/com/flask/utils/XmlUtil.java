package com.flask.utils;

import com.flask.mybatis.Configuration;
import com.flask.mybatis.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 * Description:
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/22
 */
public class XmlUtil {

    public static void readMapperXml (String pathName, Configuration configuration) {
        //获取Document对象
        SAXReader reader = new SAXReader();
        //Base64Tool
        try {
            // 读取文件内容
            Document document = reader.read(new File(pathName));

            // 获取 xml 中根元素
            Element rootElement = document.getRootElement();
            if (!"mapper".equals(rootElement.getName())) {
                System.out.println("mapper xml 根元素不是 mapper");
                return;
            }

            String namespace = rootElement.attributeValue("namespace");
            // List<MappedStatement> statements = new ArrayList<>();
            Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();

            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue("id");
                String sourceId = namespace + "." + id;

                MappedStatement mapped = new MappedStatement();
                mapped.setSourceId(sourceId);
                mapped.setNamespace(namespace);
                mapped.setResultType(element.attributeValue("resultType"));
                mapped.setSql(element.getTextTrim());

                mappedStatementMap.put(sourceId, mapped);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
