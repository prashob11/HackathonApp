package com.smu.residencemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.regex.*;
import java.sql.Struct;
import java.util.*;
import org.json.JSONArray;

import java.util.HashMap;

public class BookFacility extends AppCompatActivity {
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/BookFacility.php";
    String finalResult ;
    String activityType;

    Intent intent;
    ArrayList<String> bookedEvents=new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_facility);
    }

    public void bookOnClick(View view)
    {
        intent = new Intent(BookFacility.this, ReservationActivity.class);
        switch(view.getId())
        {
            case R.id.buttonKitchen:
                intent.putExtra("activityType","Kitchen");
                activityType="KITCHEN";

                break;

            case R.id.buttonGym:
                intent.putExtra("activityType","Gym");
                activityType="KITCHEN";
                break;
            case R.id.buttonDryer:
                intent.putExtra("activityType","Dryer");
                activityType="KITCHEN";
                break;
            case R.id.buttonStudyRoom:
                intent.putExtra("activityType","Study Room");
                activityType="KITCHEN";
                break;
            case R.id.buttonBathroom:
                intent.putExtra("activityType","Bathroom");
                activityType="KITCHEN";
                break;
            case R.id.buttonWasher:
                intent.putExtra("activityType","Washer");
                activityType="KITCHEN";
                break;
            case R.id.buttonCommonRoom:
                intent.putExtra("activityType","Common Room");
                activityType="KITCHEN";
                break;
            case R.id.buttonPlayRoom:
                intent.putExtra("activityType","Play Room");
                activityType="KITCHEN";
                break;
            default:Log.d("myTag", "I am Out of here");
                intent.putExtra("activityType","Nothing");
                activityType="KITCHEN";
                 break;
        }
        getBookingDetail(activityType);
        intent.putExtra("bookedEvents", bookedEvents);
        startActivity(intent);
    }

    public void getBookingDetail(final String activityType){
        class BookingDetailFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(BookFacility.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                Log.d("Response from server", httpResponseMsg.toString());

                Toast.makeText(BookFacility.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                Pattern pattern = Pattern.compile("(button\\d+AM\\d+AM)");
                Matcher matcher = pattern.matcher(httpResponseMsg.toString());
                //bookedEvents = new ArrayList<>();
                while (matcher.find()) {
                    // Get the matching string
                    bookedEvents.add(matcher.group());
                    Log.d("This",matcher.group());
                }
                for(String s:bookedEvents){
                    Log.d("List",s);
                }
               // Intent intent = new Intent(BookFacility.this, ReservationActivity.class);
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("facilityName",params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                Log.d("Activity" , params[0]);


                return finalResult;
            }

        }
        BookingDetailFunctionClass bookingDetailFunctionClass = new BookingDetailFunctionClass();

        bookingDetailFunctionClass.execute(activityType);
    }


}
