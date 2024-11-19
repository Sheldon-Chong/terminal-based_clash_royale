
/*
 * The GameSystem class is responsible for managing the game world and its entities
 * It contains the game grid, players, troops, and the current round number
 * contains methods to spawn troops, shuffle player cards, and update the world
 */

class GameSystem {

    // -- CONSTANTS --

    public final static String  FILENAME = "GameMap.txt";

    private final static char  SYMBOL_TOWER         = 'T';
    private final static char  SYMBOL_FLOOR         = '.';
    private final static char  SYMBOL_VOID         = ' ';
    private final static char  SYMBOL_P1_PRINCESS   = 'P';
    private final static char  SYMBOL_P1_KING       = 'K';
    private final static char  SYMBOL_P2_PRINCESS   = 'p';
    private final static char  SYMBOL_P2_KING       = 'k';
    private final static char  SYMBOL_P1_NAV_MARKER = 'e';
    private final static char  SYMBOL_P2_NAV_MARKER = 'E';

    public final static int  NO_REGION      = 0;
    public final static int  PLAYER1_REGION = 1;
    public final static int  PLAYER2_REGION = 2;

    public final static int  ERR_INVALID_FORMAT = -1;
    public final static int  ERR_INVALID_ROW    = -2;
    public final static int  ERR_INVALID_COL    = -3;
    public final static int  ERR_OUT_OF_BOUNDS  = -4;
    public final static int  ERR_INVALID_DEPLOY_REGION = -5;
    public final static int  ERR_OCCUPIED_SPACE = -6;
    
    public final static int  ERR_INVALID_CARD = -7;
    public final static int  ERR_ELIXIR_COST = -8;

    public final static String [] CARDS = {
        "barbarian",
        "elixir golem",
        "giant",
        "goblins",
        "golem",
        "hog rider",
        "knight",
        "lumberjack",
        "pekka",
        "skeletons",

        "lightning",
        "fireball",
        "zap"
    };


    // -- ATTRIBUTES --
    
    private ObjList   troops;
    private Cell[][]  worldGrid;
    private ObjList   spellQueue;
    private           Player player1;
    private           Player player2;
    private int       currentRound; // Trakcs the current round number
    private Player    currentPlayer;
    private Card      []cards;
    private Player    winner;

    private ObjList   p1TravelPoints;
    private ObjList   p2TravelPoints;

    private final int maxRow = 17;    // Maximum index for rows (0-27)
    private final int maxCol = 28;    // Maximum index for columns (A-Q, 0-12)


    // -- CONSTRUCTORS --
    
    // DEVELOPED BY: Sheldon
    /* default constructor for GameSystem */
    public GameSystem() {
        this.initWorld();
    }

    
    // -- GETTERS AND SETTERS --

    // DEVELOPED BY : DAIKI
    /* Advance to the next round.
    * This method increments the counter for the current round in the game.
    * It represents the progression of the game turn-by-turn. */
    public void NextRound() {
        this.currentRound ++;
    }

    // DEVELOPED BY : DAIKI
    // Gets the winning player
    public Player GetWinner() {
        return winner;
    }
    // DEVELOPED BY: Daiki
    /* Retrieve the array of cards managed by the GameSystem.
    * This method is used to access the full set of cards that are available in the game, 
    * allowing other components of the system to interact with the card data.
    * @return Card[] - returns an array of Card objects */
    public Card []GetCards() {
        return this.cards;
    }

