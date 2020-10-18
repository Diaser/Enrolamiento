package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dan.almedici.enrolamiento.R;
import com.dan.almedici.enrolamiento.connection.WebServiceGetSms;
import com.dan.almedici.enrolamiento.to.UserRegistration;
import com.dan.almedici.enrolamiento.utils.ExpressionRegularValidator;
import com.dan.almedici.enrolamiento.utils.IViewPagerCommunication;
import com.dan.almedici.enrolamiento.utils.Utils;
import com.dan.almedici.enrolamiento.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CellphoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CellphoneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText editCellPhone, editTextSms;
    private Button btnCellPhone, btnRegister;
    IViewPagerCommunication callback;
    OnCellPhoneFragmentListener cellphoneCallback;
    LinearLayout progress;

    public CellphoneFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CellphoneFragment newInstance(String param1, String param2) {
        CellphoneFragment fragment = new CellphoneFragment();
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
        return inflater.inflate(R.layout.fragment_cellphone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editCellPhone = view.findViewById(R.id.edit_cellphone);
        editTextSms = view.findViewById(R.id.edit_smscode);
        btnCellPhone = view.findViewById(R.id.btn_sendsms);
        btnRegister = view.findViewById(R.id.btn_register);

        editCellPhone.addTextChangedListener(new ExpressionRegularValidator(getContext(), editCellPhone, Validator.TELEFONO));


        progress = (LinearLayout) view.findViewById(R.id.linear_progress);
        btnCellPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "";
                if(!editCellPhone.getText().toString().trim().equals(""))
                    if(Utils.expressionRegularValidate(editCellPhone.getText().toString().trim(), Validator.TELEFONO ))
                        message = cellphoneCallback.enviarSms();
                    else
                        Utils.mostrarMensaje(getContext(),"Ingrese un número de correcto válido");
                else Utils.mostrarMensaje(getContext(),"Hace falta el número de teléfono");
                if(!message.equals(""))
                    Utils.mostrarMensaje(getContext(),message);
                /*
                Utils.printLogCat("btnCellPhone", "previous Handler");
                if (!editCellPhone.getText().toString().trim().equals("")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AsincSendSMSVirtual().execute();
                        }
                    }, 3000);

                }

                 */
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                    cellphoneCallback.sendAllInformation();
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            Utils.printLogCat("onAttach", "pre callbacks");
            //callback = (IViewPagerCommunication) context;
            cellphoneCallback = (OnCellPhoneFragmentListener) context;

            Utils.printLogCat("onAttach", "post callbacks");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Debes implementar IViewPagerCommunication");
        }
    }
    public String getData(Context context) {

        if (!editCellPhone.getText().toString().trim().equals("")) {
            UserRegistration.getInstance().setCell(editCellPhone.getText().toString().trim());
        }else
            return context.getResources().getString(R.string.validating) + context.getResources().getString(R.string.cel);

        return "";
    }

    public String getSmsCode(){
        if(!editTextSms.getText().toString().equals(""))
            return editTextSms.getText().toString();
        else return null;
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getActivity(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private class AsincSendSMSVirtual extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.printLogCat("AsincSendSMSVirtual", "onPreExecute");
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Utils.printLogCat("AsincSendSMSVirtual", "onPostExecute");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    editTextSms.setText("00151521235");
                    btnRegister.setEnabled(true);
                }
            }, 3000);


        }
    }

    public interface OnCellPhoneFragmentListener {
        String enviarSms();
        void sendAllInformation();
    }



    public void ocultarProgress() {
        progress.setVisibility(View.GONE);
        Utils.printLogCat("ocultarProgress : ", "Ocultando la barra de progreso");
    }
}