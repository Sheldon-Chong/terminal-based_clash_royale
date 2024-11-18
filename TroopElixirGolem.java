// DEVELOPED BY: Daiki
/*
 * The TroopElixirGolem class defines the Elixir Golem troop, characterized by its substantial health and moderate attack values.
 * This class extends from the Troop class and configures the Elixir Golem's specific properties like its
 * starting position, health, and attack values.
 */
public class TroopElixirGolem extends Troop {

    // DEVELOPED BY: Daiki
    /*
     * Constructs an Elixir Golem troop with a specified starting position and player ownership.
     * Sets initial health and attack values specific to an Elixir Golem.
     * @param startingPos - the starting position of the Elixir Golem on the game grid.
     * @param player - the player object this troop belongs to, determining its team in the game.
     */
    public TroopElixirGolem(Pos startingPos, Player player) {
        super(startingPos, "elixir golem", player);
        this.SetHP(15); // HP value
        this.SetAttack(2); // Attack value
    }
}
