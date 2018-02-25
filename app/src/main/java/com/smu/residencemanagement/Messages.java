package com.smu.residencemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Messages extends AppCompatActivity {

    EditText txtMessage;
    Button btnMessage;
    ProgressDialog progressDialog;
    String EmailHolder;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/ChatActivity.php";
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        txtMessage = findViewById(R.id.txtMessages);

        btnMessage = findViewById(R.id.btnMessages);


        if(getIntent().getExtras()!=null)
        {
            EmailHolder=getIntent().getStringExtra("UserEmail");
        }
        //EmailHolder = intent.getStringExtra("UserEmail");
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message=txtMessage.getText().toString();
                if(!message.isEmpty()){
                    Log.d("In mail",message);
                    Log.d("In create",EmailHolder);
                    MessageFunction(EmailHolder, message );

                }
                else {

                    Toast.makeText(Messages.this, "Please enter your message.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public void MessageFunction(final String email, final String message){

        class MessageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Messages.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();

                    Intent intent = new Intent(Messages.this, MainMenu.class);

                    intent.putExtra("UserEmail",email);

                    startActivity(intent);

                }
                else{

                    Toast.makeText(Messages.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("message",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        MessageFunctionClass messageClass = new MessageFunctionClass();

        messageClass.execute(email,message);
    }

}