package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality in project 2
 */
public class Project2Test extends InvokeMainTestCase {

    @Test
    public void testSampleArgument() {

        String[] args = {"-texFile"};
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> arguments = new ArrayList<String>();
        String filePath = null;

        for (int i=0; i<args.length; i++) {

            if (args[i].charAt(0) == '-') {
                options.add(args[i]);
                if(args[i].equals("-textFile")) {
                    filePath = args[i+1];
                    i++;
                }
            } else
                arguments.add(args[i]);

        }

        System.out.println(filePath);
    }

    @Test
    public void testTextDumper() {

      /*  PhoneCall call1 = new PhoneCall("000-111-2222", "000-222-3333", "1/15/2015 10:10", "1/15/2015 10:30");
        PhoneCall call2 = new PhoneCall("000-333-2222", "000-333-5555", "1/20/2015 14:22", "1/20/2015 15:99");

        PhoneBill bill = new PhoneBill("A cat");
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);

        TextDumper td = new TextDumper(new File("abc.txt"));
        try {
            td.dump(bill);
        } catch (Exception e) {

        }
*/

    }

    @Test
    public void testTextParser() {

        AbstractPhoneCall aCall = null;
        AbstractPhoneBill bill = null;

        TextParser tp = new TextParser(new File("abc.txt"));

        try {
            bill = tp.parse();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (bill == null)
            System.out.println("false");
 /*       System.out.println(bill.toString());

        for (Object object: bill.getPhoneCalls()){
            aCall =(AbstractPhoneCall) object;
            System.out.println(aCall.toString());
        }
*/
    }

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
