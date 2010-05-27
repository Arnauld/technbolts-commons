/* $Id$ */
package org.technbolts.dto.serializer.xstream;

import static org.technbolts.util.xml.XPathUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.technbolts.dto.configuration.InvalidConfigurationException;
import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.testmodel.TestData;
import org.technbolts.dto.testmodel.TestLinkedVersionableData;
import org.technbolts.dto.testmodel.TestParentData;
import org.technbolts.dto.testmodel.TestVersionableData;
import org.w3c.dom.Document;

import org.technbolts.util.xml.DOMHelperImpl;

import junit.framework.TestCase;

public class XStreamSerializerTest extends TestCase
{

    private XStreamSerializer serializer;
    
    private TestParentData parentData;
    private TestData data;
    private String dateAsString;

    @Override
    protected void setUp() throws Exception
    {
        data = new TestData ();
        data.setId("17");
        data.setName("Merlin");
        data.setCount(3407);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
        calendar.set(Calendar.YEAR, 2009);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 17);
        calendar.set(Calendar.SECOND, 51);
        calendar.set(Calendar.MILLISECOND, 000);
        
        dateAsString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S z").format(calendar.getTime());
        
        parentData = new TestParentData ();
        parentData.setCreationDate(calendar.getTime());
    }
    
    public void initSerializer(Version version, Class<?>... types) throws InvalidConfigurationException {
        serializer = new XStreamSerializer ();
        for(Class<?> type : types)
            serializer.recursivelyProcessAnnotations(type, version);
    }
    
    public void testDataV0 () throws Exception {
        initSerializer(Version.V0, TestData.class);
        String xmlData = serializer.toXml(data);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only three nodes
        assertThat (matchingCount(document, "/test_data/*"), equalTo (3));
        
        assertThat (stringValue  (document, "/test_data/equality"), equalTo ("3407"));
        assertThat (stringValue  (document, "/test_data/name"),     equalTo ("Merlin"));
        assertThat (stringValue  (document, "/test_data/id"),       equalTo ("17"));
    }
    
    public void testParentDataV0 () throws Exception {
        initSerializer(Version.V0, TestParentData.class);
        String xmlData = serializer.toXml(parentData);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only one node
        assertThat (matchingCount(document, "/parent_data/*"), equalTo (1));
        assertThat (stringValue  (document, "/parent_data/creation_date"), equalTo (dateAsString));
    }
    
    public void testDataV1 () throws Exception {
        initSerializer(Version.V1, TestData.class);
        String xmlData = serializer.toXml(data);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only three nodes
        assertThat (matchingCount(document, "/test_data/*"), equalTo (3));
        
        assertThat (stringValue  (document, "/test_data/equals"),     equalTo ("3407"));
        assertThat (stringValue  (document, "/test_data/name"),       equalTo ("Merlin"));
        assertThat (stringValue  (document, "/test_data/identifier"), equalTo ("17"));
    }
    
    public void testParentDataV1 () throws Exception {
        initSerializer(Version.V1, TestParentData.class);
        String xmlData = serializer.toXml(parentData);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only one node
        assertThat (matchingCount(document, "/parent_data/*"), equalTo (1));
        assertThat (stringValue  (document, "/parent_data/creation_date"), equalTo (dateAsString));
    }
    
    public void testDataV2 () throws Exception {
        initSerializer(Version.V2, TestData.class);
        String xmlData = serializer.toXml(data);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only three nodes
        assertThat (matchingCount(document, "/data/*"), equalTo (2));
        
        assertThat (stringValue  (document, "/data/equals"),   equalTo ("3407"));
        assertThat (stringValue  (document, "/data/fullname"), equalTo ("Merlin"));
        assertThat (stringValue  (document, "/data/@uuid"),    equalTo ("17"));
    }
    
    public void testParentDataV2 () throws Exception {
        initSerializer(Version.V2, TestParentData.class);
        String xmlData = serializer.toXml(parentData);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only one node
        assertThat (matchingCount(document, "/parentData/*"), equalTo (1));
        assertThat (stringValue  (document, "/parentData/creation_datetime"), equalTo (dateAsString));
    }
    
    public void testParentDataWithChildV2 () throws Exception {
        initSerializer(Version.V2, TestParentData.class);
        parentData.setInfos(data);
        String xmlData = serializer.toXml(parentData);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only one node
        assertThat (matchingCount(document, "/parentData/*"), equalTo (2));
        assertThat (stringValue  (document, "/parentData/creation_datetime"), equalTo (dateAsString));
        assertThat (stringValue  (document, "/parentData/extra/equals"), equalTo ("3407"));
        assertThat (stringValue  (document, "/parentData/extra/name"), equalTo ("Merlin"));
        assertThat (stringValue  (document, "/parentData/extra/identifier"), equalTo ("17"));
    }
    
    public void testVersionableData_v0 () throws Exception {
        initSerializer(Version.V0, TestVersionableData.class);
        parametrizedTestVersionableData(Version.V0);
    }
    
    public void testVersionableData_v1 () throws Exception {
        initSerializer(Version.V1, TestVersionableData.class);
        parametrizedTestVersionableData(Version.V1);
    }
    
    public void testVersionableData_v2ThroughV0Linked () throws Exception {
        initSerializer(Version.V0, TestLinkedVersionableData.class);
        // Linked uses v2 as require
        parametrizedTestVersionableData(Version.V2);
    }
    
    public void testVersionableData_v2ThroughV1Linked () throws Exception {
        initSerializer(Version.V1, TestLinkedVersionableData.class);
        // Linked uses v2 as require
        parametrizedTestVersionableData(Version.V2);
    }
    
    private void parametrizedTestVersionableData (Version expectedVersion) throws Exception {
        TestVersionableData versionable = new TestVersionableData ();
        versionable.setCount(17);
        String xmlData = serializer.toXml(versionable);
        Document document = new DOMHelperImpl ().toDocument(xmlData);
        
        // ensure one has only one node
        assertThat (stringValue(document, "/versionableData/@version"), equalTo (expectedVersion.toStringIdentifier()));
        assertThat (stringValue(document, "/versionableData/count"),    equalTo ("17"));
        assertThat (versionable.getVersion(), equalTo(expectedVersion));
    }
    
}
