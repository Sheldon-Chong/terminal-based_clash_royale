// Written by Daiki

public class TroopGiant extends Troop {

    public TroopGiant(Pos startingPos, Player player) {
        super(startingPos, "giant", player); // 'giant' for Giant
        this.SetHP(120); // Example value, modify as needed
        this.SetAttack(25); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
