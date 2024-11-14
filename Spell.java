



/*
 * a world instance of a spell that occupies a position and radius
 */

public abstract class Spell extends Obj {
    
    // -- ATTRIBUTES --
    
    private Pos         startPos;
    private Pos         endPos;
    private Texture     textureDeploy;
    private Texture     textureInEffect;
    private int         deployTime;
    private int         effectDuration;
    private GameSystem  gameSys;
    private int         radius;


    // -- CONSTRUCTOR --
    
    public Spell (Pos startingPos, Pos endPos, int deployTime, int effectDuration) {
        this.effectDuration = effectDuration;
        this.deployTime = deployTime;
        this.startPos = startingPos;
        this.endPos = endPos;

        this.textureDeploy = new Texture();

        this.textureDeploy.setTexture(Texture.CORNER_TOP_LEFT, new String [] {
            "._____",
            "|     ",
            "|     "
        });

        this.textureDeploy.setTexture(Texture.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            "|     ",
            "|_____"
        });

        this.textureDeploy.setTexture(Texture.CORNER_BOTTOM_RIGHT, new String [] {
            "      ",
            "     |",
            "_____|"
        });

        this.textureDeploy.setTexture(Texture.CORNER_TOP_RIGHT, new String [] {
            "_____.",
            "     |",
            "     |"
        });

        
        this.textureInEffect = new Texture();

        this.textureInEffect.setTexture(Texture.CORNER_TOP_LEFT, new String [] {
            "/\\/\\/",
            ">     ",
            ">     "
        });

        this.textureInEffect.setTexture(Texture.CORNER_BOTTOM_LEFT, new String [] {
            "      ",
            ">     ",
            "/\\/\\/"
        });

        this.textureInEffect.setTexture(Texture.CORNER_BOTTOM_RIGHT, new String [] {
            "     <",
            "     <",
            "/\\/\\/\\"
        });

        this.textureInEffect.setTexture(Texture.CORNER_TOP_RIGHT, new String [] {
            "/\\/\\/",
            "     <",
            "     <"
        });

        this.textureInEffect.setTexture(Texture.SIDE_RIGHT, new String [] {
            "     <",
            "     <",
            "     <"
        });

        this.textureInEffect.setTexture(Texture.SIDE_LEFT, new String [] {
            ">     ",
            ">     ",
            ">     "
        });

        this.textureInEffect.setTexture(Texture.SIDE_TOP, new String [] {
            "/\\/\\/",
        });

        this.textureInEffect.setTexture(Texture.SIDE_BOTTOM, new String [] {
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

    public void ApplyEffect(Pos targetPos, GameSystem gameSysRef) {
        System.out.println("Fireball spell effect applied");
        
        for (int y = -this.GetRadius(); y <= this.GetRadius(); y++) {
            for (int x = -this.GetRadius(); x <= this.GetRadius(); x++) {
                Pos impactPos = new Pos(targetPos.x + x, targetPos.y + y);
                if (gameSysRef.IsWithinBoard(impactPos)) {
                    Obj obj = gameSysRef.GetCell(impactPos).GetObject();
                    if (obj instanceof Troop) {
                        System.out.println("Fireball hit " + ((Troop) obj).GetNameShort() + " at position " + impactPos.x + ", " + impactPos.y);
                        ((Troop) obj).DecreaseHP(1);
                    }
                }
            }
        }
    }

    public Texture GetTexture(int num) {
        if (num == 0)
            return this.textureDeploy;
            
        else
            return this.textureInEffect;
    }

}
