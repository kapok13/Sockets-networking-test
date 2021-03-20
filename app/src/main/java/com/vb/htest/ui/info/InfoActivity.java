package com.vb.htest.ui.info;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.vb.htest.R;
import com.vb.htest.ui.factory.ViewModelFactory;
import com.vb.htest.ui.info.fragment.InfoDetailsFragment;
import com.vb.htest.ui.info.fragment.InfoFragment;

public class InfoActivity extends AppCompatActivity {

    InfoViewModel infoViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInfoViewModel();
        setContentView(R.layout.activity_info);
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_info_fragment_container, new InfoFragment())
                .commit();
    }

    private void initInfoViewModel() {
        infoViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(InfoViewModel.class);
        if (getIntent().getBundleExtra("intentBundle") != null) {
            Bundle intentExtraBundle = getIntent().getBundleExtra("intentBundle");
            if (intentExtraBundle.getString("code") != null) {
                infoViewModel.setCode(intentExtraBundle.getString("code"));
            }
        }
    }

}
