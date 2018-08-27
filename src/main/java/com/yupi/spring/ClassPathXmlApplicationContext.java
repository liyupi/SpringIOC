package com.yupi.spring;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 功能描述：全局上下文
 *
 * @author Yupi Li
 * @date 2018/8/27 20:50
 */
public class ClassPathXmlApplicationContext {
    // xml读取路径地址
    private String xmlPath;

    public ClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Object getBean(String beanId) throws DocumentException, IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        if (StringUtils.isEmpty(beanId)) {
            throw new RuntimeException("beanId不能为空");
        }
        List<Element> elements = parseXml();
        String className = findClassByElements(elements, beanId);
        if (StringUtils.isEmpty(className)) {
            throw new RuntimeException("该bean没有配置class地址");
        }
        return newInstance(className);
    }

    public String findClassByElements(List<Element> elements, String beanId) {
        if (elements == null || elements.isEmpty()) {
            throw new RuntimeException("配置文件未配置任何bean");
        }
        for (Element element : elements) {
            if ("bean".equals(element.getName())) {
                String xmlBeanId = element.attributeValue("id");
                if (StringUtils.isEmpty(xmlBeanId)) {
                    continue;
                }
                if (xmlBeanId.equals(beanId)) {
                    return element.attributeValue("class");
                }
            }
        }
        return null;
    }

    // 根据类名生成对象
    public Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return Class.forName(className).newInstance();
    }

    public List<Element> parseXml() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(getResourceAsStream());
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        return elements;
    }

    public InputStream getResourceAsStream() {
        return this.getClass().getClassLoader().getResourceAsStream(xmlPath);
    }
}
