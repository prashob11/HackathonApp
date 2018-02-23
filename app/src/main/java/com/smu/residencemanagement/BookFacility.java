package com.smu.residencemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BookFacility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_facility);
    }

    public void bookOnClick(View view)
    {
        Intent intent = new Intent(BookFacility.this, ReservationActivity.class);
        switch(view.getId())
        {
            case R.id.buttonKitchen:
                intent.putExtra("activityType","Kitchen");
                break;

            case R.id.buttonGym:
                intent.putExtra("activityType","Gym");
                break;
            case R.id.buttonDryer:
                intent.putExtra("activityType","Dryer");
                break;
            case R.id.buttonStudyRoom:
                intent.putExtra("activityType","Study Room");
                break;
            case R.id.buttonBathroom:
                intent.putExtra("activityType","Bathroom");
                break;
            case R.id.buttonWasher:
                intent.putExtra("activityType","Washer");
                break;
            case R.id.buttonCommonRoom:
                intent.putExtra("activityType","Common Room");
                break;
            case R.id.buttonPlayRoom:
                intent.putExtra("activityType","Play Room");
                break;
            default:Log.d("myTag", "I am Out of here");
                intent.putExtra("activityType","Nothing");
                 break;
        }
        startActivity(intent);
    }
}
