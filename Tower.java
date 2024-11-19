// Entire class by DAIKI

/* The tower object.
 * This object exists to be referrenced by the gameSystem class.
 * It will occupy a tile on the grid.
 * Since the gameSystem has refferences to these tower objects, troops will know how to direct themselves to the nearest tower by comparing positions of each tower. */

public class Tower extends Obj{
    
    // -- ATTRIBUTES --
    
    private Pos     pos;
    private Player  parentPlayer;
    private int     health;
    private boolean isKingTower;


    // -- CONSTRUCTORS --
    
    // DEVELOPED BY: Daiki
    /* Default constructor for Tower class without specific initialization. 
     * It sets the tower type to "Tower" using the internal method.
     */
    Tower () { 
        this.SetStrType("Tower");
    }

    // DEVELOPED BY: Daiki
    /* Constructor for Tower with association to a player.
     * @param player - the player object to whom the tower belongs.
     * It initializes the tower's player ownership and sets its type.
     */
    Tower (Player player) {
        this.parentPlayer = player;
        this.SetStrType("Tower");
    }


    // -- SETTERS AND GETTERS --
    
    // DEVELOPED BY: Daiki
    /* Sets the position of the tower on the game board.
     * @param pos - position object specifying the tower's location.
     */
    public void SetPos(Pos pos) {
        this.pos = pos;
    }

    // DEVELOPED BY: Daiki
    /* Returns the current position of the tower.
     * @return - the position object of the tower.
     */
    public Pos GetPos() {
        return this.pos;
    }

    // DEVELOPED BY: Daiki
    /* Sets the health of the tower.
     * @param health - the health value to set for the tower.
     */
    public void setHealth(int health) {
        this.health = health;
    }
    
    // DEVELOPED BY: Daiki
    /* Subtracts a specified amount from the tower's health.
     * @param health - the amount of health to subtract.
     */
    public void subtractHealth(int health) {
        this.health -= health;
    }
    
    // DEVELOPED BY: Daiki
    /* Gets the current health of the tower.
     * @return - the current health value of the tower.
     */
    public int getHealth() {
        return this.health;
    }

    // DEVELOPED BY: Daiki
    /* Assigns a player to the tower, establishing ownership.
     * @param parentPlayer - the player object that owns the tower.
     */
    public void SetPlayer(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
    }

    // DEVELOPED BY: Daiki
    /* Retrieves the player that owns the tower.
     * @return - the player object associated with the tower.
     */
    public Player GetPlayer() {
        return this.parentPlayer;
    }
    
    // DEVELOPED BY: Daiki
    /* Checks if the tower is destroyed, if the health is less than or equal to 0
     * @return - if the tower's HP is under 0*/
    public boolean IsDestroyed() { 
        return this.health <= 0; 
    }
}
