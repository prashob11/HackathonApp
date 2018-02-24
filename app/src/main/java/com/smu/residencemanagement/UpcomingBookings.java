package com.smu.residencemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.widget.Toast;
import java.util.HashMap;

public class UpcomingBookings extends AppCompatActivity {
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/UpcomingBooking.php";
    String userEmail;

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String dateOfBooking = df.format(c);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_bookings);
        if(getIntent().getExtras()!=null)
        {
            userEmail=getIntent().getStringExtra("UserEmail");
            Log.d("Email",userEmail);
        }
        UpcomingBookingFunction(userEmail, dateOfBooking);
    }

    public void UpcomingBookingFunction(final String userEmail, final String dateOfBooking){

        class UpcomingBookingClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpcomingBookings.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
//                Log.d("Response from server:",  httpResponseMsg.toString());



    //            finish();
                //Intent intent = new Intent(UpcomingBookings.this, UpcomingBookings.class);
                //intent.putExtra("UserEmail",userEmail);
                //startActivity(intent);

                Toast.makeText(UpcomingBookings.this,httpResponseMsg,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("userEmail",params[0]);

                hashMap.put("dateOfBooking",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UpcomingBookingClass upcomingBookingClass = new UpcomingBookingClass();

        upcomingBookingClass.execute(userEmail,dateOfBooking);
    }
}
