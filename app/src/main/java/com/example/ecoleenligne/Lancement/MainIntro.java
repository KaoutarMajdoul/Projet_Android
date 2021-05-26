package com.example.ecoleenligne.Lancement;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ecoleenligne.LoginActivity;
import com.example.ecoleenligne.MainActivity;
import com.example.ecoleenligne.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainIntro extends AppCompatActivity {


    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String TAG="SplachScreenActivity";
    private static int SPLACH_screen= 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_page_intro);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser()==null) {
                    Log.d(TAG, "Lancemennt login");
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
                    if(!previouslyStarted) {
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
                        edit.commit();
                        Intent intent = new Intent(MainIntro.this, SecondPageIntro.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainIntro.this, LoginActivity.class);
                        startActivity(intent);
                    }


                }
                else{
                    Log.d(TAG, "run: "+mAuth.getCurrentUser().getEmail());
                    FirebaseUser user = mAuth.getCurrentUser();
                    DocumentReference docRef = db.collection("Users").document("user "+user.getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    if (document.get("nombreEnfant") != null) {
                                        //il y a que le pere qui as des enfant
                                        Intent intent = new Intent(MainIntro.this, MainActivity.class);
                                        intent.putExtra("Parent", true);
                                        intent.putExtra("nombreEnfant", Integer.parseInt(document.get("nombreEnfant").toString()));
                                        startActivity(intent);


                                    } else {
                                        //ici c le fils
                                        Intent intent = new Intent(MainIntro.this, MainActivity.class);
                                        intent.putExtra("Parent", false);
                                        startActivity(intent);



                                    }
                                }
                            }
                        }
                });
                }

            }
        },SPLACH_screen);
    }
    public void openSecondPage(View view) {
        Intent intent = new Intent(this, SecondPageIntro.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
