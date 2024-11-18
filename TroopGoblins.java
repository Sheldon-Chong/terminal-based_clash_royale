// DEVELOPED BY: Daiki
/*
 * The TroopGoblins class defines the Goblins, which are characterized by their quick speed and lower health.
 * This class extends from the Troop class and configures the specific attributes for Goblins,
 * including their health and attack values.
 */
public class TroopGoblins extends Troop {

    // DEVELOPED BY: Daiki
    /*
     * Constructs Goblins with a specific starting position and player ownership.
     * Initializes the health and attack values specific to Goblins.
     * @param startingPos - the starting position of the Goblins on the game grid.
     * @param player - the player object this troop belongs to, indicating its control and team dynamics in the game.
     */
    public TroopGoblins(Pos startingPos, Player player) {
        super(startingPos, "goblins", player); // 'goblins' for Goblins
        this.SetHP(3); // HP value
        this.SetAttack(1); // Attack value
    }
}
