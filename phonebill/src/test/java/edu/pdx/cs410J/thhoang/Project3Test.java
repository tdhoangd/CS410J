package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {



    @Test
    public void testPrettyPrinter(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date start1 = null;
        Date start2 = null;
        Date end1 = null;
        Date end2 = null;
        Date start3 = null;
        Date end3 = null;
        Date start4 = null;
        Date end4 = null;

        try {
            start1 = df.parse("2/1/2015 9:00 am");
            start2 = df.parse("2/1/2015 10:00 am");
            end1 = df.parse("2/1/2015 2:30 pm");
            end2 = df.parse("2/2/2015 4:22 am");

            start3 = df.parse("1/10/2015 2:00 pm");
            end3 = df.parse("1/10/2015 3:20 pm");
            start4 = df.parse("2/10/2015 3:20 am");
            end4 = df.parse("2/10/2015 11:20 am");


        } catch (ParseException e) {
            e.printStackTrace();
        }

        PhoneCall call1 = new PhoneCall("000-111-2222", "234-141-4141", start1, end1);
        PhoneCall call2 = new PhoneCall("000-111-2222", "000-111-2222", start2, end2);
        PhoneCall call3 = new PhoneCall("333-444-2222", "000-222-1111", start3, end3 );
        PhoneCall call4 = new PhoneCall("333-444-2222", "221-333-1313", start4, end4);
        PhoneCall call5 = new PhoneCall("999-444-2222", "000-332-1111", start3, end3);


        PhoneBill bill = new PhoneBill("Thanh Hoang");
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);
        bill.addPhoneCall(call4);
        bill.addPhoneCall(call5);

        File file = new File("out.txt");

        try {
            PrettyPrinter pp = new PrettyPrinter(file);
            pp.dump(bill);
        } catch (IOException e) {
            System.out.println("Error");
        }




    }

    @Test
    public void examDuration(){

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date start1 = null;
        Date end1 = null;

        try {
            start1 = df.parse("1/1/2015 9:30 am");
            end1 = df.parse("1/1/2015 11:30 pm");
        } catch (ParseException e){
            e.printStackTrace();
        }

        AbstractPhoneCall c1 = new PhoneCall("000-111-2222", "234-141-4141", start1, end1);

        long diff = c1.getEndTime().getTime() - c1.getStartTime().getTime();
        int diffMinutes = (int) (diff/(60*1000));

        System.out.println(diff);
        System.out.println(diffMinutes);

    }

    @Test
    public void testSorting(){

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date start1 = null;
        Date start2 = null;
        Date end1 = null;
        Date end2 = null;
        Date start3 = null;
        Date end3 = null;
        Date start4 = null;
        Date end4 = null;

        try {
            start1 = df.parse("2/1/2015 9:00 am");
            start2 = df.parse("2/1/2015 10:00 am");
            end1 = df.parse("2/1/2015 2:30 pm");
            end2 = df.parse("2/2/2015 4:22 am");

            start3 = df.parse("1/10/2015 2:00 pm");
            end3 = df.parse("1/10/2014 3:20 pm");
            start4 = df.parse("2/10/2015 3:20 am");
            end4 = df.parse("2/10/2015 11:20 am");


        } catch (ParseException e) {
            e.printStackTrace();
        }


        AbstractPhoneCall c1 = new PhoneCall("000-111-2222", "234-141-4141", start1, end1);
        AbstractPhoneCall c2 = new PhoneCall("000-111-2222", "000-111-2222", start2, end2);

        PhoneCall call1 = (PhoneCall) c1;
        PhoneCall call2 = (PhoneCall) c2;


        PhoneCall call3 = new PhoneCall("333-444-2222", "000-222-1111", start3, end3 );
        PhoneCall call4 = new PhoneCall("333-444-2222", "221-333-1313", start4, end4);
        PhoneCall call5 = new PhoneCall("999-444-2222", "000-332-1111", start3, end3);


        List<PhoneCall> collection = new ArrayList<PhoneCall>();

        collection.add(call1);
        collection.add(call2);
        collection.add(call3);
     //   collection.add(new PhoneCall("333-444-2222", "000-332-1111", start3, end3));
        collection.add(call5);
        collection.add(call4);
        collection.add(call1);

        Comparator comparator = Collections.reverseOrder();

        StringBuffer text1 = new StringBuffer();
        for (PhoneCall a: collection) {
            text1.append(a.toString()).append('\n');
        }

        System.out.println(text1.toString());

        try {
            Collections.sort(collection);
        } catch (Exception e) {

        }

        StringBuffer text2 = new StringBuffer();
        for (PhoneCall a:collection) {
            text2.append(a.toString()).append('\n');
        }
        System.out.println("\nAfter sort: ");
        System.out.println(text2.toString());

        System.out.println("\nPhone Bill:");

        PhoneBill bill = new PhoneBill("Thanh Hoang");
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);
        bill.addPhoneCall(call4);
        bill.addPhoneCall(call5);

        System.out.println(bill.toString());

        StringBuffer text3 = new StringBuffer();
        for (Object a: bill.getSortedPhoneCalls()) {
            PhoneCall phonecall = (PhoneCall) a;
            text3.append(phonecall.getEndTimeString()).append('\t').append(phonecall.getCaller()).append('\n');
        }
        System.out.println(text3.toString());


    }

    @Test
    public void testCompareTwoPhoneCallIsEqual() {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date start1 = null;
        Date start2 = null;
        Date end1 = null;
        Date end2 = null;

        try {
            start1 = df.parse("1/1/2015 9:00 am");
            start2 = df.parse("1/1/2015 10:00 am");
            end1 = df.parse("1/1/2015 2:30 pm");
            end2 = df.parse("1/2/2015 4:22 am");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        AbstractPhoneCall c1 = new PhoneCall("000-111-2222", "234-141-4141", start1, end1);
        AbstractPhoneCall c2 = new PhoneCall("000-111-2222", "000-111-2222", start2, end2);

        PhoneCall call1 = (PhoneCall) c1;
        PhoneCall call2 = (PhoneCall) c2;

        if (call1.compareTo(call2) == 0) {
            System.out.println("Two phone calls are equal");
        } else if (call1.compareTo(call2) < 0) {
            System.out.println("Phone call is happened before phone call 2");
        }


    }

    @Test
    public void examComparePhoneNumber(){

        String phone1 = "112-333-4442";
        String phone2 = "111-222-3333";

        int p1 = Integer.parseInt(phone1.replace("-", ""));

        System.out.println(p1 + "\n" + phone1);

    }

    @Test
    public void testPhoneCallNewVersion(){
        String pattern = "MM/dd/yyyy hh:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        Date start = null;
        Date end = null;

        String pattern2 = "MM/dd/yyyy hh:mm a";
        DateFormat df2 = new SimpleDateFormat(pattern2);

        try {
            start = df2.parse("1/12/2015 25:2 pm");
            end = df2.parse("1/12/2015 10:43 am");
            System.out.println("Start time: " + start);
            System.out.println("End time: " + end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AbstractPhoneCall aCall = new PhoneCall("001-222-3333", "222-333-4444", start, end);

        System.out.println("Phone call: \n\tStart time: " + df2.format(aCall.getStartTime()) + "\n\tEnd time: " + df2.format(aCall.getEndTime()));

        System.out.println(aCall.toString());

    }

    @Test
    public void tryDate(){
        String pattern = "MM/dd/yyyy hh:mm";
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        Date date2 = null;
        try {
            date = format.parse("10/01/2015 20:20");
            date2 = format.parse("10/1/2015 20:40");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        System.out.println(format.format(date));
        System.out.println("End time: " + format.format(date2));

        if (date2.after(date)) {
            System.out.println("Date 2 is after date 1");
        }

    }

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertEquals(new Integer(1), result.getExitCode());
    assertTrue(result.getErr().contains( "Missing command line arguments" ));
  }

}