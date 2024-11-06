// Written by Daiki

public class TroopHogRider extends Troop {

    public TroopHogRider(Pos startingPos, Player player) {
        super(startingPos, "hog rider", player); // 'hog rider' for Hog Rider
        this.SetHP(70); // Example value
        this.SetAttack(18); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
