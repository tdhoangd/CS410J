package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;
import java.text.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;



/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1Test extends InvokeMainTestCase {

    @Test
    public void testString() {
        String str = "012-213-4555";
        String date = "01/22/2014";

        assertThat(str.matches("\\d{3}-\\d{3}-\\d{4}"), is(true));
        assertThat(date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"), is(true));
    }


    @Test
    public void testPhoneBill() {
        PhoneCall call1 = new PhoneCall("000-111-2222", "000-222-3333", "1/15/2015 10:10", "1/15/2015 10:30");
        PhoneCall call2 = new PhoneCall("000-333-2222", "000-333-5555", "1/20/2015 14:22", "120/2015 15:99");

        PhoneBill bill = new PhoneBill("A cat");
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);

        PhoneBill bill2 = new PhoneBill("A dog");
        bill2.addPhoneCall(call1);
        bill2.addPhoneCall(call2);

        assertThat("Test customer name", bill.getCustomer(), is("A cat"));
        assertThat("Test phone bill size", bill.getPhoneCalls().size(), is(2));
        assertThat(bill.getPhoneCalls(), is(bill2.getPhoneCalls()));

        System.out.println(bill.toString());
        System.out.println(bill2.toString());


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