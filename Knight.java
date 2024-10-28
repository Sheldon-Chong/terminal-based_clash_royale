// Written by Daiki

public class Knight extends Troop {

    public Knight(Pos startingPos, Player player) {
        super(startingPos, 'K', player); // 'K' for Knight
        this.SetHP(55); // Example value
        this.SetAttack(10); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
