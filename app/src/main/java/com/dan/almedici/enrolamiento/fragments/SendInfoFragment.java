package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dan.almedici.enrolamiento.R;
import com.dan.almedici.enrolamiento.utils.Utils;
import com.idmission.client.IdType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OnSendInfoFListener mCallback;
    private View rootView;
    Context context;
    EditText editCustomerName, editUniqueCustomerNumber, editUniqueMerchantNumber, editServiceID,
            editCustomerAttribute, editCustomerPhone, editCustomerEmail, editUniqueEmployeeCode,
            editUniqueEmployeeNumber, editOldClientCustomerNumber, editAddres1, editAddres2, editcCoutry, editState;
    Button btnEviarDatos;
    Spinner mySpinner;
    LinearLayout progress;

    public SendInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SendInfoFragment newInstance(String param1, String param2) {
        SendInfoFragment fragment = new SendInfoFragment();
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

        container.removeAllViews();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_send_info, container, false);
        context = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniciarUI();
    }

    public interface OnSendInfoFListener{
        public void procesarYCuadrarImagen(JSONObject jsonObject, IdType idType);
        public void gotoMain();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (SendInfoFragment.OnSendInfoFListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Debes implementar OnFirstFrgmentSelectedListener");
        }

    }

    public void iniciarUI(){
        editCustomerName = (EditText) rootView.findViewById(R.id.edit_customer_name);
        //editUniqueCustomerNumber = (EditText) rootView.findViewById(R.id.edit_customer_number);
        editUniqueMerchantNumber = (EditText) rootView.findViewById(R.id.edit_umerchant_number);
        editServiceID = (EditText) rootView.findViewById(R.id.edit_service_id);
        //editCustomerType = (EditText) rootView.findViewById(R.id.edit_cust_type);
        editCustomerAttribute = (EditText) rootView.findViewById(R.id.edit_cust_attribute);
        editCustomerPhone = (EditText) rootView.findViewById(R.id.edit_cust_phone);
        editCustomerEmail = (EditText) rootView.findViewById(R.id.edit_cust_email);
        editUniqueEmployeeCode = (EditText) rootView.findViewById(R.id.edit_cust_employe_code);
        editUniqueEmployeeNumber = (EditText) rootView.findViewById(R.id.edit_cust_employe_number);
        editOldClientCustomerNumber = (EditText) rootView.findViewById(R.id.edit_oldclient_custNumber);
        editAddres1 = (EditText) rootView.findViewById(R.id.edit_addressLine1);
        editAddres2 = (EditText) rootView.findViewById(R.id.edit_addressLine2);
        editcCoutry = (EditText) rootView.findViewById(R.id.edit_country);
        editState = (EditText) rootView.findViewById(R.id.edit_state);
        btnEviarDatos = (Button) rootView.findViewById(R.id.button_eviar_info);

        mySpinner = (Spinner)rootView.findViewById(R.id.spinner_cust_type);
        progress = (LinearLayout) rootView.findViewById(R.id.linear_progress);
        progress.setVisibility(View.GONE);

        mySpinner.setAdapter(new ArrayAdapter<IdType>(context, android.R.layout.simple_spinner_item, IdType.values()));

        btnEviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                IdType idType = (IdType) mySpinner.getSelectedItem();
                mCallback.procesarYCuadrarImagen(getAdditionalDataJSON(), idType);
            }
        });
    }

    private JSONObject getAdditionalDataJSON(){
        String customername = editCustomerName.getText().toString().trim();
        //String uCustomerNumber = editUniqueCustomerNumber.getText().toString().trim();
        String uMerchantNumber = editUniqueMerchantNumber.getText().toString().trim();
        String serviceid = editServiceID.getText().toString().trim();
        //String custType = editCustomerType.getText().toString().trim();
        String custAttribute = editCustomerAttribute.getText().toString().trim();
        String custPhone = editCustomerPhone.getText().toString().trim();
        String custEmail = editCustomerEmail.getText().toString().trim();
        String uEmployeeCode = editUniqueEmployeeCode.getText().toString().trim();
        String uEmployeeNumber = editUniqueEmployeeNumber.getText().toString().trim();
        String oldClientCustNumber = editOldClientCustomerNumber.getText().toString().trim();
        String addressLine1 = editAddres1.getText().toString().trim();
        String addressLine2 = editAddres2.getText().toString().trim();
        String country = editcCoutry.getText().toString().trim();
        String state = editState.getText().toString().trim();

        JSONObject jObject = new JSONObject();
        try{
            /*
             * Only add key without spaces
             * */
            if (!Utils.isEmpty(customername)){
                jObject.put("Customer_Name", customername);
                //saveSetting(CUSTOMER_NAME, customername);
            }
            /*
            if (!Utils.isEmpty(uCustomerNumber)){
                jObject.put("Unique_Customer_Number", uCustomerNumber);
                //saveSetting(UNIQUE_CUSTOMER_NUMBER, uCustomerNumber);
            }
            */


            if (!Utils.isEmpty(uMerchantNumber)){
                jObject.put("Unique_Merchant_Number", uMerchantNumber);
                //saveSetting(UNIQUE_MERCHANT_NUMBER, uMerchantNumber);
            }

            if (!Utils.isEmpty(serviceid)){
                jObject.put("Service_ID", serviceid);
                //saveSetting(SERVICE_ID, serviceid);
            }
            /*
            if (!Utils.isEmpty(custType)){
                jObject.put("Customer_Type", custType);
                //saveSetting(CUSTOMER_TYPE, custType);
            }

             */

            if (!Utils.isEmpty(custAttribute)){
                jObject.put("Customer_Attribute", custAttribute);
                //saveSetting(CUSTOMER_ATTRIBUTE, custAttribute);
            }

            if (!Utils.isEmpty(custPhone)){
                jObject.put("Customer_Phone", custPhone);
                //saveSetting(CUSTOMER_PHONE, custPhone);
            }

            if (!Utils.isEmpty(custEmail)){
                jObject.put("Customer_Email", custEmail);
                //saveSetting(CUSTOMER_EMAIL, custEmail);
            }

            if (!Utils.isEmpty(uEmployeeCode)){
                jObject.put("Unique_Employee_Code", uEmployeeCode);
                //saveSetting(UNIQUE_EMPLOYEE_CODE, uEmployeeCode);
            }

            if (!Utils.isEmpty(uEmployeeNumber)){
                jObject.put("Unique_Employee_Number", uEmployeeNumber);
                //saveSetting(UNIQUE_EMPLOYEE_NUMBER, uEmployeeNumber);
            }

            if (!Utils.isEmpty(oldClientCustNumber)){
                jObject.put("Old_Client_Customer_Number", oldClientCustNumber);
            }

            if(!Utils.isEmpty(addressLine1)) {
                jObject.put("AddressLine1",addressLine1);
            }

            if(!Utils.isEmpty(addressLine2)) {
                jObject.put("AddressLine2",addressLine2);
            }

            if(!Utils.isEmpty(country)) {
                jObject.put("Country",country);
            }

            if(!Utils.isEmpty(state)) {
                jObject.put("State",state);
            }

        }catch (JSONException exc){
            Utils.printLogCat("SendIfoFragment", "getAdditionalDataJSON Exc : "+exc);
            Utils.mostrarMensaje(context,"getAdditionalDataJSON Exc : "+exc );
        }

        if(jObject.length() > 0)
            return jObject;

        return null;
    }

    public void ocultarProgress(){
        progress.setVisibility(View.GONE);
        Utils.printLogCat("ocultarProgress : ", "Ocultando la barra de progreso");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback.gotoMain();
    }
}