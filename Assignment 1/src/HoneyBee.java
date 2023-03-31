public abstract class HoneyBee extends Insect {
    private int cost;

    public HoneyBee(Tile position, int hp, int cost) {
        super(position, hp);
        this.cost = cost;
    }

    // Get the cost of the bee
    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object anObject) {
        return super.equals(anObject) && ((HoneyBee) anObject).cost == this.cost;
    }
}
