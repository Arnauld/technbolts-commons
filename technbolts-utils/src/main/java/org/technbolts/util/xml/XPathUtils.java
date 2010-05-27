/* $Id$ */
package org.technbolts.util.xml;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

/**
 * XPathUtils
 * @author <a href="mailto:arnauld.loyer@eptica.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class XPathUtils
{
    private static XPathFactory factory = XPathFactory.newInstance();
    
    public static NodeList select(Object item, String xpathExpression) throws Exception
    {
        XPath xpath = factory.newXPath();
        return (NodeList)xpath.evaluate(xpathExpression, item, XPathConstants.NODESET);
    }
    
    public static String stringValue(Object item) throws Exception
    {
       XPath xpath = factory.newXPath();
       return xpath.evaluate("text()", item);
    }
    
    public static String stringValue(Object item, String xpathExpression) throws Exception
    {
       XPath xpath = factory.newXPath();
       return xpath.evaluate(xpathExpression, item);
    }
    
    public static int matchingCount(Object item, String xpathExpression) throws Exception
    {
       return select(item, xpathExpression).getLength();
    }
}
