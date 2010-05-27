/* $Id$ */
package org.technbolts.dto.configuration;

/**
 * InvalidConfigurationException
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class InvalidConfigurationException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 7834223526803149467L;

    public InvalidConfigurationException()
    {
    }

    public InvalidConfigurationException(String message)
    {
        super(message);
    }

    public InvalidConfigurationException(Throwable cause)
    {
        super(cause);
    }

    public InvalidConfigurationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
