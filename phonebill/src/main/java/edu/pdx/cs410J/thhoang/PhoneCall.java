package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

/**
 * This class represents a phone call between a caller (the
 * phone number of the person who originates the call) and callee (the
 * phone number of the person whose receives the phone call).  Phone
 * calls begin and end at given times.
 */
public class PhoneCall extends AbstractPhoneCall {

    /**
     *
     * @param callerNumber
     *      Phone number of person who originated this phone call
     * @param calleeNumber
     *      Phone number of person who received this phone call
     * @param startTime
     *      Start time of the phone call
     * @param endTimeString
     *      End time of the phone call
     */
    public PhoneCall(String callerNumber, String calleeNumber, String startTimeString, String endTimeString) {
        super();
        setCallerNumber(callerNumber);
        setCalleeNumber(calleeNumber);
        setStartTimeString(startTimeString);
        setEndTimeString(endTimeString);
    }

    /**
     *
     * @param endTimeString
     */
    private void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    /**
     *
     * @param startTimeString
     */
    private void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    /**
     *
     * @param callerNumber
     */
    private void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

    /**
     *
     * @param calleeNumber
     */
    private void setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
    }

    /**
     * Returns the phone number of the person who originated this phone
     * call.
     * @return
     *      Return the callerNumber
     */
    @Override
    public String getCaller() {
        return callerNumber;
    }

    /**
     * Returns the phone number of the person who received this phone
     * call
     * @return
     *      Return the calleeNumber
     */
    @Override
    public String getCallee() {
        return calleeNumber;
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was originated.
     * @return
     *      startTimeString
     */
    @Override
    public String getStartTimeString() {
        return startTimeString;
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was completed.
     * @return
     *      endTimeString
     */
    @Override
    public String getEndTimeString() {
        return endTimeString;
    }


    private String callerNumber;
    private String calleeNumber;
    private String startTimeString;
    private String endTimeString;

}
