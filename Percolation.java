package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Author: Vagner Planello
 * Date: 25/jun/2019
 *
 * To execute from command line use week1.PercolationStats instead.
 */
public class Percolation {

    /**
     * Constant to identify Top neighbour
     */
    private static final char TOP = 'T';

    /**
     * Constant to identify Bottom neighbour
     */
    private static final char BOTTOM = 'B';

    /**
     * Constant to identify Left neighbour
     */
    private static final char LEFT = 'L';

    /**
     * Constant to identify Right neighbour
     */
    private static final char RIGHT = 'R';

    /**
     * Size of the grid.
     */
    private final int n;

    /**
     * grid to controll which sites are open
     */
    private final boolean[][] isOpen;

    /**
     * count of open sites
     */
    private int openSites = 0;

    /**
     * WeightedQuickUnionUF instance that will process the UnionFind algorithm
     */
    private final WeightedQuickUnionUF unionFind;



    /**
     * Create n-by-n grid, with all sites blocked
     * @param n grid size
     */
    public Percolation(int n) {
        this.n = n;
        isOpen = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF((n*n)+2);
    }

    /**
     * Open site (row, col) if it is not open already
     * @param row x-coordinate of the site to open
     * @param col y-coordinate of the site to open
     */
    public void open(int row, int col) {

        validate(row, col);

        int coordinate = xyTo1D(row, col, this.n);

        if (!isOpen[row - 1][col - 1]) {
            isOpen[row - 1][col - 1] = true;
            openSites++;

            int top = getOpenNeighbour(TOP, row, col);
            if (top >= 0) {
                unionFind.union(coordinate, top);
            }
            int bottom = getOpenNeighbour(BOTTOM, row, col);
            if (bottom >= 0) {
                unionFind.union(coordinate, bottom);
            }
            int left = getOpenNeighbour(LEFT, row, col);
            if (left >= 0) {
                unionFind.union(coordinate, left);
            }
            int right = getOpenNeighbour(RIGHT, row, col);
            if (right >= 0) {
                unionFind.union(coordinate, right);
            }
        }
    }

    /**
     * Is site (row, col) open?
     * @param row x-coordinate of the site to check
     * @param col y-coordinate of the site to check
     * @return boolean status of the site (open == true, closed ==false)
     */
    public boolean isOpen(int row, int col) {

        validate(row, col);

        return isOpen[row-1][col-1];
    }

    /**
     * Is site (row, col) full?
     * @param row x-coordinate of the site to check
     * @param col y-coordinate of the site to check
     * @return boolean status of the site (full == true, not full ==false)
     */
    public boolean isFull(int row, int col) {

        validate(row, col);

        return unionFind.connected(0, xyTo1D(row, col, this.n));
    }

    /**
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * does the system percolate
     * @return boolean answer if system percolates
     */

    public boolean percolates() {
        return unionFind.connected(0, (n*n)+1);
    }

    /**
     * Convert bi-dimensional coordinates to uni-dimensional coordinates
     * @param row x-coordinate
     * @param col y-coordinate
     * @param n matrix size
     * @return uni-dimensional coordinate
     */
    private int xyTo1D(int row, int col, int n) {
        return col + ((row -1) * n);
    }

    /**
     * Verify that coordinates are valid
     * @param row x-coordinate
     * @param col y-coordinate
     */
    private void validate(int row, int col) {
        if (row < 1 || row > this.n)
            throw new IllegalArgumentException();
        if (col < 1 || col > this.n)
            throw new IllegalArgumentException();
    }

    /**
     * Get Opened sites that are neighbour of the specified site
     * @param position Neighbour position to check (TOP, BOTTOM, LEFT, RIGHT)
     * @param row x-coordinate
     * @param col y-coordinate
     * @return uni-dimensional coordinate of the neighbour if it is open or -1 if it doesn't exist or is not open
     */
    private int getOpenNeighbour(char position, int row, int col) {
        switch (position) {
            case TOP:
                if (1 == row) {
                    return 0;
                } else if (isOpen(row-1, col)) {
                    return xyTo1D(row-1, col, this.n);
                }
                return -1;
            case BOTTOM:
                if (this.n == row) {
                    return (n * n) + 1;
                } else if (isOpen(row+1, col)) {
                    return xyTo1D(row+1, col, this.n);
                }
                return -1;
            case LEFT:
                if (1 == col) {
                    return -1;
                } else if (isOpen(row, col-1)) {
                    return xyTo1D(row, col -1, this.n);
                }
                return -1;
            case RIGHT:
                if (this.n == col) {
                    return -1;
                } else if (isOpen(row, col+1)) {
                    return xyTo1D(row, col+1, this.n);
                }
                return -1;
            default:
                return -1;
        }
    }

    /**
     * Internal tests only
     * @param args none
     */
    public static void main(String[] args) {


    }
}
