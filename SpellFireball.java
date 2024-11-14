// DEVELOPED BY: Daiki

public class SpellFireball extends Spell {

    // DEVELOPED BY: Daiki
    public SpellFireball() {
        super(new Pos(0,0), new Pos(0,0), 1, 1);
    }

    // DEVELOPED BY: Daiki
    public SpellFireball(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1);
        this.SetRadius(2);
        this.SetPos(startingPos);
    }
    
    // DEVELOPED BY: Daiki
    
}