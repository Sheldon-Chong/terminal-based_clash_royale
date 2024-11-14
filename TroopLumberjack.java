// DEVELOPED BY: Daiki

public class TroopLumberjack extends Troop {

    // DEVELOPED BY: Daiki
    public TroopLumberjack(Pos startingPos, Player player) {
        super(startingPos, "lumberjack", player); // 'lumberjack' for Lumberjack
        this.SetHP(60); // Example value
        this.SetAttack(15); // Example attack value
    }
}
