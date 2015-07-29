package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class represents a phone call between a caller (the
 * phone number of the person who originates the call) and callee (the
 * phone number of the person whose receives the phone call).  Phone
 * calls begin and end at given times.
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

    private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    private String callerNumber;
    private String calleeNumber;
    private String startTimeString;
    private String endTimeString;
    private Date startTime;
    private Date endTime;

    public PhoneCall(String callerNumber, String calleeNumber, Date startTime, Date endTime) {
        super();
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTime = startTime;
        this.endTime = endTime;

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
     * Returns the time that this phone call was originated as a
     * {@link Date}.
     * @return
     */
    @Override
    public Date getStartTime() {

        return startTime;
    }

    /**
     * Returns the time that this phone call was completed as a
     * {@link Date}.
     * @return end time in Date formate
     */
    @Override
    public Date getEndTime() {

        return endTime;
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
        return dateFormat.format(getStartTime());
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was completed.
     * @return endTimeString
     */
    @Override
    public String getEndTimeString() {
        return dateFormat.format(getEndTime());
    }

    /**
     * Overried compareTo method
     * @param aCall a phone call
     * @return int
     */
    @Override
    public int compareTo(PhoneCall aCall) {


        if (startTime.before(aCall.getStartTime()))
            return -1;
        else if (startTime.after(aCall.getStartTime()))
            return 1;
        else {

            try {
                int phone1 = Integer.parseInt(callerNumber.replace("-", ""));
                int phone2 = Integer.parseInt(aCall.getCaller().replace("-", ""));

                if (phone1 < phone2)
                    return -1;
                else if (phone1 > phone2)
                    return 1;
                else
                    return 0;
            } catch (NumberFormatException e) {
                return 0;
            }


        }
    }

    /**
     * Get duration in minutes of a phone call
     * @return int
     */
    public long timeDifference() {

        long diff = endTime.getTime() - startTime.getTime();

        return (int) (diff/(60*1000));
    }


}
