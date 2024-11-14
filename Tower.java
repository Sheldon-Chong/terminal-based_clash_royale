// Entire class by Sheldon

/*
 * The tower object.
 * This object exists to be referrenced by the gameSystem class.
 * It will occupy a tile on the grid.
 * Since the gameSystem has refferences to these tower objects, troops will know how to direct themselves to the nearest tower by comparing positions of each tower.
 */

public class Tower extends Obj{
    
    // -- ATTRIBUTES --
    
    private Pos     pos;
    private Player  parentPlayer;
    private int     health;


    // -- CONSTRUCTORS --
    
    // DEVELOPED BY: Daiki
    Tower () { 

    }

    // DEVELOPED BY: Daiki
    Tower (Player player) {
        this.parentPlayer = player;
    }


    // -- SETTERS AND GETTERS --
    
    // DEVELOPED BY: Daiki
    public void SetPos(Pos pos) {
        this.pos = pos;
    }

    // DEVELOPED BY: Daiki
    public Pos GetPos() {
        return this.pos;
    }

    // DEVELOPED BY: Daiki
    public void setHealth(int health) {
        this.health = health;
    }
    
    // DEVELOPED BY: Daiki
    public void subtractHealth(int health) {
        this.health -= health;
    }
    
    // DEVELOPED BY: Daiki
    public int getHealth() {
        return this.health;
    }

    // DEVELOPED BY: Daiki
    public void SetPlayer(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
    }

    // DEVELOPED BY: Daiki
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
