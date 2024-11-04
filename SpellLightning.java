// Written by Daiki

public class SpellLightning extends Spell {
    private static final int MAX_TARGETS = 3;

    // Constructor for initializing Lightning spell
    public SpellLightning(int elixirCost, int radius, int damage) {
        super(elixirCost, radius, damage, 1); // Duration of 1 since it's instant
    }

    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        // Implement the behavior for deploying the Lightning spell
        cast(targetPos, gameSysRef);
    }

    // Overridden cast method for the Lightning spell
    public void cast(Pos targetPos, GameSystem gameSysRef) {
        Troop[] allTroops = gameSysRef.GetTroops();
        Troop[] targets = new Troop[MAX_TARGETS];
        int targetCount = 0;

        // Finding all troops within the radius and adding up to MAX_TARGETS
        for (int i = 0; i < allTroops.length && targetCount < MAX_TARGETS; i++) {
            if (allTroops[i] != null && allTroops[i].getPos().calcDistance(targetPos) <= this.GetRadius()) {
                targets[targetCount++] = allTroops[i];
            }
        }

        // Shuffle the targets to simulate randomness
        for (int i = 0; i < targetCount; i++) {
            int swapIndex = i + (int) (Math.random() * (targetCount - i));
            Troop temp = targets[i];
            targets[i] = targets[swapIndex];
            targets[swapIndex] = temp;
        }

        // Inflict damage to up to three random targets
        for (int i = 0; i < targetCount; i++) {
            if (targets[i] != null) {
                targets[i].DecreaseHP(this.GetDamage());
                if (targets[i].GetHP() <= 0) {
                    gameSysRef.destroyTroop(targets[i]);
                }
            }
        }
    }
}
