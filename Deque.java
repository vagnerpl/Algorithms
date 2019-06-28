package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Vagner Planello
 * Date: 28/jun/2019
 *
 * No parameters required to run from command line (Unit Testing only)
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * Internal references for first and last elements.
     */
    private Node first, last;

    /**
     * Size of the week2.Deque.
     */
    private int size;

    /**
     * Definition of an element in the week2.Deque.
     */
    private class Node {
        /**
         * Information.
         */
        Item item;

        /**
         * Reference to the previous node.
         */
        Node previous;

        /**
         * Reference to the next node.
         */
        Node next;
    }

    /**
     * First to last Iterator implementation
     */
    private class DequeFrontToBackIterator implements Iterator<Item> {

        /**
         * Reference to the current element, starting at first.
         */
        private Node current = first;

        /**
         * Checks if the Iterable has a next element.
         * @return boolean evaluation
         */
        @Override
        public boolean hasNext() {
            return current != null;
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

            Item item = current.item;
            current = current.previous;
            return item;
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
     * Construct an empty deque.
     */

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Is the deque empty?
     * @return boolean evaluation
     */

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the deque.
     * @return size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * Add the item to the front.
     * @param item item to be added
     */
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

       Node oldFirst = first;
       first = new Node();
       first.item = item;
       first.next = null;
       first.previous = oldFirst;
       if (isEmpty())
            last = first;
       else
           oldFirst.next = first;

        size++;
    }

    /**
     * Add the item to the back.
     * @param item item to be added
     */
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = oldlast;
        last.previous = null;
        if (isEmpty())
            first = last;
        else
            oldlast.previous = last;

        size++;
    }

    /**
     * Remove and return the item from the front.
     * @return removed item
     */
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.previous;
        if (first != null)
            first.next = null;
        size--;
        if (isEmpty())
            last = null;

        return item;
    }

    /**
     * Remove and return the item from the back.
     * @return removed item
     */
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item  = last.item;
        last = last.next;
        if (last != null)
            last.previous = null;
        size--;
        if (isEmpty())
            first = null;


        return item;
    }

    /**
     * Return an iterator over items in order from front to back.
     * @return iterator for the queue
     */
    public Iterator<Item> iterator() {
        return new DequeFrontToBackIterator();
    }

    /**
     * Unit testing (required)
     * @param args not required
     */
    public static void main(String[] args) {

        System.out.println("Sanit Checks");
        Deque<String> deque = new Deque<String>();
        deque.addFirst("First");
        deque.addLast("Second");
        System.out.println("Is Empty " + deque.isEmpty());
        System.out.println("Size " + deque.size());
        System.out.println("Remove First " + deque.removeFirst());
        System.out.println("Remove Last " + deque.removeLast());
        System.out.println("Iterator " + deque.iterator());

        System.out.println("Test Iterator");
        deque.addLast("First");
        deque.addLast("Second");
        deque.addLast("Third");

        for (String item : deque) {
            System.out.println(item);
        }

        deque = new Deque<String>();

        System.out.println("Queue Behaviour");
        deque.addLast("First");
        deque.addLast("Second");
        deque.addLast("Third");

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

        System.out.println("Stack Behaviour");
        deque.addLast("First");
        deque.addLast("Second");
        deque.addLast("Third");
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());

        System.out.println("Reverse Queue Behaviour");
        deque.addFirst("First");
        deque.addFirst("Second");
        deque.addFirst("Third");

        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());

        System.out.println("Reverse Stack Behaviour");
        deque.addFirst("First");
        deque.addFirst("Second");
        deque.addFirst("Third");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

/*
        week2.Deque<Integer> deque = new week2.Deque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println();

        for (Integer entry: deque) {
            System.out.println(entry);
        }
        */
    }

}