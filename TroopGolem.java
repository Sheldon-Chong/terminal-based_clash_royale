// DEVELOPED BY: Daiki

public class TroopGolem extends Troop {
    
    // DEVELOPED BY: Daiki
    public TroopGolem(Pos startingPos, Player player) {
        super(startingPos, "golem", player); // 'golem' for Golem
        this.SetHP(100); // Example value, modify as needed
        this.SetAttack(20); // Example attack value
    }
}
