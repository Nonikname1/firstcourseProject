package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Algorithm {

    public static List<Point> findPath(int generations, int numberOfMutation, int numberOfPopulation, List<Point> points) {
        int[][] matrix = getMatrix(points);
        List<List<Integer>> initialPopulation = generateInitialPopulation(numberOfPopulation, matrix[0].length);
        List<ListItem> population = new ArrayList<>();

        for (List<Integer> innerList : initialPopulation) {
            if (!innerList.isEmpty()) {
                Integer value = getLengthOfRoute(innerList, matrix);
                population.add(new ListItem(innerList, value));
            }
        }

        population = nextGeneration(generations, population, matrix, numberOfMutation, numberOfPopulation);
        //printPopulation(population);
        return sortPoints(points, population.get(population.size() - 1).getList());

    }
    public static int[][] getMatrix(List<Point> points){
        return DistanceCalculator.calculateDistances(points);

    }


    public static List<List<Integer>> generateInitialPopulation(int populationSize, int numberOfVertices) {
        List<List<Integer>> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<Integer> route = new ArrayList<>();
            for (int j = 0; j < numberOfVertices; j++) {
                route.add(j);
            }
            Collections.shuffle(route);
            population.add(route);
        }
        return population;
    }

    public static List<Integer> crossing(List<Integer> father, List<Integer> mother) {
        Random random = new Random();
        int size = father.size();
        int start = random.nextInt(size);
        int end = random.nextInt(size);
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        List<Integer> child = new ArrayList<>(father.subList(start, end));
        for (Integer gen : mother) {
            if (!child.contains(gen)) {
                child.add(gen);
            }
        }
        return child;
    }

    public static List<Integer> mutation(List<Integer> child, int numberOfMutation) {
        Random random = new Random();
        int x = random.nextInt(0, 100);
        if (x < numberOfMutation) {
            int firstPos = random.nextInt(child.size() - 1);
            int secondPos = random.nextInt(child.size() - 1);
            int temp = child.get(firstPos);
            child.set(firstPos, child.get(secondPos));
            child.set(secondPos, temp);
        }
        return child;
    }

    public static int getLengthOfRoute(List<Integer> way, int[][] matrix) {
        if (way.isEmpty()) {
            return Integer.MAX_VALUE;
        }

        int length = 0;
        int firstPos = 0;
        int secondPos = matrix[0].length - 1;


        length += matrix[way.get(firstPos)][way.get(secondPos)];
        for (int i = 0; i < matrix[0].length - 1; i++) {
            firstPos = way.get(i);
            secondPos = way.get(i + 1);
            if (matrix[firstPos][secondPos] != -1) {
                length += matrix[firstPos][secondPos];
            }
        }
        return length;
    }

    public static List<ListItem> selection(List<ListItem> population, int numberOfPopulation) {
        while (population.size() > numberOfPopulation) {
            population.remove(0);
        }
        return population;
    }


    public static List<ListItem> nextGeneration(int n, List<ListItem> population, int[][] matrix, int numberOfMutation, int numberOfPopulation) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int size = population.size();
            for (int j = 0; j < size; j++) {
                int father = random.nextInt(size - 1);
                int mother = random.nextInt(size - 1);
                List<Integer> child = crossing(population.get(father).getList(), population.get(mother).getList());
                child = mutation(child, numberOfMutation);
                population.add(new ListItem(child, getLengthOfRoute(child, matrix)));
            }
            Collections.sort(population);
            population = selection(population, numberOfPopulation);
        }
        return population;
    }


    public static void printPopulation(List<ListItem> population) {
        for (ListItem item : population) {
            System.out.println(item.getValue() + " = " + item.getList());
        }
    }

    public static int getFinalLength(List<Point> points){
        if (points.isEmpty()) {
            return Integer.MAX_VALUE;
        }

        double totalLength = 0.0;

        Point p0 = points.get(0);
        Point pN = points.get(points.size()-1);
        totalLength += Math.sqrt(Math.pow(pN.x - p0.x, 2) + Math.pow(pN.y - p0.y, 2));

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);

            double distance = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
            totalLength += distance;
        }

        return (int)Math.round(totalLength);
    }
    public static List<Point> sortPoints(List<Point> points, List<Integer> order) {
        List<Point> sortedPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            sortedPoints.add(null);
        }
        for (int i = 0; i < order.size(); i++) {
            int index = order.get(i);
            sortedPoints.set(i, points.get(index));
        }
        return sortedPoints;
    }

}

