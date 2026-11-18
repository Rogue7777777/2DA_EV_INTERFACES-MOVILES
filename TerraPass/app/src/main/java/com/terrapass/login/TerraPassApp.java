package com.terrapass.login;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

/**
 * Clase Application: activa Material You (colores dinámicos).
 * En Android 12+ (API 31) los roles de color del tema se toman del fondo de pantalla del usuario;
 * en versiones anteriores se usa la paleta definida en res/values/colors.xml.
 */
public class TerraPassApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
