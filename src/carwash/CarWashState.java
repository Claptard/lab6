package carwash;

import external.ExponentialRandomStream;
import external.UniformRandomStream;
import simulator.State;
import simulator.EventQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Probably the largest implementation so far but its for our specific use case
 * and need alot of variables
 *
 * The mor code i write the more it looks like a damn nightmare
 *
 * if labledare asks to clean this shit, im not doing it....
 */

public class CarWashState extends State {
    //----Machines available

    /** number of free carwashes*/
    private int freeFast;
    private int freeSlow;

    //----queue variables
    /**Setting up waiting cars and the max quesize allowed*/
    private Queue<double[]> waitingCars;
    private int maxQueueSize;

    //----Stats
    /** so, quite alot of different variables but mostly just "Keep time
     * or just keep count,
     * aswell as the carIdCounter which the names kinda explains...*/
    private double totalIdleTime;
    private double totalQueueTime;
    private int rejectedCars;
    private int carsInSystem;
    private int carIdCounter;
    private String lastEventType;
    private int lastCarId;
    private boolean lastFast;
    private double idleSince;

    //---- Random variables
    /** Random variabler, cars arrival,
     *  and the time it takes for a vehicle to get cleaned in either the
     *  fast or slow machine
     *  */

    private ExponentialRandomStream arrivalStream;
    private UniformRandomStream fastStream;     // generates uniform random time for fast machine
    private UniformRandomStream slowStream;     // generates unoform random time for slow machine

    // ---- Event Queue referenses

    /**
     * Used to keep track of a reference to the shared event queue
     * being used in carwasstate so events can access it via the current state its in.
     */
    private EventQueue eventQueue;

    private double lastEventTime = 0.0;
    //---- Time the machine/simulator stops

    private double stopTime;

    //---- Constructor

    public CarWashState(int numFast, int numSlow, int maxQueueSize,
                        double stopTime,
                        ExponentialRandomStream arrivalStream,
                        UniformRandomStream fastStream,
                        UniformRandomStream slowStream,
                        EventQueue eventQueue){
        super();
        this.freeFast = numFast;
        this.freeSlow = numSlow;
        this.maxQueueSize = maxQueueSize;
        this.stopTime = stopTime;
        this.arrivalStream = arrivalStream;
        this.fastStream = fastStream;
        this.slowStream = slowStream;
        this.eventQueue = eventQueue;
        this.waitingCars = new LinkedList<>();
        this.totalIdleTime = 0.0;
        this.totalQueueTime = 0.0;
        this.rejectedCars = 0;
        this.carsInSystem = 0;
        this.carIdCounter = 0;
    }


    public double getLastEventTime() {
        return lastEventTime;
    }
    public void setLastEventTime(double time) {
        this.lastEventTime = time;
    }
    public void setLastEvent(String eventType, int carId, boolean fast){
        this.lastEventType = eventType;
        this.lastCarId = carId;
        this.lastFast = fast;
    }
    public String getLastEventType(){
        return this.lastEventType;
    }
    public int getLastCarId(){
        return this.lastCarId;
    }
    public boolean isLastFast(){
        return this.lastFast;
    }
    //---- Machine getter and setters
    /**
     * Checks the different machines for "Free room"
     * and if there is it can occupy or release depending on the state
     * and action taken.
     * */
    public double getIdleSince(){
        return this.idleSince;
    }
    public void setIdleSince(double idleSince){
        this.idleSince = idleSince;
    }
    public int getFreeFast(){
        return this.freeFast;
    }
    public int getFreeSlow(){
        return this.freeSlow;
    }
    public void occupyFast(){
        this.freeFast--;
    }
    public void releaseFast(){
        this.freeFast++;
    }
    public void occupySlow(){
        this.freeSlow--;
    }
    public void releaseSlow(){
        this.freeSlow++;
    }

    //---- Queue acess
    /** @return The que of car id's*/
    public Queue<double[]> getWaitingCars(){
        return this.waitingCars;
    }
    /** @return returns max size queue allowed*/
    public int getMaxQueueSize(){
        return this.maxQueueSize;
    }
    /** @return current queue "amount of cars" */
    public int getQueueSize(){
        return this.waitingCars.size();
    }
    /** @return true if queue is full, currently shouldnt be allowed to
     * "overflow" but hey.... better safe then sorry..
     * */
    public boolean isQueueFull(){
        return this.waitingCars.size() >= this.maxQueueSize;
    }

    //---- Stats
    /** adds to the idleTimer*/
    public void addIdleTime(double time){
        this.totalIdleTime += time;
    }
    /** @return current idleTime*/
    public double getTotalIdleTime(){
        return this.totalIdleTime;
    }
    /** Adds to the current Queue Time*/
    public void addQueueTime(double time){
        this.totalQueueTime += time;
    }
    /**Total time for the queue*/
    public double getTotalQueueTime(){
        return this.totalQueueTime;
    }
    /**Im really tryign to write comments on all of these but common?*/
    public void addRejectedCars(){
        this.rejectedCars++;
    }
    /**GEtter*/
    public int getRejectedCars(){
        return this.rejectedCars;
    }
    /** adds car into the system*/
    public void addCarInSystem(){
        this.carsInSystem++;
    }
    public int getCarsInSystem(){
        return this.carsInSystem;
    }

    //---- car id Stuff

    /**Returns the unique car id and increments the counter
     * @return next Car id Starting from 0
     *
     * */
    public int nextCarId(){
        return this.carIdCounter++;
    }

    //---- Random streams

    public double nextArrivalTime(){
        return arrivalStream.next();
    }
    public double nextFastWashTime(){
        return fastStream.next();
    }
    public double nextSlowWashTime(){
        return slowStream.next();
    }

    //---- Event queue
    /**@return THE shared event queue*/
    public EventQueue getEventQueue(){
        return eventQueue;
    }

    //---- Stop timeer

    /**@return The simulators stopTime */
    public double getStopTime(){
        return this.stopTime;
    }
}
