// entire class by Sheldon

/*
 * Represents void spaces that troops are not allowed to trasspass
 */

public class TileEmpty extends Tile {

    // -- CONSTRUCTORS -- 
    public TileEmpty(int type) {
        this();
        this.SetType(type);
    }

    // DEVELOPED BY: Sheldon
    public TileEmpty() {
        this.GetTexture().setTexture(Texture.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|     ",
            "|     "
        });

        this.GetTexture().setTexture(Texture.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "     |",
            "     |"
        });
        this.GetTexture().setTexture(Texture.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|____."
        });
        this.GetTexture().setTexture(Texture.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     | ",
            ".____|"
        });
        this.GetTexture().setTexture(Texture.SIDE_LEFT, new String [] {
            "|     ",
            "|     ",
            "|     "
        });
        this.GetTexture().setTexture(Texture.SIDE_RIGHT, new String [] {
            "     |",
            "     |",
            "     |"
        });
    }
}
