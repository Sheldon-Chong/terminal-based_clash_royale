// DEVELOPED BY: Daiki

public class SpellZap extends Spell {
    
    // -- CONSTRUCTORS --

    // DEVELOPED BY: Daiki
    /* Constructor for SpellZap */
    public SpellZap() {
        this(new Pos(0, 0));
    }
    
    // DEVELOPED BY: Daiki
    /* Constructor to initialize the SpellZap with cost, radius, and damage */
    public SpellZap(Pos startingPos) {
        super(startingPos, 2, 1, 1);
        this.SetPos(startingPos);
    }

    
    // -- PUBLIC METHODS --

    // DEVELOPED BY: Daiki
    /* Apply the zap effect at a specified position in the game world.
     * @param targetPos - the center position where the zap strikes.
     * @param gameSysRef - reference to the GameSystem to interact with game elements.
     * This method damages up to 4 troops within the radius of the zap effect.
     */
    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        
        // Initialize the count of targets hit
        int targetsHit = 0;

        // Loop through all cells within the spell's radius
        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {

                // Calculate each impact position
                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);

                // Check if the impact position is within the game board
                if (!gameSysRef.isOutOfBounds(impactPos)) {
                    // Retrieve the object at the impact position
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();

                    // Apply damage to troops, counting up to 4 targets
                    if (obj instanceof Troop && targetsHit < 4) {
                        ((Troop) obj).DecreaseHP(2);
                        targetsHit ++;
                    }
                }
            }
        }
    }

}
