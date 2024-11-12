// DEVELOPED BY: Daiki

public class SpellLightning extends Spell {
    private static final int MAX_TARGETS = 3;
    private Pos startPos; // Position where the spell impacts
    private static final int EFFECT_DURATION = 1; // Duration for the lightning spell

    // Constructor for initializing Lightning spell
    public SpellLightning(int elixirCost, int radius, int damage) {
        super(elixirCost, radius, damage, EFFECT_DURATION); // Correctly pass the duration
    }

    
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        // Implement the behavior for deploying the Lightning spell
        this.startPos = targetPos; // Setting the position where it impacts
        cast(targetPos, gameSysRef);
    }

    
    public void cast(Pos targetPos, GameSystem gameSysRef) {
        Troop[] allTroops = gameSysRef.GetTroops();
        int targetsHit = 0;

        for (Troop troop : allTroops) {
            if (troop != null && troop.GetPos().CalcDistance(targetPos) <= this.GetRadius() && targetsHit < MAX_TARGETS) {
                troop.DecreaseHP(this.GetDamage());
                if (troop.GetHP() <= 0) {
                    gameSysRef.destroyTroop(troop);
                }
                targetsHit++;
            }
        }
    }

    // Since this class does not use duration directly, this method could be omitted unless required elsewhere
    public int getEffectDuration() {
        return EFFECT_DURATION;
    }
}
