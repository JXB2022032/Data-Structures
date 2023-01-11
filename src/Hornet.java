public class Hornet extends Insect {
    private int attackDamage;   // hornet attack damage

    public Hornet(Tile position, int hp, int attackDamage) {
        super(position, hp);
        this.attackDamage = attackDamage;
    }


    @Override
    public boolean takeAction() {
        if (getPosition().getBee() != null) {   // Check if there's a bee on the current tile
            getPosition().getBee().takeDamage(attackDamage);    // hornets sting the bee
            return true;
        }

        if ((getPosition().getBee() == null) && (!getPosition().isHive())) {

            Tile adv = getPosition().towardTheHive();

            getPosition().removeInsect(this);
            setPosition(adv);
            adv.addInsect(this);    // hornets move toward the bee hive
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object anObject) {
        return super.equals(anObject) && ((Hornet) anObject).attackDamage == this.attackDamage;
    }
}
