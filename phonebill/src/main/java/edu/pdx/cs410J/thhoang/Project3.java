package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * The main class for CS410J Phone Bill Project 2,
 * (working with text file)
 */
public class Project3 {

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
        ArrayList<String> inputs = new ArrayList<String>();
        String filePath = null;
        String strPrettyFile = null;
        boolean textFileOption = false;
        boolean printOption = false;
        boolean prettyOption = false;
        File file = null;
        File prettyFile = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");


        if (args.length == 0) {
            printErrMessageAndExit("Missing command line arguments");
        }

        // Existing -README option
        for (String arg : args) {
            inputs.add(arg);
            if (arg.equals("-README")) {
                printReadMeAndExit();
            }
            if (arg.equals("-print")) {
                printOption = true;
            }
            if (arg.equals("-textFile")) {
                textFileOption = true;
            }
            if (arg.equals("-pretty")) {
                prettyOption = true;
            }

            if ((arg.charAt(0) == '-') && (arg.length() > 1)) {
                options.add(arg);
            }
        }

        // check weird option
        for (String opt : options) {

            switch (opt) {
                case "-print":
                    break;
                case "-README":
                    break;
                case "-textFile":
                    break;
                case "-pretty":
                    break;
                default:
                    printErrMessageAndExit("Error: option" + opt + " not found");
                    break;
            }

        }

        // Get filePath
        if (inputs.contains("-textFile")) {

            int j = inputs.indexOf("-textFile");

            if ((j + 1) == inputs.size()) {
                printErrMessageAndExit("Missing file path");
            } else if (j < (inputs.size() - 1)) {

                String temp = inputs.get(j + 1);
                if (temp.charAt(0) == '-') {
                    printErrMessageAndExit("Missing file path");
                } else {
                    filePath = temp;
                }

            }
        }

        // check if command line argument following pretty is - or file,
        // if not issue error
        if (prettyOption) {

            int j = inputs.indexOf("-pretty");

            if ((j+1) == inputs.size()) {
                printErrMessageAndExit("Error: missing pertty file");
            } else if ( j < (inputs.size() - 1)) {

                String temp = inputs.get(j+1);
                if ((temp.charAt(0) ==  '-') && (temp.length() == 1)) {

                    if (filePath == null) {
                        strPrettyFile = "out.txt";
                    } else if (filePath.equals("out.txt")) {
                        strPrettyFile = "prettyout.txt";
                    } else {
                        strPrettyFile = "out.txt";
                    }
                } else if ((temp.charAt(0) ==  '-') && (temp.length() > 1)) {
                    printErrMessageAndExit("Error: missing pretty file or - (stand for stardard file");
                } else {
                    strPrettyFile = temp;
                }
            }

        }

        // check if file path and pretty file are different

        if ( filePath != null && strPrettyFile != null) {
            if (filePath.equals(strPrettyFile)) {
                printErrMessageAndExit("Error: file path and pretty file need to be different");
            }
        }

        if (inputs.contains(filePath)) {

            inputs.remove(filePath);
        }

        if (inputs.contains(strPrettyFile)) {
            inputs.remove(strPrettyFile);
        }

        for (String s : inputs) {

            if (s.charAt(0) != '-')
                arguments.add(s);
        }

        if (arguments.size() > 9) {
            printErrMessageAndExit("Error: There are extraneous command line arguments ");
        }


