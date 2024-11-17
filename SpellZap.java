// DEVELOPED BY: Daiki

public class SpellZap extends Spell {
    
    // -- CONSTRUCTORS --

    // DEVELOPED BY: Daiki
    /* Constructor for SpellZap */
    public SpellZap() {
        super(new Pos(0,0), new Pos(0,0), 1, 1, 2);
    }
    
    // DEVELOPED BY: Daiki
    /* Constructor to initialize the SpellZap with cost, radius, and damage */
    public SpellZap(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1, 2);
        this.SetPos(startingPos);
    }

    // DEVELOPED BY: Daiki
    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        
        int targetsHit = 0;

        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {

                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);

                if (gameSysRef.IsWithinBoard(impactPos)) {
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();

                    if (obj instanceof Troop && targetsHit < 4) {
                        ((Troop) obj).DecreaseHP(2);
                        targetsHit ++;
                    }
                }
            }
        }
    }

}
