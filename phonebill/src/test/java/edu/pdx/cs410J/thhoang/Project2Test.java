package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.Matcher;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality in project 2
 */
public class Project2Test extends InvokeMainTestCase {

    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project2.class, args);
    }

    @Test
    public void testNoCommandArguments(){
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
//        assertTrue(result.getErr().contains("Missing command line arguments"));
    }
}
