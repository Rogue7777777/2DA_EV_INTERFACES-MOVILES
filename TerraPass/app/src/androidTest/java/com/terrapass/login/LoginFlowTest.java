package com.terrapass.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.terrapass.login.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Prueba instrumentada: el Login muestra el error de correo vacío. */
@RunWith(AndroidJUnit4.class)
public class LoginFlowTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> regla = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void correoVacio_muestraError() {
        onView(withId(R.id.etPassword)).perform(typeText("terra2026"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText(R.string.error_correo_vacio)).check(matches(isDisplayed()));
    }
}
