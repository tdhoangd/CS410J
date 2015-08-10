package edu.pdx.cs410J.thhoang.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

    private FlexTable flexTable = null;

    @Override
    public void onModuleLoad() {

        //centerPanel
        final VerticalPanel centerPanel = new VerticalPanel();
        centerPanel.add(new Label("centerPanel"));

        // fields of left side of the screen, add panel
        VerticalPanel leftPanel = new VerticalPanel();
        leftPanel.setBorderWidth(1);

        final TextBox customerTextBox = getTextBox("enter name here");
        final TextBox callerTextBox = getTextBox("nnn-nnn-nnnn");
        final TextBox calleeTextBox = getTextBox("nnn-nnn-nnnn");
        final TextBox startTimeTextBox = getTextBox("hh:mm a");
        final TextBox endTimeTextBox = getTextBox("hh:mm a");
        final DateBox startDateBox = getDateBox();
        final DateBox endDateBox = getDateBox();

        Button addButton = new Button("ADD");
        addButton.setPixelSize(150, 33);

        Label addLabel = new Label("ADD NEW PHONE CALL");
        addLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        addLabel.setWordWrap(true);
        addLabel.setPixelSize(150, 25);

        leftPanel.add(addLabel);
        leftPanel.add(new Label("Customer:   "));
        leftPanel.add(customerTextBox);
        leftPanel.add(new Label("Caller Number:  "));
        leftPanel.add(callerTextBox);
        leftPanel.add(new Label("Callee Number:  "));
        leftPanel.add(calleeTextBox);
        leftPanel.add(new Label("Start Date: "));
        leftPanel.add(startDateBox);
        leftPanel.add(new Label("Start time: "));
        leftPanel.add(startTimeTextBox);
        leftPanel.add(new Label("End Date"));
        leftPanel.add(endDateBox);
        leftPanel.add(new Label("End time:   "));
        leftPanel.add(endTimeTextBox);
        leftPanel.add(addButton);


        // right side
        VerticalPanel rightPanel = new VerticalPanel();
        rightPanel.setBorderWidth(1);

        Button helpButton = new Button("ReadMe");
        helpButton.setPixelSize(150, 33);

        Label searchLabel = new Label("SEARCH CALL");
        searchLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        searchLabel.setPixelSize(150, 25);

        final TextBox searchNameBox = getTextBox("enter name here");
        final TextBox searchStartTimeBox = getTextBox("hh:mm a");
        final TextBox searchEndTimeBox = getTextBox("hh:mm a");
        final DateBox searchStartDateBox = getDateBox();
        final DateBox searchEndDateBox = getDateBox();

        Button searchButton = new Button("SEARCH");
        searchButton.setPixelSize(150, 33);

        rightPanel.add(searchLabel);
        rightPanel.add(new Label("Name: "));
        rightPanel.add(searchNameBox);
        rightPanel.add(new Label("Start Date:   "));
        rightPanel.add(searchStartDateBox);
        rightPanel.add(new Label("Start Time: "));
        rightPanel.add(searchStartTimeBox);
        rightPanel.add(new Label("End Date: "));
        rightPanel.add(searchEndDateBox);
        rightPanel.add(new Label("End Time"));
        rightPanel.add(searchEndTimeBox);
        rightPanel.add(searchButton);


        // top panel
        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.setWidth("100%");
        topPanel.add(helpButton);
        topPanel.setCellHorizontalAlignment(helpButton, HasHorizontalAlignment.ALIGN_RIGHT);



        // Click handler
        helpButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert(getREADME());
            }
        });

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String customer = customerTextBox.getText();
                String caller = callerTextBox.getText();
                String callee = calleeTextBox.getText();
                String startTimeString = startDateBox.getTextBox().getText() + " " + startTimeTextBox.getText();
                String endTimeString = endDateBox.getTextBox().getText() + " " + endTimeTextBox.getText();

                try {
                    missingAddArguments(customer, caller, callee, startTimeString, endTimeString);
                } catch (IOException e) {
                    Window.alert(e.toString() + "\nClose this window and re-enter missing fileds please!");
                    return;
                }

                try {
                    validPhoneNumber(caller);
                } catch (ParseException e) {
                    Window.alert(e.toString() + " is malformed");
                    return;
                }

                try {
                    validPhoneNumber(callee);
                } catch (ParseException e) {
                    Window.alert(e.toString() + " is malformed");
                    return;
                }

                Date startDateTime = null;
                Date endDateTime = null;

                try {
                    startDateTime = checkDateAndTimeFormat(startTimeString);
                } catch (ParseException e) {
                    Window.alert("Malformed:\n" + e.toString());
                    return;
                }

                try {
                    endDateTime = checkDateAndTimeFormat(endTimeString);
                } catch (ParseException e) {
                    Window.alert("Malformed\n" + e.toString());
                    return;
                }

                final PhoneCall call = new PhoneCall(caller, callee, startDateTime, endDateTime);

                PhoneCallServiceAsync async = GWT.create(PhoneCallService.class);
                async.add(customer, call, new AsyncCallback<AbstractPhoneBill>() {

                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill abstractPhoneBill) {

                    //    flexTable = initHeadTable();
                        
                        updateTable(abstractPhoneBill);

                        Window.alert("came here\n" + abstractPhoneBill.toString());

                       // PhoneCall call1 = null;
                        /*centerPanel.clear();
                        centerPanel.add(new Label("New phone call added: \n\t\t" + call1.toString() + "\n"));

                        int i = 1;
                        flexTable.removeAllRows();

                        flexTable.setText(0, 0, "NO:");
                        flexTable.setText(0, 1, "START TIME");
                        flexTable.setText(0, 2, "END TIME");
                        flexTable.setText(0, 3, "CALLER NUMBER");
                        flexTable.setText(0, 4, "CALLEE NUMBER");
                        flexTable.setText(0, 5, "DURATION");

                        for (Object o : abstractPhoneBill.getPhoneCalls()) {
                            call1 = (PhoneCall) o;

                            flexTable.setText(i, 0, Integer.toString(i));
                            flexTable.setText(i, 1, call1.getStartTimeString());
                            flexTable.setText(i, 2, call1.getEndTimeString());
                            flexTable.setText(i, 3, call1.getCaller());
                            flexTable.setText(i, 4, call1.getCallee());
                            flexTable.setText(i, 5, Integer.toString(call1.timeDifference()));
                        }

                        centerPanel.add(flexTable);


                        StringBuilder sb = new StringBuilder( abstractPhoneBill.toString() );
                        sb.append(call.toString());
                        Collection<AbstractPhoneCall> calls = abstractPhoneBill.getPhoneCalls();
                        for ( AbstractPhoneCall a : calls ) {
                            sb.append(a);
                            sb.append("\n");
                        }
                        Window.alert( sb.toString() );*/

                    }
                });

            }
        });


        searchButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                String customer = searchNameBox.getText();
                String startDateTimeString = searchStartDateBox.getTextBox().getText() + " " + searchStartTimeBox.getText();
                String endDateTimeString = searchEndDateBox.getTextBox().getText() + " " + searchEndTimeBox.getText();

                try {
                    missingSearchArguments(customer, startDateTimeString, endDateTimeString);
                } catch (IOException e) {
                    Window.alert(e.toString());
                    return;
                }

                
                ArrayList<PhoneCall> callList = new ArrayList<PhoneCall>();
                Date startDateTime = null;
                Date endDateTime = null;

                try {
                    startDateTime = checkDateAndTimeFormat(startDateTimeString);
                } catch (ParseException e) {
                    Window.alert("Malformed:\n" + e.toString());
                    return;
                }

                try {
                    endDateTime = checkDateAndTimeFormat(endDateTimeString);
                } catch (ParseException e) {
                    Window.alert("Malformed\n" + e.toString());
                    return;
                }

                PhoneCallServiceAsync async = GWT.create(PhoneCallService.class);
                async.search(customer, startDateTime, endDateTime, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill abstractPhoneBill) {

                    }
                });



            }
        });




       /* button.addClickHandler(new ClickHandler() {
            public void onClick( ClickEvent clickEvent )
            {
                PingServiceAsync async = GWT.create( PingService.class );

                async.ping( new AsyncCallback<AbstractPhoneBill>() {

                    public void onFailure( Throwable ex )
                    {
                        Window.alert(ex.toString());
                    }

                    public void onSuccess( AbstractPhoneBill phonebill )
                    {
                        StringBuilder sb = new StringBuilder( phonebill.toString() );
                        Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
                        for ( AbstractPhoneCall call : calls ) {
                            sb.append(call);
                            sb.append("\n");
                        }
                        Window.alert( sb.toString() );
                    }
                });
            }
        });*/

        // Center

