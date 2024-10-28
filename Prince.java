// Written by Daiki

public class Prince extends Troop {

    public Prince(Pos startingPos, Player player) {
        super(startingPos, 'R', player); // 'R' for Prince
        this.SetHP(80); // Example value
        this.SetAttack(20); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
