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
            "._    ",
            "| |__ ",
            "|____|"
        });
        this.setTexture(Tileset.CORNER_BOTTOM_RIGHT, new String [] {
            "    _.",
            " __| | ",
            "|____|"
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
    