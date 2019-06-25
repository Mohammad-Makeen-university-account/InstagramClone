package com.example.android.instagramclone;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class SocialMediaActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tapAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tapAdapter);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.postImageItem) {
            if (android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(SocialMediaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3000);
            } else {
                captureImage();
            }
        }if(item.getItemId() == R.id.logoutUserItem){
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent = new Intent(SocialMediaActivity.this,SignUp.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 4000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3000)

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                captureImage();

            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4000 && resultCode == RESULT_OK && data != null) {

            try {
                Uri capturedImage = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(SocialMediaActivity.this.getContentResolver()
                                                    ,capturedImage);

                   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                    byte[] bytes = byteArrayOutputStream.toByteArray();

                    ParseFile parseFile = new ParseFile("img.png", bytes);
                    ParseObject parseObject = new ParseObject("Photos");

                    parseObject.put("picture", parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());


                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SocialMediaActivity.this, "Image Uploaded.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(SocialMediaActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                        }
                    });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}//end class

