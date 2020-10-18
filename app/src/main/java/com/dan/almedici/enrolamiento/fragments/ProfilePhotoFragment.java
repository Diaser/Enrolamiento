package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dan.almedici.enrolamiento.R;
import com.dan.almedici.enrolamiento.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePhotoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String stringProfilePicture = null;
    ImageView imageViewProfilePicture;
    OnProfileFragmentListener callback;
    private Button btnTakePicture;

    public static ProfilePhotoFragment newInstance(String param1) {
        ProfilePhotoFragment fragment = new ProfilePhotoFragment();
        Bundle args = new Bundle();
        args.putString(Utils.PARAM_IMAGE_PROFILE, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stringProfilePicture = getArguments().getString(Utils.PARAM_IMAGE_PROFILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewProfilePicture = view.findViewById(R.id.imageview_profile_picture);
        btnTakePicture = view.findViewById(R.id.btn_take_ine_front);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.takeProfilePicture();
            }
        });

        Utils.printLogCat("ProfilePhotoFragment", "start");
        if(stringProfilePicture != null){
            imageViewProfilePicture.setImageBitmap(stringToBitMap(stringProfilePicture));
            Utils.printLogCat("ProfilePhotoFragment", "stringProfilePicture");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (ProfilePhotoFragment.OnProfileFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Debes implementar OnFirstFrgmentSelectedListener");
        }
    }

    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public void showProfilePicture(String imageString){
        stringProfilePicture = imageString;
        imageViewProfilePicture.setImageBitmap(stringToBitMap(stringProfilePicture));
    }

    public interface OnProfileFragmentListener{
        void takeProfilePicture();
    }


}