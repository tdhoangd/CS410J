package edu.pdx.cs410J.thhoang.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.thhoang.client.PhoneBill;
import edu.pdx.cs410J.thhoang.client.PhoneCall;
import edu.pdx.cs410J.thhoang.client.PhoneCallService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thanhhoang on 8/5/15.
 */
public class PhoneCallServiceImpl extends RemoteServiceServlet implements PhoneCallService {

    Map<String, PhoneBill> phoneBillMap = new HashMap<String, PhoneBill>();

    @Override
    public AbstractPhoneBill add(String name, PhoneCall call) {

        PhoneBill bill = null;
        if (this.phoneBillMap.get(name) == null) {
            bill = new PhoneBill(name);
            bill.addPhoneCall(call);
            this.phoneBillMap.put(name, bill);
        } else {
            bill = this.phoneBillMap.get(name);
            bill.addPhoneCall(call);
            this.phoneBillMap.put(name, bill);
        }


        return bill;
    }

    @Override
    public AbstractPhoneBill search(String name, Date startTime, Date endTime) {

        PhoneBill bill = null;

        if (this.phoneBillMap.get(name) == null) {
            return null;
        }

        return this.phoneBillMap.get(name);
    }

    /**
     * Log unhandled exceptions to standard error
     *
     * @param unhandled
     *        The exception that wasn't handled
     */
    @Override
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }
}
