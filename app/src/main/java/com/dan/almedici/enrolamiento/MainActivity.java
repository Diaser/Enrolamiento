package com.dan.almedici.enrolamiento;


import android.content.DialogInterface;
import android.os.Bundle;

import com.dan.almedici.enrolamiento.connection.WebServiceGetSms;
import com.dan.almedici.enrolamiento.fragments.CellphoneFragment;
import com.dan.almedici.enrolamiento.fragments.FirstRegisterFragment;
import com.dan.almedici.enrolamiento.fragments.ProfilePhotoFragment;
import com.dan.almedici.enrolamiento.fragments.SecondRegisterFragment;
import com.dan.almedici.enrolamiento.fragments.SendInfoFragment;
import com.dan.almedici.enrolamiento.fragments.ShowINEFragment;
import com.dan.almedici.enrolamiento.fragments.SmsCodeFragment;
import com.dan.almedici.enrolamiento.to.UserRegistration;
import com.dan.almedici.enrolamiento.utils.IViewPagerCommunication;
import com.dan.almedici.enrolamiento.utils.Utils;
import com.dan.almedici.enrolamiento.utils.Validator;
import com.idmission.client.DeviceNotSupportedException;
import com.idmission.client.IdType;
import com.idmission.client.ImageProcessingResponseListener;
import com.idmission.client.ImageProcessingSDK;
import com.idmission.client.ImageType;
import com.idmission.client.InitializationException;
import com.idmission.client.PlayServiceException;
import com.idmission.client.Response;
import com.idmission.client.UIConfigurationParameters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements ImageProcessingResponseListener,
        ShowINEFragment.OnShowINEFragmentListener, ProfilePhotoFragment.OnProfileFragmentListener,
        IViewPagerCommunication, CellphoneFragment.OnCellPhoneFragmentListener {

    ImageProcessingSDK imageProcessingSDK = null;
    boolean salir = true;
    SendInfoFragment sendInfoFragment;
    String imageStringFront = null, imageStringBack = null, imageprofile = null;
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    FirstRegisterFragment firstRegisterActivity;
    SecondRegisterFragment secondRegisterFragment;
    ShowINEFragment showINEFragment;
    ProfilePhotoFragment profilePhotoFragment;
    CellphoneFragment cellphoneFragment;
    SmsCodeFragment smsCodeFragment;
    boolean keyCellPhone =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializandoProcesoImagen();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd'T'h:mm:ssXXX");
        Utils.printLogCat("Main", simpleDateFormat.format(new Date()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(salir)
            finish();
        else{
            if (mPager.getCurrentItem() == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button. This calls finish() on this activity and pops the back stack.
                super.onBackPressed();
            } else {
                // Otherwise, select the previous step.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        }

    }

    @Override
    public void takePictureIneFront() {
        imageProcessingSDK.autoCapture(MainActivity.this, ImageType.FRONT);
    }

    @Override
    public void takePictureIneBack() {
        imageProcessingSDK.autoCapture(MainActivity.this, ImageType.BACK);
    }

    @Override
    public void takeProfilePicture() {
        autocapturaRostro();
    }

    @Override
    public String enviarSms() {
        String message = "";

        if(imageprofile!=null & imageStringBack!=null & imageStringFront!=null)
        {
            message = firstRegisterActivity.getData(getApplicationContext());
            if(!message.trim().equals(""))
                return message;
            message = secondRegisterFragment.getData(getApplicationContext());
            if(!message.trim().equals(""))
                return message;
            message = cellphoneFragment.getData(getApplicationContext());
            if(!message.trim().equals(""))
                return message;
            if(message.equals(""))
                WebServiceGetSms.getSms(getApplicationContext(),UserRegistration.getInstance());
            return "";
        }else
            return "Captura las imágenes correspondientes";
    }

    @Override
    public void sendAllInformation() {

        JSONObject jsonObject = getAdditionalDataJSON();
        if(jsonObject !=null ){
            imageProcessingSDK.processImageAndMatchFace(MainActivity.this,
                    "1",
                    "1",
                    IdType.VOTER_ID_CARD,
                    "OVAL_FACE",
                    jsonObject,
                    true,
                    true);
        }else{
            Utils.mostrarMensaje(getApplicationContext(),"Tiene que completar el formulario correctamente");
            cellphoneFragment.ocultarProgress();
        }
    }
    public void inicializandoProcesoImagen(){
        try {
            imageProcessingSDK = ImageProcessingSDK.initialize(this,"https://kyc.idmission.com/IDS/service/integ/idm/thirdparty/upsert",
                    "ev_integ_52881",
                    "IDmi#881$",
                    "32337",
                    920,
                    "Identity_Validation_and_Face_Matching",
                    "es" );
            imageProcessingSDK.setImageProcessingResponseListener(this);
        }catch ( DeviceNotSupportedException e) {
            //StringUtil.printLogInDebugMode("INIT Exception", e.getMessage());
            mostrarMensaje("Error : " + e.getMessage());
        } catch (InitializationException e) {
            //StringUtil.printLogInDebugMode("INIT Exception", e.getMessage());
            mostrarMensaje("Error : " + e.getMessage());
        } catch (PlayServiceException e) {
            //StringUtil.printLogInDebugMode("INIT Exception", e.getMessage());
            mostrarMensaje("Error : " + e.getMessage());
        } catch ( Exception e){
            mostrarMensaje("Error : " + e.getMessage());
        }
    }

    public void autocapturaRostro(){
        try{
            JSONObject commonUIObject = new JSONObject();
            commonUIObject.put(UIConfigurationParameters.FD_LIGHT_THRESHOLD,"60");
            commonUIObject.put(UIConfigurationParameters.FD_FOCUS_THRESHOLD, "15");
            commonUIObject.put(UIConfigurationParameters.FD_DETECTION_THRESHOLD, "20");
            commonUIObject.put(UIConfigurationParameters.FD_MAX_IMAGE_SIZE, "500");
            commonUIObject.put(UIConfigurationParameters.FD_LAUNCH_FRONT_CAMERA, "Y");
            //commonUIObject.put(UIConfigurationParameters.FD_SHOW_PREVIEW_SCREEN, previewScreen == true ? "Y" : "N");
            //commonUIObject.put(UIConfigurationParameters.FD_SHOW_INSTRUCTION_SCREEN, instructionScreen == true ? "Y" : "N");

            //commonUIObject.put(UIConfigurationParamdetectFaceeters.FD_TOGGLE_CAMERA, toggleCamera == true ? "Y" : "N");
            commonUIObject.put("fd_toggle_camera", "N");

            imageProcessingSDK.detectFace(MainActivity.this,commonUIObject);
        }catch (Exception e){
            Utils.printLogCat("autoCapturaRostro",e.getMessage());
        }
    }

    private JSONObject getAdditionalDataJSON() {
        JSONObject jObject = new JSONObject();

        UserRegistration userRegistration = UserRegistration.getInstance();
        String name = userRegistration.getName();
        String firstSecondName = userRegistration.getFirstSecondName();
        String lastSecondName = userRegistration.getLastSecondName();
        String cellPhone = userRegistration.getCell();
        String email = userRegistration.getEmail();
        String birthday = userRegistration.getBirthday();
        String gender = userRegistration.getGender();

        try {
            jObject.put("Service_ID", "50");
            if (!Utils.isEmpty(name)) {
                jObject.put("Customer_Name", name + " " + firstSecondName + " " + lastSecondName);
                if(!Utils.expressionRegularValidate(name, Validator.NOMBRES) ||
                        !Utils.expressionRegularValidate(firstSecondName, Validator.NOMBRES) ||
                        !Utils.expressionRegularValidate(lastSecondName, Validator.NOMBRES)
                )
                    return null;
            }else return null;
            //saveSetting(CUSTOMER_NAME, customername);
            if (!Utils.isEmpty(cellPhone)) {
                jObject.put("Customer_Phone", cellPhone);
                if(!Utils.expressionRegularValidate(cellPhone, Validator.TELEFONO))
                    return null;

            }else return null;
            if (!Utils.isEmpty(email)) {
                jObject.put("Customer_Email", email);
                if(!Utils.expressionRegularValidate(email,Validator.EMAIL))
                    return null;
            }else return null;
            if (!Utils.isEmpty(gender))
                jObject.put("Gender", gender);
            else return null;
            //saveSetting(CUSTOMER_EMAIL, custEmail);

        } catch (JSONException exc) {
            Utils.printLogCat("SendIfoFragment", "getAdditionalDataJSON Exc : " + exc);
            //Utils.mostrarMensaje(context,"getAdditionalDataJSON Exc : "+exc );
        }


        return jObject;
    }

    @Override
    public void onImageProcessingResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onImageProcessingResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onImageProcessingResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onAutoImageCaptureResultAvailable(Map<String, String> map, Response response) {
        try{
            if(response.getStatusCode()==0){
                if(map.get("FRONT") != null) {
                    imageStringFront = map.get("FRONT");
                    showINEFragment.showIne(ImageType.FRONT,imageStringFront);
                    UserRegistration.getInstance().setInePictureFront(imageStringFront);
                }
                else if(map.get("BACK") != null) {
                    imageStringBack = map.get("BACK");
                    showINEFragment.showIne(ImageType.BACK,imageStringBack);
                    UserRegistration.getInstance().setInePictureBack(imageStringBack);
                }
            }
            mostrarMensaje("onAutoImageCaptureResultAvailable : " +response.getStatusMessage());
            Utils.printLogCat("onAutoImageCaptureResultAvailable : " +response.getStatusCode() + " -> ", response.getStatusMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAutoFillResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onAutoFillResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onAutoFillResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onAutoFillFieldInformationAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onAutoFillFieldInformationAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onAutoFillFieldInformationAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onFaceDetectionResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onFaceDetectionResultAvailable : " +response.getStatusMessage());

        if(response.getStatusCode()==0){
            if(map.get("FACE") !=null ){
                imageprofile = map.get("FACE");
                UserRegistration.getInstance().setProfilePicture(imageprofile);
            }
            Utils.printLogCat("onFaceDetectionResultAvailable : ", response.getStatusMessage());
            if(profilePhotoFragment !=null ){
                profilePhotoFragment.showProfilePicture(imageprofile);
            }
        }
        Utils.printLogCat("onFaceDetectionResultAvailable : " +response.getStatusCode() + " -> ", response.getStatusMessage());
    }

    @Override
    public void onFaceMatchingResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onFaceMatchingResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onFaceMatchingResultAvailable : ", response.getStatusMessage());


    }

    @Override
    public void onCardDetectionResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCardDetectionResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCardDetectionResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onCaptureProofOfAddressResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCaptureProofOfAddressResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCaptureProofOfAddressResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onCaptureBankStatementResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCaptureBankStatementResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCaptureBankStatementResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onCaptureGenericDocumentResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCaptureGenericDocumentResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCaptureGenericDocumentResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onCaptureBirthCertificateResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCaptureBirthCertificateResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCaptureBirthCertificateResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onImageProcessingAndFaceMatchingResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onImageProcessingAndFaceMatchingResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onImageProcessingAndFaceMatchingResultAvailable : ", response.getStatusMessage());
        mostrarDialogo("onImageProcessingAndFaceMatchingResultAvailable", response.getStatusMessage());
        cellphoneFragment.ocultarProgress();

        WebServiceGetSms.confirmRegistration(getApplicationContext(), UserRegistration.getInstance(),cellphoneFragment.getSmsCode());
    }

    @Override
    public void onOperationResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onOperationResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onOperationResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onCustomerVerificationResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCustomerVerificationResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCustomerVerificationResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onCustomizeUserInterfaceResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onCustomizeUserInterfaceResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onCustomizeUserInterfaceResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onVoiceRecordingFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVoiceRecordingFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVoiceRecordingFinished : ", response.getStatusMessage());
    }

    @Override
    public void onGPSCoordinateAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onGPSCoordinateAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onGPSCoordinateAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onFourFingerCaptureFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onFourFingerCaptureFinished : " +response.getStatusMessage());
        Utils.printLogCat("onFourFingerCaptureFinished : ", response.getStatusMessage());
    }

    @Override
    public void onFingerprintCaptureFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onFingerprintCaptureFinished : " +response.getStatusMessage());
        Utils.printLogCat("onFingerprintCaptureFinished : ", response.getStatusMessage());
    }

    @Override
    public void onFingerprintEnrolmentFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onFingerprintEnrolmentFinished : " +response.getStatusMessage());
        Utils.printLogCat("onFingerprintEnrolmentFinished : ", response.getStatusMessage());
    }

    @Override
    public void onFingerprintVerificationFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onFingerprintVerificationFinished : " +response.getStatusMessage());
        Utils.printLogCat("onFingerprintVerificationFinished : ", response.getStatusMessage());
    }

    @Override
    public void onVideoRecordingFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVideoRecordingFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVideoRecordingFinished : ", response.getStatusMessage());
    }

    @Override
    public void onScanBarcodeFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onScanBarcodeFinished : " +response.getStatusMessage());
        Utils.printLogCat("onScanBarcodeFinished : ", response.getStatusMessage());
    }

    @Override
    public void onCaptureSignatureFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onCaptureSignatureFinished : " +response.getStatusMessage());
        Utils.printLogCat("onCaptureSignatureFinished : ", response.getStatusMessage());
    }

    @Override
    public void onVerifyAddressFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVerifyAddressFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVerifyAddressFinished : ", response.getStatusMessage());
    }

    @Override
    public void onCreateEmployeeFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onCreateEmployeeFinished : " +response.getStatusMessage());
        Utils.printLogCat("onCreateEmployeeFinished : ", response.getStatusMessage());
    }

    @Override
    public void onVerifyEmployeeFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVerifyEmployeeFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVerifyEmployeeFinished : ", response.getStatusMessage());
    }

    @Override
    public void onGenerateTokenFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onGenerateTokenFinished : " +response.getStatusMessage());
        Utils.printLogCat("onGenerateTokenFinished : ", response.getStatusMessage());
    }

    @Override
    public void onVerifyTokenFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVerifyTokenFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVerifyTokenFinished : ", response.getStatusMessage());
    }

    @Override
    public void onSnippetImageCaptureResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onSnippetImageCaptureResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onSnippetImageCaptureResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onUpdateCustomerFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onUpdateCustomerFinished : " +response.getStatusMessage());
        Utils.printLogCat("onUpdateCustomerFinished : ", response.getStatusMessage());
    }

    @Override
    public void onGenerateOTPFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onGenerateOTPFinished : " +response.getStatusMessage());
        Utils.printLogCat("onGenerateOTPFinished : ", response.getStatusMessage());
    }

    @Override
    public void onVerifyOTPFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVerifyOTPFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVerifyOTPFinished : ", response.getStatusMessage());
    }

    @Override
    public void onInitializationResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onInitializationResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onInitializationResultAvailable : ", response.getStatusMessage());
    }

    @Override
    public void onExecuteCustomProductCall(Map<String, String> map, Response response) {
        mostrarMensaje("onExecuteCustomProductCall : " +response.getStatusMessage());
        Utils.printLogCat("onExecuteCustomProductCall : ", response.getStatusMessage());
    }

    @Override
    public void onUpdateEmployeeFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onUpdateEmployeeFinished : " +response.getStatusMessage());
        Utils.printLogCat("onUpdateEmployeeFinished : ", response.getStatusMessage());
    }

    @Override
    public void onIDValidationAndVideoMatchingFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onIDValidationAndVideoMatchingFinished : " +response.getStatusMessage());
        Utils.printLogCat("onIDValidationAndVideoMatchingFinished : ", response.getStatusMessage());
    }

    @Override
    public void genericApiCallResponse(Map<String, String> map, Response response) {
        mostrarMensaje("genericApiCallResponse : " +response.getStatusMessage());
        Utils.printLogCat("genericApiCallResponse : ", response.getStatusMessage());
    }

    @Override
    public void onVideoConferencingFinished(Map<String, String> map, Response response) {
        mostrarMensaje("onVideoConferencingFinished : " +response.getStatusMessage());
        Utils.printLogCat("onVideoConferencingFinished : ", response.getStatusMessage());
    }

    @Override
    public void onDownloadXsltResultAvailable(Map<String, String> map, Response response) {
        mostrarMensaje("onDownloadXsltResultAvailable : " +response.getStatusMessage());
        Utils.printLogCat("onDownloadXsltResultAvailable : ", response.getStatusMessage());
    }

    public void mostrarMensaje(String texto){
        Toast t = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
        t.show();
    }

    public void mostrarDialogo(String titulo, String mensaje){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensaje);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        Fragment fragment = null;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Utils.printLogCat("getItem"," Fragment position "+ position);
            //Fragment f = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.view_pager);
            UserRegistration userRegistration = UserRegistration.getInstance();

            switch (position){
                case 0: firstRegisterActivity = new FirstRegisterFragment(); fragment = firstRegisterActivity;  break;
                case 1: secondRegisterFragment = new SecondRegisterFragment();  fragment = secondRegisterFragment; break;
                case 2: showINEFragment = ShowINEFragment.newInstance(userRegistration.getInePictureFront(), userRegistration.getInePictureBack()); fragment = showINEFragment; break;
                case 3: profilePhotoFragment = ProfilePhotoFragment.newInstance(userRegistration.getProfilePicture()); fragment = profilePhotoFragment; break;
                case 4: cellphoneFragment = new CellphoneFragment(); fragment = cellphoneFragment; break;
                //case 5: smsCodeFragment = new SmsCodeFragment(); fragment = smsCodeFragment; break;
            }
            return fragment;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void next() {
        mPager.setCurrentItem(getItem(+1), true);
    }

    @Override
    public void previous() {

    }

    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }
}