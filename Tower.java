// Entire class by Sheldon

/*
 * The tower object.
 * This object exists to be referrenced by the gameSystem class.
 * It will occupy a tile on the grid.
 * Since the gameSystem has refferences to these tower objects, troops will know how to direct themselves to the nearest tower by comparing positions of each tower.
 */

public class Tower extends Obj{
    
    // ATTRIBUTES
    private Pos pos;
    private Player parentPlayer;


    // CONSTRUCTORS
    Tower () {
    }

    Tower (Player player) {
        this.parentPlayer = player;
    }


    // SETTERS AND GETTERS
    public void SetPos(Pos pos) { this.pos = pos; }
    public Pos  GetPos()        { return this.pos; }

    public void   SetPlayer(Player parentPlayer) { this.parentPlayer = parentPlayer; }
    public Player GetPlayer() { return this.parentPlayer; }

}
