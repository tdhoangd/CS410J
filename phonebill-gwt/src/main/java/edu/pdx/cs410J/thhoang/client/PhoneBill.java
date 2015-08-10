package edu.pdx.cs410J.thhoang.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.*;

public class PhoneBill extends AbstractPhoneBill
{/*
    private Collection<AbstractPhoneCall> calls = new ArrayList<AbstractPhoneCall>();

    @Override
    public String getCustomer() {
        return "CS410J";
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public Collection getPhoneCalls() {
        return this.calls;
    }*/



    private String customerName;
    private List<AbstractPhoneCall> collection = new ArrayList<AbstractPhoneCall>();

    public PhoneBill(){
        super();
        this.customerName = null;
    }

    /**
     * Constructor of PhoneBill class
     * @param name CustomerName
     */
    public PhoneBill(String name) {
        super();
        this.customerName = name;
        // this.collection = new ArrayList<PhoneCall>();
    }

    /**
     * Return the name of the customer whose bill this is
     * @return customerName
     */
    @Override
    public String getCustomer() {
        return customerName;
    }

    /**
     * Add a phone call to this phone bill
     * @param call a PhoneCall object
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        collection.add(call);
    }

    /**
     * Return all of phone calls in this phone bill
     * @return collection
     */
    @Override
    public Collection getPhoneCalls() {
        return getSortedPhoneCalls();
    }

    /**
     * Return a sorted phone call list
     * @return arraylist of phone call
     */
    public Collection getSortedPhoneCalls() {

        ArrayList<PhoneCall> aList = new ArrayList<PhoneCall>();
        PhoneCall aCall = null;

        for (AbstractPhoneCall a : collection) {

            aCall = (PhoneCall) a;
            aList.add(aCall);
        }

        Collections.sort(aList);

        return aList;
    }

    /**
     * Searching phone calls occurred betweeen start time and end time
     * @param start start time
     * @param end end time
     * @return return arraylist of sorted phone calls in time frame between start time and end time
     */
    public Collection searchPhoneCalls(Date start, Date end) {
        ArrayList<PhoneCall> resultList = new ArrayList<PhoneCall>();
        ArrayList<PhoneCall> fullPhoneCallsList = (ArrayList<PhoneCall>) getSortedPhoneCalls();
        PhoneCall call = null;


        for (Object o : fullPhoneCallsList) {
            call = (PhoneCall) o;

            if (start.before(call.getStartTime()) || start.equals(call.getStartTime())) {

                if (end.after(call.getStartTime()) || end.equals(call.getStartTime())) {
                    resultList.add(call);
                }
            }
        }

        return resultList;
    }


}
