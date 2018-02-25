package com.smu.residencemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatForum extends AppCompatActivity {
    Button msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_forum);
    }

    public void gotoMessages(View view){
        Intent intent = new Intent(this, Messages.class);
        startActivity(intent);
    }

}
