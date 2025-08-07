package com.htg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView registerText;

    boolean debug = true;
    TextView debugError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);
        debugError = findViewById(R.id.debugError);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            LoginRequest request = new LoginRequest(email, password);
            ApiService api = ApiClient.getClient().create(ApiService.class);

            api.loginUser(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (!response.isSuccessful()) {
                        showDebugMessage("HTTP error: " + response.code() + " - " + response.message());
                        return;
                    }

                    LoginResponse loginResponse = response.body();
                    if (loginResponse == null) {
                        showDebugMessage("Empty response body");
                        return;
                    }

                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse res = response.body();

                        if ("success".equals(res.status)) {
                            //Toast.makeText(MainActivity.this, "Welcome " + res.user.name, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "TimesLogged:  " + res.user.timesLogged, Toast.LENGTH_SHORT).show();
                            // TODO: Go to next activity
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed: " + res.message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    showDebugMessage("Network Error: " + t.getMessage());
                }
            });
        });

        registerText.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }
    private void showDebugMessage(String message) {
        if (debug) { // ou use sua própria variável "debug"
            TextView debugTextView = findViewById(R.id.debugTextView);
            debugTextView.setText(message);
            debugTextView.setVisibility(View.VISIBLE);
        }
    }

}
