package com.park.smet_k.bauman_gis.searchMap;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class WeightedGraph {
    private int graphSize;
    private int edgesCount;

    // TODO(): точно Short?
    private ArrayList<ArrayList<Pair<Integer, Integer>>> outEdges;

    public WeightedGraph() {
        this.graphSize = 0;
        this.edgesCount = 0;
        outEdges = new ArrayList<>();
    }

    public WeightedGraph(int graphSize, int edgesCount) {
        this.graphSize = graphSize;
        this.edgesCount = edgesCount;
        outEdges = new ArrayList<>();

//        outEdges.ensureCapacity(graphSize);

        for (int i = 0; i < graphSize; i++) {
            outEdges.add(new ArrayList<>());
        }
    }

    public int getGraphSize() {
        return graphSize;
    }

    public int getEdgesCount() {
        return edgesCount;
    }

    public void addEdge(Integer from, Integer to, Integer weight) {
        outEdges.get(from).add(new Pair<>(to, weight));
        outEdges.get(to).add(new Pair<>(from, weight));
    }

    public ArrayList<Pair<Integer, Integer>> getNextVertices(Integer vertex) {
        return outEdges.get(vertex);
    }

    public ArrayList<Integer> dijkstra(Integer from, Integer to) {
        TreeSet<Pair<Integer, Integer>> deque = new TreeSet<>((o1, o2) -> {
            if (o1.first - o2.first == 0) {
                if (o1.second.equals(o2.second))
                    return 1;
                else
                    return o1.second - o2.second;
            }
            return o1.first - o2.first;
        });

        ArrayList<Integer> weights = new ArrayList<>(this.getGraphSize());
        ArrayList<ArrayList<Integer>> verts_from = new ArrayList<>();
        for (int i = 0; i < this.getGraphSize(); i++) {
            weights.add(Integer.MAX_VALUE);
            verts_from.add(new ArrayList<>());
        }

        Integer tmp_from = 0;
        deque.add(new Pair<>(0, from));
        weights.set(from, 0);

        while (!deque.isEmpty()) {
            tmp_from = deque.first().second;
            deque.pollFirst();

            for (Pair<Integer, Integer> vert : this.getNextVertices(tmp_from)) {
                if (weights.get(vert.first) > vert.second + weights.get(tmp_from)) {
//                    Log.d("kek", vert.first + " " + vert.second);

                    if (!verts_from.get(vert.first).isEmpty()) {
                        verts_from.get(vert.first).clear();
                    }
                    verts_from.get(vert.first).add(tmp_from);

                    deque.remove(new Pair<>(weights.get(tmp_from), vert.first));
                    weights.set(vert.first, vert.second + weights.get(tmp_from));

                    deque.add(new Pair<>(weights.get(vert.first), vert.first));
                }
            }
        }

        ArrayList<Integer> route = new ArrayList<>();
        Integer i = to;
        while (verts_from.get(i).size() != 0) {
            route.add(i);
            i = verts_from.get(i).get(0);
        }

        route.add(from);
        Collections.reverse(route);
        Log.d("dijkstra", "end");

        return route;
    }

}
