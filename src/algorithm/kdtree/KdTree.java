package algorithm.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

/**
 * Created by iHge2k on 15/10/26.
 */
public class KdTree {

    private static class KDNode {
        private KDNode lchild;
        private KDNode rchild;
        private final boolean isVertical;
        private final double x;
        private final double y;

        public KDNode(KDNode lchild, KDNode rchild, double x, double y, boolean isVertical) {
            this.lchild = lchild;
            this.rchild = rchild;
            this.x = x;
            this.y = y;
            this.isVertical = isVertical;
        }
    }

    private static final RectHV rectHV = new RectHV(0, 0, 1, 1);
    private KDNode root;
    private int size;

    public KdTree() {
        this.size = 0;
        this.root = null;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(root, p, true);
    }

    private KDNode insert(KDNode node, Point2D p, boolean isVertical) {
        if (node == null) {
            size++;
            return new KDNode(null, null, p.x(), p.y(), isVertical);
        }

        //already exists
        if (node.x == p.x() && node.y == p.y()) {
            return node;
        }

        if ((isVertical && p.x() < node.x) || (!isVertical && p.y() < node.y)) {
            node.lchild = insert(node.lchild, p, !isVertical);
        } else {
            node.rchild = insert(node.rchild, p, !isVertical);
        }

        return node;

    }

    public boolean contains(Point2D p) {
        return contains(root, p);
    }

    private boolean contains(KDNode node, Point2D p) {
        if (node == null) {
            return false;
        }

        if (p.x() == node.x && p.y() == node.y) {
            return true;
        }

        if ((node.isVertical && p.x() < node.x) || (!node.isVertical && p.y() < node.y)) {
            return contains(node.lchild, p);
        } else {
            return contains(node.rchild, p);
        }
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> treeSet = new TreeSet<>();
        range(root, rectHV, treeSet, rect);
        return treeSet;
    }

    private void range(KDNode node, RectHV rectHV, TreeSet<Point2D> treeSet, RectHV rect) {
        if (node == null) {
            return;
        }

        if (rect.intersects(rectHV)) {
            Point2D p = new Point2D(node.x, node.y);
            if (rect.contains(p)) {
                treeSet.add(p);
            }
            range(node.lchild, leftRect(rectHV, node), treeSet, rect);
            range(node.rchild, rightRect(rectHV, node), treeSet, rect);
        }
    }

    private RectHV leftRect(RectHV rect, KDNode node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
        }
    }

    private RectHV rightRect(RectHV rect, KDNode node) {
        if (node.isVertical) {
            return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
        }
    }

    public Point2D nearest(Point2D p) {
        return nearest(root, rectHV, p, null);
    }

    private Point2D nearest(KDNode node, RectHV rect, Point2D p, Point2D ans) {
        if (node == null) {
            return ans;
        }

        double dist1 = 0, dist2 = 0;
        RectHV leftRect = null, rightRect = null;
        Point2D res = ans;
        if (res != null) {
            dist1 = p.distanceSquaredTo(res);
            dist2 = rect.distanceSquaredTo(p);
        }

        if (res == null || dist1 > dist2) {
            Point2D now = new Point2D(node.x, node.y);
            if (res == null || dist1 > p.distanceSquaredTo(now)) {
                res = now;
            }

            if (node.isVertical) {
                leftRect = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
                rightRect = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());

                if (p.x() < node.x) {
                    res = nearest(node.lchild, leftRect, p, res);
                    res = nearest(node.rchild, rect, p, res);
                } else {
                    res = nearest(node.rchild, rightRect, p, res);
                    res = nearest(node.lchild,leftRect, p, res);
                }
            } else {
                leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                rightRect = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
                if (p.y() < node.y) {
                    res = nearest(node.lchild, leftRect, p, res);
                    res = nearest(node.rchild, rightRect, p, res);
                } else {
                    res = nearest(node.rchild, rightRect, p, res);
                    res = nearest(node.lchild, leftRect, p, res);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }

}
