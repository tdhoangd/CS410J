package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;


import java.util.ArrayList;
import java.util.Collection;


/**
 * This class represents a customer phone bill which have
 * mutilple phone calls
 *
 * @author Thanh Hoang
 */
public class PhoneBill extends AbstractPhoneBill {

    /**
     * Constructor of PhoneBill class
     * @param name
     *      CustomerName
     */
    public PhoneBill(String name) {
        super();
        this.customerName = name;
        this.collection = new ArrayList<AbstractPhoneCall>();
    }

    /**
     * Return the name of the customer whose bill this is
     * @return customerName
     *
     */
    @Override
    public String getCustomer() {
        return customerName;
    }

    /**
     * Add a phone call to this phone bill
     * @param call
     *      a PhoneCall object
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        collection.add(call);
    }

    /**
     * Return all of phone calls in this phone bill
     * @return collection
     *
     */
    @Override
    public Collection getPhoneCalls() {
        return collection;
    }


    private final String customerName;
    private final Collection collection;

}
