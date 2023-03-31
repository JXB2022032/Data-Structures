public class Tile {
    private int foodOnTile;
    private boolean isBuiltHive;
    private boolean isBuiltNest;
    private boolean isPathHornetToBee;

    private Tile nextTile_HornetTowardBee;
    private Tile nextTile_BeeTowardHornet;
    private HoneyBee bee;
    private SwarmOfHornets hornets;

    public Tile() {
        this(0,false,false,false,null,
                null,null,null);
    }

    public Tile(int foodOnTile, boolean isBuiltHive, boolean isBuiltNest, boolean isPathHornetToBee,
                Tile nextTile_HornetTowardBee, Tile nextTile_BeeTowardHornet, HoneyBee bee,
                SwarmOfHornets hornets) {
        this.foodOnTile = foodOnTile;
        this.isBuiltHive = isBuiltHive;
        this.isBuiltNest = isBuiltNest;
        this.isPathHornetToBee = isPathHornetToBee;
        this.nextTile_HornetTowardBee = nextTile_HornetTowardBee;
        this.nextTile_BeeTowardHornet = nextTile_BeeTowardHornet;
        this.bee = bee;
        this.hornets = hornets;
    }

    // A method which returns whether a bee hive has been built on this tile
    public boolean isHive() {
        return this.isBuiltHive;
    }

    // A method which returns whether a hornet nest has been built on this tile
    public boolean isNest() {
        return this.isBuiltNest;
    }

    // A method which builds a bee hive on this tile
    public void buildHive() {
        this.isBuiltHive = true;
    }

    // A method which builds a hornet nest on this tile
    public void buildNest() {
        this.isBuiltNest = true;
    }

    // A method which returns whether this tile is part of the path that leads from nest to hive
    public boolean isOnThePath() {
        return isPathHornetToBee;
    }

    // A method which returns the next tile on the path from the hornet nest to the bee hive
    public Tile towardTheHive() {
        if (isOnThePath()) {
            return nextTile_HornetTowardBee;
        }
        return null;
    }

    // A method which returns the next tile on the path from the bee hive to the hornet nest
    public Tile towardTheNest() {
        if (isOnThePath()) {
            return nextTile_BeeTowardHornet;
        }
        return null;
    }

    // A tile that is part of the path that leads from the hornet nest to the bee hive
    public void createPath(Tile nextTile_HornetTowardBee, Tile nextTile_BeeTowardHornet) {
        this.nextTile_BeeTowardHornet = nextTile_BeeTowardHornet;
        this.nextTile_HornetTowardBee = nextTile_HornetTowardBee;
        this.isPathHornetToBee = true;
    }

    // Collect food
    public int collectFood() {
        int foodCollected = this.foodOnTile;
        this.foodOnTile = 0;
        return foodCollected;
    }

    // Store food
    public void storeFood(int foodReceived) {
        this.foodOnTile += foodReceived;
    }

    // Get the honey bee object
    public HoneyBee getBee() {
        return this.bee;
    }

    // Get the first hornet object
    public Hornet getHornet() {
        if (hornets == null) {
            this.hornets = new SwarmOfHornets();
        }
        return this.hornets.getFirstHornet();
    }

    public int getNumOfHornets() {
        if (hornets == null) {
            this.hornets = new SwarmOfHornets();
        }
        return this.hornets.sizeOfSwarm();
    }

    // A method which takes as input an Insect and adds it to the tile
    public boolean addInsect(Insect newInsect) {
        if (newInsect instanceof HoneyBee && bee == null && !newInsect.getPosition().isNest()) {
            this.bee = (HoneyBee) newInsect;    // Initialize this.bee variable
            newInsect.setPosition(this);    // Set the position of bee on this tile
            return true;
        } else if (newInsect instanceof Hornet && (isPathHornetToBee || isHive() || isNest())) {
            newInsect.setPosition(this);
            if (hornets == null) {
                this.hornets = new SwarmOfHornets();    // If hornets is null, create an empty swarm of hornet object
            }
            hornets.addHornet((Hornet) newInsect);  // Add the new hornet
            return true;
        }
        return false;
    }

    // A method which takes as input an Insect and removes it from the tile.
    public boolean removeInsect(Insect insect) {
        if (insect instanceof HoneyBee && bee != null) { // Check if the input parameter is an instance of Honeybee
            bee = null;     // Remove the bee by set it to null
            return true;
        } else if (insect instanceof Hornet && getHornet() != null) {
            hornets.removeHornet(hornets.getFirstHornet());
            return true;
        }
        return false;
    }
}
