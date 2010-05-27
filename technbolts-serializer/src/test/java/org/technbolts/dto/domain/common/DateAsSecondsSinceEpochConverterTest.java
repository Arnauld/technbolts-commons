/* $Id$ */
package org.technbolts.dto.domain.common;

import static org.technbolts.util.xml.XPathUtils.stringValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;
import java.util.TimeZone;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.technbolts.dto.serializer.xstream.XStreamHandlerWrapper;
import org.technbolts.util.DateBuilder;
import org.technbolts.util.Month;
import org.technbolts.util.xml.DOMHelperImpl;
import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;

public class DateAsSecondsSinceEpochConverterTest
{
    private static Logger logger = LoggerFactory.getLogger(DateAsSecondsSinceEpochConverterTest.class);
    
    private DateAsSecondsSinceEpochConverter converter;
    //
    private XStream xstream;
    private XStreamHandlerWrapper handler;
    //
    private Date date;
    private long timestamp;
    
    private TimeZone savedTimeZone;
    
    @Before
    public void setUp () {
        converter = new DateAsSecondsSinceEpochConverter();
        xstream = new XStream();
        handler = new XStreamHandlerWrapper(xstream);
        handler.alias("struct", Struct.class);
        handler.aliasField("creation-date", Struct.class, "creationDate");
        handler.registerConverter(Struct.class, "creationDate", converter);
        
        savedTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        date = DateBuilder.newDateBuilder().date(2010, Month.May, 20).timeOfDay(17, 43, 11).millis(0).build();
        timestamp = 1274377391L;
        
        logger.debug("date = "+date);
        logger.debug(timestamp+" ?= "+(date.getTime()/1000L));
    }
    
    @After
    public void tearDown () {
        TimeZone.setDefault(savedTimeZone);
    }
    
    @Test
    public void testConvertToXml () throws Exception {
        Struct struct = new Struct();
        struct.creationDate = date;
        
        String xml = xstream.toXML(struct);
        
        Document document = new DOMHelperImpl().toDocument(xml);
        String value = stringValue(document, "/struct/creation-date");
        Long  asLong = Long.parseLong(value);
        assertThat (asLong, equalTo(date.getTime()/1000));
        assertThat (asLong, equalTo(timestamp));
        System.out.println(xml);
    }
    
    @Test
    public void convertFromXml () throws Exception {
        String xml = "<struct>\n" +
        		     "<creation-date>"+timestamp+"</creation-date>\n" +
        		     "</struct>";
        Object o = xstream.fromXML(xml);
        assertThat (o, CoreMatchers.is(Struct.class));
        Struct struct = (Struct)o;
        assertThat (struct.creationDate.getTime()/1000, equalTo(timestamp));
    }
    
    public static final class Struct {
        private Date creationDate;
    }
}
