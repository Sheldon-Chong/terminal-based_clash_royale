public class CardSpell extends Card {
    private String spellName;
    private int radius;
    private int damage;
    
    // Constructor for CardSpell
    public CardSpell(int elixirCost, String spellName, int radius, int damage) {
        super(elixirCost); // Call parent constructor
        this.spellName = spellName;
        this.radius = radius;
        this.damage = damage;
    }

    // Getters
    public String getSpellName() {
        return spellName;
    }

    public int getRadius() {
        return radius;
    }

    public int getDamage() {
        return damage;
    }

    
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        // Deploy the spell based on its name and stats (to spawn the actual spell)
        Spell newSpell = null;

        switch (spellName.toLowerCase()) {
            case "zap":
                newSpell = new SpellZap(GetElixirCost(), radius, damage);
                break;
            case "fireball":
                newSpell = new SpellFireball(GetElixirCost(), radius, damage);
                break;
            case "lightning":
                newSpell = new SpellLightning(GetElixirCost(), radius, damage);
                break;
            // More spell cases can be added here...
        }

        if (newSpell != null) {
            gameSysRef.spawnSpell(newSpell); // Pass the Spell object directly
            System.out.println(spellName + " spell deployed!");
        }
    }
}
