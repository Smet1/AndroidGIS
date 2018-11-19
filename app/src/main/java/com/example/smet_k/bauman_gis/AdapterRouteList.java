package com.example.smet_k.bauman_gis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class AdapterRouteList extends RecyclerView{
    public AdapterRouteList(@NonNull Context context) {
        super(context);
    }

    final static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView from;
        private final TextView to;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.num);

        }

    }
}
