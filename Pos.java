
/*
 * The Pos class is used to store the position of objects in the world
 * It exists as a separate class to make it easier to manage positions  
 * It contains methods for adding, subtracting, and comparing positions
 */

public class Pos {

    // -- ATTRIBUTES --
    
    public int x;
    public int y;


    // -- CONSTRUCTOR --
    
    // DEVELOPED BY: Sheldon
    /* constructor for the Pos class */
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // -- GETTER AND SETTER -- 
    
    // DEVELOPED BY: Sheldon
    /* SetPos sets the position of the object using x and y coordinates
     * @param x - the x coordinate of the new position
     * @param y - the y coordinate of the new positions */
    public void SetPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // DEVELOPED BY: Sheldon
    /* GetPos returns the position of the object
     * @return - Pos */
    public Pos GetPos() {
        return new Pos(this.x, this.y);
    }


    // -- PUBLIC METHODS --

    // OPERATIONS

    // DEVELOPED BY: Sheldon
    /* adds current position with an xy value
     * @param xVal - the x value to add
     * @param yVal - the y value to add
     * @return - Pos */
    public Pos Add(int xVal, int yVal) {
        Pos pos = new Pos(this.x + xVal, this.y + yVal);
        return pos;
    }

    // DEVELOPED BY: Sheldon
    /* adds current position with another position
     * @param pos - the position to add
     * @return - Pos */
    public Pos Add(Pos pos) {
        Pos newPos = new Pos(this.x + pos.x, this.y + pos.y);
        return newPos;
    }

    // DEVELOPED BY: Sheldon
    /* multiplies current position with an xy value
     * @param xVal - the x value to multiply
     * @param yVal - the y value to multiply
     * @return - Pos */
    public Pos Multiply(int xVal, int yVal) {
        Pos pos = new Pos(this.x * xVal, this.y * yVal);
        return pos;
    }

    // COMPARE OPERATIONS

    // DEVELOPED BY: Sheldon
    /* checks if two positions are equal
     * @param pos - the position to compare to
     * @return - boolean */
    public boolean IsEquals(Pos pos) {
        return (this.x == pos.x && this.y == pos.y);
    }
    
    // COMPLEX OPERATIONS

    // DEVELOPED BY: Sheldon
    /* calculates the distance between two positions by using the pythagorean theorem
     * @param pos - the position to calculate the distance to
     * @return - double */
    public double DistanceFrom(Pos pos) {
        return Math.sqrt(Math.pow(this.x - pos.x, 2) + Math.pow(this.y - pos.y, 2));
    }

    // OTHERS

    // DEVELOPED BY: Sheldon
    /* creates a copy of the current position
     * @return - Pos */
    public Pos Copy() {
        return new Pos(this.x, this.y);
    }
}
