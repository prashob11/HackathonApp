package com.smu.residencemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if(getIntent().getExtras()!=null)
        {
            email=getIntent().getStringExtra("UserEmail");
            //Log.d("Email",email);
        }

        //intent.putExtra("UserEmail",intent.getStringExtra("UserEmail"));
    }

    public void goToBookFacility(View view){
        Intent intent = new Intent(MainMenu.this, ScanActivity.class);
  //      Intent intent = new Intent(MainMenu.this, BookFacility.class);
        //Log.d("Email",intent.getStringExtra("UserEmail").toString());
        intent.putExtra("UserEmail",email);
        startActivity(intent);

    }

    public void goToUpcomingBookings(View view){

        Intent intent = new Intent(MainMenu.this, UpcomingBookings.class);
        intent.putExtra("UserEmail",email);
        startActivity(intent);

    }

    public void goToChatForum(View view){

        Intent intent = new Intent(MainMenu.this, ChatForum.class);
        intent.putExtra("UserEmail",email);
        startActivity(intent);

    }
    public void goToContactManagement(View view){

        Intent intent = new Intent(MainMenu.this, ContactManagementActivity.class);
        intent.putExtra("UserEmail",email);

        startActivity(intent);

    }

    public void goToFeelinglucky(View view){

        Intent intent = new Intent(MainMenu.this, Feelinglucky.class);
        intent.putExtra("UserEmail",email);

        startActivity(intent);

    }

}
