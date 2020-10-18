package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dan.almedici.enrolamiento.R;
import com.dan.almedici.enrolamiento.utils.Utils;
import com.idmission.client.ImageType;

public class ShowINEFragment extends Fragment {

    String imageStringFront = null;
    String imageStringBack =  null;
    ImageView imageViewFront, imageViewBack;
    OnShowINEFragmentListener callback;
    Button btnTakeIneFront, btnTakeIneBack;

    public static ShowINEFragment newInstance(String imageStringFront, String imageStringBack) {
        ShowINEFragment fragment = new ShowINEFragment();
        Bundle args = new Bundle();
        args.putString(Utils.PARAM_IMAGE_INE_FRONT, imageStringFront);
        args.putString(Utils.PARAM_IMAGE_INE_BACK, imageStringBack);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageStringFront = getArguments().getString(Utils.PARAM_IMAGE_INE_FRONT);
            imageStringBack = getArguments().getString(Utils.PARAM_IMAGE_INE_BACK);
        }
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_ine, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTakeIneFront = view.findViewById(R.id.btn_take_ine_front);
        btnTakeIneBack =  view.findViewById(R.id.btn_take_ine_back);
        imageViewFront = view.findViewById(R.id.imageview_front);
        imageViewBack = view.findViewById(R.id.imageview_back);

        if(imageStringFront!=null){

            imageViewFront.setImageBitmap(stringToBitMap(imageStringFront));
        }
        if(imageStringBack != null){

            imageViewBack.setImageBitmap(stringToBitMap(imageStringBack));
        }
        btnTakeIneFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.takePictureIneFront();
            }
        });
        btnTakeIneBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.takePictureIneBack();
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (ShowINEFragment.OnShowINEFragmentListener) context;
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
    public void showIne(ImageType imageType, String stringImage){
        if(imageType == ImageType.FRONT){
            imageStringFront = stringImage;
            imageViewFront.setImageBitmap(stringToBitMap(stringImage));
        }else if(imageType == ImageType.BACK){
            imageStringBack =  stringImage;
            imageViewBack.setImageBitmap(stringToBitMap(stringImage));
        }

    }

    public interface OnShowINEFragmentListener{
        void takePictureIneFront();
        void takePictureIneBack();
    }

}