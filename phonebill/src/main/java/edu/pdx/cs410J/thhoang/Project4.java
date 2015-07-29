package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";
    private static boolean hostOption = false;
    private static boolean portOption = false;
    private static boolean searchOption = false;
    private static boolean printOption = false;

    private static String hostName = null;
    private static String portString = null;
    private static Date startTime = null;
    private static Date endTime = null;
    private static String customer = null;
    private static String caller = null;
    private static String callee = null;

    public static void main(String... args) {

        String key = null;
        String value = null;

        ArrayList<String> inputs = new ArrayList<String>();


        if (args.length == 0) {
            usage(MISSING_ARGS);
        }

        for (String s : args) {
            if ("-README".equals(s)) {
                printReadMeAndExit();
            }

            inputs.add(s);

            if ("-host".equals(s)) hostOption = true;
            if ("-port".equals(s)) portOption = true;
            if ("-search".equals(s)) searchOption = true;
            if ("-print".equals(s)) printOption = true;

            if (s != null && s.charAt(0) == '-') {
                switch (s) {
                    case "-print":
                        break;
                    case "-host":
                        break;
                    case "-port":
                        break;
                    case "-search":
                        break;
                    case "-README":
                        break;
                    default:
                        usage("Option " + s + " not found");
                        break;
                }
            }

        }

        if (inputs.contains("-print") && inputs.contains("-search")) {
            error("Cant handle both print and search at the same time");
        }

        parsingCommandLine(inputs);

        String startTimeString = null;
        String endTimeString = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        try {
            startTimeString = df.format(startTime);
            endTimeString = df.format(endTime);
        } catch (Exception ex) {

        }

        if (searchOption) {

            if (hostName == null) {
                usage(MISSING_ARGS);
            } else if (portString == null) {
                usage("Missing port");
            }

            int port;
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException ex) {
                usage("Port \"" + portString + "\" must be an integer");
                return;
            }

            PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

            HttpRequestHelper.Response response;
            try {
                response = client.searchPhoneCall(customer, startTimeString, endTimeString);
            } catch (IOException ex) {
                error("While contacting server: " + ex);
                return;
            }

            System.out.println(response.getContent());
            System.exit(0);

        } else {

            AbstractPhoneCall call = new PhoneCall(caller,  callee, startTime, endTime);

            if (printOption) {
                System.out.println("Description of new phone call:");
                System.out.println(call.toString());
            }

            if (portString != null) {
                int port;
                try {
                    port = Integer.parseInt(portString);
                } catch (NumberFormatException ex) {
                    usage("Port \"" + portString + "\" must be an integer");
                    return;
                }

                PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

                HttpRequestHelper.Response response;
                try {
                    response = client.addPhoneCall(customer, caller, callee, startTimeString, endTimeString);
                    checkResponseCode(HttpURLConnection.HTTP_OK, response);

                } catch (IOException ex) {
                    error("While contacting server: " + ex);
                    return;
                }

                System.out.println(response.getContent());
            }




            System.exit(0);
        }
        System.exit(0);
    }


    /**
     * parsing command line
     * @param args
     */
    public static void parsingCommandLine(ArrayList args) {

        String startDate = null;
        String startTimeStr = null;
        String startMeridiem = null;
        String endDate = null;
        String endTimeStr = null;
        String endMeridiem = null;
        String s = null;

        if (args.contains("-print")) args.remove("-print");
        if (args.contains("-search")) args.remove("-search");

        if ((hostOption ^ portOption) == false) {

            // get hostName, portString; then remove them out of args list
            if (hostOption == true && portOption == true) {

                if (args.indexOf("-host") + 1 < args.size()) {
                    hostName = (String) args.get(args.indexOf("-host") + 1);
                    args.remove("-host");
                    args.remove(hostName);
                }

                if (args.indexOf("-port") + 1 < args.size()) {
                    portString = (String) args.get(args.indexOf("-port") + 1);
                    args.remove("-port");
                    args.remove(portString);
                }

                if (hostName == null) {
                    usage(MISSING_ARGS);
                } else if (portString == null) {
                    usage("Missing port");
                }
            }

            if (searchOption) {

                args.remove("-search");
                for (int i = 0; i < args.size(); i++) {
                    s = (String) args.get(i);

                    if (customer == null) customer = s;
                    else if (startDate == null) startDate = s;
                    else if (startTimeStr == null) startTimeStr = s;
                    else if (startMeridiem == null) startMeridiem = s;
                    else if (endDate == null) endDate = s;
                    else if (endTimeStr == null) endTimeStr = s;
                    else if (endMeridiem == null) endMeridiem = s;
                    else {
                        usage("Extraneous command line argument: " + s);
                    }
                }

                if (customer == null) usage("Missing customer");
                else if (startDate == null ) usage("Missing start date");
                else if (endDate == null) usage("Missing end date");

                startTime = checkDateAndTimeFormat(startDate, startTimeStr, startMeridiem);
                endTime = checkDateAndTimeFormat(endDate, endTimeStr, endMeridiem);

            } else {

                if (printOption)
                    args.remove("-print");

                for (int i = 0; i < args.size(); i++) {
                    s = (String) args.get(i);

                    if (customer == null) customer = s;
                    else if (caller == null) caller = s;
                    else if (callee == null) callee = s;
                    else if (startDate == null) startDate = s;
                    else if (startTimeStr == null) startTimeStr = s;
                    else if (startMeridiem == null) startMeridiem = s;
                    else if (endDate == null) endDate = s;
                    else if (endTimeStr == null) endTimeStr = s;
                    else if (endMeridiem == null) endMeridiem = s;
                    else {
                        usage("Extraneous command line argument: " + s);
                    }
                }

                if (customer == null) usage("Missing customer");
                else if (caller == null) usage("Missing caller number");
                else if (callee == null) usage("Missing callee number");
                else if (startDate == null) usage("Missing start date");
                else if (endDate == null) usage("Missing end date");

                validPhoneNumber(caller);
                validPhoneNumber(callee);
                startTime = checkDateAndTimeFormat(startDate, startTimeStr, startMeridiem);
                endTime = checkDateAndTimeFormat(endDate, endTimeStr, endMeridiem);

            }
        } else {

            usage("Have to specify both host and port.");
        }


    }

    /**
     * check date and time format
     * @param dateStr
     * @param time
     * @param a
     * @return
     */
    public static Date checkDateAndTimeFormat(String dateStr, String time, String a) {

        Date date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String dateAndTimeString = dateStr + " " + time + " " + a;

        try {
            date = df.parse(dateAndTimeString);
        } catch (ParseException e) {
            error(dateAndTimeString + " is malformed");
        }

        return date;
    }

    /**
     * check phone no
     * @param phoneNo
     */
    public static void validPhoneNumber(String phoneNo) {

        if (phoneNo == null || !phoneNo.matches("\\d{3}-\\d{3}-\\d{4}")) {
            error(phoneNo + " is malformed");
        }
    }


    /**
     * Print out README  and exit program.
     */
    private static void printReadMeAndExit() {

        System.out.println("Project 4");
        System.out.println("@author: Thanh Hoang");
        System.out.println("This is readme for phonebill project 4");
        System.out.println("The command line has to be in this order: \n" +
                "[options] <args> \n" +
                "\tOptions:\n" +
                "\t\t-host hostname     Host computer on which the server runs\n" +
                "\t\t-port port         Port on which the server is listening\n" +
                "\t\t-search            Phone calls should be searcher for" +
                "\n\t\t-print           Prints a description of the new phone call\n" +
                "\t\t-REAME             Prints a README for this project and exits\n" +
                "\tArgs:    \n" +
                "\t\tcustomer           Person whose phone bill we're modeling\n" +
                "\t\tcallerNumber       Phone number of caller\n" +
                "\t\tcallerNumber       Phone number of person who was  called\n" +
                "\t\tstartTime          Date and time call began\n" +
                "\t\tendTime            Date and time call ended\n");

        System.exit(1);

    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    /**
     *
     * @param message
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 [options] <args>");
        err.println("args are (in this order):");
        err.println("   customer        Person whose phone bill we’re modeling");
        err.println("   callerNumber    Phone number of caller");
        err.println("   calleeNumber    Phone number of person who was called");
        err.println("   startTime       Date and time call began");
        err.println("   endTime         Date and time call ended");
        err.println("options are (options may appear in any order):");
        err.println("   -host hostname  Host computer on which the server runs");
        err.println("   -port port      Port on which the server is listening");
        err.println("   -search         Phone calls should be searched for");
        err.println("   -print          Prints a description of the new phone call");
        err.println("   -README         Prints a README for this project and exits");
        err.println();
        err.println("It is error to specify a host without a port and vice versa.");
        err.println("If the -search option is provided, only the customer, startTime and endTime are required.");
        err.println("It is error to specify both -search and -print at same time");
        err.println();

        System.exit(1);
    }


}