package week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Author: Vagner Planello
 * Date: 28/jun/2019
 *
 * Command line parameters:
 * K: integer number of elements to return
 * A series of strings to run the permutation
 */
public class Permutation {

    /**
     *
     * @param args first one covertible to integer, next ones treated as strings
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());

        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }

}
