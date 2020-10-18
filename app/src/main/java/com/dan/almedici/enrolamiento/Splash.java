package com.dan.almedici.enrolamiento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Splash extends AppCompatActivity {

    private final int DURATION_SPLASH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        boolean pasar = validarPermisos();
        if(pasar)
            pasarMenu();

    }

    public void pasarMenu(){
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURATION_SPLASH);
    }

    public boolean validarPermisos(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if( (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)
                && (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA)) || (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)){
            cargarDialogoRecomendación();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA,READ_EXTERNAL_STORAGE},100);
        }

        return false;
    }

    public void cargarDialogoRecomendación(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Porfavor acepte los permisos");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA,READ_EXTERNAL_STORAGE},100);
            }
        });
        dialogo.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                pasarMenu();
            }else{
                solicitarPermisoManual();
            }
        }
    }

    public void solicitarPermisoManual(){

    }

}