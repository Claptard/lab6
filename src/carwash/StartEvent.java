package carwash;

import simulator.Event;
import simulator.EventQueue;
import simulator.State;
/**
 * @author Oskar Borg, Oscar Algotsson
 */
public class StartEvent extends Event {
    /**
     * pretty straight forward, calls "mother constructor"
     * with params,
     *
     * */
    public StartEvent(EventQueue eventQueue) {
        super(0.0,eventQueue);
    }

    /**
     * Sets up the start eveent, and queues up the first car arrival,
     * and stop event for said event
     *
     * @param state The current state of the simulation
     *              intellij asking to add Override so surely its beneficial
     * */
    @Override
    public void perform(State state) {
        CarWashState cws = (CarWashState) state;

        double firstArrival = cws.getTime() + cws.nextArrivalTime();
        /**Schedule the first car event*/
        eventQueue.add(new ArriveEvent(firstArrival,eventQueue));
        /**Schedules the stop event*/
        eventQueue.add( new StopEvent(cws.getStopTime(),eventQueue));
    }
}
