// DEVELOPED BY: Daiki
/*
 * The TroopGiant class defines the Giant troop, known for its significant health and attack capabilities.
 * This class extends from the Troop class and sets up the specific attributes for the Giant,
 * such as its health and attack values.
 */
public class TroopGiant extends Troop {

    // DEVELOPED BY: Daiki
    /* Default constructor for the Giant troop.
     * Initializes the Giant with no specific starting position or player ownership.*/
    public TroopGiant() {
        this(null, null);
    }

    // DEVELOPED BY: Daiki
    /*
     * Constructs a Giant troop with a specified starting position and player ownership.
     * Sets initial health and attack values specific to a Giant.
     * @param startingPos - the starting position of the Giant on the game grid.
     * @param player - the player object this troop belongs to, determining its allegiance and strategy in the game.
     */
    public TroopGiant(Pos startingPos, Player player) {
        super(startingPos, "giant", player); // 'giant' for Giant
        this.SetHP(12); // HP value
        this.SetAttack(2); // Attack value
    }
}
