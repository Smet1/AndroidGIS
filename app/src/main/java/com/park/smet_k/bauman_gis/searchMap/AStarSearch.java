package com.park.smet_k.bauman_gis.searchMap;

import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

// для приоритетной очередин
@RequiresApi(api = Build.VERSION_CODES.N)
public class AStarSearch {
    private PriorityQueue<Pair<GridLocation, Double>> frontier;

    private static Comparator<Pair<GridLocation, Double>> PQComparator = (c1, c2) -> (int) (
            c1.second > c2.second ? 1 : -1
    );


    public AStarSearch() {
        frontier = new PriorityQueue<>(PQComparator);
    }

    public void clear() {
        frontier = new PriorityQueue<>(PQComparator);
    }

    public void doAStarSearch(GridWithWeights graph,
                              GridLocation start, GridLocation goal,
                              Map<GridLocation, GridLocation> came_from, Map<GridLocation, Double> cost_so_far) {
        frontier.add(new Pair<>(start, 0.0));

        came_from.put(start, start);
        cost_so_far.put(start, 0.0);

        int counter = 0;
        int dx1;
        int dy1;
        int dx2;
        int dy2;
        float cross;

        while (!frontier.isEmpty()) {
            counter += 1;
            GridLocation current = frontier.poll().first;

//            if (current == goal) {
            if (current.getX().equals(goal.getX()) && current.getY().equals(goal.getY())) {
                break;
            }

            for (GridLocation next : graph.neighbors(current)) {
                double new_cost = cost_so_far.get(current) + graph.cost(current, next);

                if (cost_so_far.get(next) == null) {
                    cost_so_far.put(next, new_cost);

//                    dx1 = current.getX() - goal.getX();
//                    dy1 = current.getY() - goal.getY();
//                    dx2 = start.getX() - goal.getX();
//                    dy2 = start.getY() - goal.getY();
//                    cross = Math.abs(dx1 * dy2 - dx2 * dy1);
//
//                    double priority = new_cost + heuristic(next, goal) + cross * 0.001;

                    double priority = new_cost + heuristic(next, goal);
                    frontier.add(new Pair<>(next, priority));
                    came_from.put(next, current);

                } else if (new_cost < cost_so_far.get(next)) {
                    cost_so_far.replace(next, new_cost);

//                    dx1 = current.getX() - goal.getX();
//                    dy1 = current.getY() - goal.getY();
//                    dx2 = start.getX() - goal.getX();
//                    dy2 = start.getY() - goal.getY();
//                    cross = Math.abs(dx1 * dy2 - dx2 * dy1);
//
//                    double priority = new_cost + heuristic(next, goal) + cross * 0.001;

                    double priority = new_cost + heuristic(next, goal);
                    frontier.add(new Pair<>(next, priority));
                    came_from.put(next, current);
                }
            }
        }
        Log.d("search", Integer.toString(counter));
    }

    public ArrayList<GridLocation> reconstruct_path(GridLocation start, GridLocation goal, Map<GridLocation, GridLocation> came_from) {
        ArrayList<GridLocation> path = new ArrayList<>();
        GridLocation current = goal;

        if (came_from.size() < 2) {
            return path;
        }

        while (current != start) {
            path.add(current);
            current = came_from.get(current);
        }

        path.add(start);

        return path;
    }


    // hеuвристика для A со звездой
    static double heuristic(GridLocation a, GridLocation b) {
        // манхэтаннское расстояние
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());

        // евклидова евристика, медленная, но красивая
//        return Math.sqrt(Math.pow(Math.abs(a.getX() - b.getX()), 2) + Math.pow(Math.abs(a.getY() - b.getY()), 2));
    }
}
