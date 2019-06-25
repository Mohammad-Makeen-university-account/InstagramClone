package com.example.android.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        linearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObj = getIntent();
        final String receivedUsername = receivedIntentObj.getStringExtra("username");

        setTitle(receivedUsername +"'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photos");
        parseQuery.whereEqualTo("username",receivedUsername);
        parseQuery.orderByDescending("createdAt");



        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(objects.size() > 0 && e == null){

                    FancyToast.makeText(UsersPosts.this,"it works!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

                    for(ParseObject post : objects){

                        final TextView postDescription = new TextView(UsersPosts.this);
                        postDescription.setText(post.get("image_desc")+"");

                        ParseFile postPicture = (ParseFile) post.get("Picture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if(data != null && e == null){

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);

                                    LinearLayout.LayoutParams imagView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imagView_params.setMargins(5,5,5,5);

                                    postImageView.setLayoutParams(imagView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);


                                    LinearLayout.LayoutParams description_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    description_params.setMargins(5,5,5,5);

                                    postDescription.setBackgroundColor(Color.GREEN);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setTextColor(Color.BLACK);
                                    postDescription.setTextSize(29f);

                                    linearLayout.addView(postDescription);
                                    linearLayout.addView(postImageView);


                                }
                            }
                        });

                    }
                }else{
//                    FancyToast.makeText(UsersPosts.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                    FancyToast.makeText(UsersPosts.this,receivedUsername +" does not have any posts!",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
//                    finish();

                }
            }


    });

}


}
