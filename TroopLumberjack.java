// DEVELOPED BY: Daiki
/*
 * The TroopLumberjack class represents the Lumberjack troop, known for its balanced health and high attack power.
 * This class extends the Troop class, specifying the attributes for the Lumberjack.
 */
public class TroopLumberjack extends Troop {

    // DEVELOPED BY: Daiki
    /*
     * Constructor for the Lumberjack troop, setting its starting position, player ownership, health, and attack.
     * @param startingPos - the initial position of the Lumberjack on the game grid.
     * @param player - the player who owns and controls the Lumberjack.
     */
    public TroopLumberjack(Pos startingPos, Player player) {
        super(startingPos, "lumberjack", player); // 'lumberjack' for Lumberjack
        this.SetHP(6); // HP value
        this.SetAttack(3); // Attack value
    }
}
