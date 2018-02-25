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
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class ChatForum extends AppCompatActivity {
    Button msg;
    String email;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURL = "http://dev.cs.smu.ca/~n_akash/ResidenceManagement/ChatActivity.php";
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_forum);
        if(getIntent().getExtras()!=null)
        {
            email=getIntent().getStringExtra("UserEmail");
            Log.d("UserEmail------------>",email);
            //getIntent().putExtra("UserEmail",email);
        }
        MessageFunction();
        listView=findViewById(R.id.listChatForum);
    }

    public void gotoMessages(View view){
        Intent intent = new Intent(this, Messages.class);
        intent.putExtra("UserEmail",email);
        startActivity(intent);
    }

    public void MessageFunction(){

        class MessageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(ChatForum.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    //listView.add

                    Intent intent = new Intent(ChatForum.this, MainMenu.class);

                    intent.putExtra("UserEmail",email);

                    startActivity(intent);

                }
                else{

                    Toast.makeText(ChatForum.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        MessageFunctionClass messageClass = new MessageFunctionClass();

        messageClass.execute();
    }

}
