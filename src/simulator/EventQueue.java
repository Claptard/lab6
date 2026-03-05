package simulator;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class EventQueue {
    private PriorityQueue<Event> queue;

    public EventQueue() {
        queue = new PriorityQueue<>(Comparator.comparing(Event::getTime));
    }

    /**
     * adds event to the queue
     *
     * @param event
     */
    public void add(Event event){
        queue.add(event);
    }

    /**#############   DEBUG  ############
     * **debug inorder to check queue
     * #######################################
     * @return
     */
    public Event peek(){
        return queue.peek();
    }

    /**
     * Retrives Queue item and removes it from the queue
     * if list empty returns null
     *
     * Shouuuuld not crash right?
     * @return
     */
    public Event next(){
        return queue.poll();
    }

    /**
     * Simple check to see if queue is empty,
     *
     * @return returns true if empty
     */
    public boolean isEmpty(){
        return queue.isEmpty();
    }

}
