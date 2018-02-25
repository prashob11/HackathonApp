package com.smu.residencemanagement;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    String[] keyValue;

    private Map<String, Integer> stringForResourceIdMap = createMap();

    private Map<String, Integer> createMap() {
        Map<String, Integer> result = new HashMap<>();

        result.put("KITCHEN", R.string.Kitchen);
        result.put("BATHROOM", R.string.Bathroom);
        result.put("DRYER", R.string.Dryer);
        result.put("GYM", R.string.Gym);
        result.put("COMMONROOM", R.string.CommonRoom);
        result.put("PLAYROOM", R.string.PlayRoom);
        result.put("STUDYROOM", R.string.StudyRoom);
        result.put("WASHER", R.string.Washer);


        result.put("button6AM7AM", R.string.string_6AM7AM);
        result.put("button7AM8AM", R.string.string_7AM8AM);
        result.put("button8AM9AM", R.string.string_8AM9AM);
        result.put("button9AM10AM", R.string.string_9AM10AM);
        result.put("button10AM11AM", R.string.string_10AM11AM);
        result.put("button11AM12PM", R.string.string_11AM12PM);
        result.put("button12PM1PM", R.string.string_12PM1PM);
        result.put("button1PM2PM", R.string.string_1PM2PM);
        result.put("button2PM3PM", R.string.string_2PM3PM);
        result.put("button3PM4PM", R.string.string_3PM4PM);
        result.put("button4PM5PM", R.string.string_4PM5PM);
        result.put("button5PM6PM", R.string.string_5PM6PM);
        result.put("button6PM7PM", R.string.string_6PM7PM);
        result.put("button7PM8PM", R.string.string_7PM8PM);
        result.put("button8PM9PM", R.string.string_8PM9PM);
        result.put("button9PM10PM", R.string.string_9PM10PM);
        result.put("button10PM11PM", R.string.string_10PM11PM);
        result.put("button11PM12AM", R.string.string_11PM12AM);
        return result;
    }

    private int getMyStringResource(String lookupString) {
        int resourceId = stringForResourceIdMap.get(lookupString); // R.string.xxx
        return (resourceId);
    }

    private ArrayList<Bookings> bookings = new ArrayList<>();


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
                Log.d("Response from server:",  httpResponseMsg.toString());
                Pattern pattern = Pattern.compile("(\"button\\d+(?:AM|PM)\\d+(?:AM|PM)\",\"[A-z]+\")");
                Matcher matcher = pattern.matcher(httpResponseMsg.toString());

                while (matcher.find()) {

                    keyValue = matcher.group().replace("\"","").split(",");

                  bookings.add(new Bookings(getMyStringResource(keyValue[0]),getMyStringResource(keyValue[1])));
                   /* Log.d("This****",matcher.group());
                    Log.d("This****",String.valueOf(getMyStringResource(keyValue[0])));
                    Log.d("This****",String.valueOf(getMyStringResource(keyValue[1])));
                    //Log.d("This****", String.valueOf());*/
                }

                GridView gridView = (GridView)findViewById(R.id.gridview);
                UpcomingBookingsAdapter bookingsAdapter = new UpcomingBookingsAdapter(UpcomingBookings.this, bookings);
                gridView.setAdapter(bookingsAdapter);
                /*for( Bookings booking : bookings )
                {
                        Log.d("Here", Integer.toString(booking.getbookingTime()));
                        Log.d("Here", Integer.toString(booking.getFacility()));

                }
*/

                //Toast.makeText(UpcomingBookings.this,httpResponseMsg,Toast.LENGTH_LONG).show();
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
