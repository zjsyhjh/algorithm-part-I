/**
 * Created by iHge2k on 15/10/18.
 */


package algorithm.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private WeightedQuickUnionUF UnionUF;
    private WeightedQuickUnionUF UnionUF_backwash;
    private int N;
    private boolean[][] checkOpen;

    private int rt1;
    private int rt2;

    private int[][] dir = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    public Percolation(int N) {
        this.N = N;
        this.rt1 = N * N;
        this.rt2 = N * N + 1;
        UnionUF = new WeightedQuickUnionUF(N * N + 2);
        UnionUF_backwash = new WeightedQuickUnionUF(N * N + 2);
        checkOpen = new boolean[N + 1][N + 1];

        for (int i = 1; i <= N; ++i) {
            for (int j = 1; j <= N; ++j) checkOpen[i][j] = false;
        }

        for (int i = 0; i < N; ++i) {
            UnionUF.union(rt1, i);
            UnionUF.union(rt2, (N - 1) * N + i);
            UnionUF_backwash.union(rt1, i);
        }
    }


    private boolean checkOk(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            return false;
        }
        return true;
    }

    public void open(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException("out of bound!");
        }

        if (checkOpen[i][j]) {
            return;
        }

        checkOpen[i][j] = true;
        for (int k = 0; k < 4; ++k) {
            int x = i + dir[k][0], y = j + dir[k][1];
            if (checkOk(x, y) && isOpen(x, y)) {
                UnionUF.union((i-1)*N+(j-1), (x-1)*N+(y-1));
                UnionUF_backwash.union((i-1)*N+(j-1), (x-1)*N+(y-1));
            }
        }

    }

    public boolean isOpen(int i, int j) {
        return checkOpen[i][j];
    }

    public boolean isFull(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException("out of bound!");
        }
        return UnionUF_backwash.connected(rt1, (i-1)*N+(j-1)) && isOpen(i, j);
    }

    public boolean percolates() {
        return UnionUF.connected(rt1, rt2);
    }

}
