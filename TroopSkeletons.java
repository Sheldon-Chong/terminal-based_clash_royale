// DEVELOPED BY: DAIKI

public class TroopSkeletons extends Troop {

    // DEVELOPED BY: Daiki
    public TroopSkeletons(Pos startingPos, Player player) {
        super(startingPos, "skeletons", player); // 'skeletons' for Skeletons
        this.SetHP(20); // Example value
        this.SetAttack(5); // Example attack value
    }

}
