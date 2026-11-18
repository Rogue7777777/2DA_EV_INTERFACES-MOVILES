package com.terrapass.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.terrapass.login.R;

/** Pantalla de bienvenida mostrada tras un inicio de sesión simulado exitoso. */
public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_CORREO = "extra_correo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String correo = getIntent().getStringExtra(EXTRA_CORREO);
        String usuario = (correo != null && correo.contains("@"))
                ? correo.substring(0, correo.indexOf('@')) : getString(R.string.app_name);
        ((TextView) findViewById(R.id.tvSaludo)).setText(getString(R.string.home_saludo, usuario));

        findViewById(R.id.btnCerrarSesion).setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
