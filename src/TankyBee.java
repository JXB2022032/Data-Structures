public class TankyBee extends HoneyBee {
    // Slow to sting, but are very sturdy
    private int attackDamage;
    private int armor;

    public TankyBee(Tile position, int attackDamage, int armor) {
        super(position, 30, 3);
        this.attackDamage = attackDamage;
        this.armor = armor;
    }

    @Override
    public boolean takeAction() {
        if (getPosition().getHornet() != null) {
            getPosition().getHornet().takeDamage(attackDamage);
            return true;
        }
        return false;
    }

    @Override
    public void takeDamage(int attackDamage) {
        if (getPosition().getBee() instanceof TankyBee) {
            // The damage is multiplied by a multiplier which depends on this bee’s armor
            super.takeDamage((int) (attackDamage*(double) 100/(100 + armor)));  // The multiplier is a double equal to 100/(100 + armor)
        }

    }

    @Override
    public boolean equals(Object anObject) {
        return super.equals(anObject) && this.attackDamage == ((TankyBee) anObject).attackDamage && this.armor == ((TankyBee) anObject).armor;
    }
}
