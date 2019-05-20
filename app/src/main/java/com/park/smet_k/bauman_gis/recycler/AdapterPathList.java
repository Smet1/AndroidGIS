package com.park.smet_k.bauman_gis.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.model.News;
import com.park.smet_k.bauman_gis.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.List;

public class AdapterPathList extends RecyclerView.Adapter<AdapterPathList.PathRecyclerViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<Pair<Bitmap, Integer>> data;
    private final OnItemClickListner<Pair<Bitmap, Integer>> onItemClickListener;

    public AdapterPathList(Context context, OnItemClickListner<Pair<Bitmap, Integer>> onItemClickListener) {
        layoutInflater = LayoutInflater.from(context);

        this.data = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PathRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterPathList.PathRecyclerViewHolder(layoutInflater.inflate(R.layout.item_path, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PathRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position), this.onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(Pair<Bitmap, Integer> newData) {
        data.add(0, newData);
        notifyItemInserted(0);
    }

    public class PathRecyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView level;
        private final PhotoViewAttacher photoViewAttacher;

        public PathRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.photo_view);
            level = itemView.findViewById(R.id.level);
            photoViewAttacher = new PhotoViewAttacher(imageView);
        }

        public void bind(Pair<Bitmap, Integer> bitmap, OnItemClickListner onItemClickListener) {
            imageView.setImageBitmap(bitmap.first);
            level.setText("Level " + bitmap.second.toString());
            photoViewAttacher.update();

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(bitmap));
        }
    }
}
