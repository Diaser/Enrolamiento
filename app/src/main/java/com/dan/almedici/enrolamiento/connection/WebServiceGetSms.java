package com.dan.almedici.enrolamiento.connection;

import android.content.Context;
import android.os.AsyncTask;

import com.dan.almedici.enrolamiento.to.UserRegistration;
import com.dan.almedici.enrolamiento.utils.Utils;
import com.journeyapps.barcodescanner.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class WebServiceGetSms {

    public static void getSms(Context context, UserRegistration userRegistration){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try{
            Date date = Utils.simpleDateFormat.parse(userRegistration.getBirthday());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd'T'h:mm:ssXXX");

            jsonParams.put("dateOfBirth",simpleDateFormat.format(date));
            jsonParams.put("firstName",userRegistration.getName());
            jsonParams.put("lastName",userRegistration.getFirstSecondName());
            jsonParams.put("motherLastName",userRegistration.getLastSecondName());
            String gender ="Femenino";
            if(userRegistration.getGender().equals("M"))
                gender = "Masculino";
            jsonParams.put("gender",gender);
            jsonParams.put("email",userRegistration.getEmail());
            jsonParams.put("userName",userRegistration.getEmail());
            jsonParams.put("passwordHash",userRegistration.getPassword());
            jsonParams.put("phoneNumber","52"+userRegistration.getCell());
            jsonParams.put("rfc",userRegistration.getRfc());
            jsonParams.put("curp",userRegistration.getCurp());
            jsonParams.put("deviceToken","");
            jsonParams.put("mobileOs","Android");
            Utils.printLogCat("JSON try",jsonParams.toString());
            entity = new StringEntity(jsonParams.toString());
        }catch (JSONException e){
            jsonParams = null;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


        Utils.printLogCat("WebServiceGetSms","pre service Authorize");
        client.post(context, Utils.URL_WEBSERVICE_AUTHORIZE, entity,"application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Utils.mostrarMensaje(context,responseString);
                Utils.printLogCat("WebServiceGetSms","onFailure: "+ responseString);
                Utils.printLogCat("WebServiceGetSms","response code:" + statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Utils.mostrarMensaje(context,responseString);
                Utils.printLogCat("WebServiceGetSms","onSuccess:" + responseString);
                Utils.printLogCat("WebServiceGetSms","response code:" + statusCode);
            }
        });
    }


    public static void confirmRegistration(Context context, UserRegistration userRegistration, String smsCode){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try{
            Date date = Utils.simpleDateFormat.parse(userRegistration.getBirthday());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd'T'h:mm:ssXXX");

            jsonParams.put("dateOfBirth",simpleDateFormat.format(date));
            jsonParams.put("firstName",userRegistration.getName());
            jsonParams.put("lastName",userRegistration.getFirstSecondName());
            jsonParams.put("motherLastName",userRegistration.getLastSecondName());
            String gender ="Femenino";
            if(userRegistration.getGender().equals("M"))
                gender = "Masculino";
            jsonParams.put("gender",gender);
            jsonParams.put("email",userRegistration.getEmail());
            jsonParams.put("userName",userRegistration.getEmail());
            jsonParams.put("passwordHash",userRegistration.getPassword());
            jsonParams.put("phoneNumber","52"+userRegistration.getCell());
            jsonParams.put("rfc",userRegistration.getRfc());
            jsonParams.put("curp",userRegistration.getCurp());
            jsonParams.put("deviceToken","");
            jsonParams.put("mobileOs","Android");
            Utils.printLogCat("JSON try",jsonParams.toString());
            entity = new StringEntity(jsonParams.toString());

            entity = new StringEntity(jsonParams.toString());
            Utils.printLogCat("JSON try",jsonParams.toString());
        }catch (JSONException e){
            jsonParams = null;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }

        Utils.printLogCat("WebServiceGetSms","pre service User");
        client.post(context, Utils.URL_WEBSERVICE_USER, entity,"application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Utils.mostrarMensaje(context,responseString);
                Utils.printLogCat("WebServiceGetSms","onFailure: "+ responseString);
                Utils.printLogCat("WebServiceGetSms","response code:" + statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Utils.mostrarMensaje(context,responseString);
                Utils.printLogCat("WebServiceGetSms","onSuccess:" + responseString);
                Utils.printLogCat("WebServiceGetSms","response code:" + statusCode);
            }
        });
    }


}
