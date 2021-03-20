package com.vb.htest.data.network.repository;

import com.vb.htest.data.network.model.AuthResponse;
import com.vb.htest.data.network.model.InfoResponse;

public interface AlarStudioRepo {

    AuthResponse makeAuthRequest(String username, String password);

    InfoResponse makeInfoRequest(String code, String page);
}
