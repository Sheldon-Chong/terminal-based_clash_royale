// Written by Daiki

public class TroopGolem extends Troop {

    public TroopGolem(Pos startingPos, Player player) {
        super(startingPos, "golem", player); // 'golem' for Golem
        this.SetHP(100); // Example value, modify as needed
        this.SetAttack(20); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
