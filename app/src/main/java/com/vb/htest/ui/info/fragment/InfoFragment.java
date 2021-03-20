package com.vb.htest.ui.info.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb.htest.R;
import com.vb.htest.data.network.model.Datum;
import com.vb.htest.data.network.model.InfoResponse;
import com.vb.htest.ui.info.InfoCallback;
import com.vb.htest.ui.info.InfoViewModel;
import com.vb.htest.ui.info.adapter.InfoRecyclerAdapter;
import com.vb.htest.ui.info.adapter.RecyclerItemClickCallback;

import java.util.Objects;

public class InfoFragment extends Fragment implements InfoCallback, RecyclerItemClickCallback {

    private InfoViewModel infoViewModel;

    private boolean isPaginationLoading = false;

    private ProgressBar infoLoadingProgressBar;

    private RecyclerView infoRecycler;
    private InfoRecyclerAdapter infoRecyclerAdapter;

    private Toolbar infoToolbar;

    private InfoCallback requestCallback = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        infoToolbar = view.findViewById(R.id.info_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(infoToolbar);
        infoViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(InfoViewModel.class);
        infoRecyclerAdapter = new InfoRecyclerAdapter(view.getContext(), this);
        infoLoadingProgressBar = view.findViewById(R.id.info_loading_progress_bar);
        initRecycler(view);
    }

    private void initRecycler(View currentView) {
        infoRecycler = currentView.findViewById(R.id.info_recycler);
        infoRecycler.setLayoutManager(new LinearLayoutManager(currentView.getContext()));
        infoRecycler.addItemDecoration(new DividerItemDecoration(currentView.getContext(), DividerItemDecoration.HORIZONTAL));
        infoRecycler.setAdapter(infoRecyclerAdapter);
        infoViewModel.makeInfoRequest(infoViewModel.getCode(), String.valueOf(infoViewModel.getPage()), requestCallback, getActivity());
        infoRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !isPaginationLoading) {
                    isPaginationLoading = true;
                    infoViewModel.makeInfoRequest(infoViewModel.getCode(), String.valueOf(infoViewModel.getPage()), requestCallback, getActivity());
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void success(InfoResponse infoResponse) {
        infoLoadingProgressBar.setVisibility(View.GONE);
        infoRecyclerAdapter.addInfoItemsToAdapter(infoResponse.getData());
        infoViewModel.nextPage();
    }

    @Override
    public void error(InfoResponse infoResponse) {
        if (infoLoadingProgressBar.getVisibility() != View.VISIBLE)
            infoLoadingProgressBar.setVisibility(View.VISIBLE);
        infoViewModel.makeInfoRequest(infoViewModel.getCode(), String.valueOf(infoViewModel.getPage()), this, getActivity());
    }

    @Override
    public void onClick(Datum clickedDatum) {
        Bundle infoBundle = new Bundle();
        infoBundle.putFloat("lat", clickedDatum.getLat());
        infoBundle.putFloat("lon", clickedDatum.getLon());
        InfoDetailsFragment infoDetailsFragment = new InfoDetailsFragment();
        infoDetailsFragment.setArguments(infoBundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.activity_info_fragment_container, infoDetailsFragment)
                .commit();
    }
}
