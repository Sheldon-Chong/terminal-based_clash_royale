import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * Used to display the game state to the console
 */


public class Displayer {

    // CONSTANTS
    final static int BOTTOM_LEFT_CORNER = 0;
    final static int BOTTOM_RIGHT_CORNER = 1;
    final static int TOP_LEFT_CORNER = 2;
    final static int TOP_RIGHT_CORNER = 3;


    // ATTRIBUTES
    private GameSystem  gameRef;
    private char[][]    output;
    private Scanner     input;

    
    // CONSTRUCTORS
    public Displayer(GameSystem gameSystem) {
        output = new char[0][]; // Initialize with an empty array
        this.gameRef = gameSystem;
        
        this.input = new Scanner(System.in);
    }
    

    // PUBLIC METHODS

    // SETTER AND GETTER
    // Written by Daiki
    public String GetPlayerName(int playerNumber) { return input.nextLine(); }


    // Written by Daiki
    // Display ASCII Title Screen
    public void ShowTitleScreen() {
        try (BufferedReader reader = new BufferedReader(new FileReader("title_screen.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Press ENTER to begin game");
            System.in.read();  // Wait for user to press ENTER
        } catch (IOException e) {
            System.out.println("Error loading title screen.");
            e.printStackTrace();
        }
    }

    // Written by Daiki/Sheldon
    public void ShowGameInfo(Player player) {
        System.out.println("o===============================================================o");
        System.out.printf("|  Next:          | %s | %s | %s | %s       |\n", "Skeleton", "Knight", "Golem", "Fireball"); // Sample names
        System.out.printf("|  |   %s   |   |         |         |         |                |\n", "Zap");
        System.out.println("|  |           |  |         |         |         |                |");
        System.out.printf("|  |           |  |___[%d]___|___[%d]___|___[%d]___|___[%d]___       |\n", 3, 5, 8, 4); // Sample elixir costs
        System.out.println("|  |___[3]___|      _______________________________________     |");
        System.out.print("|              Elixir: ");
        for (int i = 1; i <= 10; i++) {
            System.out.print("|" + i + " ");
        }
        System.out.println("|");
        System.out.println("|                      |__|___|___|___|___|___|___|___|___|____| |");
        System.out.println("o===============================================================o");
    }

    public void PrintWorld(Cell[][] grid) {
        this.resetScreen();

        append("     ");
        for (int x = 0; x < grid[0].length; x++)
            add2LastItem(String.format(" %2d  ", x));

        append("   _");
        for (int x = 0; x < grid[0].length; x++) {
            if (grid[0][x].getObject() instanceof TileEmpty)
                add2LastItem("     ");
            else
                add2LastItem("_____");
        }

        for (int y = 0; y < grid.length; y++) {
            this.printEdgeRow(grid, y);
            this.printContentRow(grid, y);
        }

        this.printEdgeRow(grid, grid.length - 1);
        append("  |_");
        for (int x = 0; x < grid[0].length; x++) {
            if (grid[grid.length - 1][x].bottom_side == '|') {
                if (grid[grid.length - 1][x - 1].left_side == ' ')
                    add2LastItem("|    ");
                else
                    add2LastItem("     ");
            } 
            else if (x > 0 && grid[grid.length - 1][x - 1].getObject() instanceof TileEmpty && grid[grid.length - 1][x].left_side == ' ')
                add2LastItem("|____");
            else
                add2LastItem("_____");
        }

        for (int y = 0; y < gameRef.GetGrid().length; y++) {
            for (int x = 0; x < gameRef.GetGrid()[0].length; x++) {
                Cell cell = gameRef.GetCell(new Pos (x,y));

                Pos []cornersPositions = getCornersFromTile(new Pos (x, y));
                Pos startingCorner = cornersPositions[TOP_LEFT_CORNER];

                if (cell != null) {
                    if (cell.getObject() instanceof TileTower)
                    {    
                        TileTower towerWall = (TileTower)cell.getObject();
                        this.impose(towerWall.getTexture(towerWall.getType()), startingCorner);
                    }

                    else if (cell.getObject() instanceof TileEmpty)
                    {
                        TileEmpty empty = (TileEmpty)cell.getObject();
                        this.impose(empty.getTexture(empty.getType()), startingCorner);
                    }
                } 
            }
        }

        for (int i = 0; i < output.length; i++)
            System.out.println(new String(output[i]));
        System.out.println();

    }


    // HELPER METHODS
    private void append(String value) {
        char[][] newOutput = new char[this.output.length + 1][];
        for (int i = 0; i < output.length; i++) {
            newOutput[i] = this.output[i];
        }
        newOutput[this.output.length] = value.toCharArray(); // Convert string to char array
        this.output = newOutput;
    }
    
    private void add2LastItem(String value) {
        if (this.output.length == 0) {
            this.output = new char[1][];
            this.output[0] = value.toCharArray();
            return;
        }
        char[] lastLine = this.output[this.output.length - 1];
        char[] newLine = new char[lastLine.length + value.length()];
        arrayCopy(lastLine, 0, newLine, 0, lastLine.length);
        arrayCopy(value.toCharArray(), 0, newLine, lastLine.length, value.length());
        this.output[this.output.length - 1] = newLine;
    }
    
    private void arrayCopy(char[] src, int srcPos, char[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = src[srcPos + i];
        }
    }

    private void setChar(Pos pos, char newChar) {
        if (pos.y < 0 || pos.y >= output.length - 1 || pos.x < 0 || pos.x >=  output[0].length - 1)
            return;
        output[pos.y][pos.x] = newChar;
    }

    private void impose(String[] texture, Pos startingPos) {
        if (texture == null)
            return;

        for (int y = 0; y < texture.length; y++) {
            for (int x = 0; x < texture[y].length(); x++) {
                Pos pos = new Pos(x, y).Add(startingPos);
                if (pos.y < 0 || pos.y >= output.length - 1 || pos.x < 0 || pos.x >=  output[0].length - 1) {}
                else if (texture[y].charAt(x) == ' ') {}
                else
                    this.setChar(pos, texture[y].charAt(x));
            }
        }
    }

    private void resetScreen () {
        this.output = new char[0][]; 
    }

    private Pos convertPos2Corner( Pos pos ) {
        return (pos.Multiply(5,2).Add(4, 2));
    }

    private Pos[] getCornersFromTile (Pos pos) {
        Pos [] corners = new Pos[4];

        corners[TOP_LEFT_CORNER]     = convertPos2Corner(pos.Add(0,0));
        corners[TOP_RIGHT_CORNER]    = convertPos2Corner(pos.Add(1, 0));
        corners[BOTTOM_LEFT_CORNER]  = convertPos2Corner(pos.Add(0,1));
        corners[BOTTOM_RIGHT_CORNER] = convertPos2Corner(pos.Add(1,1));
        
        return corners;
    }

    private Cell getCell(int x, int y) {
        if (x < 0 || x >= gameRef.GetGrid()[0].length || y < 0 || y >= gameRef.GetGrid().length)
            return new Cell();

        if (y < 0 || y >= gameRef.GetGrid().length)
            return new Cell();

        Cell cell = gameRef.GetGrid()[y][x];

        if (cell == null)
            return new Cell();

        return cell;
    }

    private void printEdgeRow(Cell[][] grid, int y) {
        // Initialize the buffer
        String buffer = String.format("  | ", (char) (y + 'A'));

        for (int x = 0; x < grid[y].length; x++) {
            char corner = '.';

            if (getCell(x, y - 1).left_side == '|' || getCell(x - 1, y - 1).right_side == '|')
                corner = '|';

            String edge = "    ";

            if (getCell(x, y).top_side == '|' || getCell(x, y - 1).bottom_side == '|')
                edge = "____";

            buffer += String.format("%c%s", corner, edge);
        }

        // Append the buffer to the output array
        this.append(buffer);
    }

    private void printContentRow(Cell[][] grid, int y) {
        // Initialize the buffer
        String buffer = String.format("%c | ", (char) (y + 'A'));

        // Print columns
        for (int x = 0; x < grid[y].length; x++) {
            char health = ' ';
            char icon = ' ';
            char prefix = ' ';
            char suffix = ' ';
            char edge;

            if (grid[y][x].getObject() instanceof Tower) {
                health = '5';
                icon = 'T';
            }

            if (grid[y][x].getObject() instanceof Troop) {
                Troop troop = (Troop) grid[y][x].getObject();

                health = (char) (troop.GetHP() + '0');
                icon = troop.GetNameInitial();
                if (troop.GetPlayer().GetPlayerNum() == GameSystem.PLAYER1_REGION)
                    prefix = '1';
                else if (troop.GetPlayer().GetPlayerNum() == GameSystem.PLAYER2_REGION)
                    prefix = '2';

                if (troop.GetAction() == Troop.ACTION_MOVE)
                    suffix = '>';

                if (troop.GetAction() == Troop.ACTION_ATTACK)
                    suffix = '*';
            }

            edge = ' ';

            buffer += String.format("%s%s%c%c%c", edge, prefix, icon, health, suffix);
        }

        // Append the buffer to the output array
        this.append(buffer);
    }
}



