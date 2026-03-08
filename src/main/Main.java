package main;

import carwash.CarWashState;
import carwash.CarWashView;
import carwash.StartEvent;
import external.ExponentialRandomStream;
import external.UniformRandomStream;
import simulator.EventQueue;
import simulator.Simulator;

import java.util.HashMap;
import java.util.Map;

public class Main{

    public static void main(String[] args) {
        int numFast = 2;
        int numSlow = 2;
        int maxQueueSize = 5;
        double stopTime = 15.0;

        //----Random variables
        final int randSeed = 1234;
        final int lambda = 2;
        final double fastRand1 = 2.8;
        final double fastRand2 = 4.6;
        final double slowRand1 = 3.5;
        final double slowRand2 = 6.7;

        //----- Random Streams
        ExponentialRandomStream arrivalStream = new ExponentialRandomStream(lambda, randSeed);
        UniformRandomStream fastStream = new UniformRandomStream(fastRand1, fastRand2, randSeed);
        UniformRandomStream slowStream = new UniformRandomStream(slowRand1, slowRand2, randSeed);

        //---- linking up components----

        EventQueue eventQueue = new EventQueue();

        CarWashState state = new CarWashState(
                numFast, numSlow, maxQueueSize, stopTime,
                arrivalStream, fastStream, slowStream,
                eventQueue);

        CarWashView view = new CarWashView();
        state.addObserver(view);

        //---- printHeader before sim start

        Map<String, String> print_data = new HashMap<>();
        print_data.put("fm", numFast + "");
        print_data.put("sm", numSlow + "");
        print_data.put("flt", fastRand1 + "");
        print_data.put("fut", fastRand2 + "");
        print_data.put("slt", slowRand1 + "");
        print_data.put("sut", slowRand2 + "");
        print_data.put("lambda", lambda + "");
        print_data.put("seed", randSeed + "");
        print_data.put("queuesize", maxQueueSize + "");


        view.printHeader(print_data);

        //---- Schedule the first event and then starts Sim

        eventQueue.add(new StartEvent(eventQueue));
        Simulator simulator = new Simulator(eventQueue,state);
        simulator.run();
    }
}
