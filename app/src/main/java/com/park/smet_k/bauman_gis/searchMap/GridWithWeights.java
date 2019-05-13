package com.park.smet_k.bauman_gis.searchMap;

import com.park.smet_k.bauman_gis.searchMap.GridLocation;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class GridWithWeights {
    ArrayList<GridLocation> DIRS;
    Integer width, height;
    Set<GridLocation> walls;

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
        width = w;
        height = h;
        DIRS = new ArrayList<>();
        walls = new TreeSet<>(GridLocation::compare);

        DIRS.add(new GridLocation(1, 0));
        DIRS.add(new GridLocation(0, -1));
        DIRS.add(new GridLocation(-1, 0));
        DIRS.add(new GridLocation(0, 1));
    }

    public void add_rect(Integer x1, Integer y1, Integer x2, Integer y2) {
        for (Integer x = x1; x < x2; ++x) {
            for (Integer y = y1; y < y2; ++y) {
                walls.add(new GridLocation(x, y));
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
        return !walls.contains(id);
    }

    // возможные шаги (вправо, вниз, влево, вверх)
    public ArrayList<GridLocation> neighbors(GridLocation id) {
        ArrayList<GridLocation> results = new ArrayList<>();

        for (GridLocation dir : DIRS) {
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
