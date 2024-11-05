
// Entire class written by Sheldon

/*
 * The Pos class is used to store the position of objects in the world
 * It exists as a separate class to make it easier to manage positions  
 * It contains methods for adding, subtracting, and comparing positions
 */

public class Pos {

    // ATTRIBUTES
    public int x;
    public int y;


    // CONSTRUCTOR
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // GETTER AND SETTER
    public void setPos(int x, int y) { this.x = x; this.y = y; }
    public Pos  getPos() { return new Pos(this.x, this.y); }


    // PUBLIC METHODS
    //      ADD OPERATIONS
    public Pos Add(int xVal, int yVal) {
        Pos pos = new Pos(this.x + xVal, this.y + yVal);
        return pos;
    }

    public Pos Add(Pos pos) {
        Pos newPos = new Pos(this.x + pos.x, this.y + pos.y);
        return newPos;
    }

    public Pos Multiply(int xVal, int yVal) {
        Pos pos = new Pos(this.x * xVal, this.y * yVal);
        return pos;
    }

    // COMPARE OPERATIONS
    public boolean IsEquals(Pos pos) {
        return (this.x == pos.x && this.y == pos.y);
    }

    public Pos Copy() {
        return new Pos(this.x, this.y);
    }

    // MATH OPERATIONS
    public double CalcDistance(Pos pos) {
        return Math.sqrt(Math.pow(this.x - pos.x, 2) + Math.pow(this.y - pos.y, 2));
    }

}
