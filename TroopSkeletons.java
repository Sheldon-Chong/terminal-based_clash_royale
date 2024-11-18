// DEVELOPED BY: DAIKI
/*
 * The TroopSkeletons class represents the Skeletons troop, known for their low health but high attack power.
 * This class extends the Troop class, customizing attributes specific to Skeletons.
 */
public class TroopSkeletons extends Troop {

    // DEVELOPED BY: Daiki
    /*
     * Constructor for the Skeletons troop, setting its initial attributes like position, health, and attack.
     * @param startingPos - the initial position of the Skeletons on the game grid.
     * @param player - the player who owns and controls the Skeletons.
     */
    public TroopSkeletons(Pos startingPos, Player player) {
        super(startingPos, "skeletons", player); // 'skeletons' for Skeletons
        this.SetHP(2); // HP value
        this.SetAttack(5); // Attack value
    }

}
