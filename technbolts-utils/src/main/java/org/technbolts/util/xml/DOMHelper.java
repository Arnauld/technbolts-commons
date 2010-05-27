/* $Id$ */
package org.technbolts.util.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public interface DOMHelper
{
    Document createDocument() throws ParserConfigurationException, IOException, SAXException;

    Document toDocument(String xml) throws ParserConfigurationException, IOException, SAXException;
    
    String getStr(NodeList list);
    
    String getChildValuesAsString(Node node);
    
    Element addChild(Element parent, String childName);
    
    Element addChild(Document document, String childName);
    
    Element addChild(Element parent, String childName, String childValue);
    
    String getXmlOutput(Document document, String encoding) throws TransformerException;
}
