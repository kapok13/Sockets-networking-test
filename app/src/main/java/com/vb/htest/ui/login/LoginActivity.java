package com.vb.htest.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.vb.htest.R;
import com.vb.htest.Util;
import com.vb.htest.data.network.model.AuthResponse;
import com.vb.htest.ui.factory.ViewModelFactory;
import com.vb.htest.ui.info.InfoActivity;


public class LoginActivity extends AppCompatActivity implements AuthCallback {

    private ProgressBar downloadingProgressBar;
    private EditText usernameEditField;
    private EditText passwordEditField;
    private Button logInButton;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initLoginViewModel();
        initEditFieldsBehavior();
        initLogIn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getInsetsController().hide(WindowInsets.Type.statusBars());
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    private void initLoginViewModel() {
        loginViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(LoginViewModel.class);
    }

    private void initEditFieldsBehavior() {
        usernameEditField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Util.hideKeyboard(this);
                return true;
            } else return false;
        });
        passwordEditField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Util.hideKeyboard(this);
                return true;
            } else return false;
        });
    }

    private void initLogIn() {
        logInButton.setOnClickListener((view) -> {
            if (usernameEditField.getText().toString() == null || usernameEditField.getText().toString().isEmpty())
                generateInvalidUserDataSnackbar(0);
            else if (passwordEditField.getText().toString() == null || passwordEditField.getText().toString().isEmpty())
                generateInvalidUserDataSnackbar(1);
            else {
                Util.hideKeyboard(this);
                downloadingProgressBar.setVisibility(View.VISIBLE);
                view.setEnabled(false);
                loginViewModel
                        .makeAuthRequest(usernameEditField.getText().toString(), passwordEditField.getText().toString(), this, this);
            }
        });
    }

    private void generateInvalidUserDataSnackbar(int code) {
        if (code == 0)
            Snackbar.make(usernameEditField, R.string.empty_username, Snackbar.LENGTH_SHORT).show();
        else if (code == 1)
            Snackbar.make(usernameEditField, R.string.empty_password, Snackbar.LENGTH_SHORT).show();
    }

    private void initViews() {
        usernameEditField = findViewById(R.id.login_name_edit);
        passwordEditField = findViewById(R.id.login_password_edit);
        logInButton = findViewById(R.id.login_button);
        downloadingProgressBar = findViewById(R.id.login_download_progress_bar);
    }

    @Override
    public void authWasCompleted(AuthResponse authResponse) {
        downloadingProgressBar.setVisibility(View.GONE);
        Bundle intentBundle = new Bundle();
        intentBundle.putString("code", authResponse.getCode());
        Intent infoActivityIntent = new Intent(this, InfoActivity.class);
        infoActivityIntent.putExtra("intentBundle", intentBundle);
        startActivity(infoActivityIntent);
    }

    @Override
    public void error(AuthResponse authResponse) {
        logInButton.setEnabled(true);
        downloadingProgressBar.setVisibility(View.GONE);
        new WrongUserDataDialog().show(getSupportFragmentManager(), "wrong data dialog");
    }
}
