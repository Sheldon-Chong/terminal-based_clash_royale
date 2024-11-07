

public class WorldSpell extends Obj {
    
    // -- ATTRIBUTES --
    
    private Pos startPos;
    private Pos endPos;
    private Texture textureTimer;
    private Texture textureDeployed;
    private int cooldown;
    private int duration;


    // -- PUBLIC METHODS --

    // CONSTRUCTOR
    
    public WorldSpell (Pos startingPos, Pos endPos, int cooldown) {
        this.cooldown = cooldown;
        this.startPos = startingPos;
        this.endPos = endPos;

        this.textureTimer = new Texture();

        this.textureTimer.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            "._____",
            "|     ",
            "|     "
        });

        this.textureTimer.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|_____"
        });

        this.textureTimer.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     |",
            "_____|"
        });

        this.textureTimer.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            "_____.",
            "     |",
            "     |"
        });

        
        this.textureDeployed = new Texture();

        this.textureDeployed.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            "/\\/\\/",
            ">     ",
            ">     "
        });

        this.textureDeployed.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            ">     ",
            "/\\/\\/"
        });

        this.textureDeployed.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "     <",
            "     <",
            "/\\/\\/\\"
        });

        this.textureDeployed.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            "/\\/\\/",
            "     <",
            "     <"
        });

        this.textureDeployed.setTexture(Tile.SIDE_RIGHT, new String [] {
            "     <",
            "     <",
            "     <"
        });

        this.textureDeployed.setTexture(Tile.SIDE_LEFT, new String [] {
            ">     ",
            ">     ",
            ">     "
        });

        this.textureDeployed.setTexture(Tile.SIDE_TOP, new String [] {
            "/\\/\\/",
        });

        this.textureDeployed.setTexture(Tile.SIDE_BOTTOM, new String [] {
            "     ",
            "     ",
            "/\\/\\/"
        });
    }
    
    // GETTER AND SETTER

    public void SetDuration(int duration) { this.duration = duration; }
    public int  GetDuration() { return this.duration; }

    public Pos GetStartPos() { return this.startPos; }
    public Pos GetEndPos() { return this.endPos; }
    
    public void DeductCooldown() { this.cooldown--; }
    public int  GetCooldown() { return this.cooldown; }

    public Texture getTexture(int num) {
        if (num == 0)
            return this.textureTimer;
        else
            return this.textureDeployed;
    }

}
