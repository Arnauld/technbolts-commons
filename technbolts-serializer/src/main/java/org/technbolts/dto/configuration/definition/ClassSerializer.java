package org.technbolts.dto.configuration.definition;

import org.technbolts.dto.configuration.Version;
import org.technbolts.util.New;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class ClassSerializer {

    private Version version;
    private String alias;
    private Object converter;
    private boolean omitted;
    //
    private Map<String,FieldSerializer> fields = New.hashMap();

    public FieldSerializer getFieldSerializer(Field field) {
        FieldSerializer fieldSerializer = fields.get(field.getName());
        if(fieldSerializer==null) {
            fieldSerializer = new FieldSerializer(field);
            fields.put(field.getName(), fieldSerializer);
        }
        return fieldSerializer;
    }
}
