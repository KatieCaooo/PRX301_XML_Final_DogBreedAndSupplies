/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuct.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 *
 * @author katherinecao
 */
public class XMLUtils implements Serializable {

    public static Document parseFileToDOM(String filePath) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File f = new File(filePath);
        Document doc = db.parse(f);
        return doc;
    }

    public static XPath createXPath() throws Exception {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();
        return xPath;
    }

    public static boolean transformXMLToStreamResult(Node node, String filePath) {
        Source src = new DOMSource(node);
        File f = new File(filePath);
        Result result = new StreamResult(f);

        TransformerFactory tff = TransformerFactory.newInstance();
        try {
            Transformer trans = tff.newTransformer();
            trans.transform(src, result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }

    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream convertStringToInputStream(String xmlStr) {
        InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes(Charset.forName("UTF-8")));
        return inputStream;
    }
}
