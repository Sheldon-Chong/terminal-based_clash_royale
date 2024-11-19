// entire class by Sheldon

/*
 * Represents void spaces that troops are not allowed to trasspass
 */

public class TileVoid extends Tile {

    // -- CONSTRUCTORS -- 
    
    // DEVELOPED BY: Sheldon
    /* Constructor for TileEmpty 
     * @param type: the type of the tile */
    public TileVoid(int type) {
        this();
        this.SetType(type);
        this.SetStrType("TileEmpty");
    }

    // DEVELOPED BY: Sheldon
    /* Constructor for TileEmpty */
    public TileVoid() {
        this.SetStrType("TileEmpty");
        this.GetAppearance().SetTexture(TextureSet.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|     ",
            "|     "
        });

        this.GetAppearance().SetTexture(TextureSet.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "     |",
            "     |"
        });
        this.GetAppearance().SetTexture(TextureSet.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|____."
        });
        this.GetAppearance().SetTexture(TextureSet.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     | ",
            ".____|"
        });
        this.GetAppearance().SetTexture(TextureSet.SIDE_LEFT, new String [] {
            "|     ",
            "|     ",
            "|     "
        });
        this.GetAppearance().SetTexture(TextureSet.SIDE_RIGHT, new String [] {
            "     |",
            "     |",
            "     |"
        });
    }
}
