package com.example.android.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

        private EditText email,password;
        private Button signUp, login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(login);
                }
                return false;
            }
        });


        login = findViewById(R.id.loginBtn_loginAct);
        signUp = findViewById(R.id.signUpBtn_loginAct);


        signUp.setOnClickListener(LoginActivity.this);


        login.setOnClickListener(LoginActivity.this);

        if(ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.loginBtn_loginAct:

                if(password.getText().toString().equals("") || email.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this,"missing field(s) required",FancyToast.LENGTH_LONG, FancyToast.INFO,false) .show();
                }else {

                    ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this, "Logged in Successfull!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                        transitionToSocialMediaActivity();
                                    } else {
                                        FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                                    }
                                }
                            });
                }
            break;

            case R.id.signUpBtn_loginAct:

                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
                break;
        }
    }

    public void loginRootTapped (View view){
        try{
            InputMethodManager inputmethomang = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmethomang.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();

        }
    }
    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}//end class
