// Written by Daiki

public class SpellFireball extends Spell {

    public SpellFireball(int elixirCost, int radius, int damage) {
        super(elixirCost, radius, damage, 1);  // Fireball has a fixed duration of 1
    }

    // Implement the required deploy method and pass GameSystem as a parameter
    
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        cast(targetPos, gameSysRef);  // Pass gameSysRef to the cast method
    }

   
    public void cast(Pos targetPos, GameSystem gameSysRef) {
        if (gameSysRef.IsWithinBoard(targetPos)) {
            for (int y = -GetRadius(); y <= GetRadius(); y++) {
                for (int x = -GetRadius(); x <= GetRadius(); x++) {
                    Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                    if (gameSysRef.IsWithinBoard(impactPos)) {
                        Obj obj = gameSysRef.GetCell(impactPos).getObject();
                        if (obj instanceof Troop) {
                            ((Troop) obj).DecreaseHP(GetDamage());
                        }
                    }
                }
            }
        }
    }
}
