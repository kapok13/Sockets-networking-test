package com.vb.htest.ui.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vb.htest.R;
import com.vb.htest.data.network.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class InfoRecyclerAdapter extends RecyclerView.Adapter<InfoRecyclerAdapter.InfoRecyclerViewHolder> {

    private Context context;
    private ArrayList<Datum> infoItems;
    private RecyclerItemClickCallback callback;

    public InfoRecyclerAdapter(Context context, RecyclerItemClickCallback callback) {
        this.context = context;
        this.callback = callback;
        infoItems = new ArrayList<>();
    }

    public void addInfoItemsToAdapter(List<Datum> datumList) {
        infoItems.addAll(datumList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InfoRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InfoRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InfoRecyclerViewHolder holder, int position) {
        Datum currentInfo = infoItems.get(position);
        holder.userIdTV.setText(currentInfo.getId());
        holder.usernameTV.setText(currentInfo.getName());
        holder.countryTV.setText(currentInfo.getCountry());
        holder.itemView.setOnClickListener((view) -> callback.onClick(currentInfo));
    }

    @Override
    public int getItemCount() {
        return infoItems != null ? infoItems.size() : 0;
    }

    protected static class InfoRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView userIdTV;
        TextView usernameTV;
        TextView countryTV;

        public InfoRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTV = itemView.findViewById(R.id.recycler_item_id);
            usernameTV = itemView.findViewById(R.id.recycler_item_username);
            countryTV = itemView.findViewById(R.id.recycler_item_country);
        }
    }
}
