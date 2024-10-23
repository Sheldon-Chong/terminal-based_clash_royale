public class Pos {
    public int x;
    public int y;

    // CONSTRUCTOR
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // GETTER AND SETTER
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos getPos() {
        return new Pos(this.x, this.y);
    }

    // ADD OPERATIONS
    public Pos Add(int xVal, int yVal) {
        Pos pos = new Pos(this.x + xVal, this.y + yVal);
        return pos;
    }

    public Pos Add(Pos pos) {
        Pos newPos = new Pos(this.x + pos.x, this.y + pos.y);
        return newPos;
    }

    // COMPARE OPERATIONS
    public boolean isEquals(Pos pos) {
        return (this.x == pos.x && this.y == pos.y);
    }

    public Pos copy() {
        return new Pos(this.x, this.y);
    }

    // MATH OPERATIONS
    public double calcDistance(Pos pos) {
        return Math.sqrt(Math.pow(this.x - pos.x, 2) + Math.pow(this.y - pos.y, 2));
    }

}


    // public static final int ADD = 0;
    // public static final int SUBTRACT = 1;
    // public static final int MULTIPLY = 2;
    // public static final int DIVIDE = 3;
    // public static final int MODULO = 4;

    // public static final int isEquals = 0;
    // public static final int GREATER = 1;
    // public static final int LESS = 2;
