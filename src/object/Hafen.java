import java.util.List;
;

package object;

public class Hafen {
    private int cargoCount;
    private String id;
}

    public Hafen(String id) {
        this.id = id;
    }

    public int getCargoCount() {
        return cargoCount;
    }

    public void setCargoCount(int cargoCount) {
        this.cargoCount = cargoCount;
    }

    public int getReservedCargoCount() {
        // Add your implementation here
    }

    public void setReservedCargoCount(int reservedCargoCount) {
        this.reservedCargoCount = reservedCargoCount;
    }

    public List<Ship> getShips() {
        // Add your implementation here
    }

