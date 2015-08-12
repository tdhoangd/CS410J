package edu.pdx.cs410J.thhoang.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by thanhhoang on 8/5/15.
 */
@RemoteServiceRelativePath("call")
public interface PhoneCallService extends RemoteService {

    public AbstractPhoneBill add(String name, PhoneCall call);
    public AbstractPhoneBill search(String name, Date startTime, Date endTime);
    public AbstractPhoneBill getPhoneBill(String name);
}
