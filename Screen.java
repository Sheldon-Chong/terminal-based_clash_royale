public class Screen {
    private char[][] output;

    // -- CONSTRUCTORS --

    public Screen() {
        this.output = new char[0][];
    }

    // -- GETTERS AND SETTERS --

    public char[][] GetScreen() { return this.output; }

    public void SetPixel(Pos pos, char newChar) {
        if (pos.y < 0 || pos.y >= output.length || pos.x < 0 || pos.x >= output[pos.y].length)
            return;
        output[pos.y][pos.x] = newChar;
    }
    public void SetScreen(char[][] output) { this.output = output; }

    // Written by Sheldon
    public void ImposeImage(String[] texture, Pos startingPos) {
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
                    // skip if empty
                } 
                else
                    this.SetPixel(pos, texture[y].charAt(x));
            }
        }
    }

    // Written by Sheldon
    public void ImposeImage(String texture, Pos startingPos) {
        String[] textureArr = {texture};
        this.ImposeImage(textureArr, startingPos);
    }

    // Written by Sheldon
    public void ResetScreen() {
        this.output = new char[0][];
    }

    // Written by Sheldon
    public void PrintScreen() {
        
        for (int i = 0; i < output.length; i++)
        System.out.println(new String(output[i]));
        
        System.out.println();
        
    }
    
    // Written by Sheldon
    public void Add2LastItem(String value) {
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

    // Written by Sheldon
    public void AppendLine(String value) {
        
        char[][] newOutput = new char[this.output.length + 1][];

        for (int i = 0; i < output.length; i++)
            newOutput[i] = this.output[i];
        
        newOutput[this.output.length] = value.toCharArray(); // Convert string to char array
        this.output = newOutput;

    }
}