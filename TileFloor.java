

public class TileFloor extends Tile {
    public TileFloor () {
        this.SetStrType("TileFloor");
    }
    
    public TileFloor (int type) {
        SetType(type);
        this.SetStrType("TileFloor");
    }

    public TileFloor (Pos pos) {
        this();
        this.SetPos(pos);
    }
}
