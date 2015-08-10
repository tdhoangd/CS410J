package edu.pdx.cs410J.thhoang.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.util.Date;

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>
{
/*

    @Override
    public String getCaller() {
        return "123-345-6789";
    }

    @Override
    public Date getStartTime() {
        return new Date();
    }

    public String getStartTimeString() {
        return "START " + getStartTime();
    }

    @Override
    public String getCallee() {
        return "345-677-2341";
    }

    public Date getEndTime() {
        return new Date();
    }

    public String getEndTimeString() {
        return "END " + getEndTime();
    }
*/

  //  private static DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy"); /*new SimpleDateFormat("MM/dd/yyyy hh:mm a");*/
    private String callerNumber;
    private String calleeNumber;
    private Date startTime;
    private Date endTime;

    public PhoneCall() {

    }

    public PhoneCall(String callerNumber, String calleeNumber, Date startTime, Date endTime) {
        super();
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTime = startTime;
        this.endTime = endTime;

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
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");

        return dateTimeFormat.format(startTime);
    }

    /**
     * Returns a textual representation of the time that this phone call
     * was completed.
     * @return endTimeString
     */
    @Override
    public String getEndTimeString() {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");

        return dateTimeFormat.format(endTime);
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
    public int timeDifference() {

        long diff = endTime.getTime() - startTime.getTime();

        return (int) (diff/(60*1000));
    }




}
