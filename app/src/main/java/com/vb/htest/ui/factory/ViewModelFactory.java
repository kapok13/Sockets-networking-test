package com.vb.htest.ui.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vb.htest.data.network.repository.AlarStudioRepo;
import com.vb.htest.data.network.repository.AlarStudioRepoImpl;
import com.vb.htest.ui.info.InfoViewModel;
import com.vb.htest.ui.login.LoginViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.getSimpleName().equals(LoginViewModel.class.getSimpleName())) {
            try {
                return modelClass.getConstructor(AlarStudioRepo.class).newInstance(AlarStudioRepoImpl.getInstance());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (modelClass.getSimpleName().equals(InfoViewModel.class.getSimpleName())) {
            try {
                return modelClass.getConstructor(AlarStudioRepo.class).newInstance(AlarStudioRepoImpl.getInstance());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }
}

