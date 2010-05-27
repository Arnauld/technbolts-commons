/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 *  An annotation for marking that a field exists since a specific version, thus
 *  it shouldn't appear in previous version.
 *  For a fine grainer way to activate/deactivate a field in its serialization
 *  form prefer the annotation <code>OmitField</code>
 *  
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 * @see OmitField
 * @see Until
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Since
{
    /** Version since the annotated element exists. */
    Version  value();
}
