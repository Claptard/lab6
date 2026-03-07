package simulator;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class EventQueue {
    private PriorityQueue<Event> queue;


    /**
     * Why use this damn queue thing?..
     * helps with out of sequence events, FIFO is abit sensitive
     * and would not work for events.
     * Example, 2 events can be out of order, slow carwash finishes after fast but
     * the fast car entered first..... so it gets out of order, not great and can be dealt with
     * but this is a simpler appoach where we can just validate with time and event.
     */
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

    public Event peek(){
        return queue.peek();
    }
     */

    /**
     * Retrives Queue item and removes it from the queue
     * if list empty returns null
     *
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
