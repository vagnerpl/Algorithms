import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Author: Vagner Planello
 * Date: 30/jul/2019
 *
 * No parameters required to run from command line (Unit Testing only)
 */
public class Board {

    /**
     * Dimension of the board
     */
    private final int n;

    /**
     * Manhattan index for the board
     */
    private int manhattan = -1;

    /**
     * Hamming index for the board
     */
    private int hamming = -1;

    /**
     * The board array
     */
    private final int[][] tiles;

    /**
     * X-position of the empty cell
     */
    private int emptyRow;

    /**
     * y-position of the empty cell
     */
    private int emptyCol;

    /**
     * create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     * @param tiles
     */
    public Board(int[][] tiles) {

        n = tiles.length;

        assert (n >= 2);
        assert (n < 128);

        for (int i = 0; i < tiles.length; i++) {
            assert (tiles[i].length == n);
            for (int j = 0; j < tiles.length; j++) {
                assert (tiles[i][j] < (n * n));
                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }

        this.tiles = Arrays.copyOf(tiles, tiles.length);

    }

    /**
     * string representation of this board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append('\n');
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                sb.append(" " + tiles[i][j]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     *
     * @return board dimension n
     */
    public int dimension() {
        return n;
    }

    /**
     *
     * @return number of tiles out of place
     */
    public int hamming() {
        if (hamming >= 0)
            return hamming;

        hamming = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != (n * i) + (j % n) + 1) {
                    hamming++;
                   // System.out.println("Hamming " + tiles[i][j]);
                }
            }
        }

        return hamming;
    }

    /**
     *
     * @return sum of Manhattan distances between tiles and goal
     */
    public int manhattan() {

        if (manhattan >= 0)
            return manhattan;

        manhattan = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
               if (tiles[i][j] > 0) {
                   int row = (tiles[i][j]-1) / n;
                   int col = (tiles[i][j]-1) % n;

                   int manhattanRow = (i - row) > 0 ? (i - row) : (row - i);
                   int manhattanCol = (j - col) > 0 ? (j - col) : (col - j);

                   //System.out.println("Manhattan " + tiles[i][j] + " = " + (manhattanRow + manhattanCol));

                   manhattan = manhattan + manhattanRow + manhattanCol;
               }
            }
        }

        return manhattan;
    }

    /**
     *
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        return this.equals(getGoalBoard(this.n));
    }

    /**
     *
     * @param y that board
     * @return does this board equal y?
     */
    public boolean equals(Object y) {
        if (this == y) return true;

        if (y == null) return false;

        if (this.getClass() != y.getClass()) return false;

        Board that = (Board) y;

        if (this.n != that.n) return false;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }

        return true;
    }

    /**
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        Board neighbour = north();
        if (neighbour != null) {
            neighbors.add(neighbour);
        }

        neighbour = south();
        if (neighbour != null) {
            neighbors.add(neighbour);
        }

        neighbour = west();
        if (neighbour != null) {
            neighbors.add(neighbour);
        }

        neighbour = east();
        if (neighbour != null) {
            neighbors.add(neighbour);
        }

        return neighbors;
    }

    /**
     *
     * @return north neighbor or null if on top row
     */
    private Board north() {
        if (emptyRow == 0)
            return null;

        int[][] clonedData = cloneData();

        int temp = clonedData[emptyRow-1][emptyCol];
        clonedData[emptyRow-1][emptyCol] = 0;
        clonedData[emptyRow][emptyCol] = temp;

        return new Board(clonedData);
    }

    /**
     *
     * @return south neighbor or null if on bottom row
     */
    private Board south() {
        if (emptyRow == (n-1))
            return null;

        int[][] clonedData = cloneData();

        int temp = clonedData[emptyRow+1][emptyCol];
        clonedData[emptyRow+1][emptyCol] = 0;
        clonedData[emptyRow][emptyCol] = temp;

        return new Board(clonedData);
    }

    /**
     *
     * @return west neighbor or null if on first column
     */
    private Board west() {
        if (emptyCol == 0)
            return null;

        int[][] clonedData = cloneData();

        int temp = clonedData[emptyRow][emptyCol-1];
        clonedData[emptyRow][emptyCol-1] = 0;
        clonedData[emptyRow][emptyCol] = temp;

        return new Board(clonedData);
    }

    /**
     *
     * @return east neighbor or null if on last column
     */
    private Board east() {
        if (emptyCol == (n-1))
            return null;

        int[][] clonedData = cloneData();

        int temp = clonedData[emptyRow][emptyCol+1];
        clonedData[emptyRow][emptyCol+1] = 0;
        clonedData[emptyRow][emptyCol] = temp;

        return new Board(clonedData);
    }

    /**
     *
     * @return a board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        int[][] clonedData = cloneData();

        if (clonedData[0][0] != 0 && clonedData[0][1] != 0) {
            clonedData[0][0] = tiles[0][1];
            clonedData[0][1] = tiles[0][0];
        } else {
            clonedData[1][0] = tiles[1][1];
            clonedData[1][1] = tiles[1][0];
        }

        return new Board(clonedData);
    }

    /**
     *
     * @return cloned array of data
     */
    private int[][] cloneData() {
        int[][] clonedData = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clonedData[i][j] = this.tiles[i][j];
            }

        }
        return clonedData;
    }

    /**
     *
     * @param n size of the board
     * @return the goal bord for the n size
     */
    private static Board getGoalBoard(int n) {

        int[][] goalBoardArray = new int[n][n];

        int current = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goalBoardArray[i][j] = current++;
            }
        }

        goalBoardArray[n-1][n-1] = 0;

        return new Board(goalBoardArray);
    }

    /**
     * unit testing (not graded)
     * @param args - none
     */
    public static void main(String[] args) {


        Board goalBoard = getGoalBoard(3);
        System.out.println(goalBoard);


        System.out.println(">Hamming, Manhattan and is Goal for sample board");
        Board randomBoard = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(randomBoard.hamming());
        System.out.println(randomBoard.manhattan());
        System.out.println(randomBoard.isGoal());
        System.out.println();

        System.out.println(">Is goal for goal, sample and other goal board");
        System.out.println(goalBoard.isGoal());

        System.out.println(goalBoard.equals(randomBoard));

        Board otherGoalBoard = getGoalBoard(3);
        System.out.println(goalBoard.equals(otherGoalBoard));

        System.out.println(">Neighbours");
        System.out.println(">>North");
        Board north = randomBoard.north();
        System.out.println(north);

        System.out.println(">>South");
        Board south = randomBoard.south();
        System.out.println(south);

        System.out.println(">>West");
        Board west = randomBoard.west();
        System.out.println(west);

        System.out.println(">>East");
        Board east = randomBoard.east();
        System.out.println(east);

        System.out.println(">>East twin");
        Board twin = east.twin();
        System.out.println(twin);

    }

}