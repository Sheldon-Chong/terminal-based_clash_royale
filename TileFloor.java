

public class TileFloor extends Tile {
    
    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Default constructor for TileFloor */
    public TileFloor () {
        this.SetStrType("TileFloor");
    }
    
    // DEVELOPED BY: Sheldon
    /* Constructor for TileFloor
     * @param - type: the type of the tile */
    public TileFloor (int type) {
        SetType(type);
        this.SetStrType("TileFloor");
    }

    // DEVELOPED BY: Sheldon
    /* Constructor for TileFloor
     * @param - pos: the position of the tile */
    public TileFloor (Pos pos) {
        this();
        this.SetPos(pos);
    }
}
