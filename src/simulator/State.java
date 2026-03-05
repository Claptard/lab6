package simulator;

import java.util.ArrayList;
import java.util.List;

public class State {
    private double time;
    private boolean running;
    private List<View> observers;

    /**
     * Constructor settign everything in the state to its
     * default values,(time = 0... not running and an empty list of observers)
     * @param time
     * @param running
     * @param observers
     */
    public State(double time, boolean running, List<View> observers) {
        this.time = 0.0;
        this.running = false;
        this.observers = new ArrayList<>();
    }

    /**
     * Adding observers so they will be notified when events happen
     * @param view
     */
    public void addObserver(View view){
        observers.add(view);
    }

    /**
     * Notifies all observed objects that a stat change
     * could have happend //View as of Writting this is not yet configured
     *
     *----
     * But it should update the state
     */
    public void notifyObserver(){
        for(View view : observers){
            view.update(this);
        }
    }

    /**
     * Getters
     * @return
     */
    public double getTime(){
        return this.time;
    }

    /**
     * Setting the timer
     * @param newTime
     */
    public void setTime(double newTime){
        this.time = newTime;
    }
    public boolean isRunning(){
        return this.running;
    }

    /**
     * Setter, let's u set the state of running
     * @param isRunning
     */
    public void setRunning(boolean isRunning){
        this.running = isRunning;
    }

}
