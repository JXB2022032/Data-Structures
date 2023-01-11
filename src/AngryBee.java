public class AngryBee extends HoneyBee {
    private int attackDamage;

    public AngryBee(Tile position, int attackDamage) {
        super(position, 10, 1);
        this.attackDamage = attackDamage;
    }

    @Override
    public boolean takeAction() {

        if ((getPosition().isOnThePath() || getPosition().isHive()) && getPosition().getHornet() != null && !getPosition().isNest()) {
            getPosition().getHornet().takeDamage(attackDamage);
            return true;
        }

        Tile t = getPosition();     // Created a temp tile called t
        while (this.getPosition().getHornet() == null) {    // check the hornet on the current tile
            if (t.getHornet() != null && !t.isNest()) {
                t.getHornet().takeDamage(attackDamage);     // The first hornet receive the damage from the bee
                return true;
            }

            if(t.towardTheNest() == null) {
                break;
            }

            t = t.towardTheNest();      // Update tile t to the next tile toward nest
        }

        return false;
    }

    public boolean equals (Object anObject){
        return super.equals(anObject) && this.attackDamage == ((AngryBee) anObject).attackDamage;
    }

}
