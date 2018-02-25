package com.smu.residencemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ManagementAckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_ack);
    }

    public void  goToContactManagement(View view){

        Intent intent = new Intent(ManagementAckActivity.this, MainMenu.class);
        startActivity(intent);
    }

}
