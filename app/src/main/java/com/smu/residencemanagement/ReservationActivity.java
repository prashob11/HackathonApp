package com.smu.residencemanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import java.util.*;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ReservationActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker;
    Button timeButton;
    EditText txtDate;
    private int mYear, mMonth, mDay;
    Intent intent;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult ;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/UserRegistration.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent intent = getIntent();
        String activityType = "Reserve a slot for " + intent.getStringExtra("activityType");
        ((TextView) findViewById(R.id.tvMessage)).setText(activityType);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        txtDate = (EditText) findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);
        String userEmail = intent.getStringExtra("UserEmail");
        String[] buttonIdArray={"button6AM7AM",	"button7AM8AM",	"button8AM9AM",	"button9AM10AM",	"button10AM11AM",	"button11AM12PM",	"button12PM1PM",	"button1PM2PM",	"button2PM3PM",	"button3PM4PM",	"button4PM5PM",	"button5PM6PM",	"button6PM7PM",	"button7PM8PM",	"button8PM9PM",	"button9PM10PM",	"button10PM11PM",	"button11PM12AM"};


        ArrayList<String> bookedEventTimes = getIntent().getStringArrayListExtra("bookedEvents");
        //Log.d("Booked event time:","Hi ");
        int resId;

        //Log.d("SIZE:", Integer.toString(bookedEventTimes.size()));

        /*for(String s: bookedEventTimes){
            //Log.d("Id of button",Integer.toString(R.id.button5PM6PM) );
            Log.d("Booked event time:", s);
        }*/

        //Log.d("IdName of button", Integer.toString (resId));
        for(String buttonId: buttonIdArray){
            resId= getResources().getIdentifier(buttonId, "id",getPackageName());
            timeButton=(Button) findViewById(resId);
            Log.d("Timeslot:",buttonId);

            Log.d("Facility",  intent.getStringExtra("activityType"));
            Log.d("UserEmail",  userEmail);

            //For swap
            //Log.d("Booked event time:","Hi ");
            if(bookedEventTimes.contains(buttonId)){
                timeButton.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        /*Intent intent = new Intent(ReservationActivity .this, SwapRequestForm.class);
                        startActivity(intent);*/
                        //Log.d("Inside Swap","inside swap");
                        showAlert("Are you sure you want to ask for swapping?");
                    }
                });
            }
            else
            {
                timeButton.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        showAlert("Are you sure you want to book this slot?");
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

                            txtDate.setText(year+"-"+dayOfMonth + "-" + (monthOfYear + 1) );

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private void showAlert(final String swapOrBook) {
        Log.d("Date of booking:", txtDate.getText().toString());
        Log.d("Inside Alter",swapOrBook);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(swapOrBook)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                if(swapOrBook.equals("Are you sure you want to book this slot?")){

                                     intent = new Intent(ReservationActivity .this, BookingSummaryActivity.class);
                                }
                                else
                                {
                                     intent = new Intent(ReservationActivity .this, SwapRequestForm.class);

                                }
                                startActivity(intent);
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
    public void BookingFunction(final String timeSlot, final String email, final String facility, final String dateOfBooking){

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

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                Log.d("signin" , params[0]);


                return finalResult;
            }
        }

        BookingFunctionClass bookingFunctionClass = new BookingFunctionClass();

        bookingFunctionClass.execute(timeSlot,email,facility,dateOfBooking);
    }
}
