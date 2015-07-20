package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;


/**
 * This class represents a phone call between a caller (the
 * phone number of the person who originates the call) and callee (the
 * phone number of the person whose receives the phone call).  Phone
 * calls begin and end at given times.
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable {


    /**
     * Returns the time that this phone call was originated as a
     * {@link Date}.
     * @return
     */
    @Override
    public Date getStartTime() {

        return null;
    }

    /**
     * Returns the time that this phone call was completed as a
     * {@link Date}.
     * @return
     */
    @Override
    public Date getEndTime() {

        return null;
    }

    /**
     * Create a new <code>PhoneCall</code>
     *
     * @param callerNumber
     *      Phone number of person who originated this phone call
     * @param calleeNumber
     *      Phone number of person who received this phone call
     * @param startTimeString
     *      Start time of the phone call
     * @param endTimeString
     *      End time of the phone call
     */
    public PhoneCall(String callerNumber, String calleeNumber, String startTimeString, String endTimeString) {
        super();
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;

    }

    /**
     * Returns the phone number of the person who originated this phone
     * call.
     * @return Return the callerNumber
     */
    @Override
    public String getCaller() {
        return callerNumber;
    }

    /**
     * Returns the phone number of the person who received this phone
     * call
     * @return Return the calleeNumber
     */
    @Override
    public String getCallee() {
        return calleeNumber;
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was originated.
     * @return startTimeString
     */
    @Override
    public String getStartTimeString() {
        return startTimeString;
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was completed.
     * @return endTimeString
     */
    @Override
    public String getEndTimeString() {
        return endTimeString;
    }


    private String callerNumber;
    private String calleeNumber;
    private String startTimeString;
    private String endTimeString;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
