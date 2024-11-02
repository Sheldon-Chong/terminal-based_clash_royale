// Written by Daiki

public class ElixirGolem extends Troop {

    public ElixirGolem(Pos startingPos, Player player) {
        super(startingPos, 'E', player);
        this.SetHP(80); // Example value, modify as needed
        this.SetAttack(15); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
