// DEVELOPED BY: Daiki

/*
 * The TroopBarbarian class defines the Barbarian troop, characterized by its specific health and attack values.
 * This class extends from the Troop class and sets up the Barbarian's initial properties such as its
 * starting position, health, and attack values.
 */
public class TroopBarbarian extends Troop {

    // DEVELOPED BY: Daiki
    /*
     * Constructs a Barbarian troop with specified starting position and player ownership.
     * Sets initial health and attack values specific to a Barbarian.
     * @param startingPos - the starting position of the Barbarian on the game grid.
     * @param player - the player object this troop belongs to, determining its allegiance in the game.
     */
    public TroopBarbarian(Pos startingPos, Player player) {
        super(startingPos, "barbarian", player); // 'barbarian' for Barbarian
        this.SetHP(5); // HP value
        this.SetAttack(2); // attack value
    }

}
