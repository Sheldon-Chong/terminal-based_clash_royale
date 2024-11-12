// DEVELOPED BY: Daiki

public class SpellFireball extends Spell {

    public SpellFireball(Pos startingPos) {
        super(startingPos);  // Fireball has a fixed duration of 1
    }

    // Implement the required deploy method and pass GameSystem as a parameter
    
    

   
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
