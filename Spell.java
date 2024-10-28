// Written by Daiki

public abstract class Spell extends Card {
    private int radius;
    private int damage;
    private int duration;

    // Constructor for initializing basic spell attributes
    public Spell(int elixirCost, int radius, int damage, int duration) {
        super(elixirCost);
        this.radius = radius;
        this.damage = damage;
        this.duration = duration;
    }

    // Abstract cast method to be overridden by each specific spell type
    public abstract void cast(Pos targetPos, GameSystem gameSysRef);
    
    // Getters for accessing spell properties if needed
    public int getRadius() {
        return radius;
    }

    public int getDamage() {
        return damage;
    }

    public int getDuration() {
        return duration;
    }
}
