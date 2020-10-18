package com.dan.almedici.enrolamiento.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.dan.almedici.enrolamiento.R;

import java.util.Calendar;
import java.util.Date;

public class OldYearsValidator implements TextWatcher {
    Context context;
    final EditText editText;
    public OldYearsValidator(Context context, EditText editText){
        this.editText =  editText;
        this.context =  context;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        try{
            Date date = Utils.simpleDateFormat.parse(editable.toString());
            if(Utils.isOldYearsValidator(date))
                editText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark, context.getTheme()));
            else
                editText.setTextColor(context.getResources().getColor(R.color.colorRed, context.getTheme()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
