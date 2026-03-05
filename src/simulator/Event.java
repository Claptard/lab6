package simulator;

public abstract class Event {

    private double time;
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
     * Getter for time
     * @return
     */
    public double getTime(){
        return this.time;
    }

    /**
     * Kinda wierd not sure how these "unimplemented methods" work
     * but they should work sort of as "Place holders"
     * and when implemented makes alot more sense to who ever makes carwash
     * @param state
     */
    public abstract void perform(State state);
}
