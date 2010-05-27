/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 *  An annotation for marking that the extended class must be serialized in a
 *  specific version. This is usefull when sharing common class in type
 *  hierarchie, the super type can evolve to an higher version (let say V8) 
 *  while some sub types stay in V7, while other goes to V8.
 *  This is mainly for backward compatibility and version requirement management.
 *   
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequireSuper
{
    Version since() default Version.V0;
    Version until() default Version.Last;
    Version value ();
}
