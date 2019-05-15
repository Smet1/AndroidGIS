package com.park.smet_k.bauman_gis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.compontents.AppComponent;
import com.park.smet_k.bauman_gis.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private String LOG_TAG = "SignUpActivity";
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                findViewById(R.id.signup).setEnabled(false);
                findViewById(R.id.textViewLogin).setEnabled(false);

                userRegister();

                break;
            case R.id.textViewLogin:
                findViewById(R.id.signup).setEnabled(false);
                findViewById(R.id.textViewLogin).setEnabled(false);
                startLoginActivity();

                break;
        }
    }

    private void userRegister() {
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

        Callback<User> callback = new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, Response<User> response) {
                User body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- Login OK body != null ---");

                    startLoginActivity();

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
        AppComponent.getInstance().bgisApi.userSignUp(new User(email_str, password_str)).enqueue(callback);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
