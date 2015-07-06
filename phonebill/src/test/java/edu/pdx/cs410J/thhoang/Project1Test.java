package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import java.util.Date;
import java.text.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static junit.framework.Assert.fail;


/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

    @Test
    public void testPhoneBill() {

    }

    @Test
    public void testPhoneCall() {
        String callerNo = "000-111-2222";
        String calleeNo = "000-222-3333";
        String starTime = "1/15/2015 19:00";
        String endTime = "1/15/2015 19:30";

        PhoneCall phonecall = new PhoneCall(callerNo, calleeNo,starTime, endTime );

        assertThat("Caller numbers should be similar",phonecall.getCaller(), is("000-111-2222"));
        assertThat("Callee numbers should be the same", phonecall.getCallee(), is("000-222-3333"));
        assertThat(phonecall.getStartTimeString(), is("1/15/2015 19:00"));
        assertThat(phonecall.getEndTimeString(), is("1/15/2015 19:30"));
        
        System.out.println(phonecall.toString());

    }

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments1() {
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing command line arguments"));
    }

}