/* $Id$ */
package org.technbolts.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * javax implementation
 * @author <a href="mailto:trouillard@gmail.com">trouillard</a>
 * @version $Revision$
 */
public class DOMHelperImpl implements DOMHelper
{
    public Document createDocument() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    public Document toDocument(String xml) throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }
    
    public Document toDocument(InputStream xmlStream) throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        return builder.parse(xmlStream);
    }

    public String getStr(NodeList list)
    {
        return list.item(0).getTextContent();
    }

    public String getChildValuesAsString(Node node)
    {
        return node.getTextContent();
    }

    public Element addChild(Element parent, String childName)
    {
        Element element = parent.getOwnerDocument().createElement(childName);
        parent.appendChild( element);
        return element;
    }

    public Element addChild(Document document, String childName)
    {
        Element element = document.createElement(childName);
        document.appendChild( element);
        return element;
    }

    public Element addChild(Element parent, String childName, String childValue)
    {
        Element element = parent.getOwnerDocument().createElement(childName);
        element.setTextContent(childValue);
        parent.appendChild( element);
        return element;
    }
    
    public String getXmlOutput(Document document, String encoding) throws TransformerException {
    	Source source = new DOMSource(document);
    	
        StringWriter strWriter = new StringWriter();
        Result result = new StreamResult(strWriter);
        
    	TransformerUtils.createPrettyPrint(encoding).transform(source, result);
        
        return strWriter.toString();
    }
}
