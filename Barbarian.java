// Written by Daiki

public class Barbarian extends Troop {

    public Barbarian(Pos startingPos, Player player) {
        super(startingPos, 'B', player);
        this.SetHP(50); // Example value
        this.SetAttack(12); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