        String a1 = null;
        String a2 = null;

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
            } else if (a1 == null) {
                a1 = arg;
            } else if (endDate == null) {
                endDate = arg;
            } else if (endTime == null) {
                endTime = arg;
            } else if (a2 ==null) {
                a2 = arg;
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

        if (arguments.size() < 9) {
            printErrMessageAndExit("Error: missing arguments");
        }

        // validate arguments format
        if (!checkPhoneNumber(callerNumber)) {
            printErrMessageAndExit("callerNumber is not input in the right format: nnn-nnn-nnnn (where n is 0-9)");
        } else if (!checkPhoneNumber(calleeNumber)) {
            printErrMessageAndExit("calleeNumber is not input in the right format: nnn-nnn-nnnn (where n is 0-9)");
        } else if (!checkDateFormat(startDate)) {
            printErrMessageAndExit("startDate is not input in the right format: mm/dd/yyyy");
        } else if (!checkTimeFormat(startTime)) {
            printErrMessageAndExit("startTime is not input in the right format: hh:mm");
        } else if (!checkDateFormat(endDate)) {
            printErrMessageAndExit("endDate is not input in the right format: mm/dd/yyyy");
        } else if (!checkTimeFormat(endTime)) {
            printErrMessageAndExit("endTime is not input in the right format: hh:mm");
        }

        if (!a1.toLowerCase().equals("am") && !a1.toLowerCase().equals("pm")) {
            printErrMessageAndExit("Error: am/pm");
        } else if (!a2.toLowerCase().equals("am") && !a2.toLowerCase().equals("pm")) {
            printErrMessageAndExit("Error: am/pm");
        }

        // add new phone call to collection
        PhoneBill phonebill = null;
        PhoneCall aCall = null;

        // create a new phone call
        String start = startDate + " " + startTime + " " + a1;
        String end = endDate + " " + endTime + " " + a2;
        try {
            aCall = new PhoneCall(callerNumber, calleeNumber, df.parse(start), df.parse(end));
        } catch (ParseException e) {
            printErrMessageAndExit("Error: cant create new phone call");
        }

        // load file to phone bill
        if (textFileOption && (filePath != null)) {

            file = new File(filePath);

            if (!file.exists()) {

            } else {

                try {
                    TextParser tp = new TextParser(new File(filePath));
                    phonebill = (PhoneBill) tp.parse();

                    System.out.println("Loading text file");

                    if (phonebill == null) {
                        printErrMessageAndExit("Text file is not the right format");
                    } else {

                        if (phonebill.getCustomer().equals(customer) == false) {
                            printErrMessageAndExit("Error: customers are not similar");
                        }
                    }

                    System.out.println("Loaded text file");

                } catch (ParserException e) {
                    System.err.println("Error: cant parse text file to phone bill");
                }
            }

        }

        // print description of new phone call
        if (printOption) {
            System.out.println("Description of new phone call");
            System.out.println(aCall.toString());
        }

        // create new phone bill
        if (phonebill == null) {

            phonebill = new PhoneBill(customer);
            phonebill.addPhoneCall(aCall);
        }


        // save phone bill to text file
        if (textFileOption && (filePath != null)) {

            if (phonebill == null) {
                phonebill = new PhoneBill(customer);
            }

            phonebill.addPhoneCall(aCall);
            TextDumper td = new TextDumper(new File(filePath));

            try {
                System.out.println("Saving phone bill to text file");
                td.dump(phonebill);
                System.out.println("Successful saved phone bill to text file");
            } catch (IOException e) {
                System.err.println(e.getMessage());
                printErrMessageAndExit("Error: cant dump phone bill to text file");
            }
        }

        // pretty print
        if (prettyOption) {

            try {
                System.out.println("Creating pretty print file");
                PrettyPrinter pp = new PrettyPrinter(new File(strPrettyFile));
                pp.dump(phonebill);
                System.out.println("Successful created pretty print file");
            } catch (IOException e) {
                System.err.println(e.getMessage());
                printErrMessageAndExit("Error: cant print pretty print");
            }
        }


        System.exit(1);
    }

    /**
     * Validate a date is in mm/dd/yyyy or not
     *
     * @param date string date format in form mm/dd/yyyy
     * @return boolean
     */
    private static boolean checkDateFormat(String date) {

        if (date.matches("\\d{2}/\\d{2}/\\d{4}"))
            return true;
        else if (date.matches("\\d{1}/\\d{2}/\\d{4}"))
            return true;
        else if (date.matches("\\d{2}/\\d{1}/\\d{4}"))
            return true;
        else return date.matches("\\d{1}/\\d{1}/\\d{4}");

    }

    /**
     * Validate if time is in hh:mm format or not
     *
     * @param time time string in form hh:mm
     * @return boolean
     */
    private static boolean checkTimeFormat(String time) {

        if (time.matches("\\d{2}:\\d{2}"))
            return true;
        else if (time.matches("\\d{2}:\\d{1}")) {
            return true;
        } else if (time.matches("\\d{1}:\\d{2}")) {
            return true;
        } else return time.matches("\\d{1}:\\d{1}");
    }

    /**
     * Validate if the phone number is in the format nnn-nnn-nnnn
     *
     * @param phoneNumberString phone number string in form nnn-nnn-nnnn
     * @return boolean
     */
    private static boolean checkPhoneNumber(String phoneNumberString) {

        return phoneNumberString.matches("\\d{3}-\\d{3}-\\d{4}");

    }

    /**
     * Print out README  and exit program.
     */
    private static void printReadMeAndExit() {

        System.out.println("Project 3");
        System.out.println("@author: Thanh Hoang");
        System.out.println("This project is built from project 3; adding sorting and pretty print ability");
        System.out.println("The command line has to be in this order:");
        System.out.println("\t[options] <args>");
        System.out.println("options are:");
        System.out.println("\t-print\t\tprints a description of the new phone call");
        System.out.println("\t-README\t\tprints a README for this project and exits");
        System.out.println("\t-textFile file \t\t Where to read/write the phone bill");
        System.out.println("\t-pretty file \t\t print a phone bill to a text file or stardard out (file -)");
        System.out.println("args are (in this order):");
        System.out.println("\tcustomer\tperson whose phone bill we're modeling");
        System.out.println("\tcallerNumber\tPhone number of caller (in format nnn-nnn-nnnn)");
        System.out.println("\tcalleeNumber\tphone number of person who was called (in format nnn-nnn-nnnn)");
        System.out.println("\tstartTime\tDate and time call began (12- hour time)");
        System.out.println("\tendTime \tDate and time call end (12-hour time)");
        System.out.println("\t\t\tDate and time in this format :\tmm/dd/yyyy hh:mm");

        System.exit(1);

    }

    /**
     * Print out error message and exit program.
     *
     * @param message Error message
     */
    private static void printErrMessageAndExit(String message) {
        System.err.println(message);
        System.exit(1);
    }


}
