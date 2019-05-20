package com.park.smet_k.bauman_gis.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.park.smet_k.bauman_gis.R;
import com.park.smet_k.bauman_gis.compontents.AppComponent;
import com.park.smet_k.bauman_gis.model.News;
import com.park.smet_k.bauman_gis.model.RoutePoint;
import com.park.smet_k.bauman_gis.model.Stairs;
import com.park.smet_k.bauman_gis.recycler.AdapterPathList;
import com.park.smet_k.bauman_gis.searchMap.AStarSearch;
import com.park.smet_k.bauman_gis.searchMap.GridLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public class RouteFragment extends Fragment {
    // просчет маршрута при создании фрагмента
    String LOG_TAG = "RouteFragment";
    ArrayList<Integer> route;

    GridLocation start = new GridLocation();
    GridLocation goal = new GridLocation();
    AStarSearch aStarSearch = new AStarSearch();
    private RecyclerView mapsList;
    private List<Pair<Bitmap, Integer>> pathList = new ArrayList<>();


    // насколько нужно умножать пискельные координаты,
    // чтобы они легли на битмапу нормально, без искажений
    float multiplyDP = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blue, container, false);
    }

    public static RouteFragment newInstance(String from, String to) {
        Bundle args = new Bundle();
        args.putString("from", from);
        args.putString("to", to);

        RouteFragment fragment = new RouteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapsList = view.findViewById(R.id.path_list);

        if (AppComponent.getInstance().StairsGraph == null || AppComponent.getInstance().StairsGraph.getGraphSize() == 0) {
            Toast toast = Toast.makeText(getContext(),
                    "stairs graph empty",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (AppComponent.getInstance().StairsArray == null || AppComponent.getInstance().StairsArray.size() == 0) {
            Toast toast = Toast.makeText(getContext(),
                    "stairs array empty",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Stairs fromStair;
        Stairs toStair;
        String fromPoint;
        String toPoint;

        Bundle args = getArguments();
        if (args != null) {
            fromPoint = args.getString("from");
            toPoint = args.getString("to");
        } else {
            return;
        }

        RoutePoint routePointFrom = AppComponent.getInstance().PointsMap.get(fromPoint);
        RoutePoint routePointTo = AppComponent.getInstance().PointsMap.get(toPoint);

        fromStair = AppComponent.getInstance().GetClosestStair(routePointFrom);
        toStair = AppComponent.getInstance().GetClosestStair(routePointTo);

        Paint p = new Paint();
        Bitmap bitmapImg1 = BitmapFactory.decodeResource(getResources(), R.drawable.bmstuplan);
        int width = bitmapImg1.getWidth();
        int height = bitmapImg1.getHeight();

        multiplyDP = (float) height / 1080;

        Bitmap bitmapImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapImg);

        p.setColor(getResources().getColor(R.color.colorMiddle));
        p.setStrokeWidth(5);

        assert routePointFrom != null;
        assert routePointTo != null;
        if (routePointFrom.getLevel().equals(routePointTo.getLevel())) {
            Log.d(LOG_TAG, "if");

            start.setX(routePointFrom.getX());
            start.setY(routePointFrom.getY());

            goal.setX(routePointTo.getX());
            goal.setY(routePointTo.getY());

            TreeMap<GridLocation, GridLocation> came_from = new TreeMap<>(GridLocation::compare);
            TreeMap<GridLocation, Double> cost_so_far = new TreeMap<>(GridLocation::compare);

            aStarSearch.clear();
            aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(routePointFrom.getLevel()), start, goal, came_from, cost_so_far);

            ArrayList<GridLocation> path = aStarSearch.reconstruct_path(start, goal, came_from);

            for (GridLocation gl : path) {
                Log.d("kek", "points: " + gl.getX().toString() + " " + gl.getY().toString());
                canvas.drawPoint(gl.getX() * multiplyDP, gl.getY() * multiplyDP, p);
            }

            p.setColor(getResources().getColor(R.color.colorMiddle));
            canvas.drawCircle(start.getX() * multiplyDP, start.getY() * multiplyDP, 20, p);

            p.setColor(getResources().getColor(R.color.colorPrimaryDark));
            p.setTextAlign(Paint.Align.RIGHT);
            p.setTextSize(30);
            canvas.drawText(routePointFrom.getName(), (routePointFrom.getX()) * multiplyDP, (routePointFrom.getY()) * multiplyDP, p);

            p.setColor(getResources().getColor(R.color.colorAccent));
            canvas.drawCircle(goal.getX() * multiplyDP, goal.getY() * multiplyDP, 20, p);

            p.setColor(getResources().getColor(R.color.colorPrimary));
            p.setTextAlign(Paint.Align.RIGHT);
            p.setTextSize(30);
            canvas.drawText(routePointTo.getName(), (routePointTo.getX()) * multiplyDP, (routePointTo.getY()) * multiplyDP, p);

            if (path.size() == 0) {
                Toast toast = Toast.makeText(getContext(),
                        "can't find path, sorry",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            Bitmap merge = overlay(bitmapImg1, bitmapImg);
            pathList.add(new Pair<>(merge, routePointFrom.getLevel()));
        } else {
            Log.d(LOG_TAG, "else");

            // фиксируем прибыль (уличная магия)
            route = AppComponent.getInstance().StairsGraph.dijkstra(fromStair.getId() - 1, toStair.getId() - 1);

            start.setX(routePointFrom.getX());
            start.setY(routePointFrom.getY());

            goal.setX(AppComponent.getInstance().StairsArray.get(route.get(0)).getX());
            goal.setY(AppComponent.getInstance().StairsArray.get(route.get(0)).getY());

            TreeMap<GridLocation, GridLocation> came_from = new TreeMap<>(GridLocation::compare);
            TreeMap<GridLocation, Double> cost_so_far = new TreeMap<>(GridLocation::compare);

            aStarSearch.clear();
            aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(routePointFrom.getLevel()), start, goal, came_from, cost_so_far);

            ArrayList<GridLocation> path = aStarSearch.reconstruct_path(start, goal, came_from);

            for (GridLocation gl : path) {
                Log.d("kek", "points: " + gl.getX().toString() + " " + gl.getY().toString());
                canvas.drawPoint(gl.getX() * multiplyDP, gl.getY() * multiplyDP, p);
            }

            p.setColor(getResources().getColor(R.color.colorMiddle));
            canvas.drawCircle(start.getX() * multiplyDP, start.getY() * multiplyDP, 20, p);

            p.setColor(getResources().getColor(R.color.colorPrimaryDark));
            p.setTextAlign(Paint.Align.RIGHT);
            p.setTextSize(30);
            canvas.drawText(routePointFrom.getName(), (routePointFrom.getX()) * multiplyDP, (routePointFrom.getY()) * multiplyDP, p);

            p.setColor(getResources().getColor(R.color.colorAccent));
            canvas.drawCircle(goal.getX() * multiplyDP, goal.getY() * multiplyDP, 20, p);

            if (path.size() == 0) {
                Toast toast = Toast.makeText(getContext(),
                        "can't find path, sorry",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            Bitmap merge = overlay(bitmapImg1, bitmapImg);
            pathList.add(new Pair<>(merge, routePointFrom.getLevel()));

            ////////////

            for (int i = 0; i < route.size() - 1; i++) {
                if (AppComponent.getInstance().StairsArray.get(route.get(i)).getLevel().
                        equals(AppComponent.getInstance().StairsArray.get(route.get(i + 1)).getLevel())) {
                    Log.d("kek", "same level, run A star on " +
                            (route.get(i) + 1) + " " + (route.get(i + 1) + 1));

                    bitmapImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(bitmapImg);

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

                    came_from = new TreeMap<>(GridLocation::compare);
                    cost_so_far = new TreeMap<>(GridLocation::compare);

                    aStarSearch.clear();
                    aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(0), start, goal, came_from, cost_so_far);

                    path = aStarSearch.reconstruct_path(start, goal, came_from);

                    for (GridLocation gl : path) {
                        Log.d("kek", "points: " + gl.getX().toString() + " " + gl.getY().toString());
                        canvas.drawPoint(gl.getX() * multiplyDP, gl.getY() * multiplyDP, p);
                    }

                    p.setColor(getResources().getColor(R.color.colorPrimaryDark));
                    canvas.drawCircle(x_f * multiplyDP, y_f * multiplyDP, 20, p);
                    p.setColor(getResources().getColor(R.color.colorAccent));
                    canvas.drawCircle(x_l * multiplyDP, y_l * multiplyDP, 20, p);

                    merge = overlay(bitmapImg1, bitmapImg);
                    pathList.add(new Pair<>(merge, AppComponent.getInstance().StairsArray.get(route.get(i)).getLevel()));
                }
            }

            ////////

            bitmapImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmapImg);

            start.setX(AppComponent.getInstance().StairsArray.get(route.get(route.size() - 1)).getX());
            start.setY(AppComponent.getInstance().StairsArray.get(route.get(route.size() - 1)).getY());

            goal.setX(routePointTo.getX());
            goal.setY(routePointTo.getY());

            came_from = new TreeMap<>(GridLocation::compare);
            cost_so_far = new TreeMap<>(GridLocation::compare);

            aStarSearch.clear();
            aStarSearch.doAStarSearch(AppComponent.getInstance().LevelsGraph.get(routePointTo.getLevel()), start, goal, came_from, cost_so_far);

            path = aStarSearch.reconstruct_path(start, goal, came_from);

            p.setColor(getResources().getColor(R.color.colorMiddle));
            p.setStrokeWidth(5);

            for (GridLocation gl : path) {
                canvas.drawPoint(gl.getX() * multiplyDP, gl.getY() * multiplyDP, p);
            }

            p.setColor(getResources().getColor(R.color.colorMiddle));
            canvas.drawCircle(start.getX() * multiplyDP, start.getY() * multiplyDP, 20, p);

            p.setColor(getResources().getColor(R.color.colorAccent));
            canvas.drawCircle(goal.getX() * multiplyDP, goal.getY() * multiplyDP, 20, p);

            p.setColor(getResources().getColor(R.color.colorPrimary));
            p.setTextAlign(Paint.Align.RIGHT);
            p.setTextSize(30);
            canvas.drawText(routePointTo.getName(), (routePointTo.getX()) * multiplyDP, (routePointTo.getY()) * multiplyDP, p);

            if (path.size() == 0) {
                Toast toast = Toast.makeText(getContext(),
                        "can't find path, sorry",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            merge = overlay(bitmapImg1, bitmapImg);
            pathList.add(new Pair<>(merge, AppComponent.getInstance().StairsArray.get(route.get(route.size() - 1)).getLevel()));
        }

        AdapterPathList adapterPathList = new AdapterPathList(getContext(), this::onItemClick);

        mapsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mapsList.setAdapter(adapterPathList);
        mapsList.setHasFixedSize(true);

        for (Pair<Bitmap, Integer> i : pathList) {
            adapterPathList.add(i);
        }
    }

    private void onItemClick(Pair<Bitmap, Integer> i) {
        // TODO(): здесь пока ничего нет, если будет надо, взять из RoutesListFragment
    }

    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }
}
