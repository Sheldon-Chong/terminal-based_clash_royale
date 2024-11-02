// Written by Daiki

public class SpellZap extends Spell {

    public SpellZap(int elixirCost, int radius, int damage) {
        super(elixirCost, radius, damage, 1); // Fixed duration of 1 for instant effect
    }

    
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        cast(targetPos, gameSysRef); // Pass gameSysRef to the cast method
    }

    
    public void cast(Pos targetPos, GameSystem gameSysRef) {
        if (gameSysRef.isWithinBoard(targetPos)) {
            for (int y = -getRadius(); y <= getRadius(); y++) {
                for (int x = -getRadius(); x <= getRadius(); x++) {
                    Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                    if (gameSysRef.isWithinBoard(impactPos)) {
                        Obj obj = gameSysRef.GetCell(impactPos).getObject();
                        if (obj instanceof Troop) {
                            ((Troop) obj).DecreaseHP(getDamage());
                        }
                    }
                }
            }
        }
    }
}
