package algorithm.kdtree;

import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Deque;
import java.util.LinkedList;


/**
 * Created by iHge2k on 15/10/26.
 */
public class PointSET {

    private SET<Point2D> st;

    public PointSET() {
        st = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return st.isEmpty();
    }

    public int size() {
        return st.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (!st.contains(p)) {
            st.add(p);
        }
    }

    public boolean contains(Point2D p) {
        return st.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D p : st) {
            StdDraw.point(p.x(), p.y());
        }
        StdDraw.show(0);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Deque<Point2D> deq = new LinkedList<>();
        for (Point2D p : st) {
            if (rect.contains(p)) deq.add(p);
        }
        return deq;
    }

    public Point2D nearest(Point2D p) {
        Point2D q = null;
        double minDist = Double.MAX_VALUE;
        for (Point2D that : st) {
            if (!that.equals(p) && p.distanceTo(that) < minDist) {
                minDist = p.distanceTo(that);
                q = that;
            }
        }
        return q;
    }

}
