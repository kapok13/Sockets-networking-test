package com.vb.htest.data.network.service;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class AlarStudioServiceImpl implements AlarStudioService {

    private static final String BASE_URL = "https://www.alarstudios.com/test/";

    private static AlarStudioService instance;


    private AlarStudioServiceImpl() {
    }

    @Override
    public String makeRequest(@Nullable String additionalPath, int timeout) {

        URL requestUrl;

        if (additionalPath != null) {
            requestUrl = createURL(BASE_URL + additionalPath);
        } else {
            requestUrl = createURL(BASE_URL);
        }

        if (requestUrl == null) return null;
        else {
            return connect(requestUrl, timeout);
        }
    }

    @Override
    public String makeRequest(@Nullable String additionalPath, @Nullable String parameters, int timeout) {

        URL requestUrl;

        if (parameters != null && additionalPath != null) {
            requestUrl = createURL(BASE_URL + additionalPath + "?" + parameters);
        } else if (additionalPath != null) {
            requestUrl = createURL(BASE_URL + additionalPath);
        } else {
            requestUrl = createURL(BASE_URL);
        }

        if (requestUrl == null) return null;
        else {
            return connect(requestUrl, timeout);
        }

    }


    public static AlarStudioService getInstance() {
        if (instance == null) instance = new AlarStudioServiceImpl();
        return instance;
    }


    private String connect(URL url, int timeout) {
        String response = "";
        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket alarSocket = (SSLSocket) socketFactory.createSocket(url.getHost(), 443);
            alarSocket.setSoTimeout(timeout);
            alarSocket.getOutputStream().write(("GET " + url.getPath() + "?" + url.getQuery() + " HTTP/1.1\r\nHost: "
                    + url.getHost() + "\r\n\r\n").getBytes());
            response = readFromNetworkInputStream(alarSocket.getInputStream());
            return response;
        } catch (Exception e) {
            return response;
        }
    }

    private static String readFromNetworkInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private URL createURL(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException m) {
            return null;
        }
    }
}
