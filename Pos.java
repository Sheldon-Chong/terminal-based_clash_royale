
// Entire class DEVELOPED BY: Sheldon

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
    
    /* constructor for the Pos class */
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // -- GETTER AND SETTER -- 
    
    public void SetPos(int x, int y) { this.x = x; this.y = y; }
    public Pos  GetPos() { return new Pos(this.x, this.y); }


    // -- METHODS --

    // OPERATIONS

    /* adds current position with an xy value
     * @param xVal - the x value to add
     * @param yVal - the y value to add
     * @return - Pos
     */
    public Pos Add(int xVal, int yVal) {
        Pos pos = new Pos(this.x + xVal, this.y + yVal);
        return pos;
    }

    /* adds current position with another position
     * @param pos - the position to add
     * @return - Pos
     */
    public Pos Add(Pos pos) {
        Pos newPos = new Pos(this.x + pos.x, this.y + pos.y);
        return newPos;
    }

    /* multiplies current position with an xy value
     * @param xVal - the x value to multiply
     * @param yVal - the y value to multiply
     * @return - Pos
     */
    public Pos Multiply(int xVal, int yVal) {
        Pos pos = new Pos(this.x * xVal, this.y * yVal);
        return pos;
    }

    // COMPARE OPERATIONS

    /* checks if two positions are equal
     * @param pos - the position to compare to
     * @return - boolean
     */
    public boolean IsEquals(Pos pos) {
        return (this.x == pos.x && this.y == pos.y);
    }
    

    // COMPLEX OPERATIONS

    /* calculates the distance between two positions by using the pythagorean theorem
     * @param pos - the position to calculate the distance to
     * @return - double
     */
    public double CalcDistance(Pos pos) {
        return Math.sqrt(Math.pow(this.x - pos.x, 2) + Math.pow(this.y - pos.y, 2));
    }

    // OTHERS

    public Pos Copy() {
        return new Pos(this.x, this.y);
    }
}
