package week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Vagner Planello
 * Date: 28/jun/2019
 *
 * No parameter required for running in command line (Unit testing only)
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * Inner representation of the Queue
     */
    private final Deque<Item> deque;

    /**
     * Random Queue iterator implementation
     */
    private class RandomQueueIterator implements Iterator<Item> {

        /**
         * Inner representation fo the queue for iteration
         */
       private final Deque<Item> internalQueue;

        /**
         * Instantiate inner queue and fills it
         */
       public RandomQueueIterator() {
        internalQueue = new Deque<>();
        for (Item item: deque) {
            internalQueue.addFirst(item);
        }

       }

        /**
         * Checks if the Iterable has a next element.
         * @return boolean evaluation
         */
        @Override
        public boolean hasNext() {
            return !internalQueue.isEmpty();
        }

        /**
         * Next element of the iterable
         * @return element
         */
        @Override
        public Item next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int coin = StdRandom.uniform(0, 2);
            if (coin == 0) {
                return internalQueue.removeFirst();
            } else {
                return internalQueue.removeLast();
            }

        }

        /**
         * Not implemented, should not be used.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Construct an empty randomized queue.
     */
    public RandomizedQueue() {
        deque = new Deque<>();
    }

    /**
     * Is the randomized queue empty?
     * @return boolean evaluation
     */
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * Return the number of items on the randomized queue.
     * @return size of the queue
     */
    public int size() {
        return deque.size();
    }

    /**
     * Add the item to the queue
     * @param item item to be added
     */
    public void enqueue(Item item) {
        int coin = StdRandom.uniform(0, 2);
        if (coin == 0) {
            deque.addFirst(item);
        } else {
            deque.addLast(item);
        }
    }

    /**
     * Remove and return a random item.
     * @return random item from the queue
     */
    public Item dequeue() {
        int coin = StdRandom.uniform(0, 2);
        if (coin == 0) {
            return deque.removeFirst();
        } else {
            return deque.removeLast();
        }
    }

    /**
     * Return a random item (but do not remove it).
     * @return random item to inspection
     */
    public Item sample() {
        int coin = StdRandom.uniform(0, 2);
        if (coin == 0) {
            Item item = deque.removeFirst();
            deque.addLast(item);

            return item;
        } else {
            Item item = deque.removeLast();
            deque.addFirst(item);


            return item;
        }
    }

    /**
     * Return an independent iterator over items in random order
     * @return iterator
     */
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    /**
     * unit testing (required).
     * @param args no arqs
     */
    public static void main(String[] args) {

        System.out.println("Sanit Checks");
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");

      for (String item: queue) {
          System.out.print(item);
      }
        System.out.println();

        System.out.println("Sample " + queue.sample());


        for (String item: queue) {
            System.out.print(item);
        }
        System.out.println();

        System.out.println("Is Empty " + queue.isEmpty());
        System.out.println("Size " + queue.size());
        System.out.println("Remove " + queue.dequeue());
        System.out.println("Iterator " + queue.iterator());

/*
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;

        week2.RandomizedQueue<String> queue = new week2.RandomizedQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");

        for (int i = 0; i < 12000; i++) {
            String sample = queue.sample();
            if ("A".equals(sample)) {
                a++;
            } else if ("B".equals(sample)) {
                b++;
            } else if ("C".equals(sample)) {
                c++;
            } else if ("D".equals(sample)) {
                d++;
            }else if ("E".equals(sample)) {
                e++;
            }
        }

        System.out.println(a + " " + b + " " + c + " " + d + " " + e);
*/


    }

}
