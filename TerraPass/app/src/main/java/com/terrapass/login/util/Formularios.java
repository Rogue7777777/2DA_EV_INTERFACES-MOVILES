package com.terrapass.login.util;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputLayout;

/** Utilidades para mostrar y limpiar errores en campos Material 3. */
public final class Formularios {

    private Formularios() {
    }

    /**
     * Muestra el error en el campo (o lo limpia si el código es {@link Validador#OK}).
     *
     * @return true si el campo es válido
     */
    public static boolean mostrarError(TextInputLayout layout, @StringRes int error) {
        if (error == Validador.OK) {
            layout.setError(null);
            return true;
        }
        layout.setError(layout.getContext().getString(error));
        return false;
    }

    /** Quita el mensaje de error en cuanto la persona vuelve a escribir. */
    public static void limpiarAlEscribir(TextInputLayout layout) {
        if (layout.getEditText() == null) return;
        layout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static String texto(TextInputLayout layout) {
        return layout.getEditText() == null || layout.getEditText().getText() == null
                ? "" : layout.getEditText().getText().toString();
    }
}
