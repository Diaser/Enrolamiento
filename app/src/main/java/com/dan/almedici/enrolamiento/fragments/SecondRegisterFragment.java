package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dan.almedici.enrolamiento.R;
import com.dan.almedici.enrolamiento.to.UserRegistration;
import com.dan.almedici.enrolamiento.utils.ExpressionRegularValidator;
import com.dan.almedici.enrolamiento.utils.Validator;

public class SecondRegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    EditText editRfc, editCurp, editPassword, editRepeatPassword;

    public SecondRegisterFragment() {
        // Required empty public constructor
    }


    public static SecondRegisterFragment newInstance(String param1, String param2) {
        SecondRegisterFragment fragment = new SecondRegisterFragment();
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
        return inflater.inflate(R.layout.fragment_second_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editRfc = view.findViewById(R.id.edit_rfc);
        editRfc.addTextChangedListener(new ExpressionRegularValidator(getContext(),editRfc, Validator.RFC));
        editCurp = view.findViewById(R.id.edit_curp);
        editCurp.addTextChangedListener(new ExpressionRegularValidator(getContext(),editCurp, Validator.CURP));
        editPassword = view.findViewById(R.id.edit_password);
        //editPassword.addTextChangedListener(new ExpressionRegularValidator(getContext(), editPassword, Validator.PASSWORD));
        editRepeatPassword = view.findViewById(R.id.edit_password_confirm);
        //editRepeatPassword.addTextChangedListener(new ExpressionRegularValidator(getContext(),editRepeatPassword,Validator.PASSWORD));
    }

    public String getData(Context context){

        UserRegistration userRegistration =  UserRegistration.getInstance();
        if(!editRfc.getText().toString().trim().equals(""))
            userRegistration.setRfc(editRfc.getText().toString().trim());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.rfc);
        if(!editCurp.getText().toString().trim().equals(""))
            userRegistration.setCurp(editCurp.getText().toString().trim());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.curp);
        if(!editPassword.getText().toString().trim().equals("") && !editRepeatPassword.getText().toString().trim().equals("")){
            if(editRepeatPassword.getText().toString().trim().equals(editPassword.getText().toString().trim()))
                userRegistration.setPassword(editPassword.getText().toString().trim());
            else
                return context.getResources().getString(R.string.validating_password_password2);
        }else
            return context.getResources().getString(R.string.validating_password);


        return "";
    }
}