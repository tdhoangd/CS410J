package edu.pdx.cs410J.thhoang.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by thanhhoang on 8/5/15.
 */
public interface PhoneCallServiceAsync {

    void add(String name, PhoneCall call, AsyncCallback<AbstractPhoneBill> async);
    void search(String name, Date startTime, Date endTime, AsyncCallback<AbstractPhoneBill> async);
    void getPhoneBill(String name, AsyncCallback<AbstractPhoneBill> async);
}
