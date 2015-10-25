package algorithm.puzzle;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by iHge2k on 15/10/24.
 */
public class Board {

    private int[][] blocks;
    private int N;

    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return this.N;
    }

    // number of blocks out of place
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * N + j + 1)) cnt++;
            }
        }
        return cnt;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * N + j + 1)) {
                    int x = (blocks[i][j] / N);
                    int y = (blocks[i][j] % N - 1);
                    dist += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * N + j + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        Board newBoard = new Board(blocks);
        if (newBoard.blocks[0][0] != 0 && newBoard.blocks[0][1] != 0) {
            swap(newBoard.blocks, 0, 0, 0, 1);
        } else {
            swap(newBoard.blocks, 1, 0, 1, 1);
        }
        return newBoard;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> que = new LinkedList<>();
        int zeroX = -1, zeroY = -1;
        boolean tag = false;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (blocks[i][j] == 0) {
                    tag = true;
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
            if (tag) break;
        }
        int[] dirX = {0, 0, -1, 1};
        int[] dirY = {1, -1, 0, 0};
        for (int k = 0; k < 4; ++k) {
            int x = zeroX + dirX[k];
            int y = zeroY + dirY[k];
            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            Board newBorad = new Board(blocks);
            swap(newBorad.blocks, zeroX, zeroY, x, y);
            que.add(newBorad);
        }
        return que;
    }

    private void swap(int[][] blocks, int preX, int preY, int nowX, int nowY) {
        int tmp = blocks[nowX][nowY];
        blocks[nowX][nowY] = blocks[preX][preY];
        blocks[preX][preY] = tmp;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}