/*
        FlexTable flexTable = new FlexTable();
        flexTable.setText(0, 0, "No");
        flexTable.setText(0, 1, "Start Time");
        flexTable.setText(0, 2, "End Time");
        flexTable.setText(0, 3, "Caller");
        flexTable.setText(0, 4, "Callee");
        flexTable.setText(0, 5, "Duration");
        flexTable.setBorderWidth(1);
        flexTable.getColumnFormatter().setWidth(0, "400px");

        flexTable.setText(1, 0, "No");
        flexTable.setText(1, 1, "Start Time");
        flexTable.setText(1, 2, "End Time");
        flexTable.setText(1, 3, "Caller");
        flexTable.setText(1, 4, "Callee");
        flexTable.setText(1, 5, "Duration");

        centerPanel.add(flexTable);*/

        flexTable = initHeadTable();

        centerPanel.add(new Label("Phone call list"));
        centerPanel.add(flexTable);

        RootPanel rootPanel = RootPanel.get();

        DockPanel dockPanel = new DockPanel();
        dockPanel.setWidth("100%");
        dockPanel.add(topPanel, DockPanel.NORTH);
        dockPanel.add(leftPanel, DockPanel.WEST);
        dockPanel.setCellHorizontalAlignment(leftPanel, HasHorizontalAlignment.ALIGN_LEFT);
        dockPanel.add(rightPanel, DockPanel.EAST);
        dockPanel.setCellHorizontalAlignment(rightPanel, HasHorizontalAlignment.ALIGN_RIGHT);

        dockPanel.add(centerPanel, DockPanel.CENTER);

        rootPanel.add(dockPanel);


    }

    private void updateTable(AbstractPhoneBill abstractPhoneBill) {
        flexTable.removeAllRows();
        flexTable = initHeadTable();
        PhoneCall phoneCall = null;
        int i = 1;

        for (Object o : abstractPhoneBill.getPhoneCalls()) {
            phoneCall = (PhoneCall) o;

            flexTable.setText(i, 0, Integer.toString(i));
            flexTable.setText(i, 1, phoneCall.getStartTimeString());
            flexTable.setText(i, 2, phoneCall.getEndTimeString());
            flexTable.setText(i, 3, phoneCall.getCaller());
            flexTable.setText(i, 4, phoneCall.getCallee());
            flexTable.setText(i, 5, Integer.toString(phoneCall.timeDifference()));
            ++i;
        }

    }

    private DateBox getDateBox() {
        final DateBox dateBox = new DateBox();
        DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
        dateBox.setFormat(new DateBox.DefaultFormat(df));
        dateBox.getElement().setAttribute("placeholder", "Pick date");
        dateBox.setPixelSize(145, 23);
        return dateBox;
    }


    private TextBox getTextBox(String placeholder) {
        TextBox textBox = new TextBox();
        textBox.getElement().setAttribute("placeholder", placeholder);
        textBox.setPixelSize(145, 23);
        return textBox;
    }

    private String getREADME() {
        String text = "Phone Bill application:\n" +
                "This application will store phone calls and phone bills.\n" +
                "It is easy to use. To add and search phone calls, sinply enter data into text box. \n";

        return text;
    }

    private void missingAddArguments(String name, String caller, String callee, String start, String end) throws IOException {

        boolean flag = false;

        if (name.isEmpty()) flag = true;
        if (caller.isEmpty()) flag = true;
        if (callee.isEmpty()) flag = true;
        if (start.isEmpty()) flag = true;
        if (end.isEmpty()) flag = true;

        if (flag) {
            throw new IOException("Missing Fields");
        }

    }

    private void missingSearchArguments(String name, String start, String end) throws IOException {

        boolean isMissing = false;

        if (name.isEmpty()) isMissing = true;
        if (!name.isEmpty()) {

            if (start.isEmpty() && !end.isEmpty()) {
                isMissing = true;
            }
            if (!start.isEmpty() && end.isEmpty()) {
                isMissing = true;
            }
        }

        if (isMissing) {
            throw new IOException("Missing Fields");
        }

    }

    private void validPhoneNumber(String phoneNo) throws ParseException{

        if (phoneNo == null || !phoneNo.matches("\\d{3}-\\d{3}-\\d{4}")) {
            throw new ParseException(phoneNo, 1);
        }
    }

    private Date checkDateAndTimeFormat(String dateTimeString) throws ParseException{

        Date dateTime = null;
        DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");

        try {
            dateTime = df.parse(dateTimeString);
        } catch (Exception e) {
            throw new ParseException(dateTimeString.toString() + " is malformed", 1);
        }

        return dateTime;
    }

    private FlexTable initHeadTable() {
        FlexTable table = new FlexTable();

        table.setText(0, 0, "NO:");
        table.setText(0, 1, "START TIME");
        table.setText(0, 2, "END TIME");
        table.setText(0, 3, "CALLER NUMBER");
        table.setText(0, 4, "CALLEE NUMBER");
        table.setText(0, 5, "DURATION");

        return table;
    }
}
