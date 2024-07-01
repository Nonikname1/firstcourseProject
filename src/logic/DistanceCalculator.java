package logic;

import java.util.List;

public class DistanceCalculator {

    public static int[][] calculateDistances(List<Point> points) {
        int size = points.size();
        Graph g = new Graph(size);
        int[][] matrix = g.getAdjMatrix();

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int distance = points.get(i).distance(points.get(j));
                g.addEdge(i,j,distance);
            }
        }
        return matrix;
    }
}
