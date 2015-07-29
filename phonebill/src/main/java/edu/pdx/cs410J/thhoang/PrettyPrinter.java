package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;


/**
 * The class that implements <code>PhoneBillDumper</code> interface and creates
 * a nicely formatted textual presentation of the phone calls in a phone bill
 */
public class PrettyPrinter implements PhoneBillDumper {

    private BufferedWriter bw;

    PrettyPrinter() {
        this.bw = null;
    }

    /**
     * Constructor
     * @param bw
     */
    PrettyPrinter(BufferedWriter bw) {
        this.bw = bw;
    }

    /**
     * Constructor
     * @param file file
     * @throws IOException
     */
    public PrettyPrinter(File file) throws IOException {
        this.bw = new BufferedWriter(new FileWriter(file));
    }

    /**
     * Constructor
     * @param fileName file name in string format
     * @throws IOException
     */
    public PrettyPrinter(String fileName) throws IOException {
        this(new File(fileName));
    }

    /**
     * Create a niely formatted textual presentation of the phone calls in a phone bill.
     * @param bill
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        PhoneCall aCall;
        PhoneBill sortedBill;
        sortedBill = (PhoneBill) bill;
        String line;
        int i = 0;


        String formatted;

        if (bill != null) {

            bw.write("\t\t");
            bw.write(bill.getCustomer() + "'s PHONE BILL");
            bw.newLine();
            bw.newLine();

            formatted = String.format("No:  %-20s %-20s %-15s %-15s %-12s", "Start Time", "End Time", "Caller", "Callee", "Duration");
            bw.write(formatted);
            bw.newLine();
            formatted = String.format("%-5s%-20s %-20s %-15s %-15s %12s", "----", "-------------------", "-------------------", "-----------", "-----------", "-(minutes)-");
            bw.write(formatted);

            for (Object object : sortedBill.getSortedPhoneCalls()) {

                aCall = (PhoneCall) object;
                i++;

                formatted = String.format("%-5d%-20s %-20s %-15s %-15s %12s", i, aCall.getStartTimeString(), aCall.getEndTimeString(), aCall.getCaller(), aCall.getCallee(), aCall.timeDifference());
                bw.newLine();
                bw.write(formatted);


            }
        }

        bw.flush();
        bw.close();
    }

    /**
     *
     * @param phoneCalls
     * @return
     */
    public String print(ArrayList phoneCalls) {

        PhoneCall aCall = null;
        String prettyPrintPhoneCalls = "\n";
        int i = 0;

        prettyPrintPhoneCalls += String.format("No:  %-22s %-22s %-15s %-15s %-12s", "Start Time", "End Time", "Caller", "Callee", "Duration");


        for (Object o : phoneCalls) {
            aCall = (PhoneCall) o;
            i++;

            prettyPrintPhoneCalls += "\n";
            prettyPrintPhoneCalls += String.format("%-5d%-22s %-22s %-15s %-15s %12s", i, aCall.getStartTimeString(), aCall.getEndTimeString(), aCall.getCaller(), aCall.getCallee(), aCall.timeDifference());

        }

        return prettyPrintPhoneCalls;
    }

}
