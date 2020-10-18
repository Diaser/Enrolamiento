package com.dan.almedici.enrolamiento.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dan.almedici.enrolamiento.R;
import com.dan.almedici.enrolamiento.to.UserRegistration;
import com.dan.almedici.enrolamiento.utils.ExpressionRegularValidator;
import com.dan.almedici.enrolamiento.utils.OldYearsValidator;
import com.dan.almedici.enrolamiento.utils.Utils;
import com.dan.almedici.enrolamiento.utils.Validator;

import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstRegisterFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editName, editFirstSecondName, editlastSecondName, editEmail, editBirthday;
    RadioGroup radioGroup;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstRegisterFragment() {
        // Required empty public constructor
    }


    public static FirstRegisterFragment newInstance(String param1, String param2) {
        FirstRegisterFragment fragment = new FirstRegisterFragment();
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
        return inflater.inflate(R.layout.fragment_first_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editName = view.findViewById(R.id.edit_name);
        editName.addTextChangedListener(new ExpressionRegularValidator(getContext(),editName, Validator.NOMBRES));
        editFirstSecondName = view.findViewById(R.id.edit_first_secondname);
        editFirstSecondName.addTextChangedListener(new ExpressionRegularValidator(getContext(),editFirstSecondName, Validator.NOMBRES));
        editlastSecondName =  view.findViewById(R.id.edit_last_secondname);
        editlastSecondName.addTextChangedListener(new ExpressionRegularValidator(getContext(),editlastSecondName, Validator.NOMBRES));
        editEmail = view.findViewById(R.id.edit_email);
        editEmail.addTextChangedListener(new ExpressionRegularValidator(getContext(),editEmail,Validator.EMAIL));
        editBirthday = view.findViewById(R.id.edit_birthday);
        editBirthday.addTextChangedListener(new OldYearsValidator(getContext(), editBirthday));
        radioGroup = view.findViewById(R.id.gender_option);

        editBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }


    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // +1 because January is zero
        //final String selectedDate = day + " / " + (month+1) + " / " + year;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String selectedDate = Utils.simpleDateFormat.format(calendar.getTime());

        editBirthday.setText(selectedDate);
    }

    private JSONObject getAdditionalDataJSON(){

        String serviceid = "50";
        String name = editName.getText().toString().trim();
        String firstSecondName = editFirstSecondName.getText().toString().trim();
        String lastSecondName = editlastSecondName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String birthday = editBirthday.getText().toString().trim();
        String gender = "";
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId == R.id.radio_female)
            gender = "F";
        else
            gender = "M";


        return null;
    }
    @Override
    public void onDestroyView() {
        Utils.printLogCat("onDestroyView","FirstRegisterActivity");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Utils.printLogCat("onDestroy","FirstRegisterActivity");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Utils.printLogCat("onDetach","FirstRegisterActivity");
        super.onDetach();
    }

    public String getData(Context context){

        Utils.printLogCat("getData", editName.getText().toString());
        UserRegistration userRegistration = UserRegistration.getInstance();
        if(editName.getText().toString()!=null && !editName.getText().toString().trim().equals(""))
            userRegistration.setName(editName.getText().toString().trim());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.name);
        if(editFirstSecondName.getText().toString() != null && !editFirstSecondName.getText().toString().trim().equals(""))
            userRegistration.setFirstSecondName(editFirstSecondName.getText().toString());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.second_name1);
        if(editlastSecondName.getText().toString() !=null && !editlastSecondName.getText().toString().trim().equals(""))
            userRegistration.setLastSecondName(editlastSecondName.getText().toString());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.second_name2);
        if(!editEmail.getText().toString().trim().equals(""))
            userRegistration.setEmail(editEmail.getText().toString().trim());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.email);
        if(!editBirthday.getText().toString().trim().equals(""))
            userRegistration.setBirthday(editBirthday.getText().toString().trim());
        else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.birthdat);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId == R.id.radio_female)
            userRegistration.setGender("F");
        else
            userRegistration.setGender("M");

        return "";

    }
}