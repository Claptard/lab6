package carwash;

public enum EventType {
    START(0),ARRIVE(1),LEAVE(2),STOP(3);


    private final int index;

    private EventType(int index){this.index = index;}

    public int getIndex(){return index;}
}
