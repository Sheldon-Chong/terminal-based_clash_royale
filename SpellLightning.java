// DEVELOPED BY: Daiki

public class SpellLightning extends Spell {
    private static final int MAX_TARGETS = 3;
    private static final int EFFECT_DURATION = 1; // Duration for the lightning spell

    // DEVELOPED BY: Daiki
    /* Constructor for initializing Lightning spell 
     * @param startingPos: the starting position of the spell */
    public SpellLightning(Pos startingPos) {
        super(startingPos.Add(-1,-1), startingPos.Add(1,1), 1, 1);
        this.SetPos(startingPos);
    }
    
    // DEVELOPED BY: Daiki
    

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
