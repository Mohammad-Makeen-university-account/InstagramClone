package com.example.android.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class sharePictureTab extends Fragment implements View.OnClickListener{

    private Button sharedBtn;
    private EditText description;
    private ImageView sharedImg;
      Bitmap receivedImageBitmap;
    public sharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        sharedBtn = view.findViewById(R.id.btnShareImg);
        sharedImg = view.findViewById(R.id.imgShared);
        description = view.findViewById(R.id.description);


        sharedImg.setOnClickListener(sharePictureTab.this);
        sharedBtn.setOnClickListener(sharePictureTab.this);


        return view;
    }

    @Override
    public void onClick(View view) {

         switch (view.getId()){

            case R.id.btnShareImg:

                if (receivedImageBitmap != null){

                    if(description.getText().toString().equals("")){

                        FancyToast.makeText(getContext(),"Error: Enter a description for an image.",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else {
                        //Because images are large in size, can not be uploaded direclty
                        // That's why we convert it to an array of bytes.

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        byte[] bytes = byteArrayOutputStream.toByteArray();

                            //ParseFile are user to store images, documents and more.

                        ParseFile parseFile = new ParseFile("img.png",bytes);
                        ParseObject parseObject = new ParseObject("Photos");

                        parseObject.put("picture",parseFile);
                        parseObject.put("image_desc",description.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());



                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                    if(e == null){
                                        FancyToast.makeText(getContext(),"Image Uploaded.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                    }else{
                                        FancyToast.makeText(getContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                    }
                            }
                        });


                    }

                }else{
                    FancyToast.makeText(getContext(),"Error:You must select an image.",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
                break;

            case R.id.imgShared:

                if(android.os.Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED){

                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }else{
                    getChosenImg();
                }
                break;
        }

    }

    private void getChosenImg() {


//        FancyToast.makeText(getContext(),"Access the images",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1000)

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getChosenImg();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == 2000){

                if(resultCode == Activity.RESULT_OK){

                    //Do something with your captured image.

                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn
                                ,null,null,null);

                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                        String picturePath = cursor.getString(columnIndex);

                        cursor.close();// in order not to conusme unneeded resources

                         receivedImageBitmap = BitmapFactory.decodeFile(picturePath);

                        sharedImg.setImageBitmap((receivedImageBitmap));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

    }
}
