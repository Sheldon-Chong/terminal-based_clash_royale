// Written by Daiki

public class TroopElixirGolem extends Troop {

    public TroopElixirGolem(Pos startingPos, Player player) {
        super(startingPos, 'E', player);
        this.SetHP(80); // Example value, modify as needed
        this.SetAttack(15); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards towers
    }
}
