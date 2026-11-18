package com.terrapass.login.util;

import static org.junit.Assert.assertEquals;

import com.terrapass.login.R;

import org.junit.Test;

/** Pruebas unitarias locales de las reglas de validación. */
public class ValidadorTest {

    @Test
    public void correo_vacio_y_formato() {
        assertEquals(R.string.error_correo_vacio, Validador.correo("  "));
        assertEquals(R.string.error_correo_formato, Validador.correo("usuario@"));
        assertEquals(R.string.error_correo_formato, Validador.correo("usuario.com"));
        assertEquals(Validador.OK, Validador.correo("ana.lopez@correo.mx"));
    }

    @Test
    public void passwordLogin_longitud() {
        assertEquals(R.string.error_password_vacia, Validador.passwordLogin(""));
        assertEquals(R.string.error_password_corta, Validador.passwordLogin("abc123"));
        assertEquals(Validador.OK, Validador.passwordLogin("abcdefgh"));
    }

    @Test
    public void passwordRegistro_exige_letra_y_numero() {
        assertEquals(R.string.error_password_debil, Validador.passwordRegistro("abcdefgh"));
        assertEquals(R.string.error_password_debil, Validador.passwordRegistro("12345678"));
        assertEquals(Validador.OK, Validador.passwordRegistro("terra2026"));
    }

    @Test
    public void nombre_y_confirmacion() {
        assertEquals(R.string.error_nombre, Validador.nombre("Al"));
        assertEquals(Validador.OK, Validador.nombre("Ana"));
        assertEquals(R.string.error_confirmar_vacia, Validador.confirmacion("terra2026", ""));
        assertEquals(R.string.error_no_coinciden, Validador.confirmacion("terra2026", "terra2027"));
        assertEquals(Validador.OK, Validador.confirmacion("terra2026", "terra2026"));
    }
}
