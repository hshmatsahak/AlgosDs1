import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] trials;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n must be positive");
        this.trials = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            int numopen = 0;
            while (!grid.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!grid.isOpen(row, col)) {
                    grid.open(row, col);
                    numopen++;
                }
            }
            this.trials[i] = numopen * 1.0 / (n * n);
        }
        mean = StdStats.mean(this.trials);
        stddev = StdStats.stddev(this.trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (1.96 * stddev / Math.sqrt(trials.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96 * stddev / Math.sqrt(trials.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats pr = new PercolationStats(n, t);

        String confidence = pr.confidenceLo() + ", " + pr.confidenceHi();
        System.out.println("mean                    = " + pr.mean);
        System.out.println("stddev                  = " + pr.stddev);
        System.out.println("95% confidence interval = " + confidence);
    }

}
