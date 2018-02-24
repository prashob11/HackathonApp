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
        msg = findViewById(R.id.newmsg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_forum);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    gotoMessages(v);
                }
        });

    }
    public void gotoMessages(View v){
        Intent intent = new Intent(this, Messages.class);
        startActivity(intent);
    }

}
