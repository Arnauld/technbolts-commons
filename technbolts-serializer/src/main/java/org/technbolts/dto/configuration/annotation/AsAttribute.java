/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 * Defines that a field should be serialized as an attribute.
 * A field can be serialized as an attribute in a range of version. 
 * This allows an element to be serialized or not as attribute between
 * different version.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AsAttribute
{
    Version since() default Version.V0;
    Version until() default Version.Last;
}
