/* $Id$ */
package org.technbolts.util.xml;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 * TransformerUtils
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class TransformerUtils
{

    /**
     * Create a transformer for a pretty print result
     * <ul>
     *  <li>version</li>
     *  <li>indentation</li>
     *  <li>...</li>
     * </ul>
     * @param encoding
     * @return
     * @throws javax.xml.transform.TransformerConfigurationException
     */
    public static Transformer createPrettyPrint(String encoding) throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return transformer;
    }
}
