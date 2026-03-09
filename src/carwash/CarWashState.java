package carwash;

import carfactory.Car;
import carfactory.CarFactory;
import external.ExponentialRandomStream;
import external.UniformRandomStream;
import simulator.State;
import simulator.EventQueue;

import java.util.LinkedList;
import java.util.Queue;
/**
 * @author Oliver Horvath, Oscar Algotsson
 */
public class CarWashState extends State {
    /** number of free carwashes*/
    private int freeFast;
    private int freeSlow;

    /**Setting up waiting cars and the max quesize allowed*/
    private Queue<double[]> waitingCars = new LinkedList<>();
    private int maxQueueSize;

    /** so, quite alot of different variables but mostly just "Keep time
     * or just keep count,
     * aswell as the carIdCounter which the names kinda explains...*/
    private double totalIdleTime = 0.0;
    private double totalQueueTime = 0.0;
    private int rejectedCars = 0;
    private int carsInSystem = 0;
    private EventType lastEventType;
    private int lastCarId;
    private boolean lastFast;
    private double idleSince;
    private CarFactory carFactory = new CarFactory();

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
    }

    /**
     * Setters
     */
    public void setIdleSince(double idleSince){
        this.idleSince = idleSince;
    }
    public void setLastEventTime(double time) {
        this.lastEventTime = time;
    }
    public void setLastEvent(EventType eventType, int carId, boolean fast){
        this.lastEventType = eventType;
        this.lastCarId = carId;
        this.lastFast = fast;
    }

    /**
     * Getters
     */
    public double getLastEventTime() {
        return lastEventTime;
    }
    public EventType getLastEventType(){
        return this.lastEventType;
    }
    public int getLastCarId(){
        return this.lastCarId;
    }
    public int getCarsInSystem(){
        return this.carsInSystem;
    }
    public double getIdleSince(){
        return this.idleSince;
    }
    public int getFreeFast(){
        return this.freeFast;
    }
    public int getFreeSlow(){
        return this.freeSlow;
    }
    public int getMaxQueueSize(){
        return this.maxQueueSize;
    }
    public int getQueueSize(){
        return this.waitingCars.size();
    }
    public double getTotalIdleTime(){
        return this.totalIdleTime;
    }
    public Queue<double[]> getWaitingCars(){
        return this.waitingCars;
    }
    public double getTotalQueueTime(){
        return this.totalQueueTime;
    }
    public int getRejectedCars(){
        return this.rejectedCars;
    }
    public double getStopTime(){
        return this.stopTime;
    }
    /**
     * Checks the different machines for "Free room"
     * and if there is it can occupy or release depending on the state
     * and action taken.
     * */
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
    /** Adds to the current Queue Time*/
    public void addQueueTime(double time){
        this.totalQueueTime += time;
    }
    /**Im really tryign to write comments on all of these but common?*/
    public void addRejectedCars(){
        this.rejectedCars++;
    }
    /** adds car into the system*/
    public void addCarInSystem(){
        this.carsInSystem++;
    }

    //---- car id Stuff

    /**Returns the unique car id and increments the counter
     * @return next Car id Starting from 0
     *
     * */
    public Car nextCar(){
        return carFactory.createCar();
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
}
