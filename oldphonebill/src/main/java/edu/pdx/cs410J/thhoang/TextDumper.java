package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;

/**
 * Class that dumps the contents of a phone bill (including its calls)
 * to a text file.
 */
public class TextDumper implements PhoneBillDumper {

    private File file;

    TextDumper(File file) {
        this.file = file;
    }

    /**
     *  Dumps a phone bill to some destination.
     * @param bill a phone bill
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        AbstractPhoneCall aCall;

        try {

            // Check if file exist or not
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter  fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            if (bill.getCustomer() != null) {

                bw.write("Name: " + bill.getCustomer());

                for (Object object: bill.getPhoneCalls()) {
                    aCall = (AbstractPhoneCall) object;

                    bw.newLine();
                    bw.write(aCall.getCaller() + ";" + aCall.getCallee() + ";");
                    bw.write(aCall.getStartTimeString() + ";" + aCall.getEndTimeString());

                }
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new IOException(e.getCause());
        }

    }
}
