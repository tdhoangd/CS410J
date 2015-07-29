package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */

public class PhoneBillServlet extends HttpServlet
{
    private final Map<String, String> data = new HashMap<>();
    private final Map<String, PhoneBill> phoneBillMap = new HashMap<String, PhoneBill>();
    private PhoneBill phoneBill = null;

    /**
     * Handles an HTTP GET request from a client by writing the value of the key
     * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
     * parameter is not specified, all of the key/value pairs are written to the
     * HTTP response.
     * @param request a
     * @param response a
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter("customer", request);
        String startTime = getParameter("startTime", request);
        String endTime = getParameter("endTime", request);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");


        try {
            if (customer == null && startTime == null && endTime == null) {
                PrintWriter pw = response.getWriter();
                pw.println(Messages.getMappingCount(phoneBillMap.size()));
                pw.flush();
            }

            if (customer != null && phoneBillMap.get(customer) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, " The phone bill for " + customer + " does not found");
            }

            if (customer != null & startTime == null && endTime == null) {
                writePhoneBill(customer, response);
            }

            if (customer != null && startTime != null && endTime != null) {

                Date start = null;
                Date end = null;

                try {
                    start = df.parse(startTime);
                    end = df.parse(endTime);
                } catch (ParseException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, " Date and time is malformed");
                }

                if (end.before(start)) {
                    response.sendError(HttpServletResponse.SC_CONFLICT, " End time is happen before start time" );
                } else {

                    writeSearchResult(customer, start, end, response);

                }

            }

            if (customer != null && startTime != null && endTime == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, " Missing end time ");
            }

            if (customer != null && startTime == null && endTime != null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Missing start time");
            }
        } catch (Exception ex) {
            return;
        }




     /*   String value = getParameter( "key", request );
        if (value != null) {
            writeValue(value, response);

        } else {
            writeAllMappings(response);
        }*/
    }

    /**
     * write search result out
     * @param customer customer name
     * @param start start time
     * @param end end time
     * @param response response
     * @throws IOException
     */
    private void writeSearchResult(String customer, Date start, Date end, HttpServletResponse response) throws IOException
    {
        PrintWriter pw = response.getWriter();
        PhoneBill bill = phoneBillMap.get(customer);
        PrettyPrinter pp = new PrettyPrinter();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String s1 = null;
        String s2 = null;

        s1 = df.format(start);
        s2 = df.format(end);

        pw.println("Search Result for Phone Call Occurred between: " + s1 + " and " + s2 + "\n");

        if (bill.searchPhoneCalls(start, end).size() == 0) {
            pw.println("Found none\n");
        } else {
            pw.println("Found: " + bill.searchPhoneCalls(start,end).size() + " results");
            pw.println(pp.print((ArrayList) bill.searchPhoneCalls(start, end)));
        }

        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * write phone bill
     * @param customer
     * @param response
     * @throws IOException
     */
    private void writePhoneBill(String customer, HttpServletResponse response) throws IOException {

        PhoneBill bill = null;
        if (phoneBillMap.get(customer) != null) {
            bill = phoneBillMap.get(customer);
        }

        PrettyPrinter pp = new PrettyPrinter();
        PrintWriter pw = response.getWriter();


        pw.println("\t\tPHONE BILL FOR " + customer.toUpperCase() + "\n\n");
        pw.println("\tThere is " + bill.getPhoneCalls().size() + " phone calls in this phone bill. The phone calls' details is shown below:\n");

        pw.println(pp.print((ArrayList) bill.getPhoneCalls()));

        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * "key" and "value" request parameters.  It writes the key/value pair to the
     * HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter("customer", request);
        if (customer == null) {
            missingRequiredParameter(response, "Missing customer");
            return;
        }

        String callerNumber = getParameter("callerNumber", request);
        if (callerNumber == null) {
            missingRequiredParameter(response, "Missing callerNumber");
        }

        String calleeNumber = getParameter("calleeNumber", request);
        if (calleeNumber == null) {
            missingRequiredParameter(response, "Missing calleeNumber");
        }

        String startTime = getParameter("startTime", request);
        if (startTime == null) {
            missingRequiredParameter(response, "Missing startTime");
        }

        String endTime = getParameter("endTime", request);
        if (endTime == null) {
            missingRequiredParameter(response, "Missing endTime");
        }

        if (validPhoneNumber(callerNumber) == false) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, callerNumber + " : the phone number is malformed");
        }

        if (validPhoneNumber(calleeNumber) == false) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, calleeNumber + " : the phone number is malformed");
        }

        Date startDateAndTime = null;
        Date endDateAndTime = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        try {
            startDateAndTime = df.parse(startTime);
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, startTime + " is malformed");
            return;
        }

        try {
            endDateAndTime = df.parse(endTime);
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, endTime + " is malformed");
            return;
        }

        AbstractPhoneCall call = new PhoneCall(callerNumber, calleeNumber, startDateAndTime, endDateAndTime);

        if (this.phoneBillMap.get(customer) != null) {
            this.phoneBill.addPhoneCall(call);
        } else {
            PhoneBill temp = new PhoneBill(customer);
            this.phoneBill = temp;
            this.phoneBill.addPhoneCall(call);
            this.phoneBillMap.put(customer, this.phoneBill);
        }

        PrintWriter pw = response.getWriter();
        pw.println("Added: " + call.toString());
        pw.flush();



  /*      this.data.put(key, value);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.mappedKeyValue(key, value));
        pw.flush();*/

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(parameterName));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * Writes the value of the given key to the HTTP response.
     *
     * The text of the message is formatted with {@link Messages#getMappingCount(int)}
     * and {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeValue( String key, HttpServletResponse response ) throws IOException
    {
        String value = this.data.get(key);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        pw.println(Messages.formatKeyValuePair(key, value));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the key/value pairs to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(data.size()));

        for (Map.Entry<String, String> entry : this.data.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue()));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    /**
     * validate phone number
     * @param phoneNo phone string
     * @return
     */
    private boolean validPhoneNumber(String phoneNo) {

        if (phoneNo == null || !phoneNo.matches("\\d{3}-\\d{3}-\\d{4}")) {
            return false;
        } else {
            return true;
        }
    }

}
