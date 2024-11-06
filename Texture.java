public class Texture {
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

    private String [][]textures;

    public Texture() {
        this.textures = new String [12][];
    }

    public void      setTexture(int textureId, String [] texture) { this.textures[textureId] = texture; }
    public String [] getTexture(int textureId) { return this.textures[textureId]; }
    public String [][] GetTextures() { return this.textures; }

    public void setAllTextures(String [] texture) {
        for (int i = 0; i < this.textures.length; i++)
        this.textures[i] = texture;
    }
}
