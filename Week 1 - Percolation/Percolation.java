/* *****************************************************************************
 *  Name:              Ishan Daga
 * Last Modified:      2/3/21
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid; // in linear representation node = row*n+col
    private WeightedQuickUnionUF WQUF; // elements from 0->n-1 so code must be normalised as row,col values are from 1->n
    private int n; //

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        this.n = n;
        if(n<=0){
             throw new IllegalArgumentException("impermissible n value passed");
        }
        for(int i =0; i<n;i++){
            for (int j =0; j<n; j++){
                grid[i][j] = 1; // initialised and blocked
            }
        }
        WeightedQuickUnionUF WQUF = new WeightedQuickUnionUF((n*n)+2); // virtual top and bottom node // n*n, n*n+1 are top and bottom nodes
        for(int i=0; i<n; i++){
            WQUF.union(n*n, i); // top row common node = n*n
            WQUF.union(n*n+1, n*(n-1)+i); // bottom row common node = n*n+1, bottom row = n*(n-1)+i
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        grid[row-1][col-1] = 0; // normalise from 1->n to 0->n-1 representation
        if(row!=1 && isOpen(row-1, col)){ // isOpen() is already normalised
            WQUF.union(((row-1)*n)+(col-1), ((row-2)*n)+(col-1));
        }
        if(col!=1 && isOpen(row, col-1)){
            WQUF.union(((row-1)*n)+(col-1), ((row-1)*n)+(col-2));
        }
        if (row!=n && isOpen(row + 1, col)) {
            WQUF.union(((row - 1) * n) + (col - 1), ((row) * n) + (col - 1));
        }
        if (col!=n && isOpen(row, col + 1)) {
            WQUF.union(((row - 1) * n) + (col - 1), ((row - 1) * n) + (col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){// normalise from 1->n to 0->n-1 representation
        if(grid[row-1][col-1] == 0){return true;}
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        return WQUF.connected(n*n, ((row-1)*n)+(col-1));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int count = 0;
        for(int i =0; i<n;i++){
            for (int j =0; j<n; j++){
                if(grid[i][j] == 0){
                    count++;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        return  WQUF.connected(n*n, n*n+1); // top common node and bottom common node
    }

    // test client (optional)
    public static void main(String[] args){

    }
}
