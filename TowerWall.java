// entire class by Sheldon

/*
 * The TowerWall class are walls that make up the towers in the game
 * A series of adjacently connected TowerWalls make up a *complete tower*
 * This complete tower is referrenced by *parentTower* 
 * (aka one Towerclass will have several adjacent TowerWalls that belong under it)
 */

public class TowerWall extends Tileset {
    private int type;
    private Tower parentTower;

    public TowerWall () {
        this.setTexture(Tileset.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|  __|",
            "|_|   "
        });

        this.setTexture(Tileset.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "|__  |",
            "   |_|"
        });
        this.setTexture(Tileset.CORNER_BOTTOM_LEFT, new String [] {
            ".     ",
            "| |__ ",
            "|____|"
        });
        this.setTexture(Tileset.CORNER_BOTTOM_RIGHT, new String [] {
            "     .",
            " __| | ",
            "|____|"
        });
        this.setTexture(Tileset.SIDE_LEFT, new String [] {
            "  ",
            " :",
            " : "
        });
        this.setTexture(Tileset.SIDE_RIGHT, new String [] {
            "     ",
            "    :",
            "    :"
        });
        this.setTexture(Tileset.SIDE_BOTTOM, new String [] {
            "     ",
            "     ",
            " ----"
        });
        this.setTexture(Tileset.SIDE_TOP, new String [] {
            "     ",
            " ----",
            "     "
        });
    }

    public TowerWall (int type) {
        this();
        this.setType(type);
    }
    
    public TowerWall (int type, Tower parentTower) {
        this.setType(type);
        this.parentTower = parentTower;
    }

    public void SetParent(Tower parentTower) {
        this.parentTower = parentTower;
    }

    public Tower GetParent() {
        return this.parentTower;
    }
}
    