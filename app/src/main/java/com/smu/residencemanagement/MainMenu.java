package com.smu.residencemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void goToBookFacility(View view){

        Intent intent = new Intent(MainMenu.this, BookFacility.class);

        startActivity(intent);

    }

    /*public void goToUpcomingBookings(View view){

        Intent intent = new Intent(MainMenu.this, UpcomingBookings.class);

        startActivity(intent);

    }
*/
    public void goToUpcomingBookings(View view){

        Intent intent = new Intent(MainMenu.this, SwapRequestForm.class);

        startActivity(intent);

    }

}
