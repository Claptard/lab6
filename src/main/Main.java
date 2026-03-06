package main;

import carwash.CarWashState;
import carwash.CarWashView;
import carwash.StartEvent;
import external.ExponentialRandomStream;
import external.UniformRandomStream;
import simulator.EventQueue;
import simulator.Simulator;
import simulator.State;

public class Main{

    public static void main(String[] args) {


        int numFast = 2;
        int numSlow = 2;
        int maxQueueSize = 5;
        double stopTime = 15.0;


        //----- Random Streams
        ExponentialRandomStream arrivalStream = new ExponentialRandomStream(2, 1234);
        UniformRandomStream fastStream = new UniformRandomStream(2.8, 4.6, 1234);
        UniformRandomStream slowStream = new UniformRandomStream(3.5, 6.7, 1234);

        //---- linking up components----

        EventQueue eventQueue = new EventQueue();

        CarWashState state = new CarWashState(
                numFast, numSlow, maxQueueSize, stopTime,
                arrivalStream, fastStream, slowStream,
                eventQueue);

        CarWashView view = new CarWashView();
        state.addObservers(view);

        //---- printHeader before sim start

        view.printHeader(state);

        //---- Schedule the first event and then starts Sim

        eventQueue.add(new StartEvent(eventQueue));
        Simulator simulator = new Simulator(eventQueue,state);
        simulator.run();
    }
}
