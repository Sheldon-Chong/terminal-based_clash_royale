// DEVELOPED BY: Daiki
/*
 * The TroopPEKKA class represents the P.E.K.K.A troop, known for its high health and strong attack capability.
 * This class extends the Troop class, customizing attributes specific to P.E.K.K.A.
 */
public class TroopPEKKA extends Troop {

    // DEVELOPED BY: Daiki
    /* Default constructor for the P.E.K.K.A troop.
     * Initializes the P.E.K.K.A with no specific starting position or player ownership.*/
    public TroopPEKKA() {
        this(null, null);
    }

    // DEVELOPED BY: Daiki
    /* Constructor for the P.E.K.K.A troop, setting its initial attributes like position, health, and attack.
     * @param startingPos - the initial position of the P.E.K.K.A on the game grid.
     * @param player - the player who owns and controls the P.E.K.K.A. */
    public TroopPEKKA(Pos startingPos, Player player) {
        super(startingPos, "pekka", player); // 'pekka' for P.E.K.K.A
        this.SetHP(13); // HP value
        this.SetAttack(5); // Attack value
    }
}
