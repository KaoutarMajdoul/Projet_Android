package com.example.ecoleenligne.Lancement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ecoleenligne.R;
import android.content.Intent;

import android.view.View;
public class FirstPageIntro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openSecondPage(View view) {
        Intent intent = new Intent(this, SecondPageIntro.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
