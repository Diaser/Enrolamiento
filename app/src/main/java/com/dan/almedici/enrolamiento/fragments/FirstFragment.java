package com.dan.almedici.enrolamiento.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dan.almedici.enrolamiento.R;

public class FirstFragment extends Fragment {
    private View rootView;
    private Button btnIneFrente, btnIneAtras, btnFoto, btnEnviarInfo, btnShowIne, btnShowProfilePicture;
    Context context;

    OnFirstFrgmentSelectedListener mCallback;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_first, container, false);
        btnIneFrente = rootView.findViewById(R.id.button_ine_frente);
        btnIneAtras = rootView.findViewById(R.id.button_ine_atras);
        btnFoto = rootView.findViewById(R.id.button_selfie);
        btnEnviarInfo = rootView.findViewById(R.id.button_eviar_info);
        btnShowIne = rootView.findViewById(R.id.button_watch_INE);
        btnShowProfilePicture = rootView.findViewById(R.id.button_watch_selfie);
        context = this.getContext();
        return  rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Inicializando Proceso

        btnIneFrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrarMensaje("Agregar funcionalidad captura INE Frente");
                mCallback.capturarIneFrente();
            }
        });

        btnIneAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrarMensaje("Agregar funcionalidad captura INE Atras");
                mCallback.capturarIneReverso();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrarMensaje("Agregar funcionalidad captura Tomar foto")
                mCallback.capturarRostro();
            }
        });

        btnEnviarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.gotoInfo();
            }
        });

        btnShowIne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.gotoShowIne();
            }
        });

        btnShowProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.gotoShowProfilePicture();
            }
        });

    }


    public interface OnFirstFrgmentSelectedListener {
        void gotoInfo();
        void capturarIneFrente();
        void capturarIneReverso();
        void capturarRostro();
        void gotoShowIne();
        void gotoShowProfilePicture();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnFirstFrgmentSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Debes implementar OnFirstFrgmentSelectedListener");
        }
    }
}