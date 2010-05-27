/* $Id$ */
package org.technbolts.dto.domain.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Implicit;
import org.technbolts.dto.configuration.annotation.Since;

public class LocalizedString
{
    @Since(Version.V1)
    @Implicit(itemFieldName="label", since=Version.V1)
    private List<Label> labels;

    public LocalizedString()
    {
    }
    
    public LocalizedString(Label... labels)
    {
        setLabels(labels);
    }
    
    public List<Label> getLabels()
    {
        return labels;
    }

    public void setLabels(Label... labels)
    {
        this.labels = Arrays.asList(labels);
    }
    
    public void addLabel(String locale, String content)
    {
        addLabel(new Label (locale, content));
    }
    
    public void addLabel(Label label)
    {
        if(labels==null)
            labels = new ArrayList<Label> ();
        this.labels.add(label);
    }
}