    // DEVELOPED BY: Daiki
    /* Set the current player for the game turn.
    * This method assigns the player who is currently taking their turn.
    * @param player - the Player object representing the current player */
    public void SetCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }
    
    // DEVELOPED BY: Daiki
    /* Retrieve the current player.
     * This method returns the player object of the player who is currently taking their turn.
     * @return Player - the current player object */
    public Player GetCurrentPlayer() {
        return this.currentPlayer;
    }

    // DEVELOPED BY: Daiki
    /* Retrieve player 1's object.
     * This method returns the object of player 1 from the game system.
     * @return Player - player 1's object */
    public Player GetPlayer1() {
        return this.player1;
    }
    
    // DEVELOPED BY: Daiki
    /* Retrieve player 2's object.
    * This method returns the object of player 2 from the game system.
    * @return Player - player 2's object */
    public Player GetPlayer2() {
        return this.player2;
    }

    // DEVELOPED BY: Sheldon
    /* Get the troops in the game world
     * @return - the list of troops in the game world as a troop object*/
    public Troop[] GetTroops() {
        
        Obj[] objArray = this.troops.GetList();

        // if the list is empty, return null
        if (objArray == null)
            return null;

        // convert the object array to a troop array
        Troop[] troopArray = new Troop[objArray.length];
        
        // iterate for all objects in the object array
        for (int i = 0; i < objArray.length; i++)
            troopArray[i] = (Troop) objArray[i];

        return troopArray;
    }
 
    // DEVELOPED BY: Daiki
    /* Retrieve the entire game grid.
    * This method returns a 2D array of Cell objects representing the current state of the game board.
    * @return Cell[][] - the grid of cells that make up the game board */
    public Cell [][] GetGrid() {
        return this.worldGrid; 
    }

    // DEVELOPED BY: Daiki
    /* Get the height of the game grid.
    * This method returns the number of rows in the grid, indicating the vertical dimension of the game board.
    * @return int - the height of the grid measured in cells */
    public int GetHeight() {
        return this.worldGrid.length;
    }

    // DEVELOPED BY: Daiki
    /* Get the width of the game grid.
    * This method returns the number of columns in the grid, indicating the horizontal dimension of the game board.
    * @return int - the width of the grid measured in cells */
    public int GetWidth() {
        return this.worldGrid[0].length;
    }

    // DEVELOPED BY: Daiki
    /* Get the dimensions of the game grid as a position object.
    * This method constructs a new Pos object representing the width and height of the grid.
    * @return Pos - a position object where x is the width and y is the height of the grid */
    public Pos GetDimensions() {
        return new Pos(this.GetWidth(), this.GetHeight());
    }

    // DEVELOPED BY: Daiki
    /* Set a cell at a specific position in the grid.
    * This method places a Cell object at the specified row and column in the grid.
    * @param row - the row index where the cell will be set
    * @param col - the column index where the cell will be set
    * @param cell - the Cell object to place in the grid */
    public void SetCell(int row, int col, Cell cell) {
         this.worldGrid[row][col] = cell;
    }

    // DEVELOPED BY: Daiki
    /* Retrieve a cell from a specific row and column in the grid.
    * This method returns the Cell object located at the specified row and column.
    * @param row - the row index of the cell to retrieve
    * @param col - the column index of the cell to retrieve
    * @return Cell - the cell at the specified row and column */
    public Cell GetCell(int row, int col) { 
        return this.worldGrid[row][col];
    }

    // DEVELOPED BY: Daiki
    /* Retrieve a cell from a specified position on the grid*/
    public Cell GetCell(Pos pos) {
        if (this.IsOutOfBounds(pos))
            return null;
        
        return this.worldGrid[pos.y][pos.x];
    }

    // DEVELOPED BY: Daiki
    /* checks if a position is out of bounds
     * @param pos - the position to be checked
     * @return - true if the position is out of bounds, false otherwise*/
    public boolean IsOutOfBounds (Pos pos) {
        return pos.x < 0 || pos.x >= this.worldGrid[0].length || pos.y < 0 || pos.y >= this.worldGrid.length;
    }

    // DEVELOPED BY: Daiki
    /* Retrieve the current round number in the game.
    * This method returns the integer value representing the current round of the game.
    * @return int - the current game round number */
    public int GetRound() {
        return this.currentRound;
    }

    // DEVELOPED BY: Daiki
    /* Get the list of active spells on the game board.
    * This method returns the ObjList containing all currently active and queued spells in the game.
    * @return ObjList - a list of spells currently in play */
    public ObjList GetSpells() {
        return this.spellQueue;
    }

    // DEVELOPED BY: Daiki
    /* Determine which side of the board an object is on based on its x-coordinate.
    * Objects on the left half (x from 0 to 13) are in PLAYER1_REGION, while objects on the right half (x from 14 onward) are in PLAYER2_REGION
    * @param object - the object to determine the region for
    * @return int - PLAYER1_REGION or PLAYER2_REGION based on the object's position */
    public int GetObjRegion(Obj object) {
        Pos pos = object.GetPos();

        if (pos.x > 0 && pos.x < 14)
            return PLAYER1_REGION;
        else
            return PLAYER2_REGION;
    }

    // DEVELOPED BY: Sheldon
    /* destroy the troop given a refference, by popping the item of the troop array */
    public void DestroyTroop(Troop troop) {
        this.troops.Pop(troop);
    }


    // -- HELPER METHODS --

    // DEVELOPED BY: Sheldon
    /* update the troops in the world grid, by accessing each element of the troops list*/
    private void updateTroops() {
        
        // iterate for all troops
        for (int i = 0; i < troops.GetLen(); i++) {

            Troop currentTroop = (Troop)troops.GetItem(i);

            // get the current position of the troop and store it temporarily
            Pos tempPos = currentTroop.GetPos().Copy();

            // move the troop
            currentTroop.Action();

            // replace the previous tile with a floor tile, and the new tile with the refference to the troop
            this.GetCell(tempPos).SetObject(new TileFloor());
            this.GetCell(currentTroop.GetPos()).SetObject(currentTroop);
        }
    }

    // DEVELOPED BY: Sheldon
    /* update the tiles in the world grid
     * checks if any towers have been destroyed. If so, replace them with floor tiles */
    private void updateTiles() {

        // iterate through the grid
        for (int y = 0; y < this.GetGrid().length; y ++) {
            for (int x = 0; x < this.GetGrid()[y].length; x++) {
                
                // if the object is a tower
                if (this.GetCell(y, x).GetObject() instanceof Tower) {
                    Tower tower = (Tower) this.GetCell(y, x).GetObject();

                    // if the tower is destroyed
                    if (tower.IsDestroyed())
                        // replace the tower with a floor tile
                        this.GetCell(y, x).SetObject(new TileFloor());
                }

                // if the object is a tile tower
                if (this.GetCell(y, x).GetObject() instanceof TileTower) {
                    TileTower tower = (TileTower) this.GetCell(y, x).GetObject();

                    // if the parent tower is destroyed
                    if (tower.GetParent().IsDestroyed())
                        // replace the tower with a floor tile
                        this.GetCell(y, x).SetObject(new TileFloor());
                }
            }
        }
    }

    // DEVELOPED BY: Sheldon
    /* update the spell queue by deducting the deploy time of each spell */
    private void updateSpellQueue() {
        
        // iterate through the spell queue and deduct the remaining deploy time of each spell
        for (int i = 0; i < this.spellQueue.GetLen(); i++) {
            Spell spell = (Spell)this.spellQueue.GetItem(i);

            spell.DeductDeployTime();

            if (spell.GetDeployTime() * -1 > spell.GetDuration())
                this.spellQueue.SetItem(i, null);
            
            else if (spell.GetDeployTime() < 0)
                spell.ApplyEffect(spell.GetPos(), this);
        }

        int index = 0;
        
        // remove null items from the spell queue
        while(index < this.spellQueue.GetLen()) {

            if (this.spellQueue.GetItem(index) == null)
                this.spellQueue.Pop(index);
            else
                index++;
        }
    }

    // DEVELOPED BY: Sheldon
    /* get the type of the tile based on the surrounding tiles
     * @param cell - the cell to be checked
     * @param subjects - the array of objects to be checked
     * @return - the type of the tile */
    private int getCellSideType(Cell cell, String[] connectingTileTypes) {
        
        Cell[] neighbours = cell.GetNeighbours();
    

        // - GET NEIGHBOURS -

        Cell neighbourLeft = neighbours[Cell.NEIGHBOUR_LEFT];
        Cell neighbourRight= neighbours[Cell.NEIGHBOUR_RIGHT];
        Cell neighbourUp   = neighbours[Cell.NEIGHBOUR_UP];
        Cell neighbourDown = neighbours[Cell.NEIGHBOUR_DOWN];

        boolean nLeft = neighbourLeft  != null && neighbourLeft.GetObject().IsType(connectingTileTypes);
        boolean nRight= neighbourRight != null && neighbourRight.GetObject().IsType(connectingTileTypes);
        boolean nUp   = neighbourUp    != null && neighbourUp.GetObject().IsType(connectingTileTypes);
        boolean nDown = neighbourDown  != null && neighbourDown.GetObject().IsType(connectingTileTypes);
    

        // - GET ADJACENT WALL COUNT -

        int wallCount = 0;
    
        if (nLeft)  wallCount++;
        if (nRight) wallCount++;
        if (nUp)    wallCount++;
        if (nDown)  wallCount++;
    

        // - DETERMINE TILE TYPE -

        if (wallCount == 0) return TextureSet.INDEPENDANT;     // if no adjacent walls
        if (wallCount == 4) return TextureSet.INSIDE;          // if all adjacent walls
    
        // sides

        if (nLeft && nRight) {
            if (nUp && !nDown) return TextureSet.SIDE_BOTTOM;
            if (nDown && !nUp) return TextureSet.SIDE_TOP;

            return TextureSet.PIPE_H;
        }

        else if (nUp && nDown) {
            if (nLeft && !nRight) return TextureSet.SIDE_RIGHT;
            if (nRight && !nLeft) return TextureSet.SIDE_LEFT;

            return TextureSet.PIPE_V;
        }

        // corners
        else {
            if (nRight && nDown) return TextureSet.CORNER_TOP_LEFT;
            if (nLeft && nDown)  return TextureSet.CORNER_TOP_RIGHT;
            if (nRight && nUp)   return TextureSet.CORNER_BOTTOM_LEFT;
            if (nLeft && nUp)    return TextureSet.CORNER_BOTTOM_RIGHT;
        }
    
        // inside
        return TextureSet.INSIDE;
    }

    // DEVELOPED BY: Sheldon
    /* initialize the game world by creating the grid, players, and troops */
    private void initWorld() {
        
        FileHandler fHandler = new FileHandler();
        this.currentRound = 0;        

        // - INITIALIZE CARD STATS -
        
        String[] cardsRaw = fHandler.ReadFile2Lines("cards.csv");
        this.cards        = new Card[cardsRaw.length];
        
        // iterate for each card
        for (int i = 0; i < cardsRaw.length; i++) {

            String[] contentsSplitted = cardsRaw[i].split(",");
            String cardName = contentsSplitted[0];
            
            int elixirCost = Integer.valueOf(contentsSplitted[1]);
            String rawType = contentsSplitted[2];

            int type = Card.TROOP;

            if (rawType.equals("troop"))
                type = Card.TROOP;
            else if (rawType.equals("spell"))
                type = Card.SPELL;

            this.cards[i] = new Card(cardName, elixirCost, type);
        }

        
        // - INITIALIZE LISTS -
        
        this.spellQueue = new ObjList();
        this.troops = new ObjList();
        this.p1TravelPoints = new ObjList();
        this.p2TravelPoints = new ObjList();


        // - INITIALIZE PLAYERS - 

        this.player1 = new Player("Player1", 1, this);
        this.player2 = new Player("Player2", 2, this);

        this.SetCurrentPlayer(this.GetPlayer1());
        this.ShufflePlayerCards();

        // Initialize world grid with error handling for the main game grid file
        this.worldGrid = this.CharGrid2CellGrid(fHandler.ReadFile2Grid(FILENAME));


        // - INITIALIZE TOWER REFFERENCES -
        
        // Setup towers and neighbors in the grid
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {

                // if the current object is a tower
                if (this.GetCell(new Pos(x, y)).GetObject() instanceof Tower) {

                    // get neighbours and the current tower
                    Cell[] neighbours = this.GetCell(new Pos(x, y)).GetAllNeighbours();
                    Tower tower = (Tower) this.GetCell(new Pos(x, y)).GetObject();

                    // set the player's tower king refference based on the current tower king
                    if (tower instanceof TowerKing) {
                        if (tower.GetPlayer().GetPlayerNum() == PLAYER1_REGION)
                            player1.SetKingTower(tower);

                        if (tower.GetPlayer().GetPlayerNum() == PLAYER2_REGION)
                            player2.SetKingTower(tower);
                    }

                    // set all tiles serrounding the Tower to belong to that tower.
                    for (int i = 0; i < 8; i++) {
                        if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileTower)
                            ((TileTower) neighbours[i].GetObject()).SetParent(tower);
                    }
                }
            }
        }
        

        // - UPDATE TILE APPEARANCE - 

        // iterate for each cell in the worldgrid
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {

                Obj object = this.GetCell(new Pos(x, y)).GetObject();
                Cell currentCell = this.GetCell(new Pos(x, y));


                // - SET TILE TEXTURE BASED ON SURROUNDING TILES -

                // if object is a tile
                if (object instanceof Tile) {
                    
                    // if object is tower
                    if (object instanceof TileTower) {
                        TileTower tower = (TileTower) object;
                        tower.SetType(this.getCellSideType(currentCell, new String[] {"TileTower", "Tower"}));
                    }

                    // if object is an empty tile
                    if (object instanceof TileVoid) {
                        TileVoid empty = (TileVoid) object;
                        empty.SetType(this.getCellSideType(currentCell, new String[] {"TileEmpty"}));
                    }
                }
            }
        }

        this.UpdateWorld();
    }

    // DEVELOPED BY: Sheldon
    /* Convert a 2D char array to a 2D Cell array
     * @param grid - the 2D char array to be converted
     * @return - the converted 2D Cell array */
    public Cell[][] CharGrid2CellGrid(char[][] grid) {

        // create a empty grid of cells
        Cell [][]wGrid = new Cell[grid.length][grid[0].length];
    

        // - POPULATE WORLD WITH CELLS -

        // iterate for each character in the char grid
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                
                char currentTile = grid[row][col];
                Obj tileContent = new TileFloor();
                

                // - CREATE A TILE BASED ON THE CHARACTER -

                switch (currentTile) {
                    // player 1
                    case SYMBOL_P1_PRINCESS:
                        tileContent = new TowerPrincess(this.player1);
                    break; case SYMBOL_P1_KING:
                        tileContent = new TowerKing(this.player1);
                        
                    // player 2
                    break; case SYMBOL_P2_PRINCESS:
                        tileContent = new TowerPrincess(this.player2);
                    break; case SYMBOL_P2_KING:
                        tileContent = new TowerKing(this.player2);

                    // other types of objects
                    break; case SYMBOL_TOWER:
                        tileContent = new TileTower(TextureSet.CORNER_BOTTOM_LEFT);
                    break; case SYMBOL_VOID:
                        tileContent = new TileVoid(TextureSet.CORNER_BOTTOM_LEFT);
                    break; case SYMBOL_FLOOR:
                        tileContent = new TileFloor();
                    
                    // default case
                    break; default:
                        tileContent = new TileFloor();
                }
                
                
                // - HANDLE NAVIGATION MARKERS -
                // append navigation markers to another list besides the world grid
                
                // if it is a player1 navigation marker
                if (currentTile == SYMBOL_P1_NAV_MARKER) {
                    tileContent = new TileFloor(new Pos(col, row));
                    this.p1TravelPoints.Append(tileContent);
                }

                // if it is a player2 navigation marker
                else if (currentTile == SYMBOL_P2_NAV_MARKER) {
                    tileContent = new TileFloor(new Pos(col, row));
                    this.p2TravelPoints.Append(tileContent);
                }

                
                // - ADD NEW TILE INTO A CELL -

                // if the content is a tile, set its back-link referrence to the current instance of GameSystem
                if (tileContent instanceof Tile)
                    ((Tile)(tileContent)).SetGameSysRef(this);

                wGrid[row][col] = new Cell(tileContent, wGrid, new Pos(col, row));
            }
        }

        return wGrid;
    }

    // DEVELOPED BY: Sheldon
    /* Get the navigation markers for player 1 
     * @return - the list of navigation markers for player 1 as a cell array*/
    public Cell [] GetP1NavMarkers () {
        Obj  [] navigationMarkers     = this.p1TravelPoints.GetList();
        Cell [] navigationMarkerCells = new Cell[navigationMarkers.length];

        for (int i = 0; i < navigationMarkers.length; i++)
            navigationMarkerCells[i] = ((Tile)(navigationMarkers[i])).GetCell();

        return navigationMarkerCells;
    }

    // DEVELOPED BY: Sheldon
    /* Get the navigation markers for player 2 
     * @return - the list of navigation markers for player 2 as a cell array*/
    public Cell [] GetP2NavMarkers () {
        Obj  [] navigationMarkers     = this.p2TravelPoints.GetList();
        Cell [] navigationMarkerCells = new Cell[navigationMarkers.length];

        for (int i = 0; i < navigationMarkers.length; i++)
            navigationMarkerCells[i] = ((Tile)(navigationMarkers[i])).GetCell();

        return navigationMarkerCells;
    }


    // DEVELOPED BY: Sheldon
    /* Find all cells containing a specific type of object in the game grid.
    * This method searches through the entire game grid and collects cells that contain objects of a specified type.
    * @param objType - the type of object to look for within the cells.
    * @return an array of cells containing objects of the specified type. */
    public Cell[] FindAllCellContaining(String objType) {
        Cell[] cellArr = null; // Initialize an empty array to store cells.
    
        // Loop through all cells in the grid.
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {

                // Get the object in the current cell.
                Obj obj = this.GetCell(new Pos(x, y)).GetObject();

                // If the object is not null and matches the specified type, add the cell to the array.
                if (obj != null && obj.GetStrType().equals(objType))
                    cellArr = AppendCell(cellArr, this.GetCell(new Pos(x, y)));
            }
        }
    
        return cellArr;
    }
    
    // DEVELOPED BY: Sheldon
    /* Append a cell to an existing array of cells.
    * This method expands an array of cells by adding a new cell to it.
    * @param cellList - the existing array of cells to which the new cell will be added.
    * @param cell - the new cell to add to the array.
    * @return the updated array including the newly added cell. */
    public Cell [] AppendCell(Cell [] cellList, Cell cell) {

        if (cellList == null)
            return new Cell[]{cell};

        Cell [] newCellList = new Cell[cellList.length + 1];

        for (int i = 0; i < cellList.length; i ++) {
            newCellList[i] = cellList[i];
        }

        newCellList[cellList.length] = cell;
        return newCellList;
    }

    // DEVELOPED BY : DAIKI
    /* Check if the game is over by determining if either player's king tower has been destroyed*/
    public boolean IsGameOver() {

        if (player1.GetKingTower() == null || player1.GetKingTower().IsDestroyed()) {
            winner = player2;
            return true;
        }

        else if (player2.GetKingTower() == null || player2.GetKingTower().IsDestroyed()) {
            winner = player1;
            return true;
        }

        return false;
    }


    // -- PUBLIC METHODS --

    // DEVELOPED BY: Daiki
    /* Shuffle the cards in each player's hand at the start of a new round.
    * This method shuffles the cards currently held by both players to randomize their hand.
    * It prints a message indicating that the shuffling process is taking place. */
    public void ShufflePlayerCards() {         
        player1.ShuffleCards();
        player2.ShuffleCards();
    }

    // DEVELOPED BY: Daiki
    /* Updates the game state for the current round.*/
    public void UpdateWorld() { 
        this.updateTroops();
        this.updateTiles();
        this.updateSpellQueue();
        this.RegenerateElixir();
        this.NextRound();
    }

    
    // DEVELOPED BY: Daiki
    /*Increases each player's elixir count to allow more card plays */
    public void RegenerateElixir() {
        this.player1.RegenerateElixir();
        this.player2.RegenerateElixir();
    }


    // DEVELOPED BY: Sheldon
    /* Set the player that is not the current player */
    public void AlternatePlayer() {
        if (this.currentPlayer == this.player1)
            this.currentPlayer = this.player2;
        else
            this.currentPlayer = this.player1;
    }

    // DEVELOPED BY: Sheldon
    /* check if a card is deployable based on a given raw string input
     * @param input - the input string
     * @return - the error code based on the validation result */
    public int ValidateDeploymentOfCard(String input) {
        if (input.length() != 1) 
            return ERR_INVALID_FORMAT;
        //System.out.println("Invalid input. Please enter a valid card number.");

        else if (input.charAt(0) < '1' || input.charAt(0) > '4') 
            return ERR_INVALID_CARD;
        //System.out.println("Invalid card number. Please select a number between 1 and 4.");

        Card card = this.GetCurrentPlayer().GetCard(Integer.valueOf(input) - 1);

        if (currentPlayer.GetElixir() < card.GetElixirCost())
            return ERR_ELIXIR_COST;

        return 1;
    }

    // DEVELOPED BY: Sheldon
    /* Check whether a position string is parsable and within the bounds of the game grid
     * @param input - the string input by the user
     * @return - the error code based on the validation result */
    public int ValidatePositionString(String input, Card card) {

        // check if the input is of the correct format
        if (input.length() < 2 || input.length() > 3 || !(input.charAt(0) >= 'A' && input.charAt(0) <= 'Q'))
            return ERR_INVALID_FORMAT;
            
        input = input.toUpperCase();

        // check whether positions are readable
        if (input.charAt(0) < 'A' || input.charAt(0) > 'Q')
            return ERR_INVALID_ROW;
        if (input.charAt(1) < '0' || input.charAt(1) > '9')
            return ERR_INVALID_COL;
        if (input.length() == 3 && (input.charAt(2) < '0' || input.charAt(2) > '9'))
            return ERR_INVALID_COL;

        Pos pos = ParsePosition(input);

        // if out of bounds
        if (pos.x < 0 || pos.x >= maxCol || pos.y < 0 || pos.y >= maxRow)
            return ERR_OUT_OF_BOUNDS;
            
        // if outside deployable region
        if (card.GetType() == Card.TROOP) {
            if ((currentPlayer.GetPlayerNum() == GameSystem.PLAYER1_REGION && (pos.x < 0 || pos.x > 14)) ||
                (currentPlayer.GetPlayerNum() == GameSystem.PLAYER2_REGION && (pos.x < 15 || pos.x > 28))) {
                System.out.println(card.GetName());
                return ERR_INVALID_DEPLOY_REGION;
            }
        }

        Obj obj = this.GetCell(pos).GetObject();

        if (!(obj instanceof TileFloor)) {
            if (obj instanceof Tile)
                System.out.println(((Tile)(obj)).GetStrType());
            return ERR_OCCUPIED_SPACE;
        }

        return 1;
    }

    // DEVELOPED BY : DAIKI
    /* Convert a user's input string, which represents a position on the game grid (ex: A25) */
    public Pos ParsePosition(String input) {

        int  row    = input.charAt(0) - 'A';  // Convert column character to an integer
        int  column = Integer.parseInt(input.substring(1)) - 1;  // Convert the substring to an integer

        return new Pos(column, row);
    }

    // DEVELOPED BY: Sheldon & DAIKI
    /* deploy a card by spawning a troop or summoning a spell
     * @param index - the index of the card to be deployed
     * @param pos - the position where the card will be deployed
     * @return - 1 if the card was deployed successfully, -1 if the player does not have enough elixir, -2 if the index is out of bounds */
    public int DeployCard(int index, Pos pos) {
        Card   card          = this.GetCurrentPlayer().GetCard(index);
        Player currentPlayer = this.GetCurrentPlayer();
    
        // Deduct elixir cost
        currentPlayer.DeductElixir(card.GetElixirCost());
    
        // Determine the type of the card and spawn the appropriate object
        if (card.GetType() == Card.SPELL)
            this.SpawnSpell(card.GetName(), pos);
        else
            this.SpawnTroop(card.GetName(), pos);
    
        // Remove the card from the player's hand
        currentPlayer.RemoveCard(index);

        return 1;
    }

    // DEVELOPED BY: Sheldon
    /* create a new troop based on the type of the troop
     * @param troopType - the type of the troop to be created
     * @return - the new troop object */
    public Troop NewTroop(String troopType) {

        Troop newTroop = null;

        troopType = troopType.toLowerCase();

        if      (troopType.equals("barbarian"))   { newTroop = new TroopBarbarian(); }
        else if (troopType.equals("elixirgolem")) { newTroop = new TroopElixirGolem(); }
        else if (troopType.equals("giant"))       { newTroop = new TroopGiant(); }
        else if (troopType.equals("goblins"))     { newTroop = new TroopGoblins(); }
        else if (troopType.equals("golem"))       { newTroop = new TroopGolem(); }
        else if (troopType.equals("hogrider"))    { newTroop = new TroopHogRider(); }
        else if (troopType.equals("knight"))      { newTroop = new TroopKnight(); }
        else if (troopType.equals("lumberjack"))  { newTroop = new TroopLumberjack(); }
        else if (troopType.equals("pekka"))       { newTroop = new TroopPEKKA(); }
        else if (troopType.equals("skeleton"))    { newTroop = new TroopSkeletons(); }

        return newTroop;
    }

    // DEVELOPED BY: Sheldon
    /* create a new spell instance based on the type of the spell
     * @param spellType - the type of the spell to be created
     * @return - the new spell object */
    public Spell NewSpell(String spellType) {

        Spell newSpell = null;

        spellType = spellType.toLowerCase();

        if      (spellType.equals("lightning")) { newSpell = new SpellLightning(); }
        else if (spellType.equals("fireball"))  { newSpell = new SpellFireball(); }
        else if (spellType.equals("zap"))       { newSpell = new SpellZap(); }

        return newSpell;
    }

    // DEVELOPED BY: Sheldon
    /* spawn a troop on the game grid
     * @param troopType - the type of troop to be spawned
     * @param startingPos - the position where the troop will be spawned
     * @return - 1 if the troop was spawned successfully, 0 otherwise */
    public int SpawnTroop(String troopType, Pos startingPos) {
        Player parent = this.GetCurrentPlayer();

        troopType = troopType.toLowerCase();
        
        if (troopType.equals("skeleton") 
         || troopType.equals("goblins") 
         || troopType.equals("barbarian")) {
            this.SpawnTroopHoard(troopType, startingPos);
        }

        else {
            Troop newTroop = NewTroop(troopType);
            newTroop.SetPlayer(parent);
            newTroop.SetPos(startingPos);
            newTroop.SetGameSysRef(this);
            this.troops.Append(newTroop);
        }
    
        return 1;
    }


    // DEVELOPED BY: Sheldon
    /* spawn a troop hoard on the game grid (a group of troops)
     * @param troopType - the type of troop to be spawned
     * @param startingPos - the position where the troop will be spawned
     * @return - 1 if the troop was spawned successfully, 0 otherwise */
    public int SpawnTroopHoard(String troopType, Pos startingPos) {
        
        Pos [] positionOffsets = {
            new Pos(0, 0),
            new Pos(0, 1),
            new Pos(0, -1)
        };
        
        // iterate for each position
        for (int i = 0; i < positionOffsets.length; i++) {
            // ensure the position is within bounds
            if (!this.IsOutOfBounds(startingPos.Add(positionOffsets[i]))) {
                Troop newTroop = NewTroop(troopType);
                newTroop.SetPlayer(this.GetCurrentPlayer());
                newTroop.SetPos(startingPos.Add(positionOffsets[i]));
                newTroop.SetGameSysRef(this);
                this.troops.Append(newTroop);
            }
        }

        return 1;
    }

    // DEVELOPED BY: Sheldon
    /* summon a spell by adding it to the spell queue
     * @param name - the name of the spell to be summoned
     * @param targetPos - the position where the spell will be summoned */
    public void SpawnSpell(String name, Pos targetPos) {

        Spell spell;

        if (name.toLowerCase().equals("lightning"))
            spell = new SpellLightning(targetPos);
        else if (name.toLowerCase().equals("fireball"))
            spell = new SpellFireball(targetPos);
        else if (name.toLowerCase().equals("zap"))
            spell = new SpellZap(targetPos);
        else
            return;

        this.spellQueue.Append(spell);
    }

}