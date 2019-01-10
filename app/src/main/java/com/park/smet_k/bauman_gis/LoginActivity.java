package com.park.smet_k.bauman_gis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.park.smet_k.bauman_gis.model.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String LOG_TAG = "LoginActivity";

    private TextInputEditText emailLogin;
    private TextInputEditText passwordLogin;
    private TextInputEditText emailSignup;
    private TextInputEditText passwordSignup;

    private final static String KEY_IS_FIRST = "is_first";
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";

//    private TextView registerHeader;
    private View registerForm;
    private Button registerButton;
    private TextView registerSwitch;

//    private TextView loginHeader;
    private View loginForm;
    private Button loginButton;
    private TextView loginSwitch;


//    private final BgisApi bgisApi = AppComponent.getInstance().bgisApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        emailLogin = findViewById(R.id.edit_email_login);
        passwordLogin = findViewById(R.id.edit_password_login);

        emailSignup = findViewById(R.id.edit_email_signup);
        passwordSignup = findViewById(R.id.edit_password_signup);

//        registerHeader = findViewById(R.id.signupHeader);
        registerForm = findViewById(R.id.linearLayoutSignUp);
        registerButton = findViewById(R.id.signup);
        registerSwitch = findViewById(R.id.textViewRegister);

//        loginHeader = findViewById(R.id.loginHeader);
        loginForm = findViewById(R.id.linearLayoutLogin);
        loginButton = findViewById(R.id.login);
        loginSwitch = findViewById(R.id.textViewLogin);

        registerButton.setOnClickListener(this);
        registerSwitch.setOnClickListener(this);

        loginButton.setOnClickListener(this);
        loginSwitch.setOnClickListener(this);


        registerForm.animate().translationX(3000);
//        registerHeader.animate().translationX(3000);
        registerButton.animate().translationX(3000);
        registerSwitch.animate().translationX(3000);


        // =================
        SharedPreferences prefs = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

        // уже залогинился
        if (!prefs.getBoolean(KEY_IS_FIRST, true)) {
            startMainActivity();
        }
        // =================
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        switch (v.getId()) {
            case R.id.login:
                findViewById(R.id.login).setEnabled(false);
                findViewById(R.id.textViewLogin).setEnabled(false);
                v.startAnimation(animAlpha);

                userLogin();

                findViewById(R.id.login).setEnabled(true);
                findViewById(R.id.textViewLogin).setEnabled(true);
                break;

            case R.id.signup:
                findViewById(R.id.signup).setEnabled(false);
                findViewById(R.id.textViewRegister).setEnabled(false);
                v.startAnimation(animAlpha);

                userRegister();

                findViewById(R.id.login).setEnabled(true);
                findViewById(R.id.textViewRegister).setEnabled(true);
                break;

            case R.id.textViewLogin:
                findViewById(R.id.login).setEnabled(false);
                findViewById(R.id.textViewLogin).setEnabled(false);
                v.startAnimation(animAlpha);


                emailLogin.clearFocus();
                emailLogin.setError(null);
                Objects.requireNonNull(emailLogin.getText()).clear();

                passwordLogin.clearFocus();
                passwordLogin.setError(null);
                Objects.requireNonNull(passwordLogin.getText()).clear();



                registerForm.animate().translationX(0);
//                registerHeader.animate().translationX(0);
                registerButton.animate().translationX(0);
                registerSwitch.animate().translationX(0);

                loginForm.animate().translationX(-3000);
//                loginHeader.animate().translationX(-3000);
                loginButton.animate().translationX(-3000);
                loginSwitch.animate().translationX(-3000);

                findViewById(R.id.login).setEnabled(true);
                findViewById(R.id.textViewLogin).setEnabled(true);
                break;

            case R.id.textViewRegister:
                findViewById(R.id.signup).setEnabled(false);
                findViewById(R.id.textViewRegister).setEnabled(false);
                v.startAnimation(animAlpha);


                emailSignup.clearFocus();
                emailSignup.setError(null);
                Objects.requireNonNull(emailSignup.getText()).clear();

                passwordSignup.clearFocus();
                passwordSignup.setError(null);
                Objects.requireNonNull(passwordSignup.getText()).clear();


                registerForm.animate().translationX(3000);
//                registerHeader.animate().translationX(3000);
                registerButton.animate().translationX(3000);
                registerSwitch.animate().translationX(3000);

                loginForm.animate().translationX(0);
//                loginHeader.animate().translationX(0);
                loginButton.animate().translationX(0);
                loginSwitch.animate().translationX(0);

                findViewById(R.id.signup).setEnabled(true);
                findViewById(R.id.textViewRegister).setEnabled(true);
                break;

        }
    }

    private void userLogin() {
        String email_str = emailLogin.getText().toString().trim();
        String password_str = passwordLogin.getText().toString().trim();

        if (email_str.isEmpty()) {
            emailLogin.setError("email required");
            emailLogin.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
//            email.setError("enter valid email");
//            email.requestFocus();
//            return;
//        }

        if (password_str.isEmpty()) {
            passwordLogin.setError("password required");
            passwordLogin.requestFocus();
            return;
        }

        Callback<User> callback = new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, Response<User> response) {
                SharedPreferences.Editor editor = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE).edit();
                User body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- Login OK body != null ---");

                    // сохраняю айди пользователя
                    editor.putInt(KEY_OAUTH, body.getId());
                    // уже логинился
                    editor.putBoolean(KEY_IS_FIRST, false);

                    editor.apply();
                    startMainActivity();
                } else {
                    Log.d(LOG_TAG, "--- Login OK body == null ---");

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Invalid login/password",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, Throwable t) {
                Log.d(LOG_TAG, "--- Login ERROR onFailure ---");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Server Error",
                        Toast.LENGTH_SHORT);
                toast.show();
                t.printStackTrace();
            }
        };

        // avoid static error
        AppComponent.getInstance().bgisApi.userLogin(new User(email_str, password_str)).enqueue(callback);
        // enqueue работает в отдельном потоке
    }

    private void userRegister() {
        String email_str = emailSignup.getText().toString().trim();
        String password_str = passwordSignup.getText().toString().trim();

        if (email_str.isEmpty()) {
            emailSignup.setError("email required");
            emailSignup.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
//            email.setError("enter valid email");
//            email.requestFocus();
//            return;
//        }

        if (password_str.isEmpty()) {
            passwordSignup.setError("password required");
            passwordSignup.requestFocus();
            return;
        }

        Callback<User> callback = new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- Login OK body != null ---");

                    registerForm.animate().translationX(3000);
//                    registerHeader.animate().translationX(3000);
                    registerButton.animate().translationX(3000);
                    registerSwitch.animate().translationX(3000);

                    loginForm.animate().translationX(0);
                    loginHeader.animate().translationX(0);
                    loginButton.animate().translationX(0);
                    loginSwitch.animate().translationX(0);

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Success, now login please",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Log.d(LOG_TAG, "--- Login OK body == null ---");

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Invalid login/password",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(LOG_TAG, "--- Login ERROR onFailure ---");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Server Error",
                        Toast.LENGTH_SHORT);
                toast.show();
                t.printStackTrace();
            }
        };

        // avoid static error
        AppComponent.getInstance().bgisApi.userSignUp(new User(email_str, password_str)).enqueue(callback);
    }
}
