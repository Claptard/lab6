package carwash;


import simulator.Event;
import simulator.EventQueue;
import simulator.State;
/**
 * lowkey rätt så simpelt för en gångs skull
 * */
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
        cws.setLastEvent("Stop",-1,false);

        state.setRunning(false);
    }
}
