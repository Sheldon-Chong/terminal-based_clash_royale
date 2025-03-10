
// entire class by Sheldon

/*
 * The Obj class is the superclass for all objects in the game
 * Objects reffer to anything that can be placed in the world grid
 * It contains a position, which is used to determine where the object is in the world
 */

public class Obj {
    
    // -- ATTRIBUTES --

    private Pos pos;
    private String type;

    
    // -- CONSTRUCTOR --

    // DEVELOPED BY: Sheldon
    /* Default constructor for the Obj class*/
    public Obj () { 
        this.SetStrType("Obj");
    }

    // DEVELOPED BY: Sheldon
    /* Constructor for the Obj class with a starting position
     * @param - startPos: the starting position of the object */
    public Obj (Pos startPos) {
        this();
        pos = startPos;
    }

    
    // -- GETTERS AND SETTERS --

    public String GetStrType() {
        return type;
    }

    public void SetStrType(String type) {
        this.type = type;
    }

    // DEVELOPED BY: Sheldon
    /* GetPos returns the position of the object
     * @return - Pos */
    public Pos  GetPos() {
        return pos; 
    }

    // DEVELOPED BY: Sheldon
    /* SetPos sets the position of the object'
     * @param - Pos: the new position */
    public void SetPos (Pos pos) { 
        this.pos = pos;
    }

    // DEVELOPED BY: Sheldon
    /* SetPos sets the position of the object using x and y coordinates
     * @param - int x: the x coordinate of the new position
     * @param - int y: the y coordinate of the new positions */
    public void SetPos (int x, int y) {
        this.pos = new Pos(x, y); 
    }

    
    // -- PUBLIC METHODS --

    // DEVELOPED BY: Sheldon
    /* calls CalcDistance using the object's position 
     * @param - obj: the object to calculate the distance to
     * @return - the distance between the two objects */
    public double CalcDistance (Obj obj) {
        return pos.DistanceFrom(obj.GetPos());
    }


    // DEVELOPED BY: Sheldon
    /* IsType checks if the object falls within an array of types
     * @param - objects: an array of strings representing the types to check against
     * @return - true if the object is of any of the types in the array, false otherwise */
    public boolean IsType(String[] objects) {
    
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].equals(this.GetStrType()))
                return true;
        }
        return false;
    }
}
