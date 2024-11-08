

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


    // -- CONSTANTS --

    public static final int TEXTURE_ALIVE = 0;
    public static final int TEXTURE_DESTROYED = 1;


    // -- CONSTRUCTORS --

    public TileTower () {
        this.AddTextureToSet(new Texture());
        this.setTextureSet(TEXTURE_ALIVE, this.TextureTowerAlive());
        this.setTextureSet(TEXTURE_DESTROYED, this.TextureTowerDead());
    }

    public TileTower (int type) {
        this();
        this.SetType(type);
    }
    
    public TileTower (int type, Tower parentTower) {
        this.SetType(type);
        this.parentTower = parentTower;
    }


    // -- SETTERS AND  GETTERS --

    public void  SetParent(Tower parentTower) { this.parentTower = parentTower; }
    public Tower GetParent() { return this.parentTower; }


    // -- TEXTURES --

    /* Texture for the tower when it is alive 
     * @return - Texture
     */
    private Texture TextureTowerAlive() {
        Texture towerAlive = new Texture();

        towerAlive.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            ".____.",
            "|  __|",
            "|_|   "
        });

        towerAlive.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            ".____.",
            "|__  |",
            "   |_|"
        });
        towerAlive.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            ".     ",
            "| |__ ",
            "|____|"
        });
        towerAlive.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "     .",
            " __| | ",
            "|____|"
        });
        towerAlive.setTexture(Tile.SIDE_LEFT, new String [] {
            "  ",
            " :",
            " : "
        });
        towerAlive.setTexture(Tile.SIDE_RIGHT, new String [] {
            "     ",
            "    :",
            "    :"
        });
        towerAlive.setTexture(Tile.SIDE_BOTTOM, new String [] {
            "     ",
            "     ",
            " ----"
        });
        towerAlive.setTexture(Tile.SIDE_TOP, new String [] {
            "     ",
            " ----",
            "     "
        });
        return towerAlive;
    }

    /* Texture for the tower when it is dead
     * @return - Texture
     */
    private Texture TextureTowerDead() {
        Texture towerDead = new Texture();

        towerDead.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            "._____",
            "|     ",
            "|     "
        });

        towerDead.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            "_____.",
            "     |",
            "     |"
        });
        towerDead.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|_____"
        });
        towerDead.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     |",
            "_____|"
        });
        towerDead.setTexture(Tile.SIDE_LEFT, new String [] {
            " ",
            "|",
            "| "
        });
        towerDead.setTexture(Tile.SIDE_RIGHT, new String [] {
            "      ",
            "     |",
            "     |"
        });
        towerDead.setTexture(Tile.SIDE_BOTTOM, new String [] {
            "      ",
            "      ",
            "_____ "
        });
        towerDead.setTexture(Tile.SIDE_TOP, new String [] {
            "_____ ",
            "      ",
            "      "
        });
        return towerDead;
    }

}
    