public class Tile extends Obj {

    // -- ATTRIBUTES --

    private int         type;
    private TextureSet  texture;
    private GameSystem  gameSysRef;


    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Default constructor for the Tile class */
    public Tile() {
        this.texture = new TextureSet();
        this.SetStrType("Tile");
    }


    // DEVELOPED BY: Sheldon
    /* Constructor for the Tile class with a position
     * @param - pos: the position of the tile */
    public Tile(Pos pos) {
        this();
        this.SetPos(pos);
    }

    
    // -- GETTERS and SETTERS --

    // DEVELOPED BY: Sheldon
    /* sets the texture reference of the tile
     * @param - texture: the texture reference */

    public void SetAppearance(TextureSet texture) {
        this.texture = texture;
    }

    // DEVELOPED BY: Sheldon
    /* returns the texture reference of the tile
     * @return - the texture reference */
    public TextureSet GetAppearance() {
        return this.texture;
    }

    // DEVELOPED BY: Sheldon
    /* SetType sets the type of the tile
     * @param type - the type to set the tile to */
    public void SetType(int type) {
        this.type = type;
    }

    // DEVELOPED BY: Sheldon
    /* GetType returns the type of the tile
     * @return - the type*/
    public int GetType() {
        return this.type;
    }

    // DEVELOPED BY: Sheldon
    /* SetGameSysRef sets the reference to the game system
     * @param gameSysRef - the reference to the game system */
    public void SetGameSysRef(GameSystem gameSysRef) {
        this.gameSysRef = gameSysRef;
    }

    // DEVELOPED BY: Sheldon
    /* GetCell returns the cell that the tile is in
     * functions as a backlink
     * @return - Cell */
    public Cell GetCell() {
        return this.gameSysRef.GetCell(this.GetPos());
    }
}