public class BusyBee extends HoneyBee {
    // Bees that collect pollen

    public BusyBee(Tile position) {
        super(position, 5, 2);

    }

    @Override
    public boolean takeAction() {
        getPosition().storeFood(2);
        return true;


    }
}
