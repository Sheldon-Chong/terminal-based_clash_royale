// DEVELOPED BY: Daiki

public abstract class Spell extends Obj {
    
    // -- ATTRIBUTES --
    
    private int radius;
    private int deployTime;
    private int duration;


    // -- CONSTRUCTORS --

    public Spell() {
        super();
        this.radius = 0;
        this.duration = 0;
    }

    public Spell(Pos startingPos) {
        super();
        this.SetPos(startingPos);
    }


    // -- GETTERS AND SETTERS --

    public int  GetRadius() { return radius; }
    public void SetRadius (int radius) { this.radius = radius; }

    public int  GetDuration() { return duration; }
    public void SetDuration (int duration) { this.duration = duration; }

    public int  GetDeployTime() { return deployTime; }
    public void SetDeployTime (int deployTime) { this.deployTime = deployTime; }

}
