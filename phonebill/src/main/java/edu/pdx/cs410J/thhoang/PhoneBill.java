package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;


import java.util.Collection;

/**
 * This class represents a customer phone bill which have
 * mutilple phone calls
 *
 * @author Thanh Hoang
 */
public class PhoneBill extends AbstractPhoneBill {


    /**
     * Return the name of the customer whose bill this is
     */
    @Override
    public String getCustomer() {
        return null;
    }

    /**
     * Add a phone call to this phone bill
     * @param call
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall call) {

    }

    /**
     * Return all of phone calls in this phone bill
     * @return
     */
    @Override
    public Collection getPhoneCalls() {
        return null;
    }


    /**
     *  public String toString() {
     *      return this.getCustomer() + "'s phone bill with " +
     *          this.getPhoneCalls().size() + " phone calls";
     *  }
     */

    private String customerName;


}
