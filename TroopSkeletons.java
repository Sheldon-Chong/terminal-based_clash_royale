// DEVELOPED BY: Daiki

public class TroopSkeletons extends Troop {

    public TroopSkeletons(Pos startingPos, Player player) {
        super(startingPos, "skeletons", player); // 'skeletons' for Skeletons
        this.SetHP(20); // Example value
        this.SetAttack(5); // Example attack value
    }

}
