// DEVELOPED BY: Daiki

public class SpellFireball extends Spell {

    // DEVELOPED BY: Daiki
    public SpellFireball() {
        super(new Pos(0,0), new Pos(0,0), 1, 1, 1);
    }

    // DEVELOPED BY: Daiki
    public SpellFireball(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1, 1);
        this.SetRadius(2);
        this.SetPos(startingPos);
    }
    
    // DEVELOPED BY: Daiki
    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        
        // iterate for all cells within radius
        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {

                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);

                if (gameSysRef.IsWithinBoard(impactPos)) {
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();
                    if (obj instanceof Troop) {
                        ((Troop) obj).DecreaseHP(2);
                    }
                }

            }
        }
    }
}