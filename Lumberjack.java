// Written by Daiki

public class Lumberjack extends Troop {

    public Lumberjack(Pos startingPos, Player player) {
        super(startingPos, 'L', player); // 'L' for Lumberjack
        this.SetHP(60); // Example value
        this.SetAttack(15); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
