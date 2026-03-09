package carwash;

import simulator.State;
import simulator.View;

import java.util.Map;

/**
 * @author Oliver Horvath
 */
public class CarWashView extends View{
    private static final String SEPARATOR = "----------------------------------------";

    /** The format that rows are printed in */
    private final String head = "%-5s %-9s %-8s";
    private final String format = head + " %-5s %-7s %-8s %-10s %-10s %-12s %-1s";

    /**
     * Prints the outputs header
     * @param print_data print stats above the dashed line
     * */

    public void printHeader(Map<String, String> print_data){
        System.out.println("Fast machines: " + print_data.get("fm"));
        System.out.println("Slow machines: " + print_data.get("sm"));
        System.out.println("Fast distribution: (" + print_data.get("flt") + ", " + print_data.get("fut") + ")");
        System.out.println("Slow distribution: (" + print_data.get("slt") + ", " + print_data.get("sut") + ")");
        System.out.println("Exponential distribution with lambda = " + print_data.get("lambda"));
        System.out.println("seed = " + print_data.get("seed"));
        System.out.println("Max Queue size: " + print_data.get("queuesize"));

        System.out.println(SEPARATOR);
        System.out.printf(format,
                "", "Time", "Event", "Id", "Fast", "Slow",
                "IdleTime", "QueueTime", "QueueSize", "Rejected");
        System.out.println();

        System.out.printf(head, "", format(0.0), "Start");
        System.out.println();
    }

    /**
     * Recieves an event to print
     * @param state The CarWashState that contains the data needed
     * */
    public void update(State state){
        CarWashState cws = (CarWashState) state;

        EventType eventType = cws.getLastEventType();

        if(eventType == null) return;
        switch (eventType){
            case EventType.ARRIVE:
                printRow(cws,"Arrive", cws.getLastCarId());
                break;
            case EventType.LEAVE:
                printRow(cws,"Leave", cws.getLastCarId());
                break;
            case EventType.STOP:
                printRow(cws, "Stop", -1);
                printSummary(cws);
                break;
        }
    }

    /**
     * Prints a row
     * @param cws
     * @param event
     * @param carId
     */
    private void printRow(CarWashState cws, String event, int carId){
        /* fetches print data */
        String time = format(cws.getTime());
        String id = carId >= 0 ? String.valueOf(carId) : "";
        String fastStr = String.valueOf(cws.getFreeFast());
        String slowStr = String.valueOf(cws.getFreeSlow());
        String idle = format(cws.getTotalIdleTime());
        String queueTime = format(cws.getTotalQueueTime());
        String queueSize = String.valueOf(cws.getQueueSize());
        String rejected = String.valueOf(cws.getRejectedCars());

        System.out.printf(format,
                "", time, event, id, fastStr, slowStr,
                idle, queueTime, queueSize, rejected);
        System.out.println();
    }

    /**
     * Prints the after the simultion is complete. It prints the overall stats.
     * @param cws
     */
    private void printSummary(CarWashState cws) {
        System.out.println(SEPARATOR);
        System.out.printf("Total idle machine time: %s%n",
                format (cws.getTotalIdleTime()));

        System.out.printf("Total queueing time: %s%n",
                format (cws.getTotalQueueTime()));

        int carsInSystem = cws.getCarsInSystem();
        double meanQueueTime = carsInSystem > 0 ? cws.getTotalQueueTime() / carsInSystem : 0.0;

        System.out.printf("Mean Queueing time %s%n", format(meanQueueTime));
        System.out.printf("Rejected cars: %d%n ", cws.getRejectedCars());
    }
    private static String format(double value){
        return String.format("%.2f", value).replace('.',',');
    }
}
