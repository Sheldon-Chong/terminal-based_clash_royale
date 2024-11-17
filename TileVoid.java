// entire class by Sheldon

/*
 * Represents void spaces that troops are not allowed to trasspass
 */

public class TileVoid extends Tile {

    // -- CONSTRUCTORS -- 
    
    // DEVELOPED BY: Sheldon
    /* Constructor for TileEmpty 
     * @param type: the type of the tile
    */
    public TileVoid(int type) {
        this();
        this.SetType(type);
        this.SetStrType("TileEmpty");
    }

    // DEVELOPED BY: Sheldon
    /* Constructor for TileEmpty */
    public TileVoid() {
        this.SetStrType("TileEmpty");
        this.GetTexture().setTexture(TextureSet.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|     ",
            "|     "
        });

        this.GetTexture().setTexture(TextureSet.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "     |",
            "     |"
        });
        this.GetTexture().setTexture(TextureSet.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|____."
        });
        this.GetTexture().setTexture(TextureSet.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     | ",
            ".____|"
        });
        this.GetTexture().setTexture(TextureSet.SIDE_LEFT, new String [] {
            "|     ",
            "|     ",
            "|     "
        });
        this.GetTexture().setTexture(TextureSet.SIDE_RIGHT, new String [] {
            "     |",
            "     |",
            "     |"
        });
    }
}
