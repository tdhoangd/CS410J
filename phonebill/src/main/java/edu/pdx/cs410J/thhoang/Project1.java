package edu.pdx.cs410J.thhoang;


import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.*;
import java.lang.String;
import java.util.ArrayList;

/**
 * The main class for the CS410J Phone Bill Project 1
 */
public class Project1 {

    public static void main(String[] args) {
        String customer = null;
        String callerNumber = null;
        String calleeNumber = null;
        String startDate = null;
        String startTime = null;
        String endDate = null;
        String endTime = null;
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> arguments = new ArrayList<String>();

        if (args.length == 0) {
            printErrMessageAndExit("Missing command line arguments");
        }

        for (String arg : args) {
            if (arg.charAt(0) == '-')
                options.add(arg);
            else
                arguments.add(arg);
        }

        for (String opt : options) {
            if (opt.equals("-README")) {
                printReadMeAndExit();
            }
        }

        for (String arg : arguments) {

            if (customer == null) {
                customer = arg;
            } else if (callerNumber == null) {
                callerNumber = arg;
            } else if (calleeNumber == null) {
                calleeNumber = arg;
            } else if (startDate == null) {
                startDate = arg;
            } else if (startTime == null) {
                startTime = arg;
            } else if (endDate == null) {
                endDate = arg;
            } else if (endTime == null) {
                endTime = arg;
            }
        }

        // print err message and exit if one of argument is missing
        if (customer == null) {
            printErrMessageAndExit("Missing customer");
        } else if (callerNumber == null) {
            printErrMessageAndExit("Missing callerNumber");
        } else if (calleeNumber == null) {
            printErrMessageAndExit("Missing calleeNumber");
        } else if (startDate == null) {
            printErrMessageAndExit("Missing startDate");
        } else if (startTime == null) {
            printErrMessageAndExit("Missing startTime");
        } else if (endDate == null) {
            printErrMessageAndExit("Missing endDate");
        } else if (endTime == null) {
            printErrMessageAndExit("Missing endTime");
        }

        // validate arguments format
        if (!checkPhoneNumber(callerNumber)) {
            printErrMessageAndExit("CallerNumber is not input in the right format: nnn-nnn-nnnn (where n is 0-9)");
        } else if (!checkPhoneNumber(calleeNumber)) {
            printErrMessageAndExit("CalleeNumber is not input is the right format: nnn-nnn-nnnn (where n is 0-9)");
        } else if (!checkDateFormat(startDate)) {
            printErrMessageAndExit("startDate is not input in the right format: mm/dd/yyyy");
        } else if (!checkTimeFormat(startTime)) {
            printErrMessageAndExit("startTime is not input in the right format: hh:mm");
        } else if (!checkDateFormat(endDate)) {
            printErrMessageAndExit("endDate is not input in the right format: mm/dd/yyyy");
        } else if (!checkTimeFormat(endTime)) {
            printErrMessageAndExit("endTime is not input in the right format: hh:mm");
        }

        // add new phone call to collection
        AbstractPhoneBill newBill;
        AbstractPhoneCall aCall;

        try {
            String start = startDate + " " + startTime;
            String end = endDate + " " + endTime;

            newBill = new PhoneBill(customer);
            aCall = new PhoneCall(callerNumber, calleeNumber, start, end);
            newBill.addPhoneCall(aCall);

            System.out.println("Successful added new phone call");

            for (String s : options) {
                if (s.equals("-print")) {
                    System.out.println("Description of new phone call: ");
                    System.out.println(aCall.toString());
                }
            }

        } catch (Exception e) {
            printErrMessageAndExit("Fail to add new phone call");
        }


        System.exit(1);
    }

    private static boolean checkDateFormat(String date) {

        if (date.matches("\\d{2}/\\d{2}/\\d{4}"))
            return true;
        else if (date.matches("\\d{1}/\\d{2}/\\d{4}"))
            return true;
        else if (date.matches("\\d{2}/\\d{1}/\\d{4}"))
            return true;
        else if (date.matches("\\d{1}/\\d{1}/\\d{4}"))
            return true;
        else
            return false;

    }

    private static boolean checkTimeFormat(String time) {

        if (time.matches("\\d{2}:\\d{2}"))
            return true;
        else if (time.matches("\\d{2}:\\d{1}")) {
            return true;
        } else if (time.matches("\\d{1}:\\d{2}")) {
            return true;
        } else if (time.matches("\\d{1}:\\d{1}")) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkPhoneNumber(String phoneNumberString) {

        if (phoneNumberString.matches("\\d{3}-\\d{3}-\\d{4}"))
            return  true;
        else
            return false;

    }

    private static void printReadMeAndExit() {
        File f = new File("README.txt");

        try {
            if (!f.exists())
                f.createNewFile();
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            StringBuffer sb = new StringBuffer();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            System.out.print(sb.toString());
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(" ** " + e);
            System.exit(1);
        }
    }

    private static void printErrMessageAndExit(String message) {
        System.err.println(message);
        System.exit(1);
    }


}