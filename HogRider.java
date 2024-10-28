// Written by Daiki

public class HogRider extends Troop {

    public HogRider(Pos startingPos, Player player) {
        super(startingPos, 'H', player); // 'H' for Hog Rider
        this.SetHP(70); // Example value
        this.SetAttack(18); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
