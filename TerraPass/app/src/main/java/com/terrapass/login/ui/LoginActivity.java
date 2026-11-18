package com.terrapass.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.terrapass.login.R;
import com.terrapass.login.util.Formularios;
import com.terrapass.login.util.Validador;

/**
 * Pantalla de Login (Activity principal).
 * Valida correo y contraseña, simula la autenticación y ofrece Registro y Recuperación de contraseña.
 */
public class LoginActivity extends AppCompatActivity {

    private static final long RETARDO_SIMULADO_MS = 1200;

    private TextInputLayout layoutCorreo;
    private TextInputLayout layoutPassword;
    private MaterialButton btnLogin;
    private LinearProgressIndicator progreso;
    private View raiz;

    private final Handler handler = new Handler(Looper.getMainLooper());

    /** Recibe el correo registrado desde RegistroActivity para precargarlo. */
    private final ActivityResultLauncher<Intent> lanzadorRegistro =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultado -> {
                if (resultado.getResultCode() == RESULT_OK && resultado.getData() != null) {
                    String correo = resultado.getData().getStringExtra(RegistroActivity.EXTRA_CORREO);
                    if (layoutCorreo.getEditText() != null && correo != null) {
                        layoutCorreo.getEditText().setText(correo);
                    }
                    Snackbar.make(raiz, R.string.msg_registro_ok, Snackbar.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        raiz = findViewById(R.id.rootLogin);
        layoutCorreo = findViewById(R.id.layoutCorreo);
        layoutPassword = findViewById(R.id.layoutPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progreso = findViewById(R.id.progreso);

        Formularios.limpiarAlEscribir(layoutCorreo);
        Formularios.limpiarAlEscribir(layoutPassword);

        btnLogin.setOnClickListener(v -> intentarLogin());
        findViewById(R.id.btnCrearCuenta).setOnClickListener(v ->
                lanzadorRegistro.launch(new Intent(this, RegistroActivity.class)));
        findViewById(R.id.btnOlvide).setOnClickListener(v -> mostrarDialogoRecuperacion());

        // Enviar el formulario desde la tecla "Hecho" del teclado
        if (layoutPassword.getEditText() != null) {
            layoutPassword.getEditText().setOnEditorActionListener((tv, accion, evento) -> {
                if (accion == EditorInfo.IME_ACTION_DONE) {
                    intentarLogin();
                    return true;
                }
                return false;
            });
        }
    }

    private void intentarLogin() {
        String correo = Formularios.texto(layoutCorreo).trim();
        String password = Formularios.texto(layoutPassword);

        boolean correoOk = Formularios.mostrarError(layoutCorreo, Validador.correo(correo));
        boolean passwordOk = Formularios.mostrarError(layoutPassword, Validador.passwordLogin(password));
        if (!correoOk || !passwordOk) return;

        // Autenticación SIMULADA: no existe servidor ni base de datos de usuarios.
        establecerCargando(true);
        handler.postDelayed(() -> {
            establecerCargando(false);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(HomeActivity.EXTRA_CORREO, correo);
            startActivity(intent);
            finish();
        }, RETARDO_SIMULADO_MS);
    }

    private void establecerCargando(boolean cargando) {
        progreso.setVisibility(cargando ? View.VISIBLE : View.INVISIBLE);
        btnLogin.setEnabled(!cargando);
    }

    private void mostrarDialogoRecuperacion() {
        View contenido = getLayoutInflater().inflate(R.layout.dialog_recuperar, null);
        TextInputLayout layoutRec = contenido.findViewById(R.id.layoutCorreoRec);
        Formularios.limpiarAlEscribir(layoutRec);
        // Reutiliza el correo ya escrito en el Login
        if (layoutRec.getEditText() != null) {
            layoutRec.getEditText().setText(Formularios.texto(layoutCorreo));
        }

        AlertDialog dialogo = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_lock)
                .setTitle(R.string.recuperar_titulo)
                .setMessage(R.string.recuperar_mensaje)
                .setView(contenido)
                .setNegativeButton(R.string.btn_cancelar, null)
                .setPositiveButton(R.string.btn_enviar, null) // se asigna abajo para no cerrar con error
                .create();
        dialogo.show();

        // El diálogo permanece abierto si el correo no es válido
        dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (Formularios.mostrarError(layoutRec, Validador.correo(Formularios.texto(layoutRec)))) {
                dialogo.dismiss();
                Snackbar.make(raiz, R.string.msg_recuperacion_ok, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
