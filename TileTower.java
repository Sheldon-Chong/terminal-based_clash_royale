// entire class by Sheldon

/*
 * The TowerWall class are walls that make up the towers in the game
 * A series of adjacently connected TowerWalls make up a *complete tower*
 * This complete tower is referrenced by *parentTower* 
 * (aka one Towerclass will have several adjacent TowerWalls that belong under it)
 */

public class TileTower extends Tile {

    // -- ATTRIBUTES --
    private int     type;
    private Tower   parentTower;

    
    // -- PUBLIC METHODS --

    // CONSTRUCTORS
    public TileTower () {
        this.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|  __|",
            "|_|   "
        });

        this.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "|__  |",
            "   |_|"
        });
        this.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            ".     ",
            "| |__ ",
            "|____|"
        });
        this.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "     .",
            " __| | ",
            "|____|"
        });
        this.setTexture(Tile.SIDE_LEFT, new String [] {
            "  ",
            " :",
            " : "
        });
        this.setTexture(Tile.SIDE_RIGHT, new String [] {
            "     ",
            "    :",
            "    :"
        });
        this.setTexture(Tile.SIDE_BOTTOM, new String [] {
            "     ",
            "     ",
            " ----"
        });
        this.setTexture(Tile.SIDE_TOP, new String [] {
            "     ",
            " ----",
            "     "
        });
    }

    public TileTower (int type) {
        this();
        this.setType(type);
    }
    
    public TileTower (int type, Tower parentTower) {
        this.setType(type);
        this.parentTower = parentTower;
    }


    // SETTERS AND GETTERS
    public void  SetParent(Tower parentTower) { this.parentTower = parentTower; }
    public Tower GetParent() { return this.parentTower; }
}
    