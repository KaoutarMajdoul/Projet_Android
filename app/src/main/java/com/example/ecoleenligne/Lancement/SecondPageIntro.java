package com.example.ecoleenligne.Lancement;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ecoleenligne.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SecondPageIntro extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page_intro);
    }
    public void openThirdPage(View view) {
        Intent intent = new Intent(this, ThirdPageIntro.class);
        startActivity(intent);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

