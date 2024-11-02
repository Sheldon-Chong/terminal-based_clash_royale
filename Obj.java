
// entire class by Sheldon
public abstract class Obj {
    
    private Pos pos;
    
    public Obj () {

    }

    public Pos getPos() {
        return pos;
    }

    public double calcDistance (Obj obj) {
        return pos.calcDistance(obj.getPos());
    }

    public void setPos (Pos pos) {
        this.pos = pos;
    }
}
