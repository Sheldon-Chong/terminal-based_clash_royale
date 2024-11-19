// DEVELOPED BY: Daiki

/* SpellLightning class extends the Spell class to represent a lightning spell in the game.
 * It can target and damage multiple troops within its range. */
public class SpellLightning extends Spell {

    // -- CONSTRUCTORS --

    // DEVELOPED BY: Daiki
    /* Default constructor for the Lightning class.
     * Initializes a lightning spell with default position and effects.
     * This is primarily used for testing or when specific positions are not yet determined. */
    public SpellLightning() {
        this(new Pos(0, 0));
    }

    // DEVELOPED BY: Daiki
    /* Constructor for initializing Lightning spell 
     * @param startingPos: the starting position of the spell */
    public SpellLightning(Pos startingPos) {

        // Initialize the spell with a effectTime, deployTime and radius of 1
        super(startingPos, 1, 1, 1);
        this.SetPos(startingPos);
    }
    

    // -- PUBLIC METHODS --

    // DEVELOPED BY: Daiki
    /* Apply the effect of the lightning spell on a target position within the game world.
     * @param targetPos - the center position where the lightning strikes.
     * @param gameSysRef - reference to the GameSystem to interact with game elements.
     * This method applies damage to all troops within the radius of the lightning effect. */
    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        
        // Iterate through all positions within the spell's radius.
        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {
                
                // Calculate each impact position.
                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                
                // Check if the impact position is within the game board
                if (!gameSysRef.IsOutOfBounds(impactPos)) {
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();

                    // If the object is a troop, apply damage.
                    if (obj instanceof Troop)
                        ((Troop) obj).DecreaseHP(5);
                }
            }
        }
    }

    

}
