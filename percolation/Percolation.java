/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] open;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF fullgrid;
    private int virtualbottom;
    private int virtualtop;
    private int n;
    private int numopen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");
        this.n = n;
        open = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                open[i][j] = false;
            }
        }
        int numcells = n * n;
        grid = new WeightedQuickUnionUF(numcells + 2);
        fullgrid = new WeightedQuickUnionUF(numcells + 1);
        virtualbottom = numcells + 1;
        virtualtop = numcells;
        numopen = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        if (!isOpen(row, col)) {
            open[row - 1][col - 1] = true;
            numopen++;
        }
        if (row < n && open[row][col - 1]) {
            grid.union((row - 1) * n + col - 1, row * n + col - 1);
            fullgrid.union((row - 1) * n + col - 1, row * n + col - 1);
        }
        if (row > 1 && open[row - 2][col - 1]) {
            grid.union((row - 1) * n + col - 1, (row - 2) * n + col - 1);
            fullgrid.union((row - 1) * n + col - 1, (row - 2) * n + col - 1);
        }
        if (col < n && open[row - 1][col]) {
            grid.union((row - 1) * n + col - 1, (row - 1) * n + col);
            fullgrid.union((row - 1) * n + col - 1, (row - 1) * n + col);
        }
        if (col > 1 && open[row - 1][col - 2]) {
            grid.union((row - 1) * n + col - 1, (row - 1) * n + col - 2);
            fullgrid.union((row - 1) * n + col - 1, (row - 1) * n + col - 2);
        }
        if (row == 1) {
            grid.union(col - 1, virtualtop);
            fullgrid.union(col - 1, virtualtop);
        }
        if (row == n)
            grid.union((n - 1) * n + col - 1, virtualbottom);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        if (!isOpen(row, col))
            return false;
        return fullgrid.find(virtualtop) == fullgrid.find((row - 1) * n + col - 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numopen;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(virtualtop) == grid.find(virtualbottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
