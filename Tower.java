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
    
    Tower () { }

    Tower (Player player) {
        this.parentPlayer = player;
    }


    // -- SETTERS AND GETTERS --

    public void SetPos(Pos pos) { this.pos = pos; }
    public Pos  GetPos()        { return this.pos; }

    public void setHealth(int health) { this.health = health; }
    public void subtractHealth(int health) { this.health -= health; }
    public int  getHealth() { return this.health; }

    
    public void   SetPlayer(Player parentPlayer) { this.parentPlayer = parentPlayer; }
    public Player GetPlayer() { return this.parentPlayer; }
    
    /* Checks if the tower is destroyed, if the health is less than or equal to 0
     * @return - boolean
     */
    public boolean IsDestroyed() { 
        return this.health <= 0; 
    }
}
