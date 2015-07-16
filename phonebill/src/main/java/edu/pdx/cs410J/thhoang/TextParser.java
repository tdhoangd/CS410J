package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;

/**
 * Class that reads the contents of a text file and from it
 * creates a phone bill with phone calls.
 */
public class TextParser implements PhoneBillParser {

    private File file;

    /**
     * Constructor of TextParser class
     * @param file file
     */
    TextParser(File file) {
        this.file = file;
    }

    /**
     * Dumps a phone bill to some destination.
     * @return bill or null (if file not found or malformatted
     * @throws ParserException
     *      if source can not be parsed.
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        AbstractPhoneCall aCall;
        AbstractPhoneBill bill = null;
        BufferedReader br;
        String message = "Error: File is not in the right format.";

        try {
            br = new BufferedReader(new FileReader(file));

            String currentLine;
            String [] strs;

            // Check 1sts line is customer name line or not

            currentLine = br.readLine();

            if (!file.exists())
                return null;

            if (!checkCustomerLine(currentLine)) return null;
            else {
                String name = currentLine.replace("Name: ", "");
                bill = new PhoneBill(name);
            }

            while ((currentLine = br.readLine()) != null) {
                if (checkPhoneCallLine(currentLine)) {

                    strs = currentLine.split(";");
                    aCall = new PhoneCall(strs[0], strs[1], strs[2], strs[3]);
                    bill.addPhoneCall(aCall);
                }
                else {
                    return null;
                }
            }

            if (br != null)
                br.close();


        } catch (Exception e) {
            System.err.println(message);
        }

        return bill;
    }

    /**
     *  Check customer line in format <code>"Name :" customerName</code>
     * @param currentLine string line
     * @return boolean
     */
    private boolean checkCustomerLine(String currentLine) {

        if (currentLine == null)
            return false;
        else if (currentLine.contains("Name: ")) {
            String s = currentLine;

            s.replace("Name: ", "");

            if (s.trim() != null) {
                return true;
            } else {
                return false;
            }


        } else {
            return false;
        }
    }

    /**
     * Check phone call line
     * @param currentLine string line
     * @return boolean
     */
    private boolean checkPhoneCallLine(String currentLine) {

        String[] strings = currentLine.split(";");

        try {
            return 4 == strings.length && (checkPhoneNumber(strings[0]) && checkPhoneNumber(strings[1]) && checkTime(strings[2]) && checkTime(strings[3]));
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Validate time is in mm/dd/yyyy hh:mm format or not
     * @param time string present time in format mm/dd/yyyy hh:mm
     * @return boolean
     */
    private boolean checkTime(String time) {
        String[] strings = time.split(" ");

        try {
            return strings.length == 2 && (checkDateFormat(strings[0]) && checkTimeFormat(strings[1]));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate a date is in mm/dd/yyyy or not
     * @param date string present date
     * @return boolean
     */
    private boolean checkDateFormat(String date) {

        if (date.matches("\\d{2}/\\d{2}/\\d{4}"))
            return true;
        else if (date.matches("\\d/\\d{2}/\\d{4}"))
            return true;
        else if (date.matches("\\d{2}/\\d/\\d{4}"))
            return true;
        else return date.matches("\\d/\\d/\\d{4}");

    }

    /**
     * Validate if time is in hh:mm format or not
     * @param time string represent time
     * @return boolean
     */
    private boolean checkTimeFormat(String time) {

        return (time.matches("\\d{2}:\\d{2}") || time.matches("\\d{2}:\\d") || time.matches("\\d:\\d{2}") ||time.matches("\\d{1}:\\d{1}"));
    }

    /**
     * Validate if the phone number is in the format nnn-nnn-nnnn
     * @param phoneNumberString string represented phone number
     * @return boolean
     */
    private boolean checkPhoneNumber(String phoneNumberString) {

        return phoneNumberString.matches("\\d{3}-\\d{3}-\\d{4}");

    }
}
