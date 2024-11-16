// DEVELOPED BY: Daiki

public class TroopGiant extends Troop {

    // DEVELOPED BY: Daiki
    public TroopGiant(Pos startingPos, Player player) {
        super(startingPos, "giant", player); // 'giant' for Giant
        this.SetHP(12); // Example value, modify as needed
        this.SetAttack(2); // Example attack value
    }
}
