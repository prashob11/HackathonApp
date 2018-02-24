package com.smu.residencemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class ReservationActivity extends AppCompatActivity implements
        View.OnClickListener  {

    Button btnDatePicker;
    Button timeButton;
    EditText txtDate;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent intent=getIntent();
        String activityType = "Reserve a slot for " + intent.getStringExtra("activityType");
        ((TextView)findViewById(R.id.tvMessage)).setText(activityType);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);
        String[] buttonIdArray={"button6AM7AM",	"button7AM8AM",	"button8AM9AM",	"button9AM10AM",	"button10AM11AM",	"button11AM12PM",	"button12PM1PM",	"button1PM2PM",	"button2PM3PM",	"button3PM4PM",	"button4PM5PM",	"button5PM6PM",	"button6PM7PM",	"button7PM8PM",	"button8PM9PM",	"button9PM10PM",	"button10PM11PM",	"button11PM12AM"};


        ArrayList<String> bookedEventTimes = intent.getStringArrayListExtra("bookedEvents");
        Log.d("Booked event time:","Hi ");
        int resId;

        for(String s: bookedEventTimes){
            Log.d("Id of button",Integer.toString(R.id.button5PM6PM) );
            Log.d("Booked event time:", s);
        }

        //Log.d("IdName of button", Integer.toString (resId));
        for(String buttonId: buttonIdArray){
            resId= getResources().getIdentifier(buttonId, "id",getPackageName());
            timeButton=(Button) findViewById(resId);
            //For swap
            Log.d("Booked event time:","Hi ");
            if(bookedEventTimes.contains(buttonId)){
                timeButton.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(ReservationActivity .this, SwapRequestForm.class);
                        startActivity(intent);
                        Log.d("Inside Swap","inside swap");
                    }
                });
            }
            else
            {
                timeButton.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(ReservationActivity .this, BookingSummaryActivity.class);
                        startActivity(intent);
                        Log.d("Inside create","inside create");
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

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}
