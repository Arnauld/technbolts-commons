/* $Id$ */
package org.technbolts.dto.configuration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * VersionTest
 * @author <a href="mailto:arnauld.loyer@technbolts.com">Loyer Arnauld</a>
 * @version $Revision$
 */
public class VersionTest
{
    @Test
    public void testConstants () {
        assertThat(Version.ATTRIBUTE_NAME, equalTo("version"));
    }
}
