/* $Id: Label.java,v 1.1 2008-08-12 13:26:04 arnauld Exp $ */
package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Since;

/**
 * Label
 * @author <a href="arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @author <a href="gabriel.arena@technbolts.com">Gabriel Arena</a>
 * @version $Revision: 1.1 $
 */
@Alias(value="label", since= Version.V1)
public class Label
{
    @Since(Version.V1)
    @Alias(value="locale", since=Version.V1)
    private String locale;
    
    @Since(Version.V1)
    @Alias(value="text", since=Version.V1)
    private String text;
    
    public Label()
    {
    }
    
    public Label(String locale, String text)
    {
        this.locale = locale;
        this.text = text;
    }
    
    public String getLocale()
    {
        return locale;
    }
    public void setLocale(String locale)
    {
        this.locale = locale;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
}
