package com.park.smet_k.bauman_gis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.park.smet_k.bauman_gis.api.BgisApi;
import com.park.smet_k.bauman_gis.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final String LOG_TAG = "LoginActivity";

    private EditText email;
    private EditText password;

    private final static String KEY_IS_FIRST = "is_first";
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";

//    private final BgisApi bgisApi = AppComponent.getInstance().bgisApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);


        // =================
        SharedPreferences prefs = getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

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
        switch (v.getId()) {
            case R.id.login:

                userLogin();

                break;
            case R.id.textViewRegister:
                break;
        }
    }

    private void userLogin() {
        String email_str = email.getText().toString().trim();
        String password_str = password.getText().toString().trim();

        if (email_str.isEmpty()) {
            email.setError("email required");
            email.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
//            email.setError("enter valid email");
//            email.requestFocus();
//            return;
//        }

        if (password_str.isEmpty()) {
            password.setError("password required");
            password.requestFocus();
            return;
        }

        // ski97@mail.ru
        // test
//        Call<Void> call = AppComponent.getInstance().bgisApi.userLogin(email_str, password_str);
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Log.d(LOG_TAG, "--- Login OK ---");
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.d(LOG_TAG, "--- Login ERROR ---");
//            }
//        });
        Callback<User> callback = new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- Login OK ---");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(LOG_TAG, "--- Login ERROR onFailure ---");
                t.printStackTrace();
            }
        };

        // avoid static error
        AppComponent.getInstance().bgisApi.userLogin(email_str, password_str).enqueue(callback);
    }
}
