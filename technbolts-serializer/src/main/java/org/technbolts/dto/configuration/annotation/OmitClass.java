/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 * An annotation for marking a class as not written in its serialization form.
 * A class can be omitted depending on a range of version. This allows to
 * add/remove class depending on version.
 * For new added class or removed class, the <code>Since</code> or <code>Until</code>
 * annotations could be more understandable for reader. 
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface OmitClass
{
    Version since() default Version.V0;
    Version until() default Version.Last;
}
