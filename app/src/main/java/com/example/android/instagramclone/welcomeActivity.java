package com.example.android.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

import org.w3c.dom.Text;

public class welcomeActivity extends AppCompatActivity {

    private TextView welcomeTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        welcomeTxt = findViewById(R.id.welcome);

        welcomeTxt.setText("Welcome,"+ ParseUser.getCurrentUser().get("username"));

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                finish();
            }
        });



    }
}
