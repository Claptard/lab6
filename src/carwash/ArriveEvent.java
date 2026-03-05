package carwash;
import simulator.Event;
import simulator.EventQueue;
import simulator.State;
public class ArriveEvent extends Event {

    private int carId;

    /**
     * Creates new event for arrivals, at the time of arrival
     *
     * @param time          The simulated time of arrival
     * @param eventQueue    THE event queue
     * */
    public ArriveEvent(double time, EventQueue eventQueue) {
        super(time, eventQueue);
    }
    /**
     * Deals with arrivals of vehicles and looks into where they can go
     * assignes an id to the vehicle,
     * and makes sure next event gets pre setup
     * */
    public void perform(State state){
        CarWashState cws = (CarWashState) state;

        //Assigning new carid
        carId = cws.nextCarId();

        if(cws.getFreeFast() > 0){
            //a Fash machine is free and car can get free :) yiippiee
            cws.occupyFast();
            cws.addCarInSystem();
            double washTime = cws.nextFastWashTime();
            eventQueue.add(new LeaveEvent(cws.getTime() + washTime, eventQueue, carId, true, washTime));
        }else if(cws.getFreeSlow() > 0){
            //a Slow machine is Free, small yipi :|
            cws.occupySlow();
            cws.addCarInSystem();
            double washTime = cws.nextSlowWashTime();
            eventQueue.add(new LeaveEvent(cws.getTime() + washTime,eventQueue,carId, true, washTime));
        }else if(!cws.isQueueFull()){
            //Machines are full but gets placed in queue
            cws.addCarInSystem();
            cws.getWaitingCars().add(carId);
        }else{
            //queue is full gets rejected
            cws.addRejectedCars();
        }

        double nextArrival = cws.getTime() + cws.nextArrivalTime();
        eventQueue.add(new ArriveEvent(nextArrival,eventQueue));
    }

    /**Getter for carID
     * @return  The car id...*/
    public int getCarId() {
        return this.carId;
    }
}
