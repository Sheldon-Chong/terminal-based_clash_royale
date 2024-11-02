// Written by Daiki

public class TroopPEKKA extends Troop {

    public TroopPEKKA(Pos startingPos, Player player) {
        super(startingPos, 'P', player); // 'P' for P.E.K.K.A
        this.SetHP(130); // Example value
        this.SetAttack(30); // Example attack value
    }

    
    public void MoveTo(Pos dest) {
        // Code for moving towards enemies
    }
}
