// DEVELOPED BY: Daiki

public class SpellZap extends Spell {

    // Constructor to initialize the SpellZap with cost, radius, and damage
    public SpellZap(int elixirCost, int radius, int damage) {
        super(elixirCost, radius, damage, 1); // Fixed duration of 1 for instant effect
    }

    // Deploy method called when the spell is triggered
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        cast(targetPos, gameSysRef); // Calls the cast method to apply the effect
    }

    // Cast method applies the effect of the spell to the surrounding area
    public void cast(Pos targetPos, GameSystem gameSysRef) {
        if (gameSysRef.IsWithinBoard(targetPos)) {
            // Loop over a square area determined by the radius
            for (int y = -GetRadius(); y <= GetRadius(); y++) {
                for (int x = -GetRadius(); x <= GetRadius(); x++) {
                    // Calculate the position affected by the zap
                    Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                    // Check if the position is within the bounds of the board
                    if (gameSysRef.IsWithinBoard(impactPos)) {
                        // Get the object at the impact position
                        Obj obj = gameSysRef.GetCell(impactPos).GetObject();
                        // If the object is a troop, apply the zap damage
                        if (obj instanceof Troop) {
                            ((Troop) obj).DecreaseHP(GetDamage());
                            System.out.println("Zap hit " + ((Troop) obj).GetNameShort() + " at position " + impactPos.x + ", " + impactPos.y);
                        }
                    }
                }
            }
        }
    }
}
