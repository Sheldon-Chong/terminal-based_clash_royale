// DEVELOPED BY: Daiki
/*
 * The TroopGolem class represents the Golem troop, known for its higher health and moderate attack capability.
 * This class extends the base Troop class, configuring the Golem's specific attributes, such as its health and attack power.
 */
public class TroopGolem extends Troop {
    
    // DEVELOPED BY: Daiki
    /* Default constructor for the Golem troop.
     * Initializes the Golem with no specific starting position or player ownership.*/
    public TroopGolem() {
        this(null, null);
    }

    // DEVELOPED BY: Daiki
    /*
     * Constructs a Golem troop at a specified starting position and assigns it to a player.
     * Sets the unique health and attack values characteristic of the Golem.
     * @param startingPos - the initial position of the Golem on the game grid.
     * @param player - the player who owns and controls the Golem, defining player allegiance.
     */
    public TroopGolem(Pos startingPos, Player player) {
        super(startingPos, "golem", player); // 'golem' for Golem
        this.SetHP(10); // HP value
        this.SetAttack(2); // Attack value
    }
}
