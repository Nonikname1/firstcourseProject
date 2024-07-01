package logic;


public class Point {
    int x;
    int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int distance(Point pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        return (int) Math.sqrt(px * px + py * py);
    }

}
