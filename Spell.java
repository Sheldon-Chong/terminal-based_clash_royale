



/* a world instance of a spell that occupies a position. 
 * Spells start with a specified deployTime, which deducts every round
 * When the deployTime reaches 0 and below, the spell is considored to have been "deployed"
 * A deployed spell will keep deducting further until having been on the field for the length specified by effectDuration */
public abstract class Spell extends Obj {
    
    // -- ATTRIBUTES --
    
    private Pos         startPos;
    private Pos         endPos;
    private TextureSet  textureDeploy;
    private TextureSet  textureInEffect;
    private int         deployTime;
    private int         effectDuration;
    private int         radius;


    // -- CONSTRUCTORS --
    
    // DEVELOPED BY: Sheldon
    /* Default constructor for the Spell class */
    public Spell () {
        this(new Pos(0, 0), 1, 1, 1);
    }
    
    // DEVELOPED BY: Sheldon
    /* Constructor for the Spell class with a starting position, radius, deploy time, and effect duration
     * @param startingPos - the starting position of the spell
     * @param radius - the radius of the spell effect
     * @param deployTime - the time it takes for the spell to be cast
     * @param effectDuration - the duration of the spell effect */
    public Spell (Pos startingPos, int radius, int deployTime, int effectDuration) {

        // - INITIALIZE ATTRIBUTES -

        this.effectDuration = effectDuration;
        this.deployTime     = deployTime;
        this.startPos       = startingPos.Add(-radius, -radius);
        this.endPos         = startingPos.Add(radius, radius);
        this.radius         = radius;

        this.textureDeploy = new TextureSet();


        // - SET TEXTURES - 

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


        // - SET TEXTURES WHEN IN-EFFECT -
        
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

    // DEVELOPED BY: Sheldon
    /* Retrieves the radius of the spell.
     * @return - the radius of the spell as an int. */
    public int GetRadius() {
        return this.radius;
    }

    // DEVELOPED BY: Sheldon
    /* Retrieves the duration of the spell effect.
     * @return - the duration of the spell effect as an int. */
    public int GetDuration() {
        return this.effectDuration;
    }

    // DEVELOPED BY: Sheldon
    /* Sets the starting position of the spell.
     * @param startPos - the starting position as a Pos. */
    public Pos GetBoundaryStart() {
        return this.startPos;
    }

    // DEVELOPED BY: Sheldon
    /* Retrieves the starting position of the spell.
     * @return - the starting position as a Pos. */
    public Pos GetBoundaryEnd() {
        return this.endPos;
    }

    // DEVELOPED BY: Sheldon
    /* Deducts the deploy time of the spell by 1. */
    public void DeductDeployTime() {
        this.deployTime--;
    }

    // DEVELOPED BY: Sheldon
    /* Retrieves the deploy time of the spell.
     * @return - the deploy time as an int. */
    public int GetDeployTime() {
        return this.deployTime;
    }

    // DEVELOPED BY: Sheldon
    /* Retrieves the texture set for the spell based on the current state.
     * @param num - the number of the texture set to retrieve.
     * @return - the texture set for the spell. */
    public TextureSet GetTexture(int num) {
        if (num == 0)
            return this.textureDeploy;
            
        else
            return this.textureInEffect;
    }


    // -- ABSTRACT METHODS --

    // DEVELOPED BY: Sheldon
    /* Apply the effect of the spell on a target position within the game world.
     * @param targetPos - the center position where the spell strikes.
     * @param gameSysRef - reference to the GameSystem to interact with game elements. */
    public abstract void ApplyEffect(Pos targetPos, GameSystem gameSysRef);

}
