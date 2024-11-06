// Written by Daiki

public class TroopBarbarian extends Troop {

    public TroopBarbarian(Pos startingPos, Player player) {
        super(startingPos, "barbarian", player); // 'barbarian' for Barbarian
        this.SetHP(50); // Example value
        this.SetAttack(12); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
