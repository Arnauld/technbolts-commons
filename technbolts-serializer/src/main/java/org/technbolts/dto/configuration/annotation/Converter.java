/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;
import org.technbolts.util.Adaptable;
import org.technbolts.util.AdaptableUtils;

/**
 * Annotation to declare a converter.
 * The converter used to marshall/unmarshall a <code>Field</code> or a 
 * <code>Class</code> can be different depending of the range of version.
 * This allow to use different converter depending of the version.  
 * 
 * The converter retrieved is usually versionized into an appropriate version.
 * The type of the converter is usually retrieved using the <code>Adaptable</code>
 * depending of the serializer used. 
 * 
 * <pre>
 *      class MyClass {
 *          @Converter(clazz=MyConverter.class)
 *          private Data data;
 *      }
 *      
 *      //...
 *      Object aConverter = newInstance (MyConverter.class);
 *      Object versionnedConverter = VersionUtils.versionize(versionnedConverter, requiredVersion);
 *      if(versionnedConverter==null)
 *          throw new Exception ("Unable to find a suitable converter for the required version");
 *      
 *      SerializerConverter toBeUsed = AdaptableUtils.getAdapter(aConverter, SerializerConverter.class);
 *      if(toBeUsed==null)
 *          throw new Exception ("Unable to find a suitable converter for the serializer used");
 * 
 * </pre>
 * 
 * 
 * @version $Revision$
 * @see org.technbolts.dto.configuration.VersionSensitive
 * @see org.technbolts.dto.configuration.VersionUtils#versionize(Object, Version)
 * @see Adaptable
 * @see AdaptableUtils#getAdapter(Object, Class)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Converter
{
    Version since() default Version.V0;
    Version until() default Version.Last;
    Class<?> clazz ();
}
