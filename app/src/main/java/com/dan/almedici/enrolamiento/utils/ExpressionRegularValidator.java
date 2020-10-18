package com.dan.almedici.enrolamiento.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.dan.almedici.enrolamiento.R;

public class ExpressionRegularValidator implements TextWatcher {
    Context context;
    final EditText editText;
    String exprReg;
    public ExpressionRegularValidator(Context context, EditText editText, String ExprReg){
        this.editText =  editText;
        this.context =  context;
        this.exprReg =  ExprReg;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if(Utils.expressionRegularValidate(editable.toString().trim(), exprReg))
            editText.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark, context.getTheme()));
        else
            editText.setTextColor(context.getResources().getColor(R.color.colorRed, context.getTheme()));
    }
}
