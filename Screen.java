
/* A class that represents the screen of the game
 * Contains a 2D array of characters, and has several methods to manipulate the "pixels" of the screen
 * Each pixel is represented by a character
 * The screen can be printed to the console
 */

public class Screen {

    // -- ATTRIBUTES --

    private char[][] output;


    // -- CONSTRUCTORS --

    public Screen() {
        this.output = new char[0][];
    }


    // -- GETTERS AND SETTERS --

    public char[][] GetScreen() { return this.output; }
    public void     SetScreen(char[][] output) { this.output = output; }

    public void     SetPixel(Pos pos, char newChar) {
        if (pos.y < 0 || pos.y >= output.length || pos.x < 0 || pos.x >= output[pos.y].length)
            return;
        output[pos.y][pos.x] = newChar;
    }

    // DEVELOPED BY: Sheldon
    /* places the texture on the screen at the given starting position
     * @param texture - the texture to be placed on the screen
     * @param startingPos - the position on the screen where the texture will be placed
     */
    public void ImposeImage(String[] texture, Pos startingPos) {
        this.ImposeImage(texture, startingPos, false);
    }

    public void ImposeImage(String[] texture, Pos startingPos, boolean overwrite) {
        if (texture == null) {
            return;
        }

        for (int y = 0; y < texture.length; y++) {
            for (int x = 0; x < texture[y].length(); x++) {
                Pos pos = new Pos(x, y).Add(startingPos);
                if (pos.y < 0 || pos.y >= output.length || pos.x < 0 || pos.x >= output[0].length) {
                    // skip if out of bounds
                } 
                else if (texture[y].charAt(x) == ' ') {
                    if (overwrite)
                        this.SetPixel(pos, ' ');
                } 
                else
                    this.SetPixel(pos, texture[y].charAt(x));
            }
        }
    }    

    // DEVELOPED BY: Sheldon
    /* places the texture on the screen at the given starting position
     * @param texture - the texture to be placed on the screen
     * @param startingPos - the position on the screen where the texture will be placed
     */
    public void ImposeImage(String texture, Pos startingPos) {
        String[] textureArr = {texture};
        this.ImposeImage(textureArr, startingPos);
    }

    public void ImposeImage(String texture, Pos startingPos, boolean overwrite) {
        String[] textureArr = {texture};
        this.ImposeImage(textureArr, startingPos, overwrite);
    }

    public String GetLine(int index) {
        if (index < 0 || index >= output.length) {
            return null;
        }

        return new String(output[index]);
    }

    public Pos FindPattern(String s) {
        for (int y = 0; y < output.length; y++) {
            for (int x = 0; x < output[y].length; x++) {
                
                if (output[y][x] == s.charAt(0)) {
                    boolean found = true;

                    for (int i = 1; i < s.length(); i++) {
                        if (x + i >= output[y].length || output[y][x + i] != s.charAt(i)) {
                            found = false;
                            break;
                        }
                    }

                    if (found)
                        return new Pos(x, y);
                }
            }
        }
        
        return null;
    }

    public void ImposeImage(String texture, String pattern) {
        Pos startingPos = FindPattern(pattern);
        if (startingPos == null) {
            return;
        }
        
        String[] textureArr = {texture};
        this.ImposeImage(textureArr, startingPos, true);
    }

    // DEVELOPED BY: Sheldon
    /* resets the screen
     */
    public void ResetScreen() {
        this.output = new char[0][];
    }

    // DEVELOPED BY: Sheldon
    /* prints the screen to the console
     */
    public void PrintScreen() {
        
        for (int i = 0; i < output.length; i++)
            System.out.println(new String(output[i]));
        
        System.out.println();
        
    }
    
    // DEVELOPED BY: Sheldon
    /* appends a string to the last line of the screen
     * the equivalent of print for terminals
     */
    public void AppendStrToLastLine(String value) {
        if (this.output.length == 0) {
            this.output = new char[1][];
            this.output[0] = value.toCharArray();
            return;
        }

        char[] lastLine = this.output[this.output.length - 1];
        char[] newLine = new char[lastLine.length + value.length()];
        
        System.arraycopy(lastLine, 0, newLine, 0, lastLine.length);
        System.arraycopy(value.toCharArray(), 0, newLine, lastLine.length, value.length());
        this.output[this.output.length - 1] = newLine;
    }

    // DEVELOPED BY: Sheldon
    /* appends a new line to the screen
     * the equivalent of println for terminals
     */
    public void AppendLine(String value) {
        
        char[][] newOutput = new char[this.output.length + 1][];

        for (int i = 0; i < output.length; i++)
            newOutput[i] = this.output[i];
        
        newOutput[this.output.length] = value.toCharArray(); // Convert string to char array
        this.output = newOutput;

    }
}