package com.vb.htest.ui.info.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.vb.htest.R;
import com.vb.htest.ui.info.InfoViewModel;

public class InfoDetailsFragment extends Fragment {

    private InfoViewModel infoViewModel;
    private Toolbar detailsToolbar;
    private TextView longitudeTV;
    private TextView latitudeTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(detailsToolbar);
        initViewS(view);
        infoViewModel = new ViewModelProvider(getActivity()).get(InfoViewModel.class);
    }

    private void initViewS(View currentView) {
        detailsToolbar = currentView.findViewById(R.id.details_toolbar);
        longitudeTV = currentView.findViewById(R.id.details_longitude_tv);
        latitudeTV = currentView.findViewById(R.id.details_latitude_tv);
        if (getArguments().getFloat("lat") != 0.0f)
            latitudeTV.setText(String.valueOf(getArguments().getFloat("lat")));
        if (getArguments().getFloat("lon") != 0.0f)
            longitudeTV.setText(String.valueOf(getArguments().getFloat("lon")));
        detailsToolbar.setNavigationOnClickListener((view) -> {
            infoViewModel.resetPage();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
                    .replace(R.id.activity_info_fragment_container, new InfoFragment())
                    .commit();
        });
    }
}
