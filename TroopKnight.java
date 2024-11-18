// DEVELOPED BY: Daiki
/*
 * The TroopKnight class represents the Knight troop, known for its decent health and moderate attack power.
 * This class extends the Troop class, customizing the attributes specifically for the Knight.
 */
public class TroopKnight extends Troop {
    
    // DEVELOPED BY: Daiki
    /* Default constructor for the Knight troop.
     * Initializes the Knight with no specific starting position or player ownership.*/
    public TroopKnight() {
        this(null, null);
    }

    // DEVELOPED BY: Daiki
    /*
     * Constructor for the Knight troop, setting its starting position, player ownership, health, and attack.
     * @param startingPos - the initial position of the Knight on the game grid.
     * @param player - the player who owns and controls the Knight.
     */
    public TroopKnight(Pos startingPos, Player player) {
        super(startingPos, "knight", player); // 'knight' for Knight
        this.SetHP(6); // HP value
        this.SetAttack(1); // Attack value
    }
}
