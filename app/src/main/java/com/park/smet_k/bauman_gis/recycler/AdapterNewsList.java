package com.park.smet_k.bauman_gis.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.model.News;
import com.park.smet_k.bauman_gis.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.List;

public class AdapterNewsList extends RecyclerView.Adapter<AdapterNewsList.NewsRecyclerViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<News> data;
    private final OnItemClickListner<News> onItemClickListener;


    public AdapterNewsList(Context context, OnItemClickListner<News> onItemClickListener) {
        layoutInflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NewsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsRecyclerViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position), this.onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(News newData) {
        data.add(0, newData);
        notifyItemInserted(0);
    }

    final static class NewsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView time;
        private final TextView payload;

        NewsRecyclerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            payload = itemView.findViewById(R.id.payload);
        }

        void bind(final News i, OnItemClickListner onItemClickListener) {
            title.setText(i.getTitle());
//            time.setText(i.getTime().toString());
            payload.setText(i.getPayload());

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(i));
        }

    }
}
