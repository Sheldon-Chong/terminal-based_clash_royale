// DEVELOPED BY: Daiki

public class SpellFireball extends Spell {

    // DEVELOPED BY: Daiki
    public SpellFireball() {
        super(new Pos(0,0), new Pos(0,0), 1, 1);
    }

    // DEVELOPED BY: Daiki
    public SpellFireball(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1);
    }
    
    // DEVELOPED BY: Daiki
    public void ApplyEffect() {

    }    

   
    // public void cast(Pos targetPos, GameSystem gameSysRef) {
    //     if (gameSysRef.IsWithinBoard(targetPos)) {
    //         for (int y = -GetRadius(); y <= GetRadius(); y++) {
    //             for (int x = -GetRadius(); x <= GetRadius(); x++) {
    //                 Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
    //                 if (gameSysRef.IsWithinBoard(impactPos)) {
    //                     Obj obj = gameSysRef.GetCell(impactPos).GetObject();
    //                     if (obj instanceof Troop) {
    //                         ((Troop) obj).DecreaseHP(GetDamage());
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
}
