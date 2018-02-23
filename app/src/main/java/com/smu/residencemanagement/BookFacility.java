package com.smu.residencemanagement;

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
        switch(view.getId())
        {
            case R.id.buttonKitchen:
                Log.d("myTag", "I am here");
                break;

            case R.id.buttonGym:
                Log.d("myTag", "I am here");
                break;
            case R.id.buttonDryer:
                Log.d("myTag", "I am here");
                break;
            case R.id.buttonStudyRoom:
                Log.d("myTag", "I am here");
                break;
            case R.id.buttonBathroom:
                Log.d("myTag", "I am here");
                break;
            case R.id.buttonWasher:
                Log.d("myTag", "I am here");
                break;
            case R.id.buttonCommonRoom:
                Log.d("myTag", "I am here");
                break;
            case R.id.buttonPlayRoom:
                Log.d("myTag", "I am here");
                break;
            default:Log.d("myTag", "I am Out of here");
                 break;
        }

    }
}
