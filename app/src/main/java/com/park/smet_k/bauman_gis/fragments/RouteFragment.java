package com.park.smet_k.bauman_gis.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.compontents.AppComponent;
import com.park.smet_k.bauman_gis.searchMap.AStarSearch;
import com.park.smet_k.bauman_gis.searchMap.GridLocation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

public class RouteFragment extends Fragment {
    // просчет маршрута при создании фрагмента
    String LOG_TAG = "RouteFragment";
    ArrayList<Integer> route;

    GridLocation start = new GridLocation();
    GridLocation goal = new GridLocation();
    AStarSearch aStarSearch = new AStarSearch();
//    ImageView imageView = getView().findViewById(R.id.photo_view);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blue, container, false);
        return view;
    }

    public static RouteFragment newInstance(int from, int to) {
        Bundle args = new Bundle();
        args.putInt("from", from);
        args.putInt("to", to);

        RouteFragment fragment = new RouteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.from);
        TextView routeView = view.findViewById(R.id.route);

        if (AppComponent.getInstance().StairsGraph == null || AppComponent.getInstance().StairsGraph.getGraphSize() == 0) {
            Toast toast = Toast.makeText(getContext(),
                    "stairs graph empty",
                    Toast.LENGTH_SHORT);
            toast.show();
            routeView.setText("ERROR: stairs graph empty");
            return;
        }

        if (AppComponent.getInstance().StairsArray == null || AppComponent.getInstance().StairsArray.size() == 0) {
            Toast toast = Toast.makeText(getContext(),
                    "stairs array empty",
                    Toast.LENGTH_SHORT);
            toast.show();
            routeView.setText("ERROR: stairs array empty");
            return;
        }

        Integer from = 0;
        Integer to = 0;

        Bundle args = getArguments();
        if (args != null) {
            from = args.getInt("from");
            to = args.getInt("to");
        } else {
            return;
        }

        textView.setText(from.toString());

        textView = view.findViewById(R.id.to);
        textView.setText(to.toString());

        // фиксируем прибыль (уличная магия)
        route = AppComponent.getInstance().StairsGraph.dijkstra(from - 1, to - 1);

        StringBuilder result = new StringBuilder();
        for (Integer val : route) {
            val += 1;
            result.append(", ").append(val.toString());
        }

        result.append("|");

//        result.append("run A star on: ");
//        for (int i = 0; i < route.size() - 1; i++) {
//            if (AppComponent.getInstance().StairsArray.get(route.get(i)).getLevel().
//                    equals(AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getLevel())) {
////                Log.d("kek", "same level, run A star on " +
////                        (route.get(i) + 1) + " " + (route.get(i + 1) + 1));
//                result.append(route.get(i)).append(" ").append(route.get(i) + 1).append(", ");
//
//                Integer x_f = AppComponent.getInstance().StairsArray.get(route.get(i)).getX();
//                Integer y_f = AppComponent.getInstance().StairsArray.get(route.get(i)).getY();
//                Integer x_l = AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getX();
//                Integer y_l = AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getY();
//
//                GridLocation start = new GridLocation(x_f, y_f);
//                GridLocation goal = new GridLocation(x_l, y_l);
//
//                TreeMap<GridLocation, GridLocation> came_from = new TreeMap<>(GridLocation::compare);
//                TreeMap<GridLocation, Double> cost_so_far = new TreeMap<>(GridLocation::compare);
//
//                AStarSearch aStarSearch = new AStarSearch();
//                aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(0), start, goal, came_from, cost_so_far);
//
//                ArrayList<GridLocation> path = aStarSearch.reconstruct_path(start, goal, came_from);
//
//                result.append("a_star: ");
//                for (GridLocation p : path) {
//                    Log.d("a star", p.getX().toString() + " " + p.getY().toString());
//                    result.append("|").append(p.getX().toString()).append(" ").append(p.getY().toString());
//                }
//            }
//        }


//        GridWithWeights grid = new GridWithWeights(10, 10);
//        grid.add_rect(2, 0, 3, 9);

//        GridLocation start = new GridLocation(1, 4);
//        GridLocation goal = new GridLocation(8, 5);
//
//        TreeMap<GridLocation, GridLocation> came_from = new TreeMap<>(GridLocation::compare);
//        TreeMap<GridLocation, Double> cost_so_far = new TreeMap<>(GridLocation::compare);
//
//        AStarSearch aStarSearch = new AStarSearch();
////        aStarSearch.doAStarSearch(grid, start, goal, came_from, cost_so_far);
//        aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(0), start, goal, came_from, cost_so_far);
//
//
//        ArrayList<GridLocation> path = aStarSearch.reconstruct_path(start, goal, came_from);
//
//        result.append("a_star: ");
//        for (GridLocation i : path) {
//            Log.d("a star", i.getX().toString() + " " + i.getY().toString());
//            result.append("|").append(i.getX().toString()).append(" ").append(i.getY().toString());
//        }


        routeView.setText(result.toString());

//        RelativeLayout relativeLayout = view.findViewById(R.id.canvas);
//        relativeLayout.addView(new DrawView(getActivity()));


