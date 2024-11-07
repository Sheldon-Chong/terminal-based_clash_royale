

public class WorldSpell extends Obj {
    private Pos startPos;
    private Pos endPos;
    private Texture texture;
    private int cooldown;

    public WorldSpell (Pos startingPos, Pos endPos, int cooldown) {
        this.cooldown = cooldown;
        this.startPos = startingPos;
        this.endPos = endPos;

        this.texture = new Texture();

        texture.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            "._____",
            "|     ",
            "|     "
        });

        texture.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|_____"
        });

        texture.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     |",
            "_____|"
        });

        texture.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            "_____.",
            "     |",
            "     |"
        });

        
    }

    public Pos GetStartPos() {
        return this.startPos;
    }

    public Pos GetEndPos() {
        return this.endPos;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void DeductCooldown() {
        this.cooldown--;
    }

    public int GetCooldown() {
        return this.cooldown;
    }
}
