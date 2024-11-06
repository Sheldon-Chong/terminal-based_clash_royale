// Written by Daiki

public class TroopGoblins extends Troop {

    public TroopGoblins(Pos startingPos, Player player) {
        super(startingPos, "goblins", player); // 'goblins' for Goblins
        this.SetHP(5); // Example value
        this.SetAttack(1); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
