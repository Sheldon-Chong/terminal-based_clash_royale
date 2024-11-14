// DEVELOPED BY: Daiki

public class TroopGiant extends Troop {

    // DEVELOPED BY: Daiki
    public TroopGiant(Pos startingPos, Player player) {
        super(startingPos, "giant", player); // 'giant' for Giant
        this.SetHP(120); // Example value, modify as needed
        this.SetAttack(25); // Example attack value
    }
}
