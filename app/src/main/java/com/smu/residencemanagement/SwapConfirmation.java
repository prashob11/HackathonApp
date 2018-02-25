package com.smu.residencemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SwapConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_confirmation);
    }
    public void goToMainMenu(View view) {
        Intent intent = new Intent(SwapConfirmation.this, MainMenu.class);
        startActivity(intent);
    }
}
