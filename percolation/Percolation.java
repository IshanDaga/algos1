import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid; // linearized representation of n*n grid as row*n+col
    private final WeightedQuickUnionUF wQUF;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be >=0");
        }
        this.n = n;
        grid = new boolean[n * n + 1]; // 0 is closed, 1 is open & first index of grid is not used
        wQUF = new WeightedQuickUnionUF(n * n + 2); // objects from 0 -> n*n+1
    }

    private int toIndex(int row, int col) {
        if (row < 0 || col < 0 || row > n || col > n) { // range for row, col is [1,n]
            throw new IllegalArgumentException("bad row/col values");
        }
        return row + n * (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[toIndex(row, col)] = true;
            if (row < n && isOpen(row + 1, col)) {
                wQUF.union(toIndex(row, col), toIndex(row + 1, col));
            }
            if (col < n && isOpen(row, col + 1)) {
                wQUF.union(toIndex(row, col), toIndex(row, col + 1));
            }
            if (row > 1 && isOpen(row - 1, col)) {
                wQUF.union(toIndex(row, col), toIndex(row - 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                wQUF.union(toIndex(row, col), toIndex(row, col - 1));
            }
            if (row == 1) {
                wQUF.union(0, toIndex(row, col)); // add to virtual top
            }
            if (row == n) {
                wQUF.union(n * n + 1, toIndex(row, col)); // add to virtual bottom
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (grid[toIndex(row, col)]) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (wQUF.find(toIndex(row, col)) == wQUF.find(0) && isOpen(row,
                                                                       col)); // top common node to queried point
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (isOpen(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return (wQUF.find(0) == wQUF.find(n * n + 1));
    }

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        if (perc.percolates()) {
            StdOut.println("The system percolates");
        }
        else {
            StdOut.println("System does not percolate");
        }
    }
}
