/* $Id$ */
package org.technbolts.util;

/**
 * An interface for an adaptable object.
 * Adaptable objects can be dynamically extended to provide different interfaces
 * (or "adapters"). 
 * So that an object can behave as the required <code>type</code> without
 * extending or inheriting from it.
 * 
 * By using the IAdaptable interface in your applications, you can provide
 * different views of your objects without cluttering them with non-domain/UI
 * specific code.
 * 
 * Inspired from Eclipse Framework
 * see <code>http://www.eclipsezone.com/articles/what-is-iadaptable/</code>
 * for a presentation.
 *  
 * @version $Revision$
 */
public interface Adaptable
{
    <T> T getAdapter(Class<T> adapter);
}
