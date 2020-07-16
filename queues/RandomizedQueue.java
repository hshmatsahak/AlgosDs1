import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n = 0;
    private Item [] q;

    // construct an empty randomized queue
    public RandomizedQueue(){
        q = (Item []) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return n;
    }

    private void resize(int capacity){
        Item [] copy = (Item []) new Object[capacity];
        for (int i=0; i<n; i++){
            copy[i] = q[i];
        }
        q = copy;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length) resize(2*q.length);
        q[n++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()) throw new NoSuchElementException();
        if (n == q.length/4) resize(q.length/2);
        int index = StdRandom.uniform(n);
        Item remove = q[index];
        q[index] = q[--n];
        q[n] = null;
        return remove;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty()) throw new NoSuchElementException();
        return q[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new queueIterator();
    }

    private class queueIterator implements Iterator<Item>{
        private int current = 0;
        private int [] randomindices;

        public queueIterator(){
            randomindices = new int[n];
            for (int i = 0; i < n; i++){
                randomindices[i] = i;
            }
            StdRandom.shuffle(randomindices);
        }

        public boolean hasNext(){
            return current < n;
        }

        public Item next(){
            if (!hasNext())
                throw new NoSuchElementException();
            return q[randomindices[current++]];
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.size());
        for (Integer i : queue) {
            System.out.println(i);
        }
        System.out.println("sample:" + queue.sample());
        System.out.println("dequeue");
        while (!queue.isEmpty()) System.out.println(queue.dequeue());
        System.out.println(queue.size());
    }

}