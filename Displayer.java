

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
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
    private Screen CardScreen;


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

    

    // DEVELOPED BY: Sheldon
    public void PrintWorld(Cell[][] grid) {
        this.screen.ResetScreen();
        printBoard(grid);
        renderTroops();
        renderSpells();
        renderCells();
        this.screen.PrintScreen();
    }


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
        if (gameRef.GetTroops() == null)
            return;

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

    // DEVELOPED BY: Sheldon and Daiki
    private void renderCells() {
        // Iterate through the grid
        for (int y = 0; y < gameRef.GetGrid().length; y++) {
            for (int x = 0; x < gameRef.GetGrid()[0].length; x++) {
                // Get the cell at the current position
                Cell cell = gameRef.GetCell(new Pos(x, y));
                Pos[] cornersPositions = getCornersFromTile(new Pos(x, y));  // Get the corner positions for the tile
                Pos startingCorner = cornersPositions[TOP_LEFT_CORNER];  // Get the top left corner position

                // If the cell is not null, proceed to check its object type
                if (cell != null) {
                    Obj object = cell.GetObject();  // Get the object within the cell

                    // If the object is a Tower (either Player's King or Princess Tower), render it
                    if (object instanceof Tower) {
                        Tower tower = (Tower) object;  // Cast the object to a Tower
                        screen.ImposeImage(this.getRepr(tower), startingCorner.Add(2, 1));  // Display the tower
                    }
                    // If the object is a TileTower (e.g., a wall or structure related to the tower), render it
                    else if (object instanceof TileTower) {
                        TileTower towerWall = (TileTower) object;  // Cast the object to a TileTower
                        screen.ImposeImage(towerWall.getTexture(towerWall.GetType()), startingCorner);  // Display the tower wall
                    } 
                    // If the object is a TileEmpty (empty space on the board), render it
                    else if (object instanceof TileEmpty) {
                        TileEmpty empty = (TileEmpty) object;  // Cast the object to a TileEmpty
                        screen.ImposeImage(empty.getTexture(empty.GetType()), startingCorner);  // Display the empty space
                    }
                    // Additional object types can be added here if needed (e.g., spells, troops, etc.)
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

    // Randomly select a card from a predefined set of cards
    private Card getRandomCard(Player player) {
    // List of available cards (change Troop classes to Card if necessary)
        Card[] availableCards = new Card[] {
            new CardTroop("Barbarian", new Pos(0, 0), player),
            new CardTroop("Skeletons", new Pos(0, 0), player),
            new CardTroop("Golem", new Pos(0, 0), player),
            new CardTroop("Knight", new Pos(0, 0), player)
            // Add more card classes as needed
        };

    // Pick a random card
    Random rand = new Random();
    return availableCards[rand.nextInt(availableCards.length)];
    }


   // DEVELOPED BY: Daiki
    public void DisplayCardDeck(Player player) {

        final String patternCardName   = "**";
        final String patternElixirCost = ">>";
        final String patternElixir     = "//";

        
        this.CardScreen = new Screen();
        this.CardScreen.AppendLine(" ^^                                                               ");
        this.CardScreen.AppendLine("o================================================================o");
        this.CardScreen.AppendLine("|  Next:______     | **       | **       | **       | **       | |");
        this.CardScreen.AppendLine("|  | **       |    |          |          |          |          | |");
        this.CardScreen.AppendLine("|  |          |    |          |          |          |          | |");
        this.CardScreen.AppendLine("|  |          |    |___[>>]___|___[>>]___|___[>>]___|___[>>]___| |");
        this.CardScreen.AppendLine("|  |___[>>]___|       ________________________________________   |");
        this.CardScreen.AppendLine("|              Elixir:&&                                      |< |");
        this.CardScreen.AppendLine("o================================================================o");


        Card []cards = new Card[4];
        cards[0] = new CardTroop("Skeleasdsdadton", new Pos(0, 0), player);
        cards[1] = new CardTroop("Skeleton", new Pos(0, 0), player);
        cards[2] = new CardTroop("Skeleton", new Pos(0, 0), player);
        cards[3] = new CardTroop("Skeleton", new Pos(0, 0), player);
        
        this.CardScreen.ImposeImage(this.gameRef.GetCurrentPlayer().GetName(), "^^");
        
        for (int i = 0; i < 4; i ++) {
            String name = cards[i].GetName();

            if (name.length() > 8)
                name = name.substring(0, 8);

            this.CardScreen.ImposeImage(name, "**");

            String elixirCost = String.format("%2s", "" + cards[i].GetElixirCost());
            this.CardScreen.ImposeImage(elixirCost, ">>");
        }

        String elixirBuffer = "";

        for (int i = 0; i < gameRef.GetCurrentPlayer().GetElixir(); i++) {
            elixirBuffer += "//";
        }

        this.CardScreen.ImposeImage(elixirBuffer, "&&");
        this.CardScreen.ImposeImage(gameRef.GetCurrentPlayer().GetElixir() + "", "<");

        this.CardScreen.PrintScreen();
    }
}
