package logic;

import java.util.*;

public class Graph {
    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    private int[][] adjMatrix;
    public Graph(int vertices){
        adjMatrix = new int[vertices][vertices];
        for (int[] row : adjMatrix){
            Arrays.fill(row,-1);
        }
    }

    public void addEdge (int source, int destination, int weight){
        adjMatrix[source][destination] = weight;
        adjMatrix[destination][source] = weight;
    }


    public void printMatrix() {
        System.out.print("  ");
        for (int i = 0; i < adjMatrix[0].length; i++){
            System.out.print(i + " ");
        }
        System.out.println();

        int i = 0;
        for(int[] row : adjMatrix){
            System.out.print(i + " ");
            for (int weight : row){
                if (weight == -1){
                    System.out.print(0 + " ");
                }else {
                    System.out.print(weight + " ");
                }
            }
            i++;
            System.out.println();
        }
    }

}
