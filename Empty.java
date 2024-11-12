public class Empty extends Tileset {
    public Empty(int type) {
        this();
        this.setType(type);
    }

    public Empty() {
        this.setTexture(Tileset.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|     ",
            "|     "
        });

        this.setTexture(Tileset.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "     |",
            "     |"
        });
        this.setTexture(Tileset.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|____."
        });
        this.setTexture(Tileset.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     | ",
            ".____|"
        });
        this.setTexture(Tileset.SIDE_LEFT, new String [] {
            "|     ",
            "|     ",
            "|     "
        });
        this.setTexture(Tileset.SIDE_RIGHT, new String [] {
            "     |",
            "     |",
            "     |"
        });
    }
}
