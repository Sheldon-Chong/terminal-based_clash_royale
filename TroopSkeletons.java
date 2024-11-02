// Written by Daiki

public class TroopSkeletons extends Troop {

    public TroopSkeletons(Pos startingPos, Player player) {
        super(startingPos, 'S', player); // 'S' for Skeletons
        this.SetHP(20); // Example value
        this.SetAttack(5); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