//        ImageView imageView = findViewById(R.id.photo_view);
        Paint p = new Paint();
        Bitmap bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.bmstuplan);
        Bitmap bitmapPath = Bitmap.createBitmap(1240, 1080, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapPath);

        canvas.scale(10f, 10f);
        p.setColor(Color.GRAY);
        p.setStrokeWidth(1);

        for (int i = 0; i < route.size() - 1; i++) {
            if (AppComponent.getInstance().StairsArray.get(route.get(i)).getLevel().
                    equals(AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getLevel())) {
                Log.d("kek", "same level, run A star on " +
                        (route.get(i) + 1) + " " + (route.get(i + 1) + 1));

                Integer x_f = AppComponent.getInstance().StairsArray.get(route.get(i)).getX();
                Integer y_f = AppComponent.getInstance().StairsArray.get(route.get(i)).getY();
                Integer x_l = AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getX();
                Integer y_l = AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getY();

                Log.d("kek", "points: " + x_f.toString() + " " + y_f.toString() + ", " +
                        x_l.toString() + " " + y_l.toString());

                start.setX(x_f);
                start.setY(y_f);

                goal.setX(x_l);
                goal.setY(y_l);

                TreeMap<GridLocation, GridLocation> came_from = new TreeMap<>(GridLocation::compare);
                TreeMap<GridLocation, Double> cost_so_far = new TreeMap<>(GridLocation::compare);

                aStarSearch.clear();
                aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(0), start, goal, came_from, cost_so_far);

                ArrayList<GridLocation> path = aStarSearch.reconstruct_path(start, goal, came_from);

                for (GridLocation gl : path) {
                    canvas.drawPoint(gl.getX(), gl.getY(), p);
                }

                p.setColor(Color.GREEN);
                canvas.drawCircle(x_f, y_f, 2, p);
                p.setColor(Color.RED);
                canvas.drawCircle(x_l, y_l, 2, p);
            }
        }
        Bitmap merge = createSingleImageFromMultipleImages(bitmapImg, bitmapPath);
        Log.d(LOG_TAG, "get canvas bitmap");

        ImageView imageView = Objects.requireNonNull(getView()).findViewById(R.id.photo_view);
        imageView.setImageBitmap(merge);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.update();
    }

    public class DrawView extends View {
        Paint p;
        Bitmap bitmapPath;

        public DrawView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.setDrawingCacheEnabled(true);
        }

        public DrawView(Context context) {
            super(context);
//            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            p = new Paint();
        }

        public Bitmap get() {
            return this.getDrawingCache();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Resources res = getResources();
            bitmapPath = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Bitmap bitmapImg = BitmapFactory.decodeResource(res, R.drawable.bmstuplan);
            canvas = new Canvas(bitmapPath);
//            canvas.drawBitmap(bitmap, 0, 0, p);
            canvas.scale(10f, 10f);
            p.setColor(Color.GRAY);
            p.setStrokeWidth(1);

            for (int i = 0; i < route.size() - 1; i++) {
                if (AppComponent.getInstance().StairsArray.get(route.get(i)).getLevel().
                        equals(AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getLevel())) {
                    Log.d("kek", "same level, run A star on " +
                            (route.get(i) + 1) + " " + (route.get(i + 1) + 1));

                    Integer x_f = AppComponent.getInstance().StairsArray.get(route.get(i)).getX();
                    Integer y_f = AppComponent.getInstance().StairsArray.get(route.get(i)).getY();
                    Integer x_l = AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getX();
                    Integer y_l = AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getY();

                    Log.d("kek", "points: " + x_f.toString() + " " + y_f.toString() + ", " +
                            x_l.toString() + " " + y_l.toString());

                    start.setX(x_f);
                    start.setY(y_f);

                    goal.setX(x_l);
                    goal.setY(y_l);

                    TreeMap<GridLocation, GridLocation> came_from = new TreeMap<>(GridLocation::compare);
                    TreeMap<GridLocation, Double> cost_so_far = new TreeMap<>(GridLocation::compare);

                    aStarSearch.clear();
                    aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(0), start, goal, came_from, cost_so_far);

                    ArrayList<GridLocation> path = aStarSearch.reconstruct_path(start, goal, came_from);

                    for (GridLocation gl : path) {
                        canvas.drawPoint(gl.getX(), gl.getY(), p);
                    }

//                    canvas.drawLine(0, 0, 0, 100, p);
//                    canvas.drawLine(0, 100, 100, 100, p);
//                    canvas.drawLine(100, 100, 100, 0, p);
//                    canvas.drawLine(100, 0, 0, 0, p);

                    p.setColor(Color.GREEN);
                    canvas.drawCircle(x_f, y_f, 2, p);
                    p.setColor(Color.RED);
                    canvas.drawCircle(x_l, y_l, 2, p);

                }
            }

            Bitmap merge = createSingleImageFromMultipleImages(bitmapImg, bitmapPath);
            Log.d(LOG_TAG, "get canvas bitmap");
//            ImageView imageView = findViewById(R.id.photo_view);
            ImageView imageView = Objects.requireNonNull(getView()).findViewById(R.id.photo_view);
            if (imageView != null) {
                imageView.setImageBitmap(merge);
                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
            }
        }
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage){

        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 10, 10, null);
        return result;
    }
}
