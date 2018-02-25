package com.smu.residencemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.util.*;
import java.util.HashMap;

public class BookFacility extends AppCompatActivity {
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/BookFacility.php";
    String finalResult ;
    String activityType;

    Intent intent;
    HashMap<String,String> bookedEvents=new HashMap<>();
    String email;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_facility);
        if(getIntent().getExtras()!=null)
        {
            email=getIntent().getStringExtra("UserEmail");
        }
    }

    public void bookOnClick(View view)
    {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        Log.d("Current Date:" , formattedDate);
        intent = new Intent(BookFacility.this, ReservationActivity.class);
        switch(view.getId())
        {
            case R.id.buttonKitchen:
                intent.putExtra("activityType","Kitchen");
                activityType="KITCHEN";

                break;

            case R.id.buttonGym:
                intent.putExtra("activityType","Gym");
                activityType="GYM";
                break;
            case R.id.buttonDryer:
                intent.putExtra("activityType","Dryer");
                activityType="DRYER";
                break;
            case R.id.buttonStudyRoom:
                intent.putExtra("activityType","Study Room");
                activityType="STUDYROOM";
                break;
            case R.id.buttonBathroom:
                intent.putExtra("activityType","Bathroom");
                activityType="BATHROOM";
                break;
            case R.id.buttonWasher:
                intent.putExtra("activityType","Washer");
                activityType="WASHER";
                break;
            case R.id.buttonCommonRoom:
                intent.putExtra("activityType","Common Room");
                activityType="COMMONROOM";
                break;
            case R.id.buttonPlayRoom:
                intent.putExtra("activityType","Play Room");
                activityType="PLAYROOM";
                break;
            default:Log.d("myTag", "I am Out of here");
                intent.putExtra("activityType","Nothing");
                activityType="NOTHING";
                break;
        }
        intent.putExtra("UserEmail",email);
        getBookingDetail(activityType,formattedDate);
        intent.putExtra("bookedEvents", bookedEvents);


    }

    public void getBookingDetail(final String activityType,final String formattedDate){
        class BookingDetailFunctionClass extends AsyncTask<String,String,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(BookFacility.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
               // Toast.makeText(BookFacility.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                //Pattern pattern = Pattern.compile("(button\\d+AM\\d+AM)");
                Pattern pattern= Pattern.compile("(button\\d+(?:AM|PM)\\d+(?:AM|PM)\",\"[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");
                Matcher matcher = pattern.matcher(httpResponseMsg.toString());

                String[] keyValue;
                while (matcher.find()) {
                    keyValue= matcher.group().replace("\"","").split(",");

                    bookedEvents.put(keyValue[0],keyValue[1]);
                }


                intent.putExtra("bookedEvents", bookedEvents);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("facilityName",params[0]);
                hashMap.put("dateOfBooking",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }

        }
        BookingDetailFunctionClass bookingDetailFunctionClass = new BookingDetailFunctionClass();

        bookingDetailFunctionClass.execute(activityType,formattedDate);
    }


}
