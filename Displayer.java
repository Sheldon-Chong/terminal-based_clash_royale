

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

    private final GameSystem  gameRef;
    private final Scanner     input;
    private final Screen      screen;
    private final FileHandler fHandler;
    private final String []   msgPlayerOverlay;
    private Screen            CardScreen;


    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Constructor for the Displayer class */
    public Displayer(GameSystem gameSystem) {
        this.screen   = new Screen();
        this.gameRef  = gameSystem;
        this.input    = new Scanner(System.in);
        this.fHandler = new FileHandler();
        
        this.msgPlayerOverlay = this.fHandler.readFile2Lines("MsgPlayerOverlay.txt");
    }

    
    // -- SETTER AND GETTER --

    // DEVELOPED BY: Daiki
    /* Gets the players names*/
    public String GetPlayerName(int playerNumber) {
        return input.nextLine();
    }


    // -- PUBLIC METHODS --

    // DEVELOPED BY: Daiki
    /*  Displays ASCII Title Screen */
    public void ShowTitleScreen() {
        String [] startingScreen = this.fHandler.readFile2Lines("MsgTitleScreen.txt");

        for (int i = 0; i < startingScreen.length; i ++) {
            System.out.println(startingScreen[i]);
        }
            
        System.out.println("Press ENTER to begin game");
    }
    

    public void PrintGameOver(Player winner) {
        String [] GameOverMsg = fHandler.readFile2Lines("MsgGameOver.txt");

        for (int i = 0; i < GameOverMsg.length; i ++) {
            System.out.println(GameOverMsg[i]);
        }
    }

    // DEVELOPED BY: Sheldon
    /* Displays the game board and all objects within the game world.
     * This method is responsible for rendering the game state to the console, including troops, towers, spells, and other objects.
     * The method uses the Screen class to impose images onto the game board display, updating the visual representation of the game state.
     * @param grid - the 2D array of cells representing the game world */
    public void PrintWorld(Cell[][] grid) {

        // clear screen
        this.screen.ResetScreen();

        // print player titles
        printBoard(grid);
        renderTroops();
        renderSpells();
        renderCells();

        // print screen to console
        this.screen.PrintScreen();
    }

    // DEVELOPED BY: Sheldon
    /* print player1/player2 titles above the board */
    public void PrintPlayerOverlay() {
        for (int i = 0; i < this.msgPlayerOverlay.length; i ++) {
            System.out.println(this.msgPlayerOverlay[i]);
        }
    }
    
    // DEVELOPED BY: Sheldon
    /* Displays the current player's turn on the game screen */
    public void DisplayRound() {
        System.out.printf("------------------------------------------------------------------------ Round %d -----------------------------------------------------------------\n", this.gameRef.GetRound());
    }

    // DEVELOPED BY: Sheldon
    /* get the representation of a given object, with different visual representations for different object types */
    private String getRepr(Obj object) {

        // if the object is a troop, display the troop's name and HP. E.g. "k9"
        if (object instanceof Troop) {

            Troop  troop = (Troop) object;

            // get the troop name
            String name  = troop.GetNameShort();

            // set the troop name to uppercase if it belongs to player 2
            if (troop.GetPlayer().GetPlayerNum() == GameSystem.PLAYER2_REGION)
                return name.toUpperCase() + troop.GetHP();
            else
                return name + troop.GetHP();
        } 

        // if the object is a tower, display the tower's health. E.g. "T3"
        else if (object instanceof Tower)
            return "T" + (((Tower) object).getHealth());

        return " ";
    }

    // DEVELOPED BY: Sheldon
    /* Print the game board to the console, including all cells and objects within the game world.
     * @param grid - the 2D array of cells representing the game world */
    private void printBoard(Cell[][] grid) {
        
        screen.AppendLine("     ");

        for (int x = 0; x < grid[0].length; x++)
            screen.AppendStrToLastLine(String.format(" %2d  ", x + 1));

        screen.AppendLine("   _");

        for (int x = 0; x < grid[0].length; x++) {
            if (grid[0][x].GetObject() instanceof TileVoid)
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
    /* Render troops on the game board.
     * Iterates through the list of troops and renders each troop on the game board based on its position */
    private void renderTroops() {
        
        // if no troops are present
        if (gameRef.GetTroops() == null)
            return;

        // iterate for each troop
        for (int i = 0; i < gameRef.GetTroops().length; i++) {
            Troop troop = gameRef.GetTroops()[i];

            // calculate the correct starting pixel coordinate to place the troop
            Pos pos = this.pos2Corner(troop.GetPos()).Add(2, 1);

            // place the troop onto the world
            screen.ImposeImage(this.getRepr(troop), pos);
        }
    }

    // DEVELOPED BY: Sheldon
    /* Render spells on the game board.
     * Iterates through the list of spells and renders each spell on the game board based on its position */
    private void renderSpells() {
        ObjList spellQueue = gameRef.GetSpells();

        // iterate for each spell in spellQueue
        for (int i = 0; i < spellQueue.GetLen(); i++) {

            Spell spell = (Spell) spellQueue.GetItem(i);

            Pos[] corners = getCornersFromTile(spell.GetBoundaryStart());

            Pos startingCorner = corners[TOP_LEFT_CORNER];

            int textureID = (spell.GetDeployTime() < 0) ? 1 : 0;
            TextureSet texture = spell.GetTexture(textureID);

            Pos startPos = spell.GetBoundaryStart();
            Pos endPos = spell.GetBoundaryEnd();

            screen.ImposeImage(texture.getTexture(TextureSet.CORNER_TOP_LEFT),     startingCorner);
            screen.ImposeImage(texture.getTexture(TextureSet.CORNER_BOTTOM_RIGHT), pos2Corner(endPos));
            screen.ImposeImage(texture.getTexture(TextureSet.CORNER_BOTTOM_LEFT),  pos2Corner(new Pos(startPos.x, endPos.y)));
            screen.ImposeImage(texture.getTexture(TextureSet.CORNER_TOP_RIGHT),    pos2Corner(new Pos(endPos.x, startPos.y)));

            // Render the sides
            for (int y = startPos.y + 1; y < endPos.y; y++) {
                screen.ImposeImage(texture.getTexture(TextureSet.SIDE_LEFT),  pos2Corner(new Pos(startPos.x, y)));
                screen.ImposeImage(texture.getTexture(TextureSet.SIDE_RIGHT), pos2Corner(new Pos(endPos.x, y)));
            }

            // Render the top and bottom
            for (int x = startPos.x + 1; x < endPos.x; x++) {
                screen.ImposeImage(texture.getTexture(TextureSet.SIDE_TOP),    pos2Corner(new Pos(x, startPos.y)));
                screen.ImposeImage(texture.getTexture(TextureSet.SIDE_BOTTOM), pos2Corner(new Pos(x, endPos.y)));
            }
        }
    }

    // DEVELOPED BY: Sheldon and Daiki
    /* Render cells on the game board.
    *  Iterates through the entire game grid and renders objects within each cell based on their type.
    *  This method is critical for updating the visual representation of the game state every turn.
    *  Uses the Screen class to impose images onto the game board's display. */
    private void renderCells() {

        // Iterate through the grid
        for (int y = 0; y < gameRef.GetGrid().length; y++) {
            for (int x = 0; x < gameRef.GetGrid()[0].length; x++) {

                // Get the cell at the current position
                Cell cell = gameRef.GetCell(new Pos(x, y));

                Pos[] cornersPositions = getCornersFromTile(new Pos(x, y));  // Get the corner positions for the tile
                Pos startingCorner = cornersPositions[TOP_LEFT_CORNER];      // Get the top left corner position

                // If the cell is not null, proceed to check its object type
                if (cell != null) {

                    Obj object = cell.GetObject();  

                    // If the object is a Tower (either Player's King or Princess Tower), render it
                    if (object instanceof Tower) {
                        Tower tower = (Tower) object;
                        screen.ImposeImage(this.getRepr(tower), startingCorner.Add(2, 1));
                    }

                    // If the object is a TileTower (e.g., a wall or structure related to the tower), render it
                    else if (object instanceof TileTower) {
                        TileTower towerWall = (TileTower) object;  
                        screen.ImposeImage(towerWall.GetTexture().getTexture(towerWall.GetType()), startingCorner);
                    } 

                    // If the object is a TileEmpty (empty space on the board), render it
                    else if (object instanceof TileVoid) {
                        TileVoid empty = (TileVoid) object;  
                        screen.ImposeImage(empty.GetTexture().getTexture(empty.GetType()), startingCorner);
                    }
                }
            }
        }
    }

    // DEVELOPED BY: Sheldon
    /* Convert a tile position to a corner position on the screen */
    private Pos pos2Corner(Pos pos) {
        return (pos.Multiply(5, 2).Add(4, 2));
    }

    // DEVELOPED BY: Sheldon
    /* Get the corner positions of a tile */
    private Pos[] getCornersFromTile(Pos pos) {
        Pos[] corners = new Pos[4];

        corners[TOP_LEFT_CORNER] = pos2Corner(pos.Add(0, 0));
        corners[TOP_RIGHT_CORNER] = pos2Corner(pos.Add(1, 0));
        corners[BOTTOM_LEFT_CORNER] = pos2Corner(pos.Add(0, 1));
        corners[BOTTOM_RIGHT_CORNER] = pos2Corner(pos.Add(1, 1));

        return corners;
    }

    // DEVELOPED BY: Daiki
    /* Display the current player's card deck on the game screen.
    * This method visualizes the player's available cards along with their elixir costs and updates the visual display of the current elixir status.
    * The method is responsible for creating and managing the card deck display during the game, ensuring players can see and select their next moves.
    * @param player - the player whose card deck is to be displayed */
    public void DisplayCardDeck(Player player) {

        // -- CREATE SCREEN --

        this.CardScreen = new Screen();


        // - RENDER BASE -

        this.CardScreen.AppendLine(" ^^                                                               ");
        this.CardScreen.AppendLine("o=======================#1=========#2=========#3=========#4======o");
        this.CardScreen.AppendLine("|  Next:______     | **       | **       | **       | **       | |");
        this.CardScreen.AppendLine("|  | %%       |    |          |          |          |          | |");
        this.CardScreen.AppendLine("|  |          |    |          |          |          |          | |");
        this.CardScreen.AppendLine("|  |          |    |___[>>]___|___[>>]___|___[>>]___|___[>>]___| |");
        this.CardScreen.AppendLine("|  |___[<<]___|       ________________________________________   |");
        this.CardScreen.AppendLine("|              Elixir:&&                                      |$ |");
        this.CardScreen.AppendLine("o================================================================o");

        Player currentPlayer = gameRef.GetCurrentPlayer();


        // - RENDER CARDS -
        
        Card []cards = currentPlayer.GetCardsOnHand();
        this.CardScreen.ImposeImage(currentPlayer.GetName(), "^^");
        
        // iterate for each card
        for (int i = 0; i < 4; i ++) {

            String name       = " ";
            String elixirCost = " ";
            
            // if the card is not null
            if (cards[i] != null) {
                name = cards[i].GetName();
    
                // truncate string if it exceeds the length of the card placeholder
                if (name.length() > 9)
                    name = name.substring(0, 9);

                elixirCost = String.format("%2s", "" + cards[i].GetElixirCost());
            }

            // render card
            this.CardScreen.ImposeImage(name, "**");
            this.CardScreen.ImposeImage(elixirCost, ">>");
        }

        
        // - RENDER ELIXIR -
        
        // render upcoming card
        this.CardScreen.ImposeImage(String.format("%2d", currentPlayer.GetUpcomingCard().GetElixirCost()), "<<");
        this.CardScreen.ImposeImage(currentPlayer.GetUpcomingCard().GetName() + "", "%%");
        
        // render elixir bar
        String elixirMeter = "";
        for (int i = 0; i < currentPlayer.GetElixir(); i++) {
            elixirMeter += " /";
        }

        // render elixir
        this.CardScreen.ImposeImage(elixirMeter, "&&");
        this.CardScreen.ImposeImage(currentPlayer.GetElixir() + "", "$");


        // -- PRINT SCREEN --

        this.CardScreen.PrintScreen();
    }

    

    
}
