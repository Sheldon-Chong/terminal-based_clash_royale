public class TowerWall extends Tileset {
    private int type;
    private Tower parentTower;

    public TowerWall () {
        
    }

    public TowerWall (int type) {
        this.setType(type);
    }
    
    public TowerWall (int type, Tower parentTower) {
        this.setType(type);
        this.parentTower = parentTower;
    }
}
    