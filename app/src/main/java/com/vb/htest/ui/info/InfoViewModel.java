package com.vb.htest.ui.info;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.vb.htest.data.network.model.InfoResponse;
import com.vb.htest.data.network.repository.AlarStudioRepo;

public class InfoViewModel extends ViewModel {

    private AlarStudioRepo alarStudioRepo;

    private String code = "";
    private int page = 1;

    public int getPage() {
        return page;
    }

    public void resetPage() { page = 1; }

    public void nextPage() {
        page++;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InfoViewModel(AlarStudioRepo alarStudioRepo) {
        this.alarStudioRepo = alarStudioRepo;
    }

    public void makeInfoRequest(String code, String page, InfoCallback callback, Activity hostActivity) {
        new Thread(() -> {
            InfoResponse response = alarStudioRepo.makeInfoRequest(code, page);
            hostActivity.runOnUiThread(() -> {
                if (!response.getData().isEmpty())
                    callback.success(response);
                else callback.error(response);
            });
        }).start();
    }
}
