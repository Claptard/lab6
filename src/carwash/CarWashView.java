package carwash;

import simulator.State;
import simulator.View;

public class CarWashView extends View{
    private static final String SEPARATOR = "-----------------------------------------------------------";

    EventType evType;


    private String lastEvent = "Start";
    /**
     * Text formatering för att få den att passa in
     * rtfm om du vill se vad det gör för skrev det 3 på natten så är glad att det
     * faktiskt ser rätt ut.
     * */
    public void printHeader(CarWashState state){
        System.out.println(SEPARATOR);
        System.out.printf("%-6s  %-8s %-4s %-4s %-4s %-10s %-10s %-10s %-8s%n",
                "Time", "Event", "Id", "Fast", "Slow",
                "IdleTime", "QueueTime", "QueueSize", "Rejected");
        System.out.println(SEPARATOR);

        System.out.printf("%-10s %-8s%n", format(0.0), "Start");
    }

    public void update(State state){
        CarWashState cws = (CarWashState) state;

        EventType eventType = cws.getLastEventType();

        if(eventType == null) return;

        switch (eventType){
            case ARRIVE:
                printRow(cws,"Arrive", cws.getLastCarId(),cws.isLastFast());
                break;
            case LEAVE:
                printRow(cws,"Leave", cws.getLastCarId(),cws.isLastFast());
                break;
            case STOP:
                printRow(cws, "Stop", -1, false);
                printSummary(cws);
                break;
        }

    }
    private void printRow(CarWashState cws, String event, int carId, boolean fast){
        String time = format(cws.getTime());
        String id = carId >= 0 ? String.valueOf(carId) : "";
        String fastStr = String.valueOf(cws.getFreeFast());
        String slowStr = String.valueOf(cws.getFreeSlow());
        String idle = format(cws.getTotalIdleTime());
        String queueTime = format(cws.getTotalQueueTime());
        String queueSize = String.valueOf(cws.getQueueSize());
        String rejected = String.valueOf(cws.getRejectedCars());

        System.out.printf("%6s  %-8s %-4s %-4s %-4s %-10s %-10s %-10s %-8s%n",
                time, event, id, fastStr, slowStr,
                idle, queueTime, queueSize, rejected);

    }
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
    private String format(double value){
        return String.format("%.2f", value).replace('.',',');
    }
}
