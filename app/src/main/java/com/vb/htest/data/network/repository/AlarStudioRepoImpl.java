package com.vb.htest.data.network.repository;

import com.google.gson.Gson;
import com.vb.htest.data.network.model.AuthResponse;
import com.vb.htest.data.network.model.InfoResponse;
import com.vb.htest.data.network.service.AlarStudioService;
import com.vb.htest.data.network.service.AlarStudioServiceImpl;

public class AlarStudioRepoImpl implements AlarStudioRepo {

    private static AlarStudioRepo instance;
    private AlarStudioService alarService;

    private AlarStudioRepoImpl(AlarStudioService alarService) {
        this.alarService = alarService;
    }

    public static AlarStudioRepo getInstance() {
        if (instance == null)
            instance = new AlarStudioRepoImpl(AlarStudioServiceImpl.getInstance());
        return instance;
    }

    @Override
    public InfoResponse makeInfoRequest(String code, String page) {
        StringBuilder parametersBuilder = new StringBuilder("code=");
        parametersBuilder.append(code);
        parametersBuilder.append("&p=");
        parametersBuilder.append(page);
        String response = alarService.makeRequest("data.cgi", parametersBuilder.toString(), 10000);
        String changedResponse = response.replaceFirst("\\{", "#");
        String[] splittedToJsonArray = changedResponse.split("#");
        StringBuilder responseBuffer = new StringBuilder();
        for (int counter = 1; counter < splittedToJsonArray.length; counter++) {
            if (counter == 1) responseBuffer.append("{");
            responseBuffer.append(splittedToJsonArray[counter]);
        }
        return new Gson().fromJson(responseBuffer.toString(), InfoResponse.class);
    }

    @Override
    public AuthResponse makeAuthRequest(String username, String password) {
        StringBuilder parametersBuilder = new StringBuilder("username=");
        parametersBuilder.append(username);
        parametersBuilder.append("&password=");
        parametersBuilder.append(password);
        String response = alarService.makeRequest("auth.cgi", parametersBuilder.toString(), 10000);
        String[] splittedToJsonArray = response.split("\\{");
        String responseJSON = "{" + splittedToJsonArray[1];
        return new Gson().fromJson(responseJSON, AuthResponse.class);
    }
}
