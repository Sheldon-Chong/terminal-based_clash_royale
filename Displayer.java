import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * Used to display the game state to the console
 */
public class Displayer {

    // -- CONSTANTS --

    final static int BOTTOM_LEFT_CORNER = 0;
    final static int BOTTOM_RIGHT_CORNER = 1;
    final static int TOP_LEFT_CORNER = 2;
    final static int TOP_RIGHT_CORNER = 3;


    // -- ATTRIBUTES --

    private GameSystem gameRef;
    private Scanner input;
    private Screen screen;


    // -- CONSTRUCTORS --

    public Displayer(GameSystem gameSystem) {
        this.screen = new Screen();
        this.gameRef = gameSystem;
        this.input = new Scanner(System.in);
    }


    // -- SETTER AND GETTER --

    // DEVELOPED BY: Daiki
    public String GetPlayerName(int playerNumber) {
        return input.nextLine();
    }


    // -- METHODS --
    
    // DEVELOPED BY: Daiki
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

    // DEVELOPED BY: Daiki
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

    // DEVELOPED BY: Sheldon
    public void PrintWorld(Cell[][] grid) {
        this.screen.ResetScreen();
        printBoard(grid);
        renderTroops();
        renderSpells();
        renderCells();
        this.screen.PrintScreen();
    }


    // -- HELPER METHODS --

    // DEVELOPED BY: Sheldon
    private String getRepr(Obj object) {
        if (object instanceof Troop) {
            Troop troop = (Troop) object;
            String name = troop.GetNameShort();
            if (troop.GetPlayer().GetPlayerNum() == GameSystem.PLAYER2_REGION)
                name = name.toUpperCase();

            return name + (troop.GetHP());
        } 
        else if (object instanceof Tower) {
            Tower tower = (Tower) object;
            return "T" + (tower.getHealth());
        }

        return " ";
    }

    // DEVELOPED BY: Sheldon
    private void printBoard(Cell[][] grid) {
        screen.AppendLine("     ");
        for (int x = 0; x < grid[0].length; x++)
            screen.AppendStrToLastLine(String.format(" %2d  ", x));

        screen.AppendLine("   _");

        for (int x = 0; x < grid[0].length; x++) {
            if (grid[0][x].GetObject() instanceof TileEmpty)
                screen.AppendStrToLastLine("     ");
            else
                screen.AppendStrToLastLine("_____");
        }

        for (int y = 0; y < grid.length; y++) {
            // Render edge row
            String edgeBuffer = String.format("  | ", (char) (y + 'A'));
            
            for (int x = 0; x < grid[y].length; x++) {
                char corner = '.';
                String edge = "    ";
                edgeBuffer += String.format("%c%s", corner, edge);
            }

            screen.AppendLine(edgeBuffer);

            // Render content row
            String contentBuffer = String.format("%c | ", (char) (y + 'A'));
            
            for (int x = 0; x < grid[y].length; x++)
                contentBuffer += "     ";

            screen.AppendLine(contentBuffer);
        }

        // Render the last edge row
        String lastEdgeBuffer = "  |_";
        for (int x = 0; x < grid[0].length; x++)
            lastEdgeBuffer += "_____";
        screen.AppendLine(lastEdgeBuffer);
    }

    // DEVELOPED BY: Sheldon
    private void renderTroops() {
        for (int i = 0; i < gameRef.GetTroops().length; i++) {
            Troop troop = gameRef.GetTroops()[i];
            Pos pos = this.pos2Corner((troop.GetPos())).Add(2, 1);
            screen.ImposeImage(this.getRepr(troop), pos);
        }
    }

    // DEVELOPED BY: Sheldon
    private void renderSpells() {
        ObjList spellQueue = gameRef.GetSpells();

        for (int i = 0; i < spellQueue.GetLen(); i++) {
            ObjSpell spell = (ObjSpell) spellQueue.GetItem(i);

            Pos[] corners = getCornersFromTile(spell.GetStartPos());

            Pos startingCorner = corners[TOP_LEFT_CORNER];

            int textureID = (spell.GetDeployTime() < 0) ? 1 : 0;
            Texture texture = spell.GetTexture(textureID);

            Pos startPos = spell.GetStartPos();
            Pos endPos = spell.GetEndPos();

            screen.ImposeImage(texture.getTexture(Texture.CORNER_TOP_LEFT), startingCorner);
            screen.ImposeImage(texture.getTexture(Texture.CORNER_BOTTOM_RIGHT), pos2Corner(endPos));
            screen.ImposeImage(texture.getTexture(Texture.CORNER_BOTTOM_LEFT), pos2Corner(new Pos(startPos.x, endPos.y)));
            screen.ImposeImage(texture.getTexture(Texture.CORNER_TOP_RIGHT), pos2Corner(new Pos(endPos.x, startPos.y)));

            for (int y = startPos.y + 1; y < endPos.y; y++) {
                screen.ImposeImage(texture.getTexture(Texture.SIDE_LEFT), pos2Corner(new Pos(startPos.x, y)));
                screen.ImposeImage(texture.getTexture(Texture.SIDE_RIGHT), pos2Corner(new Pos(endPos.x, y)));
            }

            for (int x = startPos.x + 1; x < endPos.x; x++) {
                screen.ImposeImage(texture.getTexture(Texture.SIDE_TOP), pos2Corner(new Pos(x, startPos.y)));
                screen.ImposeImage(texture.getTexture(Texture.SIDE_BOTTOM), pos2Corner(new Pos(x, endPos.y)));
            }
        }
    }

    // DEVELOPED BY: Sheldon
    private void renderCells() {
        for (int y = 0; y < gameRef.GetGrid().length; y++) {
            for (int x = 0; x < gameRef.GetGrid()[0].length; x++) {
                Cell cell = gameRef.GetCell(new Pos(x, y));
                Pos[] cornersPositions = getCornersFromTile(new Pos(x, y));
                Pos startingCorner = cornersPositions[TOP_LEFT_CORNER];

                if (cell != null) {
                    Obj object = cell.GetObject();

                    if (object instanceof Tower) {
                        Tower tower = (Tower) cell.GetObject();
                        screen.ImposeImage(this.getRepr(tower), startingCorner.Add(2, 1));
                    }
                    else if (object instanceof TileTower) {
                        TileTower towerWall = (TileTower) cell.GetObject();
                        screen.ImposeImage(towerWall.getTexture(towerWall.GetType()), startingCorner);
                    } 
                    else if (object instanceof TileEmpty) {
                        TileEmpty empty = (TileEmpty) cell.GetObject();
                        screen.ImposeImage(empty.getTexture(empty.GetType()), startingCorner);
                    }
                }
            }
        }
    }

    // DEVELOPED BY: Sheldon
    private Pos pos2Corner(Pos pos) {
        return (pos.Multiply(5, 2).Add(4, 2));
    }

    // DEVELOPED BY: Sheldon
    private Pos[] getCornersFromTile(Pos pos) {
        Pos[] corners = new Pos[4];

        corners[TOP_LEFT_CORNER] = pos2Corner(pos.Add(0, 0));
        corners[TOP_RIGHT_CORNER] = pos2Corner(pos.Add(1, 0));
        corners[BOTTOM_LEFT_CORNER] = pos2Corner(pos.Add(0, 1));
        corners[BOTTOM_RIGHT_CORNER] = pos2Corner(pos.Add(1, 1));

        return corners;
    }

}
