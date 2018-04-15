package com.smu.residencemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class Feelinglucky extends AppCompatActivity {
    EditText text1;
    static Random randGen = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelinglucky);
    }

    public void generateLuckyNumber(View view) {


        TextView text1 = (TextView) findViewById(R.id.textView8);
        TextView text2 = (TextView) findViewById(R.id.textView9);
        TextView text3 = (TextView) findViewById(R.id.textView10);
        TextView text4 = (TextView) findViewById(R.id.textView11);
        TextView text5 = (TextView) findViewById(R.id.textView12);
        TextView text6 = (TextView) findViewById(R.id.textView3);

        int random1 = randGen.nextInt(100);
        text1.setText(String.format("%02d", random1));

        int random2 = randGen.nextInt(100);
        text2.setText(String.format("%02d", random2));


        int random3 = randGen.nextInt(100);
        text3.setText(String.format("%02d", random3));
        int random4 = randGen.nextInt(100);
        text4.setText(String.format("%02d", random4));
        int random5 = randGen.nextInt(100);
        text5.setText(String.format("%02d", random5));
        int random6 = randGen.nextInt(100);
        text6.setText(String.format("%02d", random6));



    }
}
