// Written by Daiki

import java.util.ArrayList;
import java.util.Collections;

public class SpellLightning extends Spell {

    public SpellLightning(int elixirCost, int radius, int damage) {
        super(elixirCost, radius, damage, 1); // Fixed duration of 1 for instant effect
    }

    
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        cast(targetPos, gameSysRef); // Pass gameSysRef to the cast method
    }

    
    public void cast(Pos targetPos, GameSystem gameSysRef) {
        ArrayList<Troop> targets = new ArrayList<>();

        if (gameSysRef.isWithinBoard(targetPos)) {
            for (int y = -getRadius(); y <= getRadius(); y++) {
                for (int x = -getRadius(); x <= getRadius(); x++) {
                    Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                    if (gameSysRef.isWithinBoard(impactPos)) {
                        Obj obj = gameSysRef.GetCell(impactPos).getObject();
                        if (obj instanceof Troop) {
                            targets.add((Troop) obj);
                        }
                    }
                }
            }
        }

        // Shuffle the targets and pick the first 3 to inflict damage
        Collections.shuffle(targets);
        for (int i = 0; i < Math.min(3, targets.size()); i++) {
            targets.get(i).DecreaseHP(getDamage());
        }
    }
}
