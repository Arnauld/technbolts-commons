/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for marking that a field is used as version placeholder.
 * Thus the default version serialization settings will be applied on it.
 * <p/>
 * This can be viewed as a short cut to:
 * <pre>
 *  &nbsp;@Alias(value=Version.ATTRIBUTE_NAME)
 *  &nbsp;@AsAttribute
 *  &nbsp;@Converter(clazz=VersionConverter.class)
 * </pre>
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see org.technbolts.dto.configuration.Versionable
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VersionField
{
}
