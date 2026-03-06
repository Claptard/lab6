package carwash;
import carfactory.Car;
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

        cws.addQueueTime((cws.getTime() - cws.getLastEventTime()) * cws.getQueueSize());
        cws.setLastEventTime(cws.getTime());
        //Assigning new carid
        Car car = cws.nextCar();
        carId = car.getId();

        if(cws.getFreeFast() > 0){
            //a Fash machine is free and car can get free :) yiippiee
            int totalFree = cws.getFreeFast() + cws.getFreeSlow();
            cws.addIdleTime((cws.getTime() - cws.getIdleSince()) * totalFree);
            cws.occupyFast();
            cws.setIdleSince(cws.getTime());
            cws.addCarInSystem();
            double washTime = cws.nextFastWashTime();
            eventQueue.add(new LeaveEvent(cws.getTime() + washTime, eventQueue, carId, true, washTime));
        }else if(cws.getFreeSlow() > 0){
            //a Slow machine is Free, small yipi :|
            int totalFree = cws.getFreeSlow() + cws.getFreeFast();
            cws.addIdleTime((cws.getTime() - cws.getIdleSince()) * totalFree);
            cws.occupySlow();
            cws.setIdleSince(cws.getTime());
            cws.addCarInSystem();
            double washTime = cws.nextSlowWashTime();
            eventQueue.add(new LeaveEvent(cws.getTime() + washTime,eventQueue,carId, false, washTime));
        }else if(!cws.isQueueFull()){
            //Machines are full but gets placed in queue
            cws.addCarInSystem();
            cws.getWaitingCars().add(new double[]{carId,cws.getTime()});
        }else{
            //queue is full gets rejected
            cws.addRejectedCars();
        }

        double nextArrival = cws.getTime() + cws.nextArrivalTime();
        eventQueue.add(new ArriveEvent(nextArrival,eventQueue));

        cws.setLastEvent("Arrive",carId,false);
    }

    /**Getter for carID
     * @return  The car id...*/
    public int getCarId() {
        return this.carId;
    }
}
