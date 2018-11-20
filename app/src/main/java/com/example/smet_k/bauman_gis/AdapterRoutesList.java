package com.example.smet_k.bauman_gis;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class AdapterRouteList extends RecyclerView.Adapter<AdapterRouteList.RouteViewHolder> {
//
//    private List<Route> routeList = new ArrayList<>();
//    private View.OnClickListener itemClickListener;
//
//    public void setitems(Collection<Route> routes) {
//        routeList.addAll(routes);
//        notifyDataSetChanged();
//    }
//
//    public void addItem(Route route) {
//        routeList.add(route);
//        notifyDataSetChanged();
//    }
//
//    public void clearItems() {
//        routeList.clear();
//        notifyDataSetChanged();
//    }
//
//    public interface OnItemClickListener {
//        void onClick(View view, int position);
//    }
//
//    public void setItemClickListener(final OnItemClickListener listener) {
//        this.itemClickListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Context context = viewGroup.getContext();
//        View view = LayoutInflater.from(context).inflate(R.layout.recycler_fragment_route_list, viewGroup, false);
//        return new RouteViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RouteViewHolder routeViewHolder, int i) {
//        routeViewHolder.bind(routeList.get(i));
//    }
//
//    @Override
//    public int getItemCount() {
//        return routeList.size();
//    }
//
//
//    final static class ViewHolder extends RecyclerView.ViewHolder {
////        private final TextView from;
////        private final TextView to;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
////            from = itemView.findViewById(R.id.num);
//
//        }
//
//    }
//
//    class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private TextView textView;
//
//        public RouteViewHolder(@NonNull View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
////            TODO(): сделать передачу айтема в RouteFragment
////            textView = itemView.findViewById(R.id.tv_item);
//        }
//
//        void bind(Route route) {
////            TODO(): реализация
////            textView.setText(route.getData());
////            textView.setVisibility(route.getData() != null ? View.VISIBLE : View.GONE);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (itemClickListener != null) {
//                itemClickListener.onClick(v, getAdapterPosition());
//            }
//        }
//    }
//}

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AdapterRoutesList extends RecyclerView.Adapter<AdapterRoutesList.RoutesRecyclerViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<Route> data;
    private final OnItemClickListener<Integer> onItemClickListener;


    public AdapterRoutesList(Context context, OnItemClickListener<Integer> onItemClickListener) {
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
    public void onBindViewHolder(RoutesRecyclerViewHolder holder, int position) {
        holder.bind(data.get(position), this.onItemClickListener);
//        holder.number.setTextColor(position % 2 == 1 ? Color.RED : Color.BLUE);
//        holder.number_1.setTextColor(position % 2 == 1 ? Color.RED : Color.BLUE);
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

        void bind(final Route i, OnItemClickListener onItemClickListener) {
            Integer tmp_from = i.getFrom();
            number.setText(Integer.toString(tmp_from));

            number_1.setText(Integer.toString(i.getTo()));

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(i));
        }


    }
}

