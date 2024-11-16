// DEVELOPED BY: Daiki

public class TroopKnight extends Troop {
    
    // DEVELOPED BY: Daiki
    public TroopKnight(Pos startingPos, Player player) {
        super(startingPos, "knight", player); // 'knight' for Knight
        this.SetHP(6); // Example value
        this.SetAttack(1); // Example attack value
    }
}
