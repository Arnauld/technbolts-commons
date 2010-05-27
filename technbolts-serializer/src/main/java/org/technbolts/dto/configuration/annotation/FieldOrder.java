package org.technbolts.dto.configuration.annotation;

import org.technbolts.dto.configuration.Version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines field serialization order within a class.
 *
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FieldOrder {
    Version since() default Version.V0;
    Version until() default Version.Last;
    
    int value();
}
