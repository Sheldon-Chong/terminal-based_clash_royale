
/*
 * The GameSystem class is responsible for managing the game world and its entities
 * It contains the game grid, players, troops, and the current round number
 * contains methods to spawn troops, shuffle player cards, and update the world
 */

class GameSystem {

    // -- CONSTANTS --

    public final static String  FILENAME = "game_grid.txt";

    private final static char  SYMBOL_TOWER       = 'T';
    private final static char  SYMBOL_FLOOR       = '.';
    private final static char  SYMBOL_EMPTY       = ' ';
    private final static char  SYMBOL_P1_PRINCESS = 'P';
    private final static char  SYMBOL_P1_KING     = 'K';
    private final static char  SYMBOL_P2_PRINCESS = 'p';
    private final static char  SYMBOL_P2_KING     = 'k';

    public final static int  NO_REGION      = 0;
    public final static int  PLAYER1_REGION = 1;
    public final static int  PLAYER2_REGION = 2;

    public final static int  ERR_INVALID_FORMAT = -1;
    public final static int  ERR_INVALID_ROW    = -2;
    public final static int  ERR_INVALID_COL    = -3;
    public final static int  ERR_OUT_OF_BOUNDS  = -4;
    public final static int  ERR_INVALID_DEPLOY_REGION = -5;
    public final static int  ERR_OCCUPIED_SPACE = -6;
    
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
    private int       currentRound = 1; // Trakcs the current round number
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
    // Gets the winning player
    public Player GetWinner() {
        return winner;
    }

    // DEVELOPED BY: Daiki
    public Card []getCards() {
        return this.cards;
    }

