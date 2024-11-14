// class by Sheldon

/*
 * The princess tower, two of which belong to a single player
 */

public class TowerPrincess extends Tower {
    
    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Constructor for TowerPrincess */
    public TowerPrincess () {
        super();
        this.setHealth(5);
    }

    // DEVELOPED BY: Sheldon
    /* Constructor for TowerPrincess
     * @param parentPlayer - the player that this tower belongs to */
    public TowerPrincess (Player parentPlayer) {
        super(parentPlayer);
        this.setHealth(5);
    }
}
