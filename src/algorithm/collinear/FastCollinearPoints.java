package algorithm.collinear;

import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by iHge2k on 15/10/23.
 */
public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        int N = points.length;
        Arrays.sort(points);
        for (int i = 0; i < N; ++i) {
            Point p = points[i];
            Point[] q = new Point[N - 1];
            for (int j = 0; j < N; ++j) {
                if (j < i) q[j] = points[j];
                if (j > i) q[j - 1] = points[j];
            }
            Arrays.sort(q, p.slopeOrder());
            int count = 0, index = 0;
            double tmpSlope = p.slopeTo(q[0]);
            for (int j = 0; j < q.length; ++j) {
                if (Double.compare(p.slopeTo(q[j]), tmpSlope) == 0) {
                    count++;
                    continue;
                } else if (count >= 3) {
                    if (q[index].compareTo(p) >= 0) {
                        //StdOut.println(p + " -> " + q[j - 1]);
                        p.drawTo(q[j - 1]);
                        lineSegments.add(new LineSegment(p, q[j - 1]));
                    }
                }
                count = 1;
                index = j;
                tmpSlope = p.slopeTo(q[j]);
            }
            if (count >= 3) {
                if (q[index].compareTo(p) >= 0) {
                    //StdOut.println(p + " -> " + q[q.length - 1]);
                    p.drawTo(q[q.length - 1]);
                    lineSegments.add(new LineSegment(p, q[q.length - 1]));
                }
            }
        }
        StdDraw.show(0);
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return (LineSegment[]) lineSegments.toArray();
    }
}
