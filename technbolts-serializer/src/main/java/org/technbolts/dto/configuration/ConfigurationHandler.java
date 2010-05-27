/* $Id$ */
package org.technbolts.dto.configuration;


/**
 * ConfigurationHandler define how a serializer can be configured.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public interface ConfigurationHandler
{
    void alias(String alias, Class<?> type);
    
    void aliasField(String alias, Class<?> definedIn, String fieldName);

    void omitField(ClassField classField);
    
    void omitField(Class<?> definedIn, String fieldName);

    void addImplicitCollection(Class<?> definedIn, String fieldName,
            String itemFieldName, Class<?> itemType);
    
    void addImplicitCollection(Class<?> definedIn, String fieldName);

    void registerConverter(Class<?> definedIn, String fieldName, Object converter);
    
    void registerConverter(Class<?> type, Object converter);
    
    void useAttributeFor(Class<?> definedIn, String fieldName);

    void fieldOrder(Class<?> definedIn, String fieldName, int order);

}
