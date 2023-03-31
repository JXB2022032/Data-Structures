public abstract class Insect {

    private Tile position;
    private int hp;

    public Insect(Tile position, int hp) {
        if (this instanceof HoneyBee && position.getBee() != null) {
            throw new IllegalArgumentException("Cannot add more than one bee on a Tile");
        }
        this.position = position;
        this.hp = hp;

        this.position.addInsect(this);

    }

    // A method which retrieve the position of this insect
    public final Tile getPosition() {
        return this.position;
    }

    public final int getHealth() {
        return this.hp;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }


    public boolean equals(Object anObject) {
        if (anObject instanceof Insect) {
            return ((Insect) anObject).position == this.position && ((Insect) anObject).hp == this.hp;
        }
        return false;
    }

    // This method applies the damage to the insect by modifying its health
    public void takeDamage(int damage) {
        if (this instanceof HoneyBee) {
            // if a bee is positioned on the hive, then the damage is reduced by 10% before being applied
            if ((this.position.getBee() != null) && (this.position.isHive())) {
                this.hp = (this.hp - (int) (damage * 0.9));
            } else {
                this.hp -= damage;     // modifying the bee's health
            }
        } else if (this instanceof Hornet) {
            this.hp -= damage;      // modifying the hornet's health
        }

        if (this.hp <= 0) {
            if (this instanceof HoneyBee) {
                position.removeInsect(position.getBee());   // The bee is died
            }
            if (this instanceof Hornet) {
                position.removeInsect(position.getHornet());    // The hornet is died
            }
        }

    }

    public abstract boolean takeAction();   // Abstract method


}
