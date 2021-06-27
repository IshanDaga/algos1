/* *****************************************************************************
 *  Name:              Ishan Daga
 * Last Modified:      2/3/21
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
    private Percolation per;
    private int T;
    private double xt[];
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        this.per = new Percolation(n);
        this.T = trials;
        for(int i = 0; i<trials; i++){
            boolean percolated = false;
            do {
                int sample1 = StdRandom.uniform(1, n);
                int sample2 = StdRandom.uniform(1, n);
                if(!per.isOpen(sample1, sample2)) {
                    per.open(sample1, sample2);
                }
                percolated = per.percolates();
            }while(!percolated);
            xt[i] = per.numberOfOpenSites()/(n*n);
        }
    }
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(xt);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(xt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - ((1.96*stddev())/Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + ((1.96*stddev())/Math.sqrt(T));
    }

    // test client (see below)
    public static void main(String[] args){
        int n = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats PStat = new PercolationStats(n, T);
        StdOut.println("mean                    = "+PStat.mean());
        StdOut.println("stddev                  = "+PStat.stddev());
        StdOut.println("95% confidence interval = ["+PStat.confidenceLo()+", "+PStat.confidenceHi()+"]");
    }
}