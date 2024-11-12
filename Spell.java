// DEVELOPED BY: Daiki

public abstract class Spell extends Obj {
    private int radius;
    private int damage;
    private int duration;
    private int elixirCost;

    // Constructor for initializing basic spell attributes
    public Spell(int elixirCost, int radius, int damage, int duration) {
        super();
        this.radius = radius;
        this.damage = damage;
        this.duration = duration;
        this.elixirCost = elixirCost;
    }

    // Abstract cast method to be overridden by each specific spell type
    public abstract void cast(Pos targetPos, GameSystem gameSysRef);
    
    // Getters for accessing spell properties if needed
    public int GetRadius() {
        return radius;
    }

    public int GetDamage() {
        return damage;
    }

    public int GetDuration() {
        return duration;
    }
}
