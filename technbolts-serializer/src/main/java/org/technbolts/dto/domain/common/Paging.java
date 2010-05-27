package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Since;

public class Paging {
	
	@Since(Version.V1)
    @Alias(value="index", since= Version.V1)
    private Integer index;
    
    @Since(Version.V1)
    @Alias(value="count", since=Version.V1)
    private Integer count;
    
    public Paging()
    {
    }
    
    public Paging(Integer index, Integer count)
    {
        super();
        this.index = index;
        this.count = count;
    }
    
    public final Integer getIndex() {
		return index;
	}

	public final void setIndex(Integer index) {
		this.index = index;
	}

	public final Integer getCount() {
		return count;
	}

	public final void setCount(Integer count) {
		this.count = count;
	}  
}
