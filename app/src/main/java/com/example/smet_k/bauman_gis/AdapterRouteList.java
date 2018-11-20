package com.example.smet_k.bauman_gis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterRouteList extends RecyclerView.Adapter<AdapterRouteList.RouteViewHolder> {

    private List<Route> routeList = new ArrayList<>();
    private View.OnClickListener itemClickListener;

    public void setitems(Collection<Route> routes) {
        routeList.addAll(routes);
        notifyDataSetChanged();
    }

    public void addItem(Route route) {
        routeList.add(route);
        notifyDataSetChanged();
    }

    public void clearItems() {
        routeList.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setItemClickListener(final OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_fragment_route_list, viewGroup, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder routeViewHolder, int i) {
        routeViewHolder.bind(routeList.get(i));
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }


    final static class ViewHolder extends RecyclerView.ViewHolder {
//        private final TextView from;
//        private final TextView to;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            from = itemView.findViewById(R.id.num);

        }

    }

    class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            TODO(): сделать передачу айтема в RouteFragment
//            textView = itemView.findViewById(R.id.tv_item);
        }

        void bind(Route route) {
//            TODO(): реализация
//            textView.setText(route.getData());
//            textView.setVisibility(route.getData() != null ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
