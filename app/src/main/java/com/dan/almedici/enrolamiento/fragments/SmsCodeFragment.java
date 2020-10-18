package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dan.almedici.enrolamiento.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SmsCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmsCodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnSmsCode;
    onSmsCodeFListener mCallBack;

    public SmsCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmsCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SmsCodeFragment newInstance(String param1, String param2) {
        SmsCodeFragment fragment = new SmsCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sms_code, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSmsCode = view.findViewById(R.id.btn_smscode);
        btnSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.sendAllInformation();
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mCallBack = (SmsCodeFragment.onSmsCodeFListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " Debes implementar OnFirstFrgmentSelectedListener");
        }
    }
    public interface onSmsCodeFListener{
        void sendAllInformation();
    }
}