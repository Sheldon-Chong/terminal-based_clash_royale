// DEVELOPED BY: Daiki

public class SpellZap extends Spell {

    // DEVELOPED BY: Daiki
    /* Constructor to initialize the SpellZap with cost, radius, and damage */
    public SpellZap(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1);
    }

    // DEVELOPED BY: Daiki
    public void ApplyEffect() {

    }    

    // Cast method applies the effect of the spell to the surrounding area
    // public void cast(Pos targetPos, GameSystem gameSysRef) {
    //     if (gameSysRef.IsWithinBoard(targetPos)) {
    //         // Loop over a square area determined by the radius
    //         for (int y = -GetRadius(); y <= GetRadius(); y++) {
    //             for (int x = -GetRadius(); x <= GetRadius(); x++) {
    //                 // Calculate the position affected by the zap
    //                 Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
    //                 // Check if the position is within the bounds of the board
    //                 if (gameSysRef.IsWithinBoard(impactPos)) {
    //                     // Get the object at the impact position
    //                     Obj obj = gameSysRef.GetCell(impactPos).GetObject();
    //                     // If the object is a troop, apply the zap damage
    //                     if (obj instanceof Troop) {
    //                         ((Troop) obj).DecreaseHP(GetDamage());
    //                         System.out.println("Zap hit " + ((Troop) obj).GetNameShort() + " at position " + impactPos.x + ", " + impactPos.y);
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
}
