package simulator;

public abstract class Event {

    /**Simulated time the event will occur*/
    private double time;
    /**Reference to the event queue, used so future events can be place correctly */
    protected EventQueue eventQueue;

    /**
     *  Constructor that "Makes a new event"
     * @param time          Current simulated time the event occurs
     * @param eventQueue    Shared event queue
     */
    public Event(double time, EventQueue eventQueue) {
        this.time = time;
        this.eventQueue = eventQueue;
    }

    /**
     * Should return the events time
     * @return
     */
    public double getTime(){
        return this.time;
    }

    /**
     * Kinda wierd not sure how these "unimplemented methods" work
     * but they should work sort of as "Place holders"
     * and when implemented makes alot more sense to who ever makes carwash
     * @param state     Current state of the simulator
     */
    public abstract void perform(State state);
}
