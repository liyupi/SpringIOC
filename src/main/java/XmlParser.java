import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 功能描述：XML解析器
 *
 * @author Yupi Li
 * @date 2018/8/26 22:43
 */
public class XmlParser {

    public void parse() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(getResourceAsStream("test.xml"));
        Element rootElement = document.getRootElement();
        getNodes(rootElement);
    }

    private void getNodes(Element rootElement) {
        System.out.println(rootElement.getName());
        List<Attribute> attributes = rootElement.attributes();
        for (Attribute attribute : attributes) {
            System.out.println("property: " + attribute.getName() + " ; value: " + attribute.getValue());
        }
        System.out.println("text: " + rootElement.getTextTrim());
        List<Element> list = rootElement.elements();
        for (Element element : list) {
            getNodes(element);
        }
    }

    private InputStream getResourceAsStream(String xmlPath) {
        return this.getClass().getClassLoader().getResourceAsStream(xmlPath);
    }
}
