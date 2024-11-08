



/*
 * a world instance of a spell that occupies a position and radius
 */

public class WorldSpell extends Obj {
    
    // -- ATTRIBUTES --
    
    private Pos startPos;
    private Pos endPos;
    private Texture textureDeploy;
    private Texture textureInEffect;
    private int deployTime;
    private int effectDuration;


    // -- CONSTRUCTOR --
    
    public WorldSpell (Pos startingPos, Pos endPos, int deployTime, int effectDuration) {
        this.effectDuration = effectDuration;
        this.deployTime = deployTime;
        this.startPos = startingPos;
        this.endPos = endPos;

        this.textureDeploy = new Texture();

        this.textureDeploy.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            "._____",
            "|     ",
            "|     "
        });

        this.textureDeploy.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|_____"
        });

        this.textureDeploy.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     |",
            "_____|"
        });

        this.textureDeploy.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            "_____.",
            "     |",
            "     |"
        });

        
        this.textureInEffect = new Texture();

        this.textureInEffect.setTexture(Tile.CORNER_TOP_LEFT, new String [] {
            "/\\/\\/",
            ">     ",
            ">     "
        });

        this.textureInEffect.setTexture(Tile.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            ">     ",
            "/\\/\\/"
        });

        this.textureInEffect.setTexture(Tile.CORNER_BOTTOM_RIGHT, new String [] {
            "     <",
            "     <",
            "/\\/\\/\\"
        });

        this.textureInEffect.setTexture(Tile.CORNER_TOP_RIGHT, new String [] {
            "/\\/\\/",
            "     <",
            "     <"
        });

        this.textureInEffect.setTexture(Tile.SIDE_RIGHT, new String [] {
            "     <",
            "     <",
            "     <"
        });

        this.textureInEffect.setTexture(Tile.SIDE_LEFT, new String [] {
            ">     ",
            ">     ",
            ">     "
        });

        this.textureInEffect.setTexture(Tile.SIDE_TOP, new String [] {
            "/\\/\\/",
        });

        this.textureInEffect.setTexture(Tile.SIDE_BOTTOM, new String [] {
            "     ",
            "     ",
            "/\\/\\/"
        });
    }
    
    // -- GETTER AND SETTER --

    public void SetDuration(int duration) { this.effectDuration = duration; }
    public int  GetDuration() { return this.effectDuration; }

    public Pos GetStartPos() { return this.startPos; }
    public Pos GetEndPos() { return this.endPos; }
    
    public void DeductDeployTime() { this.deployTime--; }
    public int  GetDeployTime() { return this.deployTime; }

    public Texture GetTexture(int num) {
        if (num == 0)
            return this.textureDeploy;
        else
            return this.textureInEffect;
    }

}
