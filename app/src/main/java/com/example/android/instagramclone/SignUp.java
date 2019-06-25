package com.example.android.instagramclone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText username, email, password;
    private Button login, signup;

   // private  String allKickers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username_signup);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(signup);
                }
                return false;
            }
        });
        login = findViewById(R.id.login_signUpAct);
        signup = findViewById(R.id.signUp_signUpAct);



        signup.setOnClickListener(SignUp.this);

        login.setOnClickListener(SignUp.this);

        if(ParseUser.getCurrentUser() != null){
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }


        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.login_signUpAct:
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                break;

                case R.id.signUp_signUpAct:
                            if(username.getText().toString().equals("") ||
                                    password.getText().toString().equals("") ||
                                    email.getText().toString().equals("")){

                                FancyToast.makeText(SignUp.this,"missing field(s) required",FancyToast.LENGTH_LONG, FancyToast.INFO,false) .show();
                            }else {
                                final ParseUser appUser = new ParseUser();

                                appUser.setUsername(username.getText().toString());
                                appUser.setPassword(password.getText().toString());
                                appUser.setEmail(email.getText().toString());


                                appUser.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            transitionToSocialMediaActivity();
                                            FancyToast.makeText(SignUp.this, "Signed-up Successfull!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                            Intent intent = new Intent(SignUp.this,welcomeActivity.class);
//                            startActivity(intent);
                                        } else {
                                            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }

                                    }

                                });
                            }

                    break;
        }
    }

    public void signUpRootTapped (View view){
        try{
            InputMethodManager inputmethomang = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputmethomang.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();

        }

    }

    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}//end class



