package com.vb.htest.ui.login;

import com.vb.htest.data.network.model.AuthResponse;

public interface AuthCallback {
    void authWasCompleted(AuthResponse authResponse);
    void error(AuthResponse authResponse);
}
