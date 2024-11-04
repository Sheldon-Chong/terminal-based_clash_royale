
// entire class by Sheldon

/*
 * The Obj class is the superclass for all objects in the game
 * Objects reffer to anything that can be placed in the world grid
 * It contains a position, which is used to determine where the object is in the world
 */

public abstract class Obj {
    
    // ATTRIBUTES
    private Pos pos;

    
    // CONSTRUCTOR
    public Obj () { }

    
    // GETTERS AND SETTERS
    public Pos  getPos() { return pos; }
    public void setPos (Pos pos) { this.pos = pos; }

    
    // PUBLIC METHODS
    public double calcDistance (Obj obj) {
        return pos.calcDistance(obj.getPos());
    }
}
