package com.terrapass.login.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.terrapass.login.R;
import com.terrapass.login.util.Formularios;
import com.terrapass.login.util.Validador;

/** Pantalla de Registro: valida los cuatro campos y regresa al Login con el correo registrado. */
public class RegistroActivity extends AppCompatActivity {

    public static final String EXTRA_CORREO = "extra_correo";

    private TextInputLayout layoutNombre;
    private TextInputLayout layoutCorreo;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.toolbarRegistro);
        toolbar.setNavigationOnClickListener(v -> finish());

        layoutNombre = findViewById(R.id.layoutNombre);
        layoutCorreo = findViewById(R.id.layoutCorreoReg);
        layoutPassword = findViewById(R.id.layoutPasswordReg);
        layoutConfirmar = findViewById(R.id.layoutConfirmar);

        Formularios.limpiarAlEscribir(layoutNombre);
        Formularios.limpiarAlEscribir(layoutCorreo);
        Formularios.limpiarAlEscribir(layoutPassword);
        Formularios.limpiarAlEscribir(layoutConfirmar);

        findViewById(R.id.btnRegistrar).setOnClickListener(v -> intentarRegistro());
        findViewById(R.id.btnYaTengoCuenta).setOnClickListener(v -> finish());
    }

    private void intentarRegistro() {
        String nombre = Formularios.texto(layoutNombre);
        String correo = Formularios.texto(layoutCorreo).trim();
        String password = Formularios.texto(layoutPassword);
        String confirmar = Formularios.texto(layoutConfirmar);

        // Se evalúan todos para mostrar todos los errores a la vez
        boolean a = Formularios.mostrarError(layoutNombre, Validador.nombre(nombre));
        boolean b = Formularios.mostrarError(layoutCorreo, Validador.correo(correo));
        boolean c = Formularios.mostrarError(layoutPassword, Validador.passwordRegistro(password));
        boolean d = Formularios.mostrarError(layoutConfirmar, Validador.confirmacion(password, confirmar));
        if (!(a && b && c && d)) return;

        // Registro SIMULADO: no se guarda nada; se regresa al Login con el correo.
        Intent resultado = new Intent().putExtra(EXTRA_CORREO, correo);
        setResult(RESULT_OK, resultado);
        finish();
    }
}
