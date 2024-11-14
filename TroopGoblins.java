// DEVELOPED BY: Daiki

public class TroopGoblins extends Troop {

    // DEVELOPED BY: Daiki
    public TroopGoblins(Pos startingPos, Player player) {
        super(startingPos, "goblins", player); // 'goblins' for Goblins
        this.SetHP(5); // Example value
        this.SetAttack(1); // Example attack value
    }
}
