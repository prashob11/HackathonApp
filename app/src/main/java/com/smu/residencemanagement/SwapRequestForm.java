package com.smu.residencemanagement;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SwapRequestForm extends AppCompatActivity {

    EditText messageBody;
    Button yesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_request_form);

        messageBody = findViewById(R.id.editTextMessageBody);
        yesButton = findViewById(R.id.buttonSwapYes);
        addlisternerOnyesButton();
    }

    private void addlisternerOnyesButton() {
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = "prashob8@gmail.com";
                String to = "prashob8@gmail.com";
                String subject = "Swap Request";
                String msgBody = messageBody.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, msgBody);
                email.setType("message/rfc822");

                //startActivity(Intent.createChooser(email,"Please Choose an Email Client:"));
                startActivity(email);
            }
        });
    }
}
