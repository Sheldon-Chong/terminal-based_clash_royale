public class Tile {
    private Obj object;
    public char right_side = ' ';
    public char left_side = ' ';
    public char top_side = ' ';
    public char bottom_side = ' ';
    private Tile [][] gridRef;
    private Pos pos;

    static final int NEIGHBOUR_LEFT = 0;
    static final int NEIGHBOUR_RIGHT = 1;
    static final int NEIGHBOUR_UP = 2;
    static final int NEIGHBOUR_DOWN = 3;

    public Tile() {
        
    }

    public Tile(Obj object) {
        this.SetObject(object);
    }

    public Tile(Obj object, Tile [][] gridRef, Pos pos) {
        this.SetObject(object);
        this.gridRef = gridRef;
        this.pos = pos;
    }

    public Pos getPos() {
        return this.pos;
    }

    public Tile CopySelf() {
        Tile tile = new Tile(this.object, this.gridRef, this.pos);

        tile.right_side = this.right_side;
        tile.left_side = this.left_side;
        tile.top_side = this.top_side;
        tile.bottom_side = this.bottom_side;
        
        return tile;
    }

    public Tile [] GetNeighbours() {
        Tile [] neighbours = new Tile[4];

        for (int i = 0; i < 4; i++)
            neighbours[i] = null;

        if (pos.x -1 >= 0)
            neighbours[NEIGHBOUR_LEFT] = this.gridRef[pos.y][pos.x - 1];
        
        if (pos.y - 1 >= 0)
            neighbours[NEIGHBOUR_UP] = this.gridRef[pos.y - 1][pos.x];
        
        if (pos.x + 1 < this.gridRef[0].length)
            neighbours[NEIGHBOUR_RIGHT] = this.gridRef[pos.y][pos.x + 1];
        
        if (pos.y + 1 < this.gridRef.length)
            neighbours[NEIGHBOUR_DOWN] = this.gridRef[pos.y + 1][pos.x];

        return neighbours;
    }

    public void     SetObject(Obj object) {
        this.object = object;
    }

    public Obj getObject() {
        return this.object;
    }

    public String getRepr(){
        return "%c%d".formatted(((Troop)object).getNameInitial(), ((Troop)object).getHP());
    }
}
