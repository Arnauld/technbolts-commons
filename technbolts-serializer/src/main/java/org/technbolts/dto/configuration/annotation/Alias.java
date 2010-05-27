/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 * Alias aka name of the corresponding element in its serialization form.
 * An alias can be valid in a range of version. This allows an element to
 * modify its alias between different version.
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Alias
{
    Version since() default Version.V0;
    Version until() default Version.Last;
    String  value();
}
