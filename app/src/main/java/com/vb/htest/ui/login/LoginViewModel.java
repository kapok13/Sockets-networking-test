package com.vb.htest.ui.login;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.vb.htest.data.network.model.AuthResponse;
import com.vb.htest.data.network.repository.AlarStudioRepo;

public class LoginViewModel extends ViewModel {

    private AlarStudioRepo alarStudioRepo;

    public LoginViewModel(AlarStudioRepo alarStudioRepo) {
        this.alarStudioRepo = alarStudioRepo;
    }

    void makeAuthRequest(String username, String password, AuthCallback callback, Activity hostActivity) {
        new Thread(() -> {
            AuthResponse response = alarStudioRepo.makeAuthRequest(username, password);
            hostActivity.runOnUiThread(() -> {
                if (response.getStatus().equals("ok"))
                    callback.authWasCompleted(response);
                else callback.error(response);
            });
        }).start();
    }
}

