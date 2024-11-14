// class by Sheldon

/*
 * The king tower, which is the player's main tower
 */

public class TowerKing extends Tower {
    
    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Constructor for TowerKing */
    public TowerKing () {
        super();
        this.setHealth(9);
    }

    // DEVELOPED BY: Sheldon
    /* Constructor for TowerKing
     * @param parentPlayer - the player that this tower belongs to */
    public TowerKing (Player parentPlayer) {
        super(parentPlayer);
        this.setHealth(9);
    }
}
