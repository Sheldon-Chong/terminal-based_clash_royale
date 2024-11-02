// Written by Daiki

public class TroopGoblins extends Troop {

    public TroopGoblins(Pos startingPos, Player player) {
        super(startingPos, 'O', player);
        this.SetHP(40); // Example value
        this.SetAttack(10); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
