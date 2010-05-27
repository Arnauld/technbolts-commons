/* $Id$ */
package org.technbolts.dto.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.technbolts.dto.configuration.Version;

/**
 * An annotation for marking a field as an implicit collection.
 * This implies collection elements are not listed into an element that represents
 * the collection. A field can be implicit depending of the range of version.
 * 
 * <pre>
 *      class MyData {
 *          String title;
 *      }
 *      class MyClass {
 *          Collection<Data> dataList;
 *      }
 *      
 *      // will be serialized as
 *      <my_class>
 *          <data_list>
 *              <data>
 *                  <title>Mr Potatoes</title>
 *              </data>
 *              <data>
 *                  <title>Mrs Potatoes</title>
 *              </data>
 *          </data_list>
 *      </my_class>
 *      
 *      // while
 *      class MyClass {
 *          @Implicit
 *          Collection<Data> dataList;
 *      }
 *      
 *      // will be serialized as
 *      <my_class>
 *          <data>
 *              <title>Mr Potatoes</title>
 *          </data>
 *          <data>
 *              <title>Mrs Potatoes</title>
 *          </data>
 *      </my_class>
 * </pre>
 * 
 * 
 * @author <a href="mailto:arnauld.loyer@gmail.com">Loyer Arnauld</a>
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Implicit
{
    Version since() default Version.V0;
    Version until() default Version.Last;
    String itemFieldName () default "";
}
