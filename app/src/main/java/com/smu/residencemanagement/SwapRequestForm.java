package com.smu.residencemanagement;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;


public class SwapRequestForm extends AppCompatActivity {

    EditText messageBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_request_form);

        messageBody = findViewById(R.id.editTextMessageBody);
    }

     public void sendMail(View view) {
            try {
                String msgBody = messageBody.getText().toString();
                LongOperation l=new LongOperation();
                l.msgBody = msgBody;
                l.execute();  //sends the email in background
                Toast.makeText(this, l.get(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);

            }
     }
}
