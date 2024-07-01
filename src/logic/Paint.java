package logic;

import java.awt.*;
import java.util.List;

public class Paint {
    public static void paint(Graphics2D g, List<Point> points, int w, int h){
        double minX, minY, maxX, maxY;
        minX = maxX = points.get(0).x;
        minY = maxY = points.get(0).y;
        for (Point point : points) {
            minX = Math.min(minX, point.x);
            maxX = Math.max(maxX, point.x);
            minY = Math.min(minY, point.y);
            maxY = Math.max(maxY, point.y);
        }

        final int b = 20;
        double xk = (w - 2 * b) / (maxX - minX);
        double yk = (h - 2 * b) / (maxY - minY);
        double k =  Math.min(xk, yk);

        if (points.size() > 1) {
            Point p0 = points.get(0);
            Point pN = points.get(points.size()-1);
            int x0 = (int) Math.round(b + (p0.getX() - minX) * k);
            int y0 = (int) Math.round(b + (p0.getY() - minY) * k);
            int xN = (int) Math.round(b + (pN.getX() - minX) * k);
            int yN = (int) Math.round(b + (pN.getY() - minY) * k);
            g.drawLine(x0, y0, xN, yN);
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                int x1 = (int) Math.round(b + (p1.getX() - minX) * k);
                int y1 = (int) Math.round(b + (p1.getY() - minY) * k);
                int x2 = (int) Math.round(b + (p2.getX() - minX) * k);
                int y2 = (int) Math.round(b + (p2.getY() - minY) * k);
                g.drawLine(x1, y1, x2, y2);
            }
        }
        for (Point point : points) {
            int x = (int) Math.round(b + (point.getX() - minX) * k);
            int y = (int) Math.round(b + (point.getY() - minY) * k);
            // int size = Math.min((int) Math.round(2 * k), 7);
            int size = 2;
            g.fillOval(x - size, y - size, 2 * size + 1, 2 * size + 1);
        }
    }
}
;