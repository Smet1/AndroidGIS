package com.park.smet_k.bauman_gis.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.model.Route;
import com.park.smet_k.bauman_gis.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AdapterRoutesList extends RecyclerView.Adapter<AdapterRoutesList.RoutesRecyclerViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Route> data;
    private final OnItemClickListner<Route> onItemClickListener;


    public AdapterRoutesList(Context context, OnItemClickListner<Route> onItemClickListener) {
        layoutInflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RoutesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoutesRecyclerViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position), this.onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(Route newData) {
        data.add(0, newData);
        notifyItemInserted(0);
    }

    public void setItems(Collection<Route> in) {
        data.addAll(in);
        notifyDataSetChanged();
    }

    final static class RoutesRecyclerViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;
        private final TextView number_1;

        RoutesRecyclerViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.num);
            number_1 = itemView.findViewById(R.id.num_1);
        }

        void bind(final Route i, OnItemClickListner onItemClickListener) {
            number.setText(i.getFrom());
            number_1.setText(i.getTo());

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(i));
        }
    }
}


