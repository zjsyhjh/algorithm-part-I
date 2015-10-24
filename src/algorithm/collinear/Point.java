package algorithm.collinear;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by iHge2k on 15/10/23.
 */
public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        if (y == that.y) return +0;

        double k = (y - that.y) * 1.0 / (x - that.x);
        if (k < 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return k;
    }

    @Override
    public int compareTo(Point that) {
        if (y < that.y) return -1;
        if (y == that.y && x < that.x) return -1;
        if (y == that.y && x == that.x) return 0;
        return +1;
    }


    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double slopeO1 = slopeTo(o1);
            double slopeO2 = slopeTo(o2);
            if (slopeO1 == slopeO2) return 0;
            if (slopeO1 < slopeO2) return -1;
            return 1;
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {

    }

}
