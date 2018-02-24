package com.smu.residencemanagement;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;


public class LongOperation extends AsyncTask<Void, Void, String> {

    String to = "prashob8@gmail.com";
    String from = "prashob.87@gmail.com";

    @Override
    protected String doInBackground(Void... params) {

        try {
//            String msgBody = messageBody.getText().toString();
            GMailSender sender = new GMailSender(from, "megamind");
            sender.sendMail("Swap Request","hello from long operation",from, to);
        } catch (Exception e) {

            Log.e("error", e.getMessage(), e);
            return "Message Not Sent";
        }

        return "Message Sent";
    }



    @Override
    protected void onPostExecute(String result) {
        Log.e("LongOperation",result+"");
    }



    @Override
    protected void onPreExecute() {
    }



    @Override
    protected void onProgressUpdate(Void... values) {
    }

}