// entire class by Sheldon

/*
 * The tileset class is used to store the textures for the tiles
 * Various static objects in the world typically inherit from this class
 * The textures are stored in a 2D array, with each index representing a different part of the tile
 * Tilesets have 12 different types. The type is determined based on what tiles are adjacent to it
 * These 12 types are represented by constants in the class
 */

public abstract class Tile extends Obj {

    // CONSTANTS
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


    // ATTRIBUTES
    private int        type;
    private String [][]textures;


    // GETTERS & SETTERS
    public void      setTexture(int textureId, String [] texture) { this.textures[textureId] = texture; }
    public String [] getTexture(int textureId) { return this.textures[textureId]; }
    
    public void setAllTextures(String [] texture) {
        for (int i = 0; i < this.textures.length; i++)
        this.textures[i] = texture;
    }
    
    public void setType(int type) { this.type = type; }
    public int  getType() { return this.type; }


    // CONSTRUCTORS
    public Tile() {
        this.textures = new String [12][];
    }
}
