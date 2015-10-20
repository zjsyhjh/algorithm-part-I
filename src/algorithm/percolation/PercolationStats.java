package algorithm.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by iHge2k on 15/10/19.
 */

public class PercolationStats {

    private double resMean;
    private double resStddev;
    private double[] recT;

    public PercolationStats(int N, int T) {
        recT = new double[T];

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        int cnt = 0;
        while ( cnt < T) {
            Percolation percolation = new Percolation(N);
            boolean[][] mark = new boolean[N + 1][N + 1];
            int count = 0;
            while (true) {
                count++;
                int x = StdRandom.uniform(N) + 1;
                int y = StdRandom.uniform(N) + 1;

                StdOut.println(x + " " + y);
                while (mark[x][y]) {
                    x = StdRandom.uniform(N) + 1;
                    y = StdRandom.uniform(N) + 1;
                }
                mark[x][y] = true;
                percolation.open(x, y);

                if (percolation.percolates()) {
                    recT[cnt] = count * 1.0 / (N * 1.0 * N);
                    break;
                }
            }
            cnt++;
        }
        resMean = StdStats.mean(recT);
        resStddev = StdStats.stddev(recT);
    }

    public double mean() {
        return resMean;
    }

    public double stddev() {
        return resStddev;
    }

    public double confidenceLo() {
        return resMean - 1.96 * resStddev / Math.sqrt(recT.length);
    }

    public double confidentceHi() {
        return resMean + 1.96 * resStddev / Math.sqrt(recT.length);
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% confidence interval " + percolationStats.confidenceLo() + ", " + percolationStats.confidentceHi());
    }

}
