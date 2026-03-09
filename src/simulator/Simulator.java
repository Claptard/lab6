package simulator;

/**
 * Om något är wierd ligger det med största sanolikhet i grund här...
 *
 */
/**
 * @author Oscar Algotsson, Oliver Horvath
 */
public class Simulator {
    /** the Queue for future events being performed */
    private EventQueue eventQueue;
    /** Current state */
    private State state;

    /**
     *  Creates the simulator with a given state, hence it can then be
     * @param eventQueue    Event que, should start with 1 element due to it then "having started"...
     * @param state         Starting value/state for the simulator
     */
    public Simulator(EventQueue eventQueue, State state){
        this.eventQueue = eventQueue;
        this.state = state;
    }

    /**
     * The core loop that runs the simulation
     *
     * The "Event" check the queue for the next event.
     * The state obj gets the time from the new event,
     *
     * We also call the abstract "Perfrom" method
     *
     * State will notify the observers when the program is finished,
     * as to not print after a stop Event is called
     */
    public void run(){
        state.setRunning(true);
        while(!eventQueue.isEmpty() && state.isRunning()){
            Event event = eventQueue.next();
            state.setTime(event.getTime());
            event.perform(state);
            //state.notifyObservers();
            //System.out.println("notify thread: " + Thread.currentThread().getName());
            //System.out.println("notified");
        }
    }
}
