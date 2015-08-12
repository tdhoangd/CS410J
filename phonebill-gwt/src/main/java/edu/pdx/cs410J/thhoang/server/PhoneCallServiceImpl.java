package edu.pdx.cs410J.thhoang.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.thhoang.client.PhoneBill;
import edu.pdx.cs410J.thhoang.client.PhoneCall;
import edu.pdx.cs410J.thhoang.client.PhoneCallService;

import javax.lang.model.type.ArrayType;
import java.util.*;

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

        PhoneBill phoneBill = null;

        if (this.phoneBillMap.get(name) != null) {
            phoneBill = this.phoneBillMap.get(name);
        }

        return phoneBill;
    }

    @Override
    public AbstractPhoneBill getPhoneBill(String name) {
        PhoneBill bill = null;

        if (this.phoneBillMap.get(name) != null) {
            bill = this.phoneBillMap.get(name);
        }

        return bill;
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
