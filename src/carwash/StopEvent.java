package carwash;

import simulator.Event;
import simulator.EventQueue;
import simulator.State;
/**
 * @author Oskar Borg
 */
public class StopEvent extends Event {

    /**Constructor is basically only inherited
     * @param time          The Simulated Time when it shoud stop
     * @param eventQueue    THE queue
     * */
    public StopEvent(double time, EventQueue eventQueue) {
        super(time,eventQueue);
    }

    /**
     * it stops, calls the state to "Stop"/no running
     *
     * @param state     The current state of the simulator
     * */
    @Override
    public void perform(State state) {
        CarWashState cws = (CarWashState) state;
        cws.setLastEvent(EventType.STOP,-1,false);
        cws.addQueueTime((cws.getTime() - cws.getLastEventTime()) * cws.getMaxQueueSize());
        state.notifyObservers(state.clone_class());

        cws.setLastEventTime(cws.getTime());
        state.setRunning(false);
    }
}
