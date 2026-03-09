package carfactory;
/**
 * @author Oscar Algotsson
 */
public class CarFactory {
    private int idCounter = 0;

    public Car createCar() {
        return new Car(idCounter++);
    }
}
