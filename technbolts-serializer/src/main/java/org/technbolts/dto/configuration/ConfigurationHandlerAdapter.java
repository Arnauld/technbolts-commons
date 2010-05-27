/* $Id$ */
package org.technbolts.dto.configuration;

import java.util.ArrayList;
import java.util.List;

import org.technbolts.dto.configuration.definition.AsAttributeModel;
import org.technbolts.dto.configuration.definition.ConverterModel;
import org.technbolts.dto.configuration.definition.ImplicitModel;
import org.technbolts.dto.configuration.definition.*;
import org.technbolts.dto.configuration.definition.OmitFieldModel;

/**
 * ConfigurationHandlerAdapter
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ConfigurationHandlerAdapter implements ConfigurationHandler
{
    public static enum Mode {
        ignoreUnsupported,
        throwExceptionForUnsupported,
        collectInformations,
    }
    
    private Mode mode = Mode.ignoreUnsupported;
    
    public ConfigurationHandlerAdapter()
    {
        this(Mode.ignoreUnsupported);
    }
    
    public ConfigurationHandlerAdapter(Mode mode)
    {
        this.mode = mode;
    }

    private List<ImplicitModel> implicitCollectionList = new ArrayList<ImplicitModel> ();
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#addImplicitCollection(java.lang.Class, java.lang.String, java.lang.String, java.lang.Class)
     */
    public void addImplicitCollection(Class<?> definedIn, String fieldName,
            String itemFieldName, Class<?> itemType)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();
        implicitCollectionList.add(new ImplicitModel(definedIn, fieldName, itemFieldName, itemType));
    }

    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#addImplicitCollection(java.lang.Class, java.lang.String)
     */
    public void addImplicitCollection(Class<?> definedIn, String fieldName)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();

        implicitCollectionList.add(new ImplicitModel(definedIn, fieldName));
    }
    
    private List<AliasModel> aliasModelList = new ArrayList<AliasModel> ();

    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#alias(java.lang.String, java.lang.Class)
     */
    public void alias(String alias, Class<?> type)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();
        
        aliasModelList.add(new AliasModel (alias, type));
    }

    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#aliasField(java.lang.String, java.lang.Class, java.lang.String)
     */
    public void aliasField(String alias, Class<?> definedIn, String fieldName)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();
        

        aliasModelList.add(new AliasModel (alias, definedIn, fieldName));
    }
    
    private List<OmitFieldModel> omitFieldList = new ArrayList<OmitFieldModel> ();
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#omitField(java.lang.Class, java.lang.String)
     */
    public void omitField(Class<?> definedIn, String fieldName)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();
        
        omitFieldList.add(new OmitFieldModel (definedIn, fieldName));
    }
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#omitField(com.technbolts.dto.configuration.ClassField)
     */
    public void omitField(ClassField classField)
    {
        omitField(classField.getDefinedIn(), classField.getFieldName());
    }

    private List<ConverterModel> converterList = new ArrayList<ConverterModel> ();
    
    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#registerConverter(java.lang.Class, java.lang.Object)
     */
    public void registerConverter(Class<?> definedIn, Object converter)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();
        
        converterList.add(new ConverterModel (definedIn, converter));
    }

    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#registerConverter(java.lang.Class, java.lang.String, java.lang.Object)
     */
    public void registerConverter(Class<?> definedIn, String fieldName,
            Object converter)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();
        
        converterList.add(new ConverterModel (definedIn, fieldName, converter));
    }
    
    private List<AsAttributeModel> asAttributeList = new ArrayList<AsAttributeModel> ();

    /* (non-Javadoc)
     * @see com.technbolts.dto.configuration.ConfigurationHandler#useAttributeFor(java.lang.Class, java.lang.String)
     */
    public void useAttributeFor(Class<?> definedIn, String fieldName)
    {
        if(mode==Mode.throwExceptionForUnsupported)
            new UnsupportedOperationException ();

        asAttributeList.add(new AsAttributeModel(definedIn, fieldName));
    }

    /**
     * @return
     */
    public Mode getMode()
    {
        return mode;
    }

    public List<ImplicitModel> getImplicitCollectionList()
    {
        return implicitCollectionList;
    }

    public List<AliasModel> getAliasModelList()
    {
        return aliasModelList;
    }

    public List<OmitFieldModel> getOmitFieldList()
    {
        return omitFieldList;
    }

    public List<ConverterModel> getConverterList()
    {
        return converterList;
    }
    
    public Object getConverter(Class<?> definedIn, String fieldName)
    {
        for(ConverterModel converter : getConverterList()) {
            if(!converter.isFieldConverter())
                continue;
            Class<?> cClazz = converter.getType();
            String   cField = converter.getFieldName();
            if(definedIn.equals(cClazz) && fieldName.equals(cField)) {
                return converter.getConverter();
            }
        }
        return null;
    }

    public List<AsAttributeModel> getAsAttributeList()
    {
        return asAttributeList;
    }

}
