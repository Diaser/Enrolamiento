package com.dan.almedici.enrolamiento.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Utils {
    public static String PARAM_IMAGE_INE_FRONT = "PHOTO_INE_FRONT";
    public static String PARAM_IMAGE_INE_BACK = "PHOTO_INE_BACK";
    public static String PARAM_IMAGE_PROFILE = "PHOTO_PROFILE";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static final String URL_WEBSERVICE_AUTHORIZE = "http://ilmedicisecuritys.southcentralus.cloudapp.azure.com/api/UsuarioApp/authorize/";
    public static final String URL_WEBSERVICE_USER = "http://ilmedicisecuritys.southcentralus.cloudapp.azure.com/api/UsuarioApp/";

    public static void printLogCat(String metodo, String mensaje){
        Log.i("EDD "+metodo, mensaje);
    }

    public static boolean isEmpty(String source) {
        return (null == source || source.trim().equals("")) ? true : false;
    }
    public static void mostrarMensaje(Context context, String texto){
        Toast t = Toast.makeText(context, texto, Toast.LENGTH_LONG);
        t.show();
    }
    public static boolean expressionRegularValidate(String value, String expressionRegular){
        if (value!=null && !value.equals(""))
            if(value.matches(expressionRegular))
                return true;
        return false;
    }
    public static boolean isOldYearsValidator(Date dateBirthday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR,-18);
        Utils.printLogCat("isOldYersValidator","18 años atras " + Utils.simpleDateFormat.format(calendar.getTime()) );

        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(dateBirthday);
        Utils.printLogCat("isOldYersValidator", Utils.simpleDateFormat.format(calendarB.getTime()) );

        if(calendarB.getTime().before(calendar.getTime()) || calendarB.getTime().equals(calendar.getTime())){
            Utils.printLogCat("isOldYersValidator", "birthday es antes o igual" );
            return true;
        }

        return false;
    }
}
