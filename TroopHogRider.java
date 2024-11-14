// DEVELOPED BY: Daiki

public class TroopHogRider extends Troop {
    
    // DEVELOPED BY: Daiki
    public TroopHogRider(Pos startingPos, Player player) {
        super(startingPos, "hog rider", player); // 'hog rider' for Hog Rider
        this.SetHP(70); // Example value
        this.SetAttack(18); // Example attack value
    }
}
