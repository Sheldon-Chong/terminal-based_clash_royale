// DEVELOPED BY: Daiki

public class TroopBarbarian extends Troop {

    public TroopBarbarian(Pos startingPos, Player player) {
        super(startingPos, "barbarian", player); // 'barbarian' for Barbarian
        this.SetHP(9); // Example value
        this.SetAttack(9); // Example attack value
    }

}
