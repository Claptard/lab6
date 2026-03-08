package carwash;

import simulator.Event;
import simulator.EventQueue;
import simulator.State;

public class LeaveEvent extends Event {

    //id of car leaving
    private int carId;
    //if true car left fast, if false, slow carwash
    private boolean fast;
    //time spent in carwash
    private double washTime;

    /**
     * Constructs a leaveEvent at given time
     *
     * @param time          SimulatedTime when the car leaves
     * @param eventQueue    THE damn queue
     * @param carId         id of the car thats leaving
     * @param fast          weather it's the fast or slow carwash
     * @param washtime      duration of the carsWashing
     * */
    public LeaveEvent(double time, EventQueue eventQueue,
                      int carId,
                      boolean fast,
                      double washtime) {
        super(time,eventQueue);
        this.carId = carId;
        this.fast = fast;
        this.washTime = washtime;
    }

    public void perform(State state) {
        CarWashState cws = (CarWashState) state;

        cws.addQueueTime((cws.getTime() - cws.getLastEventTime()) * cws.getQueueSize());
        cws.setLastEventTime(cws.getTime());
        if(!cws.getWaitingCars().isEmpty()){
            //serve nextCar in queue
            double[] next = cws.getWaitingCars().poll();
            int nextCarId =(int) next[0];


            if(fast){
                double newWashTime = cws.nextFastWashTime();
                eventQueue.add(new LeaveEvent(cws.getTime() + newWashTime,
                                                eventQueue,nextCarId,true,newWashTime));
            } else {
                double newWashTime = cws.nextSlowWashTime();
                eventQueue.add(new LeaveEvent(cws.getTime() + newWashTime,
                                                eventQueue,nextCarId,false,newWashTime));
            }
        } else{
            //no Cars Waiting for wash carwash becomes Idle
            if (fast) {
                cws.releaseFast();
            }else {
                cws.releaseSlow();
            }
            cws.setIdleSince(cws.getTime());
        }

        cws.setLastEvent(EventType.LEAVE,carId, fast);
    }

    /**@return carId related to leaving car event*/
    public int getCarId() {
        return this.carId;
    }
    /**@return if its slow of fast(true = fast)*/
    public boolean isFast(){
        return this.fast;
    }
    /**@return washduration*/
    public double getWashTime() {
        return this.washTime;
    }
}
