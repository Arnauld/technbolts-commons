/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 * An annotation for marking a field as not written in its serialization form.
 * A field can be omitted depending on a range of version. This allows to
 * add/remove field depending on version.
 * For new added field or removed field, the <code>Since</code> or <code>Until</code>
 * annotations could be more understandable for reader. 
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see Since
 * @see Until
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OmitField
{
    Version since() default Version.V0;
    Version until() default Version.Last;
}
