



/*
 * a world instance of a spell that occupies a position and radius
 */

public abstract class Spell extends Obj {
    
    // -- ATTRIBUTES --
    
    private Pos         startPos;
    private Pos         endPos;
    private TextureSet     textureDeploy;
    private TextureSet     textureInEffect;
    private int         deployTime;
    private int         effectDuration;
    private GameSystem  gameSys;
    private int         radius;


    // -- CONSTRUCTORS --
    
    public Spell (Pos startingPos, Pos endPos, int deployTime, int effectDuration, int radius) {
        this.effectDuration = effectDuration;
        this.deployTime = deployTime;
        this.startPos = startingPos;
        this.endPos = endPos;
        this.radius = radius;

        this.textureDeploy = new TextureSet();

        this.textureDeploy.setTexture(TextureSet.CORNER_TOP_LEFT, new String [] {
            "._____",
            "|     ",
            "|     "
        });

        this.textureDeploy.setTexture(TextureSet.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|_____"
        });

        this.textureDeploy.setTexture(TextureSet.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     |",
            "_____|"
        });

        this.textureDeploy.setTexture(TextureSet.CORNER_TOP_RIGHT, new String [] {
            "_____.",
            "     |",
            "     |"
        });

        
        this.textureInEffect = new TextureSet();

        this.textureInEffect.setTexture(TextureSet.CORNER_TOP_LEFT, new String [] {
            "/\\/\\/",
            ">     ",
            ">     "
        });

        this.textureInEffect.setTexture(TextureSet.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            ">     ",
            "/\\/\\/"
        });

        this.textureInEffect.setTexture(TextureSet.CORNER_BOTTOM_RIGHT, new String [] {
            "     <",
            "     <",
            "/\\/\\/\\"
        });

        this.textureInEffect.setTexture(TextureSet.CORNER_TOP_RIGHT, new String [] {
            "/\\/\\/",
            "     <",
            "     <"
        });

        this.textureInEffect.setTexture(TextureSet.SIDE_RIGHT, new String [] {
            "     <",
            "     <",
            "     <"
        });

        this.textureInEffect.setTexture(TextureSet.SIDE_LEFT, new String [] {
            ">     ",
            ">     ",
            ">     "
        });

        this.textureInEffect.setTexture(TextureSet.SIDE_TOP, new String [] {
            "/\\/\\/",
        });

        this.textureInEffect.setTexture(TextureSet.SIDE_BOTTOM, new String [] {
            "     ",
            "     ",
            "/\\/\\/"
        });
    }
    

    // -- GETTER AND SETTER --

    public void SetRadius(int radius) {
        this.radius = radius;
    }

    public int GetRadius() {
        return this.radius;
    }

    public void SetDuration(int duration) {
        this.effectDuration = duration;
    }

    public int GetDuration() {
        return this.effectDuration;
    }

    public void SetGameRef(GameSystem gameSys) {
        this.gameSys = gameSys;
    }

    public GameSystem GetGameRef() {
        return this.gameSys;
    }

    public Pos GetStartPos() {
        return this.startPos;
    }

    public Pos GetEndPos() {
        return this.endPos;
    }

    public void DeductDeployTime() {
        this.deployTime--;
    }

    public int GetDeployTime() {
        return this.deployTime;
    }

    public abstract void ApplyEffect(Pos targetPos, GameSystem gameSysRef);

    public TextureSet GetTexture(int num) {
        if (num == 0)
            return this.textureDeploy;
            
        else
            return this.textureInEffect;
    }

}
