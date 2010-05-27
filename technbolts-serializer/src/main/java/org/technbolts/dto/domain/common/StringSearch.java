/* $Id$ */
package org.technbolts.dto.domain.common;


import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Since;

/**
 * StringSearch utility DTO to define a search within a string field.
 * 
 * 
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class StringSearch
{
    @Since(Version.V1)
    @Alias(value="search_type", since=Version.V1)
    private SearchType searchType;
    
    @Since(Version.V1)
    @Alias(value="case_sensitive", since=Version.V1)
    private Boolean caseSensitive;
    
    @Since(Version.V1)
    @Alias(value="text", since= Version.V1)
    private String text;
    
    public StringSearch()
    {
    }
    
    public StringSearch(SearchType searchType, String text, boolean caseSensitive)
    {
        super();
        this.searchType = searchType;
        this.caseSensitive = caseSensitive;
        this.text = text;
    }
    
    public StringSearch(SearchType searchType, String text)
    {
        super();
        this.searchType = searchType;
        this.text = text;
    }

    public SearchType getSearchType()
    {
        return searchType;
    }

    public void setSearchType(SearchType searchType)
    {
        this.searchType = searchType;
    }

    public Boolean getCaseSensitive()
    {
        return caseSensitive;
    }

    public void setCaseSensitive(Boolean caseSensitive)
    {
        this.caseSensitive = caseSensitive;
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
