package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Since;

public class Sorting {

    @Since(Version.V1)
    @Alias(value="sort_by", since=Version.V1)
    private String sortBy;
    
    @Since(Version.V1)
    @Alias(value="sort_desc", since=Version.V1)
    private Boolean sortDesc;
    
    public Sorting()
    {
    }
    
    public Sorting(String sortBy, Boolean sortDesc)
    {
        super();
        this.sortBy = sortBy;
        this.sortDesc = sortDesc;
    }

	public final String getSortBy() {
		return sortBy;
	}

	public final void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public final Boolean getSortDesc() {
		return sortDesc;
	}

	public final void setSortDesc(Boolean sortDesc) {
		this.sortDesc = sortDesc;
	}
  
}
