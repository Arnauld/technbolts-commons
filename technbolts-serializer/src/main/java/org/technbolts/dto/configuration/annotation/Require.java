/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 *  An annotation for marking that the field must be serialized in a specific
 *  version. This is usefull when sharing common type, the common type can
 *  evolve to an higher version (let say V8) while the current type is in its
 *  (let say V1 ) own version.
 *  This is mainly for backward compatibility and version requirement management.
 *   
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Require
{
    Version since() default Version.V0;
    Version until() default Version.Last;
    Version value ();
}
