// prolly inefficient af, check random github guy or smth
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size = 0;

    private class Node<Item>{
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    // construct an empty deque
    public Deque(){
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return first == null;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null) throw new IllegalArgumentException("input must be not null");
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst != null)
            oldfirst.prev= first;
        else
            last = first;
        size+=1;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null) throw new IllegalArgumentException("input must be not null");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (first == null)
            first = last;
        else
            oldlast.next = last;
        size+=1;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item oldfirst = first.item;
        first = first.next;
        if (first == null)
            last = null;
        else
            first.prev = null;
        size-=1;
        return oldfirst;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item oldlast = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;
        size -= 1;
        return oldlast;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node<Item> current = first;

        public boolean hasNext(){
            return current != null;
        }

        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.isEmpty();
        deque.isEmpty();
        deque.removeFirst();
        deque.addFirst(5);
        deque.removeFirst();
        System.out.println(deque.size());
    }
}