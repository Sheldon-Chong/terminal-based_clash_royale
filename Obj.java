
// entire class by Sheldon

/*
 * The Obj class is the superclass for all objects in the game
 * Objects reffer to anything that can be placed in the world grid
 * It contains a position, which is used to determine where the object is in the world
 */

public abstract class Obj {
    
    // -- ATTRIBUTES --

    private Pos pos;

    
    // -- CONSTRUCTOR --

    public Obj () { }

    
    // -- GETTERS AND SETTERS --

    public Pos  GetPos() { return pos; }
    public void SetPos (Pos pos) { this.pos = pos; }
    public void SetPos (int x, int y) { this.pos = new Pos(x, y); }

    
    // -- PUBLIC METHODS --

    // DEVELOPED BY: Sheldon
    /* calls CalcDistance using the object's position */
    public double CalcDistance (Obj obj) {
        return pos.CalcDistance(obj.GetPos());
    }
}
