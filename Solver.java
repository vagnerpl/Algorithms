import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Vagner Planello
 * Date: 30/jul/2019
 *
 * No parameters required to run from command line (Unit Testing only)
 */
public class Solver {

    /**
     * Solution node
     */
    private Node lastNode;

    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial initial board
     */
    public Solver(Board initial) {

        MinPQ<Node> pq = new MinPQ<>();

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        pq.insert(new Node(initial, 0, null, false));
        pq.insert(new Node(initial.twin(), 0, null, true));

        boolean initialResolved = false;

        while (!initialResolved) {
          lastNode = pq.delMin();

          initialResolved = lastNode.board.isGoal();

          for (Board neighbor: lastNode.board.neighbors()) {
              if ((lastNode.parent == null) || !neighbor.equals(lastNode.parent.board)) {
                  pq.insert(new Node(neighbor, lastNode.steps + 1, lastNode, lastNode.isTwin));
              }
          }

        }

    }

    /**
     *
     * @return is the initial board solvable? (see below)
     */
    public boolean isSolvable() {
        return !lastNode.isTwin;
    }

    /**
     *
     * @return min number of moves to solve initial board
     */
    public int moves() {
        if (lastNode.isTwin)
            return -1;
        return lastNode.steps;
    }

    /**
     *
     * @return sequence of boards in a shortest solution
     */
    public Iterable<Board> solution() {

        if (!isSolvable()) {
            return null;
        }

        Board[] solution = new Board[moves()+1];

        int i = moves();

        Node currNode = lastNode;

        while (currNode != null) {
            solution[i--] = currNode.board;
            currNode = currNode.parent;
        }

        Iterable<Board> boardIterable = new Iterable<Board>() {
            int j = 0;
            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    @Override
                    public boolean hasNext() {
                        return j < solution.length;
                    }

                    @Override
                    public Board next() {
                        try { return solution[j++]; }
                        catch (ArrayIndexOutOfBoundsException e) {
                            throw new NoSuchElementException(e.getMessage());
                        }
                    }
                };
            }
        };

      return boardIterable;
    }

    /**
     * Inner class used to encapsulate the boards into the PQ
     */
    private static class Node implements Comparable<Node> {

        /**
         * Number of steps counted from the initial board
         */
        int steps;

        /**
         * board representation
         */
        Board board;

        /**
         * Calculated priority
         */
        int priority;

        /**
         * Parent node that generated this scenario
         */
        Node parent;

        boolean isTwin;

        /**
         * Constructor for the auxiliar node
         * @param board board representation
         * @param steps number of steps from the initial board
         * @param parent parent board
         */
        Node(Board board, int steps, Node parent, boolean isTwin) {
            this.steps = steps;
            this.board = board;
            this.parent = parent;
            this.isTwin = isTwin;

            priority = board.manhattan() + steps;
        }

        @Override
        public int compareTo(Node that) {
            int priorityCompare = Integer.compare(this.priority, that.priority);

            return priorityCompare;
        }

        @Override
        public boolean equals(Object that) {
            if (that == null) return false;

            if (this.getClass() != that.getClass()) return false;

            if (this == that) return true;

            return this.compareTo((Node) that) == 0;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(this.priority);
        }
    }

    /**
     * test client (see below)
     * @param args not required
     */
    public static void main(String[] args) {
        //Solver solver = new Solver(new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}}));
        //Solver solver = new Solver(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}));
        Solver solver = new Solver(new Board(new int[][]{{8, 4, 7}, {1, 5, 6}, {3, 2, 0}}));

        if (solver.isSolvable()) {

            System.out.println("Moves: " + solver.moves());

            for (Board board: solver.solution()) {
                System.out.println(board);
            }
        } else {
            System.out.println("Unsolvable");
        }

    }

}