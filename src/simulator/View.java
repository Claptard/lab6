package simulator;

public abstract class View {
    /**
     * Called whenever an event happens, implemented by subclasses
     * should return the current state usually
     *
     * -----
     * This is basically to keep it as generic as possible and to not
     * lock it into a "Carwash simulator and just have its states"
     * @param state
     */
    public abstract void update(State state);
}
