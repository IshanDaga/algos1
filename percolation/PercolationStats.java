import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int t;
    private final double[] xt;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Bad n/trials values");
        }
        this.t = trials;
        xt = new double[t];
        for (int i = 0; i < t; i++) {
            boolean percolates = false;
            Percolation p = new Percolation(n);
            while (!percolates) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                percolates = p.percolates();
            }
            xt[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xt);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats pStat = new PercolationStats(n, t);
        StdOut.println("mean                     = " + pStat.mean());
        StdOut.println("stddev                   = " + pStat.stddev());
        StdOut.println(
                "95% confidence interval  = [" + pStat.confidenceLo() + ", " + pStat.confidenceHi()
                        + "]");
    }
}
