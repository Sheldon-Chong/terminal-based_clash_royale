// DEVELOPED BY: Daiki
/**
 * The TroopHogRider class represents the Hog Rider troop, characterized by its balanced health and higher attack power.
 * This class extends the Troop class and initializes specific attributes for the Hog Rider, such as health and attack power.
 */
public class TroopHogRider extends Troop {
    
    // DEVELOPED BY: Daiki
    /*
     * Constructor for the Hog Rider troop, setting its starting position, player ownership, health, and attack.
     * @param startingPos - the initial position of the Hog Rider on the game grid.
     * @param player - the player who owns and controls the Hog Rider.
     */
    public TroopHogRider(Pos startingPos, Player player) {
        super(startingPos, "hog rider", player); // 'hog rider' for Hog Rider
        this.SetHP(7); // HP value
        this.SetAttack(3); // Attack value
    }
}
