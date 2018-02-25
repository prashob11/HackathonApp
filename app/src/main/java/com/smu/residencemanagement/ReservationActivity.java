package com.smu.residencemanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.view.View.OnClickListener;
import android.widget.Toast;


public class ReservationActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker;
    Button timeButton;
    EditText txtDate;
    private int mYear, mMonth, mDay;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult ;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/BookingActivity.php";
    int resId;
    Intent intent;
    String activityName;
    HashMap<String, String> bookedEventTimes;
    String dateOfBooking;
    Button btnOriginal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        btnOriginal = (Button)findViewById(R.id.button5PM6PM);

        Intent intent=getIntent();
        activityName= intent.getStringExtra("activityType");
        final String activityType = "Reserve a slot for " + intent.getStringExtra("activityType");

        ((TextView) findViewById(R.id.tvMessage)).setText(activityType);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        txtDate = (EditText) findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);
        bookedEventTimes = (HashMap<String, String>)intent.getSerializableExtra("bookedEvents");
        reloadPageNeeded();
    }

    public void reloadPageNeeded(){
        final Intent intent = getIntent();
        final String userEmail = intent.getStringExtra("UserEmail");
        String[] buttonIdArray={"button6AM7AM",	"button7AM8AM",	"button8AM9AM",	"button9AM10AM",	"button10AM11AM",	"button11AM12PM",	"button12PM1PM",	"button1PM2PM",	"button2PM3PM",	"button3PM4PM",	"button4PM5PM",	"button5PM6PM",	"button6PM7PM",	"button7PM8PM",	"button8PM9PM",	"button9PM10PM",	"button10PM11PM",	"button11PM12AM"};

        txtDate.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                refreshPageFunction(txtDate.getText().toString());

            }
        });

        //ArrayList<String> bookedEventTimes = getIntent().getStringArrayListExtra("bookedEvents");

//        Log.v("HashMapTest", bookedEventTimes.get("button6AM7AM"));

        //Log.d("Booked event time:","Hi ");

        for(final String buttonId: buttonIdArray){
            resId= getResources().getIdentifier(buttonId, "id",getPackageName());
            timeButton=(Button) findViewById(resId);

            /*for(String s:bookedEventTimes.keySet())
            {
                Log.d("Value in keyset",s);
            }*/
            /*Log.d("Timeslot:",buttonId);

            Log.d("Facility",  intent.getStringExtra("activityType"));
            Log.d("UserEmail",  userEmail);
            */
            //For swap
            Log.d("Size before clear in refresh",Integer.toString(bookedEventTimes.size()));
            if(bookedEventTimes.keySet().contains(buttonId)){
                findViewById(resId).setBackgroundColor(Color.DKGRAY);
                ((Button) findViewById(resId)).setTextColor(Color.RED);
                timeButton.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v) {
                        /*Intent intent = new Intent(ReservationActivity .this, SwapRequestForm.class);
                        startActivity(intent);*/
                        //Log.d("Inside Swap","inside swap");
                        /*Log.d("User From map ---------->", bookedEventTimes.get(buttonId));
                        Log.d("user Email address------------>",userEmail);*/

                        //FOR CANCEL
                        if (!txtDate.getText().toString().isEmpty())
                        {
                            if (bookedEventTimes.get(buttonId).equalsIgnoreCase(userEmail)) {
                                showAlert("Are you sure you want to cancel this slot?", buttonId, activityName, userEmail);
                            } else {
                                showAlert("Are you sure you want to ask for swapping?", buttonId, activityName, userEmail);
                            }
                        }
                        else
                        {
                            Toast.makeText(ReservationActivity.this, "Please select date.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else
            {
                findViewById(resId).setBackgroundColor(btnOriginal.getBackground().getAlpha());
                ((Button) findViewById(resId)).setTextColor(Color.BLACK);
                timeButton.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        if(!txtDate.getText().toString().isEmpty())
                            showAlert("Are you sure you want to book this slot?",buttonId,activityName, userEmail);
                        else
                            Toast.makeText(ReservationActivity.this, "Please select date.", Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(ReservationActivity .this, BookingSummaryActivity.class);
                        //startActivity(intent);
                        //Log.d("Inside create","inside create");
                    }
                });
            }

        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(year+"-"  + (monthOfYear + 1) +"-"+dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private void showAlert(final String swapOrBook, final String buttonId, final String activity, final String userEmail) {
        dateOfBooking =txtDate.getText().toString();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(swapOrBook)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                if(swapOrBook.equals("Are you sure you want to book this slot?")){

                                    BookingFunction(buttonId,userEmail,activity,dateOfBooking,"BOOK");
                                     intent = new Intent(ReservationActivity .this, UpcomingBookings.class);
                                    //Intent intent = getIntent();
                                    //finish();
                                    intent.putExtra("UserEmail",getIntent().getStringExtra("UserEmail"));
                                    startActivity(intent);
                                }
                                else if(swapOrBook.equals("Are you sure you want to cancel this slot?")){
                                    BookingFunction(buttonId,userEmail,activity,dateOfBooking,"CANCEL");
                                    intent = new Intent(ReservationActivity .this, UpcomingBookings.class);
                                    intent.putExtra("UserEmail",getIntent().getStringExtra("UserEmail"));
                                    startActivity(intent);
                                }
                                else
                                {
                                    BookingFunction(buttonId,userEmail,activity,dateOfBooking,"SWAP");
                                    intent = new Intent(ReservationActivity .this, SwapRequestForm.class);
                                    intent.putExtra("UserEmail",getIntent().getStringExtra("UserEmail"));
                                    startActivity(intent);

                                }
//                                startActivity(intent);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                /*Intent intent = new Intent(ReservationActivity .this, BookingSummaryActivity.class);
                                startActivity(intent);*/
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void BookingFunction(final String timeSlot, final String email, final String facility, final String dateOfBooking, final String bookOrCancelOrSwap){

        class BookingFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ReservationActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();


                Toast.makeText(ReservationActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("timeSlot",params[0]);

                hashMap.put("email",params[1]);

                hashMap.put("facility",params[2]);

                hashMap.put("dateOfBooking",params[3]);
                hashMap.put("bookOrCancelOrSwap",params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        BookingFunctionClass bookingFunctionClass = new BookingFunctionClass();

        bookingFunctionClass.execute(timeSlot,email,facility,dateOfBooking,bookOrCancelOrSwap);
    }

    public void refreshPageFunction(final String dateOfBooking){

        class RefreshPageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ReservationActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);



                //bookedEventTimes.clear();
                progressDialog.dismiss();
                Toast.makeText(ReservationActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                Pattern pattern= Pattern.compile("(button\\d+(?:AM|PM)\\d+(?:AM|PM)\",\"[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");
                Matcher matcher = pattern.matcher(httpResponseMsg.toString());
                Log.d("Size before clear",Integer.toString(bookedEventTimes.size()));
                bookedEventTimes.clear();
                Log.d("Size after clear",Integer.toString(bookedEventTimes.size()));
                String[] keyValue;
                while (matcher.find()) {
                    keyValue= matcher.group().replace("\"","").split(",");

                    bookedEventTimes.put(keyValue[0],keyValue[1]);
                }


                reloadPageNeeded();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("dateOfBooking",params[0]);

                hashMap.put("ref","REFRESH");
                hashMap.put("activity",activityName);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        RefreshPageFunctionClass refreshPageFunctionClass = new RefreshPageFunctionClass();

        refreshPageFunctionClass.execute(dateOfBooking);
    }
}
