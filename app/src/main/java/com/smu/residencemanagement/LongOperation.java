package com.smu.residencemanagement;


import android.os.AsyncTask;
import android.util.Log;


public class LongOperation extends AsyncTask<Void, Void, String> {

    String to = "prashob8@gmail.com";
    String from = "prashob.87@gmail.com";
    String msgBody;
    String subject;


    @Override
    protected String doInBackground(Void... params) {

        try {
            GMailSender sender = new GMailSender(from, "megamind");
            sender.sendMail(subject,msgBody,from, to);
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