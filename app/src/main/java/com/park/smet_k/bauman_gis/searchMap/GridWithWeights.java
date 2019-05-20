package com.park.smet_k.bauman_gis.searchMap;

import android.util.Log;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class GridWithWeights {
    private ArrayList<GridLocation> DIRS;
    private Integer width, height;
    private Set<GridLocation> walls;

    public GridWithWeights() {
        width = 0;
        height = 0;
        DIRS = new ArrayList<>();
        walls = new TreeSet<>(GridLocation::compare);

        DIRS.add(new GridLocation(1, 0));
        DIRS.add(new GridLocation(0, -1));
        DIRS.add(new GridLocation(-1, 0));
        DIRS.add(new GridLocation(0, 1));
    }

    public GridWithWeights(Integer w, Integer h) {
        this.width = w;
        this.height = h;
        this.DIRS = new ArrayList<>();
        this.walls = new TreeSet<>(GridLocation::compare);

        this.DIRS.add(new GridLocation(1, 0));
        this.DIRS.add(new GridLocation(0, -1));
        this.DIRS.add(new GridLocation(-1, 0));
        this.DIRS.add(new GridLocation(0, 1));
    }

    public void add_rect(Integer x1, Integer y1, Integer x2, Integer y2) {
        for (Integer x = x1; x < x2; ++x) {
            for (Integer y = y1; y < y2; ++y) {
                this.walls.add(new GridLocation(x, y));
            }
        }
    }

    public void add_spline(Integer centerX, Integer centerY, Integer radius, boolean right) {
        double angleA;
        double c;
        if (right) {
            for (int a = 0; a <= radius; a++) {
                angleA = Math.acos((double) a / (double) radius);
                c = radius * Math.sin(angleA);

                int new_x = (centerX + a);
                int new_y = (centerY - (int) c);
                this.walls.add(new GridLocation(new_x, new_y));
                Log.d("add_spline", new_x + " " + new_y);
            }
        }
    }

    // получить вес перехода от одного узла к другому
    public double cost(GridLocation from_node, GridLocation to_node) {
        // тк веса везде одинаковые то 1
        return 1;
    }

    // в границах?
    boolean in_bounds(GridLocation id) {
        return ((0 <= id.getX()) && (id.getX() < this.width) && (0 <= id.getY()) && (id.getY() < this.height));
    }

    // проверка на проходимость
    boolean passable(GridLocation id) {
        return !this.walls.contains(id);
    }

    // возможные шаги (вправо, вниз, влево, вверх)
    public ArrayList<GridLocation> neighbors(GridLocation id) {
        ArrayList<GridLocation> results = new ArrayList<>();

        for (GridLocation dir : this.DIRS) {
            GridLocation next = new GridLocation(id.getX() + dir.getX(), id.getY() + dir.getY());
            if (in_bounds(next) && passable(next)) {
                results.add(next);
            }
        }

//            if ((id.getX() + id.getY()) % 2 == 0) {
//                // aesthetic improvement on square grids
//                std::reverse(results.begin(), results.end());
//            }

        return results;
    }

}
