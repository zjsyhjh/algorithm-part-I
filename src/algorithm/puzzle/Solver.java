package algorithm.puzzle;

import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by iHge2k on 15/10/24.
 */
public class Solver {

    private Node target = null;
    private Stack<Board> s = null;

    public Solver(Board initial) {
        MinPQ<Node> minPQ = new MinPQ<>(new NodeComparator());
        MinPQ<Node> twinPQ = new MinPQ<>(new NodeComparator());
        minPQ.insert(new Node(initial, 0, null));
        twinPQ.insert(new Node(initial.twin(), 0, null));
        boolean isSolvable = true;
        while (true) {
            Node p = minPQ.delMin();
            if (p.board.isGoal()) {
                target = p;
                break;
            }

            Iterable<Board> iter = p.board.neighbors();
            for (Board board : iter) {
                if (p.pre == null) {
                    minPQ.insert(new Node(board, p.moves + 1, p));
                } else {
                    if (!board.equals(p.pre.board)) {
                        minPQ.insert(new Node(board, p.moves +1, p));
                    }
                }
            }

            Node q = twinPQ.delMin();
            if (q.board.isGoal()) {
                isSolvable = false;
                break;
            }

            iter = q.board.neighbors();
            for (Board board : iter) {
                if (q.pre == null) {
                    twinPQ.insert(new Node(board, q.moves + 1, q));
                } else {
                    if (!board.equals(q.pre.board)) {
                        twinPQ.insert(new Node(board, q.moves + 1, q));
                    }
                }
            }
        }

        if (isSolvable) {
            Node cur = target;
            s = new Stack<>();
            while (cur != null) {
                s.push(cur.board);
                cur = cur.pre;
            }
        }
    }

    public boolean isSolvable() {
        return (target != null);
    }

    public int moves() {
        return target.moves;
    }

    public Iterable<Board> solution() {
        return s;
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.equals(o2)) return 0;
            if (o1.board.manhattan() + o1.moves == o2.board.manhattan() + o2.moves) {
                return (o1.moves < o2.moves ? -1 : 1);
            }
            return ((o1.board.manhattan() + o1.moves < o2.board.manhattan() + o2.moves) ? -1: 1);
        }
    }

    private class Node {
        Board board;
        int moves;
        Node pre;
        public Node(Board board, int moves, Node pre) {
            this.board = board;
            this.moves = moves;
            this.pre = pre;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Node that = (Node) o;
            if (this.board != null && !this.board.equals(that.board)) return false;
            if (this.moves != that.moves) return false;
            return true;
        }
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible!");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }

    }
}
