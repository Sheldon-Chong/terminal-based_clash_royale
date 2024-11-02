// Written by Daiki

public class Golem extends Troop {

    public Golem(Pos startingPos, Player player) {
        super(startingPos, 'G', player);
        this.SetHP(100); // Example value, modify as needed
        this.SetAttack(20); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