    // DEVELOPED BY: Daiki
    public void SetCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }
    
    // DEVELOPED BY: Daiki
    public Player GetCurrentPlayer() {
        return this.currentPlayer;
    }

    // DEVELOPED BY: Daiki
    public Player GetPlayer1() {
        return this.player1;
    }
    
    // DEVELOPED BY: Daiki
    public Player GetPlayer2() {
        return this.player2;
    }

    // DEVELOPED BY: Sheldon
    /* Get the troops in the game world
     * @return - the list of troops in the game world */
    public Troop[] GetTroops() {
        
        Obj[] objArray = this.troops.GetList();

        if (objArray == null)
            return null;

        Troop[] troopArray = new Troop[objArray.length];
        
        for (int i = 0; i < objArray.length; i++)
            troopArray[i] = (Troop) objArray[i];

        return troopArray;
    }
 
    // DEVELOPED BY: Daiki
    public Cell [][] GetGrid() {
        return this.worldGrid; 
    }

    // DEVELOPED BY: Daiki
    public void      SetCell(int row, int col, Cell cell) {
         this.worldGrid[row][col] = cell;
    }

    // DEVELOPED BY: Daiki
    public Cell      GetCell(int row, int col) { 
        return this.worldGrid[row][col];
    }

    // DEVELOPED BY: Daiki
    public Cell      GetCell(Pos pos) {
        if (pos.x < 0 || pos.x >= this.worldGrid[0].length || pos.y < 0 || pos.y >= this.worldGrid.length)
            return null;
        
        return this.worldGrid[pos.y][pos.x];
    }

    // DEVELOPED BY: Daiki
    /* checks if a position is out of bounds
     * @param pos - the position to be checked
     * @return - true if the position is out of bounds, false otherwise*/
    public boolean isOutOfBounds (Pos pos) {
        return pos.x < 0 || pos.x >= this.worldGrid[0].length || pos.y < 0 || pos.y >= this.worldGrid.length;
    }

    // DEVELOPED BY: Daiki
    // Method to check if troop/spell is deployed within the board
    public boolean IsWithinBoard(Pos pos) {
        return pos.x >= 0 && pos.x < worldGrid[0].length && pos.y >= 0 && pos.y < worldGrid.length;
    }

    // DEVELOPED BY: Daiki
    // Method to get the current round
    public int GetRound() {
        return this.currentRound;
    }

    // DEVELOPED BY: Daiki
    public ObjList GetSpells() {
        return this.spellQueue;
    }

    // DEVELOPED BY: Daiki
    // Method to shuffle cards in each player's hand
    public void shufflePlayerCards() { 
        System.out.println("Shuffling player cards...");
        
        player1.ShuffleCards();
        player2.ShuffleCards();
    }

    // DEVELOPED BY: Daiki
    // Method to increment the round count
    public void nextRound() { 
        this.currentRound++;
        System.out.println("Starting Round " + this.currentRound);
        shufflePlayerCards();
    }

    // DEVELOPED BY: Daiki
    public int GetObjRegion(Obj object) {
        Pos pos = object.GetPos();

        if (pos.x > 0 && pos.x < 14)
            return PLAYER1_REGION;
        else
            return PLAYER2_REGION;
    }

    // DEVELOPED BY: Sheldon
    /* destroy the troop given a refference, by popping the item of the troop array */
    public void destroyTroop(Troop troop) {
        this.troops.Pop(troop);
    }


    // -- PUBLIC METHODS --

    // DEVELOPED BY: Daiki
    public void UpdateWorld() { 

        this.updateTroops();
        this.updateTiles();
        this.updateSpellQueue();
        this.RegenerateElixir();
    }

    
    // DEVELOPED BY: Daiki
    public void RegenerateElixir() {
        this.player1.RegenerateElixir();
        this.player2.RegenerateElixir();
    }


    // DEVELOPED BY: Sheldon
    /* Get the player that is not the current player */
    public void AlternatePlayer() {
        if (this.currentPlayer == this.player1)
            this.currentPlayer = this.player2;
        else
            this.currentPlayer = this.player1;
    }

    public int ValidateDeploymentOfCard(int index) {
        Card card = this.GetCurrentPlayer().GetCard(index);

        if (currentPlayer.GetElixir() < card.GetElixirCost())
            return -1;  // Not enough elixir

        return 1;  // Valid deployment
    }

    public int ValidatePositionString(String input) {
        if (input.length() < 2 || input.length() > 3 || !(input.charAt(0) >= 'A' && input.charAt(0) <= 'Q'))
            return ERR_INVALID_FORMAT; //invalid format
            
        input = input.toUpperCase();

        if (input.charAt(0) < 'A' || input.charAt(0) > 'Q')
            return ERR_INVALID_ROW;
        if (input.charAt(1) < '0' || input.charAt(1) > '9')
            return ERR_INVALID_COL;
        if (input.length() == 3 && (input.charAt(2) < '0' || input.charAt(2) > '9'))
            return ERR_INVALID_COL;

        Pos pos = parsePosition(input);

        if (pos.x < 0 || pos.x >= maxCol || pos.y < 0 || pos.y >= maxRow)
            return ERR_OUT_OF_BOUNDS;
            
        if ((currentPlayer.GetPlayerNum() == GameSystem.PLAYER1_REGION && (pos.x < 0 || pos.x > 14)) ||
            (currentPlayer.GetPlayerNum() == GameSystem.PLAYER2_REGION && (pos.x < 15 || pos.x > 28))) {
            return ERR_INVALID_DEPLOY_REGION;
        }

        Obj obj = this.GetCell(pos).GetObject();

        if (!(obj instanceof TileFloor))
            return ERR_OCCUPIED_SPACE;

        return 1;
    }

    // DEVELOPED BY : DAIKI
    /* Convert a user's input string, which represents a position on the game grid (ex: A25) */
    public Pos parsePosition(String input) {

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
        // Check if the card index is valid
    
        Card card = this.GetCurrentPlayer().GetCard(index);
        Player currentPlayer = this.GetCurrentPlayer();
    
        // Deduct elixir cost
        currentPlayer.DeductElixir(card.GetElixirCost());
    
        if (card.GetType() == Card.SPELL)
            this.SpawnSpell(card.GetName(), pos);
        else
            this.SpawnTroop(card.GetName(), pos);
    
        currentPlayer.RemoveCard(index);

        return 1;
    }
    

        // DEVELOPED BY: Sheldon
    /* spawn a troop on the game grid
     * @param troopType - the type of troop to be spawned
     * @param startingPos - the position where the troop will be spawned
     * @return - 1 if the troop was spawned successfully, 0 otherwise */
    public int SpawnTroop(String troopType, Pos startingPos) {
        Player parent = this.GetCurrentPlayer();

        troopType = troopType.toLowerCase();
        
        Troop newTroop = null;
    
        if      (troopType.equals("barbarian"))   { newTroop = new TroopBarbarian(startingPos, parent); }
        else if (troopType.equals("elixirgolem")) { newTroop = new TroopElixirGolem(startingPos, parent); }
        else if (troopType.equals("giant"))       { newTroop = new TroopGiant(startingPos, parent); }
        else if (troopType.equals("goblins"))     { newTroop = new TroopGoblins(startingPos, parent); }
        else if (troopType.equals("golem"))       { newTroop = new TroopGolem(startingPos, parent); }
        else if (troopType.equals("hogrider"))    { newTroop = new TroopHogRider(startingPos, parent); }
        else if (troopType.equals("knight"))      { newTroop = new TroopKnight(startingPos, parent); }
        else if (troopType.equals("lumberjack"))  { newTroop = new TroopLumberjack(startingPos, parent); }
        else if (troopType.equals("pekka"))       { newTroop = new TroopPEKKA(startingPos, parent); }
        else if (troopType.equals("skeleton"))    { newTroop = new TroopSkeletons(startingPos, parent); }
        else
            return 0;
    
        newTroop.SetGameSysRef(this);
        this.troops.append(newTroop);
    
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

        this.spellQueue.append(spell);
    }


    // -- HELPER METHODS --

    // DEVELOPED BY: Sheldon
    /* update the troops in the world grid, by accessing each element of the troops list*/
    private void updateTroops() {
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
            
            if (spell.GetDeployTime() < 0)
                spell.ApplyEffect(spell.GetPos(), this);

            System.out.print("Spell " + i + " has " + spell.GetDeployTime() + " turns left. Duration is " + spell.GetDuration() + "\n");
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
    private int getCellSideType(Cell cell, String[] subjects) {
        Cell[] neighbours = cell.GetNeighbours();
    
        boolean nLeft = neighbours[Cell.NEIGHBOUR_LEFT] != null  && neighbours[Cell.NEIGHBOUR_LEFT].GetObject().isInObjArr(subjects);
        boolean nRight= neighbours[Cell.NEIGHBOUR_RIGHT] != null && neighbours[Cell.NEIGHBOUR_RIGHT].GetObject().isInObjArr(subjects);
        boolean nUp   = neighbours[Cell.NEIGHBOUR_UP] != null    && neighbours[Cell.NEIGHBOUR_UP].GetObject().isInObjArr(subjects);
        boolean nDown = neighbours[Cell.NEIGHBOUR_DOWN] != null  && neighbours[Cell.NEIGHBOUR_DOWN].GetObject().isInObjArr(subjects);
    
        int wallCount = 0;
    
        if (nLeft) wallCount++;
        if (nRight) wallCount++;
        if (nUp) wallCount++;
        if (nDown) wallCount++;
    
        if (wallCount == 0) return Texture.INDEPENDANT;
        if (wallCount == 4) return Texture.INSIDE;
    
        // sides
        if ((nLeft && nRight) || (nUp && nDown)) {
            if (nLeft && nRight) {
                if (nUp && !nDown) return Texture.SIDE_BOTTOM;
                if (nDown && !nUp) return Texture.SIDE_TOP;
    
                return Texture.PIPE_H;
            } 
            else {
                if (nLeft && !nRight) return Texture.SIDE_RIGHT;
                if (nRight && !nLeft) return Texture.SIDE_LEFT;
    
                return Texture.PIPE_V;
            }
        } 
        // corners
        else {
            if (nRight && nDown) return Texture.CORNER_TOP_LEFT;
            if (nLeft && nDown) return Texture.CORNER_TOP_RIGHT;
            if (nRight && nUp) return Texture.CORNER_BOTTOM_LEFT;
            if (nLeft && nUp) return Texture.CORNER_BOTTOM_RIGHT;
        }
    
        // inside
        return Texture.INSIDE;
    }

    // DEVELOPED BY: Sheldon
    /* initialize the game world by creating the grid, players, and troops */
    private void initWorld() {
        this.p1TravelPoints = new ObjList();
        this.p2TravelPoints = new ObjList();

        FileHandler fHandler = new FileHandler();
        
        // Initialize cards array from the cards file with error handling
        String[] cardsRaw = null;
        
        cardsRaw = fHandler.readFileLine("cards.csv");

        this.cards = new Card[cardsRaw.length];
        
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
            System.out.println(cards[i].GetRepr());
        }

        // Initialize lists
        this.spellQueue = new ObjList();
        this.troops = new ObjList();

        // Initialize players
        this.player1 = new Player("Player1", 1, this);
        this.player2 = new Player("Player2", 2, this);

        this.SetCurrentPlayer(this.GetPlayer1());
        this.shufflePlayerCards();

        // Initialize world grid with error handling for the main game grid file
        this.worldGrid = this.CharGrid2CellGrid(fHandler.readFile(FILENAME));

        // Setup towers and neighbors in the grid
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {

                if (this.GetCell(new Pos(x, y)).GetObject() instanceof Tower) {

                    Cell[] neighbours = this.GetCell(new Pos(x, y)).GetAllNeighbours();
                    Tower parent = (Tower) this.GetCell(new Pos(x, y)).GetObject();

                    for (int i = 0; i < 8; i++) {
                        if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileTower)
                            ((TileTower) neighbours[i].GetObject()).SetParent(parent);
                    }
                }
            }
        }
        
        // UPDATE TILE APPEARANCE 
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {

                Obj object = this.GetCell(new Pos(x, y)).GetObject();
                Cell currentCell = this.GetCell(new Pos(x, y));

                if (object instanceof Tile) {
                    if (object instanceof TileTower) {
                        TileTower tower = (TileTower) object;
                        tower.SetType(this.getCellSideType(currentCell, new String[] {"TileTower", "Tower"}));
                    }

                    if (object instanceof TileEmpty) {
                        TileEmpty empty = (TileEmpty) object;
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
        Cell [][]wGrid = new Cell[grid.length][grid[0].length];
    
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                
                char currentTile = grid[row][col];

                Obj tileContent = new TileFloor();
                
                // create the tile content based on the current character
                switch (currentTile) {
                    case SYMBOL_P1_PRINCESS:
                        tileContent = new TowerPrincess(this.player1);
                    break; case SYMBOL_P1_KING:
                        tileContent = new TowerKing(this.player1);
                        
                    break; case SYMBOL_P2_PRINCESS:
                        tileContent = new TowerPrincess(this.player2);
                    break; case SYMBOL_P2_KING:
                        tileContent = new TowerKing(this.player2);

                    break; case SYMBOL_TOWER:
                        tileContent = new TileTower(Texture.CORNER_BOTTOM_LEFT);
                    break; case SYMBOL_EMPTY:
                        tileContent = new TileEmpty(Texture.CORNER_BOTTOM_LEFT);
                    break; case SYMBOL_FLOOR:
                        tileContent = new TileFloor();
                    break; default:
                        tileContent = new TileFloor();
                }
                
                
                if (currentTile == 'e') {
                    tileContent = new Tile(new Pos(col, row));
                    this.p1TravelPoints.append(tileContent);
                }
                else if (currentTile == 'E') {
                    tileContent = new Tile(new Pos(col, row));
                    this.p2TravelPoints.append(tileContent);
                }
                
                if (tileContent instanceof Tile)
                    ((Tile)(tileContent)).SetGameSysRef(this);
                wGrid[row][col] = new Cell(tileContent, wGrid, new Pos(col, row));
            }
        }

        return wGrid;
    }

    public Obj [] GetP1AccessPoints () {
        return this.p1TravelPoints.GetList();
    }

    public Obj [] GetP2AccessPoints () {
        return this.p2TravelPoints.GetList();
    }

    // DEVELOPED BY : DAIKI
    // Check if the game is over
    public boolean isGameOver() {

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

    public Cell []FindAllCellContaining(String objType) {
        int len = 0;
        
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {
                if ((this.GetCell(new Pos(x, y)).GetObject() != null) 
                    && (this.GetCell(new Pos(x, y)).GetObject().GetStrType().equals(objType)))
                    len++;
            }
        }

        Cell [] cellArr = new Cell[len];
        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {
                if ((this.GetCell(new Pos(x, y)).GetObject() != null) 
                    && (this.GetCell(new Pos(x, y)).GetObject().GetStrType().equals(objType)))
                    cellArr[--len] = this.GetCell(new Pos(x, y));
            }
        }

        return cellArr;
    }
}