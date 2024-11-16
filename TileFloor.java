

public class TileFloor extends Tile {
    public TileFloor () {
        this.SetType("TileFloor");
    }
    
    public TileFloor (int type) {
        SetType(type);
        this.SetType("TileFloor");
    }
}
