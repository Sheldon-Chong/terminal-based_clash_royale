public abstract class Tile extends Obj {
    // -- ATTRIBUTES --

    private int type;
    private Texture texture;
    private boolean isSolid;


    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Default constructor for the Tile class */
    public Tile() {
        this.texture = new Texture();
        this.SetType("Tile");
    }

    
    // DEVELOPED BY: Sheldon
    /* Constructor for the Tile class with a type
     * @param - type: the type of the tile */
    public Tile(boolean isSolid) {
        this();
        this.isSolid = isSolid;
        this.SetType("Tile");
    }

    
    // -- GETTERS and SETTERS --

    // DEVELOPED BY: Sheldon
    /* SetTextureRef sets the texture reference of the tile
     * @param - texture: the texture reference */

    public void SetTextureRef(Texture texture) {
        this.texture = texture;
    }

    // DEVELOPED BY: Sheldon
    public Texture GetTexture() {
        return this.texture;
    }

    // DEVELOPED BY: Sheldon
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
    /* SetSolid sets the tile to be solid or not
     * @param isSolid - boolean value to set the tile to solid/non-solid*/
    public void SetSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    // DEVELOPED BY: Sheldon
    /* IsSolid returns whether the tile is solid or not
     * @return - boolean */
    public boolean IsSolid() {
        return this.isSolid;
    }
}