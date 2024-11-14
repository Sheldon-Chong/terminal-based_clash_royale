public abstract class Tile extends Obj {

    // -- CONSTANTS --

    public static final int INSIDE = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 2;
    public static final int CORNER_BOTTOM_LEFT = 3;
    public static final int CORNER_BOTTOM_RIGHT = 4;

    public static final int SIDE_RIGHT = 5;
    public static final int SIDE_LEFT = 6;
    public static final int SIDE_TOP = 7;
    public static final int SIDE_BOTTOM = 8;
    public static final int INDEPENDANT = 9;

    public static final int PIPE_H = 10;
    public static final int PIPE_V = 11;


    // -- ATTRIBUTES --

    private int type;
    private Texture texture;
    private boolean isSolid;


    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Default constructor for the Tile class */
    public Tile() {
        this.texture = new Texture();
    }

    
    // DEVELOPED BY: Sheldon
    /* Constructor for the Tile class with a type
     * @param - type: the type of the tile */
    public Tile(boolean isSolid) {
        this();
        this.isSolid = isSolid;
    }

    
    // -- GETTERS & SETTERS --

    // DEVELOPED BY: Sheldon
    /* SetTextureRef sets the texture reference of the tile
     * @param - texture: the texture reference */

    public void setTextureRef(Texture texture) {
        this.texture = texture;
    }

    // DEVELOPED BY: Sheldon
    public void setTexture(int textureID, String[] texture) {
        this.texture.setTexture(textureID, texture);
    }

    // DEVELOPED BY: Sheldon
    public Texture getTexture() {
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