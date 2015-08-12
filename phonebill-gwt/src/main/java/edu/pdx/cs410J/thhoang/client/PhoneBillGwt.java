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
import java.util.Collection;
import java.util.Date;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

    private FlexTable flexTable = new FlexTable();
    private PhoneCallServiceAsync service;
    // add phone call fields
    private TextBox callerTextBox;
    private TextBox customerTextBox;
    private TextBox calleeTextBox;
    private TextBox startTimeTextBox;
    private TextBox endTimeTextBox;
    private DateBox startDateBox;
    private DateBox endDateBox;
    private TextBox searchNameBox;
    private TextBox searchStartTimeBox;
    private TextBox searchEndTimeBox;
    private DateBox searchStartDateBox;
    private DateBox searchEndDateBox;

    private Label label;
    //search fields

    @Override
    public void onModuleLoad() {

        this.service = GWT.create(PhoneCallService.class);

        // create inner panels
        VerticalPanel leftPanel = createLeftPanel();
        VerticalPanel rightPanel = createRightPanel();
        HorizontalPanel topPanel = createTopPanel();
        VerticalPanel centerPanel = createCenterPanel();

        //centerPanel.add(new Label("center panel"));

        // Interface
        RootPanel rootPanel = RootPanel.get();

 /*       FlexTable table = new FlexTable();
        table.setWidth("100%");
        table.setWidget(0, 0, leftPanel);
        table.setWidget(0, 1, centerPanel);
        table.setWidget(0, 2, rightPanel);
*/
        DockPanel dockPanel = new DockPanel();
        dockPanel.setWidth("100%");
        dockPanel.add(topPanel, DockPanel.NORTH);
        dockPanel.add(leftPanel, DockPanel.WEST);
        dockPanel.setCellHorizontalAlignment(leftPanel, HasHorizontalAlignment.ALIGN_LEFT);
        dockPanel.add(rightPanel, DockPanel.EAST);
        dockPanel.setCellHorizontalAlignment(rightPanel, HasHorizontalAlignment.ALIGN_RIGHT);
        dockPanel.add(centerPanel, DockPanel.CENTER);
        dockPanel.setCellHorizontalAlignment(centerPanel, HasHorizontalAlignment.ALIGN_CENTER);

        rootPanel.add(dockPanel);
    }

    private VerticalPanel createCenterPanel() {

        //flexTable.setVisible(true);
        VerticalPanel centerPanel = new VerticalPanel();
        centerPanel.setBorderWidth(2);
       // centerPanel.setWidth("70%");


        Label welcomeLabel = new Label("WELCOME TO PHONE BILL APPLICATION");
        centerPanel.add(welcomeLabel);

        centerPanel.add(flexTable);


        return centerPanel;
    }

    private HorizontalPanel createTopPanel() {

        Button helpButton = new Button("Help");
        helpButton.setPixelSize(150, 33);

        helpButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.alert(getREADME());
            }
        });

        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.setBorderWidth(1);
        topPanel.setWidth("100%");
        topPanel.add(helpButton);
        topPanel.setCellHorizontalAlignment(helpButton, HasHorizontalAlignment.ALIGN_RIGHT);
        return topPanel;
    }

    private VerticalPanel createRightPanel() {
        VerticalPanel rightPanel = new VerticalPanel();
        rightPanel.setBorderWidth(1);

        searchNameBox = getTextBox("enter name here");
        searchStartTimeBox = getTextBox("hh:mm a");
        searchEndTimeBox = getTextBox("hh:mm a");
        searchStartDateBox = getDateBox();
        searchEndDateBox = getDateBox();

        Button searchButton = new Button("SEARCH");
        searchButton.setPixelSize(150, 33);
        searchButton.addClickHandler(searchHandler());

        Label searchLabel = new Label("SEARCH CALL");
        searchLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        searchLabel.setPixelSize(150, 25);
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

        return rightPanel;
    }


    private VerticalPanel createLeftPanel() {
        VerticalPanel leftPanel = new VerticalPanel();
        leftPanel.setBorderWidth(1);

        customerTextBox = getTextBox("enter name here");
        callerTextBox = getTextBox("nnn-nnn-nnnn");
        calleeTextBox = getTextBox("nnn-nnn-nnnn");
        startTimeTextBox = getTextBox("hh:mm a");
        endTimeTextBox = getTextBox("hh:mm a");
        startDateBox = getDateBox();
        endDateBox = getDateBox();

        Button addButton = new Button("ADD");
        addButton.setPixelSize(150, 33);
        addButton.addClickHandler(addNewPhoneCallHandler());

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


        return leftPanel;
    }

    private ClickHandler searchHandler() {

        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                String customer = searchNameBox.getText();
                String startDate = searchStartDateBox.getTextBox().getText();
                String startTime = searchStartTimeBox.getText();
                String endDate = searchEndDateBox.getTextBox().getText();
                String endTime = searchEndTimeBox.getText();
                String startDateTimeString = startDate + " " + startTime;
                String endDateTimeString = endDate + " " + endTime;

                try {
                    missingSearchArguments(customer, startDate, startTime, endDate, endTime);
                } catch (IOException e) {
                    Window.alert(e.toString());
                    return;
                }

                ArrayList<PhoneCall> callList = new ArrayList<PhoneCall>();
                Date startDateTime = null;
                Date endDateTime = null;

                if (!startDate.isEmpty() && !startTime.isEmpty()) {
                    try {
                        startDateTime = checkDateAndTimeFormat(startDateTimeString);
                    } catch (ParseException e) {
                        Window.alert("Malformed:\n" + e.toString());
                        return;
                    }
                }

                if (!endDate.isEmpty() && !endTime.isEmpty()){

                    try {
                        endDateTime = checkDateAndTimeFormat(endDateTimeString);
                    } catch (ParseException e) {
                        Window.alert("Malformed\n" + e.toString());
                        return;
                    }
                }

                if (startDateTime != null && endDateTime != null) {
                    service.search(customer, startDateTime, endDateTime, prettyPrintSearch(startDateTime, endDateTime));
                }

                if (startDateTime == null && endDateTime == null) {
                    service.search(customer, startDateTime, endDateTime, prettyPrintSearchPhoneBill());
                }

            }

            private AsyncCallback<AbstractPhoneBill> prettyPrintSearch(final Date startDateTime, final Date endDateTime) {
                return new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill abstractPhoneBill) {
                        ArrayList<PhoneCall> callsList = new ArrayList<PhoneCall>();
                        PhoneBill bill = (PhoneBill) abstractPhoneBill;
                        callsList = (ArrayList) bill.searchPhoneCalls(startDateTime, endDateTime);

                        Window.alert("Found " + Integer.toString(callsList.size()));
                        updateTable(callsList);
                    }
                };
            }

            private AsyncCallback<AbstractPhoneBill> prettyPrintSearchPhoneBill() {
                return new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill abstractPhoneBill) {

                        Window.alert("Found " + Integer.toString(abstractPhoneBill.getPhoneCalls().size()));

                        updateTable((ArrayList) abstractPhoneBill.getPhoneCalls());
                    }
                };
            }


        };

    }

    private ClickHandler addNewPhoneCallHandler() {

        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                String customer = customerTextBox.getText();
                String caller = callerTextBox.getText();
                String callee = calleeTextBox.getText();
                String startTimeString = startDateBox.getTextBox().getText() + " " + startTimeTextBox.getText();
                String endTimeString = endDateBox.getTextBox().getText() + " " + endTimeTextBox.getText();

                customerTextBox.setText(null);
                callerTextBox.setText(null);
                calleeTextBox.setText(null);
                startDateBox.getTextBox().setText(null);
                endDateBox.getTextBox().setText(null);
                startTimeTextBox.setText(null);
                endTimeTextBox.setText(null);

                try {
                    missingAddArguments(customer, caller, callee, startTimeString, endTimeString);
                } catch (IOException e) {
                    Window.alert(e.toString() + "\nClose this window and re-enter missing fields please!");
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

                service.add(customer, call, prettyPrintPhoneCalls());

            }

            private AsyncCallback<AbstractPhoneBill> prettyPrintPhoneCalls() {
                return new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill abstractPhoneBill) {

                        updateTable((ArrayList) abstractPhoneBill.getPhoneCalls());
                    }
                };
            }
        };
    }

    private void updateTable(ArrayList phoneCalls) {
        flexTable.removeAllRows();
        initHeadTable();

        PhoneCall phoneCall = null;
        int i = 1;

        if (phoneCalls.size() == 0) {
            initHeadTable();
            return;
        }


        for (Object o : phoneCalls) {
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

    private void missingSearchArguments(String name, String startDate, String startTime, String endDate, String endTime) throws IOException {

        boolean isMissing = false;
        String fields = " ";

        if (name.isEmpty()) {
            isMissing = true;
            fields += " customer name; ";
        }
        if (!name.isEmpty()) {

            String start = startDate + startTime;
            String end = endDate + endTime;

            if (!start.isEmpty() && !end.isEmpty()) {
                if (startDate.isEmpty()) {
                    isMissing = true;
                    fields += " start date;";
                }
                if (startTime.isEmpty()) {
                    isMissing = true;
                    fields += " start time;";
                }
                if (endDate.isEmpty()) {
                    isMissing = true;
                    fields += " end date;";
                }
                if (endTime.isEmpty()) {
                    isMissing = true;
                    fields += " end time;";
                }
            }
        }

        if (isMissing) {
            throw new IOException("Missing Fields\n" + fields);
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

    private void  initHeadTable() {

        if (flexTable.getRowCount() != 0)
            flexTable.removeAllRows();

        flexTable.setWidth("900px");

        flexTable.setText(0, 0, "NO:");
        flexTable.setText(0, 1, "START TIME");
        flexTable.setText(0, 2, "END TIME");
        flexTable.setText(0, 3, "CALLER NUMBER");
        flexTable.setText(0, 4, "CALLEE NUMBER");
        flexTable.setText(0, 5, "DURATION");

    }

    private void getLabel(String s) {
        label = new Label(s);
    }
}
