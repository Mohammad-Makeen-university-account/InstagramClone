package com.example.android.instagramclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileTab extends Fragment {

    private EditText profileName, bio, profession, hobbies, favoriteSport;
    private Button updateBtn;


    public profileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        profileName = view.findViewById(R.id.edtProfName);
        bio = view.findViewById(R.id.edtProfilBio);
        profession = view.findViewById(R.id.edtProfileProfession);
        hobbies = view.findViewById(R.id.edtProfileHobbies);
        favoriteSport = view.findViewById(R.id.edtProfileFavSport);

        updateBtn = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("profileName") != null){
            profileName.setText(parseUser.get("profileName").toString());
        }else{
            profileName.setText("");
        }

        if(parseUser.get("bio") != null){
            bio.setText(parseUser.get("bio").toString());
        }else{
            bio.setText("");
        }

        if(parseUser.get("profession") != null){
            profession.setText(parseUser.get("profession").toString());
        }else{
            profession.setText("");
        }

        if(parseUser.get("hobbies") != null){
            hobbies.setText(parseUser.get("hobbies").toString());
        }else{
            hobbies.setText("");
        }

        if(parseUser.get("favoriteSport") != null){
            favoriteSport.setText(parseUser.get("favoriteSport").toString());
        }else{
            favoriteSport.setText("");
        }



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", profileName.getText().toString());
                parseUser.put("bio", bio.getText().toString());
                parseUser.put("profession", profession.getText().toString());
                parseUser.put("hobbies", hobbies.getText().toString());
                parseUser.put("favoriteSport", favoriteSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            FancyToast.makeText(getContext(), "Info is updated", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();

                        } else {
                            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });

            }
        });

        return view;
    }
}
