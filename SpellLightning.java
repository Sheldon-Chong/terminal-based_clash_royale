// DEVELOPED BY: Daiki

public class SpellLightning extends Spell {

    // DEVELOPED BY: Daiki
    /* Constructor for initializing Lightning spell 
     * @param startingPos: the starting position of the spell */
    public SpellLightning(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1, 1);
        this.SetPos(startingPos);
    }
    
    // DEVELOPED BY: Daiki
    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        
        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {
                
                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                
                if (gameSysRef.IsWithinBoard(impactPos)) {
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();

                    if (obj instanceof Troop)
                        ((Troop) obj).DecreaseHP(3);
                }
            }
        }
    }

    // public void cast(Pos targetPos, GameSystem gameSysRef) {
    //     Troop[] allTroops = gameSysRef.GetTroops();
    //     int targetsHit = 0;

    //     for (Troop troop : allTroops) {
    //         if (troop != null && troop.GetPos().CalcDistance(targetPos) <= this.GetRadius() && targetsHit < MAX_TARGETS) {
    //             troop.DecreaseHP(this.GetDamage());
    //             if (troop.GetHP() <= 0) {
    //                 gameSysRef.destroyTroop(troop);
    //             }
    //             targetsHit++;
    //         }
    //     }
    // }

    // Since this class does not use duration directly, this method could be omitted unless required elsewhere

}
