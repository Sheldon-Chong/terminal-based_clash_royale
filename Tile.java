// entire class by Sheldon

/*
 * The tile class is used to store the textures for the tiles
 * Various static (non-moving) objects in the world typically inherit from this class
 */

public abstract class Tile extends Obj {

    // -- CONSTANTS --

    public static final int INSIDE              = 0;
    public static final int CORNER_TOP_LEFT     = 1;
    public static final int CORNER_TOP_RIGHT    = 2;
    public static final int CORNER_BOTTOM_LEFT  = 3;
    public static final int CORNER_BOTTOM_RIGHT = 4;

    public static final int SIDE_RIGHT  = 5;
    public static final int SIDE_LEFT   = 6;
    public static final int SIDE_TOP    = 7;
    public static final int SIDE_BOTTOM = 8;
    public static final int INDEPENDANT = 9;

    public static final int PIPE_H = 10;
    public static final int PIPE_V = 11;


    // -- ATTRIBUTES --

    private int       type;
    private int       textureState;
    private Texture []textureSet;
    private boolean   isSolid;

    
    // -- CONSTRUCTORS --

    public Tile() {
        this.textureSet = new Texture [1];
        for (int i = 0; i < this.textureSet.length; i++)
            this.textureSet[i] = new Texture();
    }

    public Tile(boolean isSolid) {
        this();
        this.isSolid = isSolid;
    }


    // -- GETTERS & SETTERS --
    
    public void      SetTextureState(int textureState) { this.textureState = textureState; }
    public int       GetTextureState() { return this.textureState; }

    public Texture   GetActiveTexture() { return this.textureSet[this.GetTextureState()]; }
    
    public void      setTextureSet(int textureSetEntry, Texture texture) { this.textureSet[textureSetEntry] = texture; }
    
    public void      setTexture(int textureId, String [] texture) { this.GetActiveTexture().setTexture(textureId, texture); }
    public String [] getTexture(int textureId) {  return this.GetActiveTexture().getTexture(textureId); }
    
    public void      setAllTextures(String [] texture) {
        for (int i = 0; i < this.GetActiveTexture().GetTextures().length; i++)
            this.setTexture(i, texture);
    }
    
    public void      SetType(int type) { this.type = type; }
    public int       GetType() { return this.type; }

    public void      SetSolid(boolean isSolid) { this.isSolid = isSolid; }
    public boolean   IsSolid() { return this.isSolid; }

    // -- METHODS --

    public void PopTextureFromSet(int textureId) {
        Texture [] newTextureSet = new Texture [this.textureSet.length - 1];

        if (textureId < 0)
            textureId = textureSet.length - 1;

        for (int i = 0; i < this.textureSet.length; i++) {
            if (i < textureId)
                newTextureSet[i] = this.textureSet[i];
            else if (i > textureId)
                newTextureSet[i - 1] = this.textureSet[i];
        } 
    }

    public void AddTextureToSet(Texture texture) {
        Texture [] newTextureSet = new Texture [this.textureSet.length + 1];

        for (int i = 0; i < this.textureSet.length; i++)
            newTextureSet[i] = this.textureSet[i];

        newTextureSet[newTextureSet.length - 1] = texture;
        this.textureSet = newTextureSet;
    }

    
}
