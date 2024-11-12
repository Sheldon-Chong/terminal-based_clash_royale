// DEVELOPED BY: Daiki

public class TroopBarbarian extends Troop {

    public TroopBarbarian(Pos startingPos, Player player) {
        super(startingPos, "barbarian", player); // 'barbarian' for Barbarian
        this.SetHP(5); // Example value
        this.SetAttack(2); // Example attack value
    }

}
