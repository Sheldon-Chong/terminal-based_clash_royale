// Written by Daiki

public class TroopKnight extends Troop {

    public TroopKnight(Pos startingPos, Player player) {
        super(startingPos, "knight", player); // 'knight' for Knight
        this.SetHP(55); // Example value
        this.SetAttack(10); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
