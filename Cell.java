// Entire class by Sheldon

/*
 * The cell object, which serves as a unit in the grid, which will then be visually represneted by the game system.
 * It's main purpose is to hold an object, and to be able to access it neighbour tiles. 
 * (neighbour tiles are tiles that are adjacent to the current cell)
 */

public class Cell {
    private Obj object;
    public char right_side = ' ';
    public char left_side = ' ';
    public char top_side = ' ';
    public char bottom_side = ' ';
    private Cell [][] gridRef;
    private Pos pos;

    static final int NEIGHBOUR_LEFT = 0;
    static final int NEIGHBOUR_RIGHT = 1;
    static final int NEIGHBOUR_UP = 2;
    static final int NEIGHBOUR_DOWN = 3;


    public Cell() {
        
    }


    public Cell(Obj object) {
        this.SetObject(object);
    }


    public Cell(Obj object, Cell [][] gridRef, Pos pos) {
        this.SetObject(object);
        this.gridRef = gridRef;
        this.pos = pos;
    }


    public Pos getPos() {
        return this.pos;
    }


    public Cell CopySelf() {
        Cell cell = new Cell(this.object, this.gridRef, this.pos);
        
        return cell;
    }


    public Cell [] GetNeighbours() {
        Cell [] neighbours = new Cell[4];

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
        return "%c%d".formatted(((Troop)object).getNameInitial(), ((Troop)object).GetHP());
    }
}
