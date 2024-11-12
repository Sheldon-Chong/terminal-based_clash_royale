// Entire class by Sheldon

/*
 * The cell object, which serves as a unit in the grid, which will then be visually represneted by the game system.
 * It's main purpose is to hold an object, and to be able to access it neighbour tiles. 
 * (neighbour tiles are tiles that are adjacent to the current cell)
 */

public class Cell {
    
    // -- CONSTANTS --
    static final int NEIGHBOUR_LEFT = 0;
    static final int NEIGHBOUR_RIGHT = 1;
    static final int NEIGHBOUR_UP = 2;
    static final int NEIGHBOUR_DOWN = 3;


    // -- ATTRIBUTES --
    private Obj object;
    private Cell [][] gridRef;
    private Pos pos;


    // -- PUBLIC METHODS --

    // CONSTRUCTORS
    public Cell() { }

    public Cell(Obj object) {
        this.SetObject(object);
    }

    public Cell(Obj object, Cell [][] gridRef, Pos pos) {
        this.SetObject(object);
        this.gridRef = gridRef;
        this.pos = pos;
    }

    // GETTERS AND SETTERS
    public Pos      GetPos() { return this.pos; }

    public void     SetObject(Obj object) { this.object = object; }
    public Obj      GetObject() { return this.object; }

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
    // Written by Daiki
    public Cell CopySelf() {
        // Deep copy the object if needed, e.g., by creating a new instance of the object
        Obj clonedObject = this.object; // Replace with deep copy if necessary
        return new Cell(clonedObject, this.gridRef, this.pos);
    }
}
