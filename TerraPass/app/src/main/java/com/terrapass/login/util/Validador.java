package com.terrapass.login.util;

import androidx.annotation.StringRes;

import com.terrapass.login.R;

import java.util.regex.Pattern;

/**
 * Reglas de validación de los formularios. Cada método devuelve el id del mensaje de error
 * (recurso de texto) o {@link #OK} (0) cuando el dato es válido.
 * No depende de vistas, por lo que se puede probar con pruebas unitarias locales.
 */
public final class Validador {

    public static final int OK = 0;
    public static final int LONGITUD_MINIMA_PASSWORD = 8;

    private static final Pattern CORREO =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private Validador() {
    }

    @StringRes
    public static int correo(String valor) {
        if (valor == null || valor.trim().isEmpty()) return R.string.error_correo_vacio;
        if (!CORREO.matcher(valor.trim()).matches()) return R.string.error_correo_formato;
        return OK;
    }

    /** Validación del Login: obligatoria y con longitud mínima. */
    @StringRes
    public static int passwordLogin(String valor) {
        if (valor == null || valor.isEmpty()) return R.string.error_password_vacia;
        if (valor.length() < LONGITUD_MINIMA_PASSWORD) return R.string.error_password_corta;
        return OK;
    }

    /** Validación del Registro: además exige al menos una letra y un número. */
    @StringRes
    public static int passwordRegistro(String valor) {
        int basico = passwordLogin(valor);
        if (basico != OK) return basico;
        boolean letra = false;
        boolean numero = false;
        for (char c : valor.toCharArray()) {
            if (Character.isLetter(c)) letra = true;
            if (Character.isDigit(c)) numero = true;
        }
        return (letra && numero) ? OK : R.string.error_password_debil;
    }

    @StringRes
    public static int nombre(String valor) {
        return (valor == null || valor.trim().length() < 3) ? R.string.error_nombre : OK;
    }

    @StringRes
    public static int confirmacion(String password, String confirmacion) {
        if (confirmacion == null || confirmacion.isEmpty()) return R.string.error_confirmar_vacia;
        return confirmacion.equals(password) ? OK : R.string.error_no_coinciden;
    }
}
