// entire class by Sheldon

/*
 * Represents void spaces that troops are not allowed to trasspass
 */

public class TileEmpty extends Tile {

    // -- CONSTRUCTORS -- 
    public TileEmpty(int type) {
        this();
        this.setType(type);
    }

    public TileEmpty() {
        this.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|     ",
            "|     "
        });

        this.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "     |",
            "     |"
        });
        this.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|____."
        });
        this.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     | ",
            ".____|"
        });
        this.setTexture(Tile.SIDE_LEFT, new String [] {
            "|     ",
            "|     ",
            "|     "
        });
        this.setTexture(Tile.SIDE_RIGHT, new String [] {
            "     |",
            "     |",
            "     |"
        });
    }
}
