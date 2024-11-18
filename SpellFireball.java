// DEVELOPED BY: Daiki

public class SpellFireball extends Spell {

    // DEVELOPED BY: Daiki
    /* Default constructor for the SpellFireball class.
     * Initializes a fireball spell with default position and effects.
     * This is primarily used for testing or when specific positions are not yet determined. */
    public SpellFireball() {
        this(new Pos(0, 0));
    }

    // DEVELOPED BY: Daiki
    /* Constructor for the SpellFireball that specifies the starting position.
     * @param startingPos - the position where the fireball spell is initiated.
     * Expands the area of effect based on the radius to create a standard fireball area. */
    public SpellFireball(Pos startingPos) {
        super(startingPos, 2, 1, 1);
        this.SetPos(startingPos);
    }
    
    // DEVELOPED BY: Daiki
    /* Apply the effect of the fireball spell on a target position.
     * @param targetPos - the center position where the fireball effect is applied.
     * @param gameSysRef - reference to the GameSystem for accessing game entities and utilities.
     * Damages all troops within the radius of the fireball effect. */
    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        
        // iterate for all cells within radius
        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {

                // Calculate the position impacted by the fireball based on its radius.
                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);

                // Ensure the impact position is within the board limits.
                if (!gameSysRef.isOutOfBounds(impactPos)) {

                    // Get the object at the impacted position.
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();
                    
                    // If the object is a troop, reduce its health.
                    if (obj instanceof Troop)
                        ((Troop) obj).DecreaseHP(2);
                }

            }
        }
    }
}