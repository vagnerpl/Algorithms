package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Author: Vagner Planello
 * Date: 25/jun/2019
 *
 * To execute: java week1.PercolationStats n trials
 * Where n is the size of the matrix and trials is the number of simulations
 */
public class PercolationStats {

    /**
     * results obtained in the trials (openSites/totalSites when percolated)
     */
    private double[] results;

    /**
     * amount of trials to run
     */
    private int trials;

    /**
     * Perform trials independent experiments on an n-by-n grid
     * @param n grid size
     * @param trials number of trials to run
     */
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.trials = trials;

        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                percolation.open(row, col);
            }

            int openSites = percolation.numberOfOpenSites();
            int totalSites = n*n;

            double ratio = (double) openSites/totalSites;

            results[i] = ratio;
        }
    }

    /**
     *
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     *
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        if (trials == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(results);
    }

    /**
     *
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (1.960 * stddev()/Math.sqrt(trials));
    }

    /**
     *
     * @return  high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.960 * stddev()/Math.sqrt(trials));
    }

    /**
     * Run trials iterations of percolation in an n sized grid and print the stats:
     * mean, stddev and 95% confidence interval
     * @param args arg1: size of the grid, arg2: trials to run
     */
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");

    }
}
