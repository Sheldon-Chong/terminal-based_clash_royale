public class TextureSet {

    // -- CONSTANTS --

    public static final int INSIDE              = 0;
    public static final int CORNER_TOP_LEFT     = 1;
    public static final int CORNER_TOP_RIGHT    = 2;
    public static final int CORNER_BOTTOM_LEFT  = 3;
    public static final int CORNER_BOTTOM_RIGHT = 4;

    public static final int SIDE_RIGHT  = 5;
    public static final int SIDE_LEFT   = 6;
    public static final int SIDE_TOP    = 7;
    public static final int SIDE_BOTTOM = 8;
    public static final int INDEPENDANT = 9;

    public static final int PIPE_H = 10;
    public static final int PIPE_V = 11;

    private String [][]textures;


    // -- CONSTRUCTOR --

    // DEVELOPED BY: Sheldon
    /* Default constructor for Texture */
    public TextureSet() {
        this.textures = new String [12][];
    }


    // -- GETTERS AND SETTERS --

    // DEVELOPED BY: Sheldon
    /* SetTexture sets the texture of the tile
     * @param textureId - the id of the texture
     * @param texture - the texture of the tile */
    public void setTexture(int textureId, String[] texture) {
        this.textures[textureId] = texture;
    }

    // DEVELOPED BY: Sheldon
    /* GetTexture returns the texture of the tile
     * @param textureId - the id of the texture
     * @return - refference to the texture stored in the object */
    public String[] getTexture(int textureId) {
        return this.textures[textureId];
    }

    // DEVELOPED BY: Sheldon
    /* GetTextures returns all the textures of the tile
     * @return - refference to the textures stored in the object*/
    public String[][] GetTextures() {
        return this.textures;
    }

    // DEVELOPED BY: Sheldon
    /* SetAllTextures sets all the textures of the tile
     * @param texture - the texture of the tile */
    public void setAllTextures(String [] texture) {
        for (int i = 0; i < this.textures.length; i++)
        this.textures[i] = texture;
    }
}
