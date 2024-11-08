import java.util.Random;

/*
 * The GameSystem class is responsible for managing the game world and its entities
 * It contains the game grid, players, troops, and the current round number
 * contains methods to spawn troops, shuffle player cards, and update the world
 */

class GameSystem {

    // -- CONSTANTS --
    public final static String  FILENAME = "game_grid.txt";

    public final static char    SYMBOL_TOWER = 'T';
    public final static char    SYMBOL_FLOOR = '.';
    public final static char    SYMBOL_EMPTY = ' ';

    public final static char    P1_TOWER_PRINCESS = 'P';
    public final static char    P1_TOWER_KING = 'K';

    public final static char    P2_TOWER_PRINCESS = 'p';
    public final static char    P2_TOWER_KING = 'k';

    public final static int     NO_REGION = 0;
    public final static int     PLAYER1_REGION = 1;
    public final static int     PLAYER2_REGION = 2;
    
    
    // -- ATTRIBUTES --
    private ObjList  troops;
    private Cell[][] worldGrid;
    private ObjList  spellQueue;
    private          Player player1;
    private          Player player2;
    private int      currentRound = 1; // Trakcs the current round number
    

    // -- CONSTRUCTORS --
    
    // DEVELOPED BY: Sheldon
    public GameSystem() {
        this.initWorld();
    }

    
    // -- GETTERS AND SETTERS --

    // DEVELOPED BY: Sheldon
    public Player    GetPlayer1() { return this.player1; }
    public Player    GetPlayer2() { return this.player2; }
    public Troop[] GetTroops() {
        Obj[] objArray = this.troops.GetList();
        Troop[] troopArray = new Troop[objArray.length];
        for (int i = 0; i < objArray.length; i++) {
            troopArray[i] = (Troop) objArray[i];
        }
        return troopArray;
    }
 
    public Cell [][] GetGrid()    { return this.worldGrid; }

    public void      SetCell(int row, int col, Cell cell) { this.worldGrid[row][col] = cell; }
    public Cell      GetCell(int row, int col) { return this.worldGrid[row][col]; }
    public Cell      GetCell(Pos pos) {
        if (pos.x < 0 || pos.x >= this.worldGrid[0].length || pos.y < 0 || pos.y >= this.worldGrid.length)
            return null;
        
        return this.worldGrid[pos.y][pos.x];
    }
    private char     accessChar(int row, int col, char [][]grid) {
        if (row < 0 || row >= grid.length)
            return 0;

        if (col < 0 || col >= grid[row].length)
            return 0;

        return grid[row][col];
    }


    public boolean isOutOfBounds (Pos pos) {
        return pos.x < 0 || pos.x >= this.worldGrid[0].length || pos.y < 0 || pos.y >= this.worldGrid.length;
    }

    // DEVELOPED BY: Daiki
    // Method to get the current round
    public int GetRound() { return this.currentRound; }
    public ObjList GetSpells () {
        return this.spellQueue;
    }

    // DEVELOPED BY: Daiki
    // Method to shuffle cards in each player's hand
    public void shufflePlayerCards() { 
        System.out.println("Shuffling player cards...");
        
        shuffleCards(player1);
        shuffleCards(player2);
    }

    // DEVELOPED BY: Daiki
    // Method to check if troop/spell is deployed within the board
    public boolean IsWithinBoard(Pos pos) {
        return pos.x >= 0 && pos.x < worldGrid[0].length && pos.y >= 0 && pos.y < worldGrid.length;
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

    // DEVELOPED BY: Daiki
    public void UpdateWorld() { 

        this.updateTroops();
        this.updateTiles();
        this.updateSpellQueue();
    }

    // DEVELOPED BY: Sheldon
    public void destroyTroop(Troop troop) {
        this.troops.Pop(troop);
    }
    
    
    // -- HELPER METHODS --

    // DEVELOPED BY: Sheldon
    /* update the troops in the world grid, by accessing each element of the troops list
     */
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
     * checks if any towers have been destroyed. If so, replace them with floor tiles
     */
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
    /* update the spell queue by deducting the deploy time of each spell
     */
    private void updateSpellQueue() {
        
        // iterate through the spell queue and deduct the remaining deploy time of each spell
        for (int i = 0; i < this.spellQueue.GetLen(); i++) {
            WorldSpell spell = (WorldSpell)this.spellQueue.GetItem(i);

            spell.DeductDeployTime();

            if (spell.GetDeployTime() * -1 > spell.GetDuration())
                this.spellQueue.SetItem(i, null);
            
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
    /* convert a string to a new instance of a troop
     * @param troopType - the type of troop to be spawned
     * @param startingPos - the position where the troop will be spawned
     * @param parent - the player that the troop belongs to
     * @return - the newly spawned troop
    */
    private Troop newTroop(String troopType, Pos startingPos, Player parent) {
        troopType = troopType.toLowerCase();
        
        Troop newTroop = null;

        if      (troopType.equals("barbarian"))   { newTroop = new TroopBarbarian(startingPos, parent); }
        else if (troopType.equals("elixir golem")){ newTroop = new TroopElixirGolem(startingPos, parent); }
        else if (troopType.equals("giant"))       { newTroop = new TroopGiant(startingPos, parent); }
        else if (troopType.equals("goblins"))     { newTroop = new TroopGoblins(startingPos, parent); }
        else if (troopType.equals("golem"))       { newTroop = new TroopGolem(startingPos, parent); }
        else if (troopType.equals("hog rider"))   { newTroop = new TroopHogRider(startingPos, parent); }
        else if (troopType.equals("knight"))      { newTroop = new TroopKnight(startingPos, parent); }
        else if (troopType.equals("lumberjack"))  { newTroop = new TroopLumberjack(startingPos, parent); }
        else if (troopType.equals("pekka"))       { newTroop = new TroopPEKKA(startingPos, parent); }
        else if (troopType.equals("skeletons"))   { newTroop = new TroopSkeletons(startingPos, parent); }

        newTroop.SetGameSysRef(this);

        return newTroop;
    }

    // DEVELOPED BY: Sheldon
    /* randomly spawn troops on the game grid, with half of the troops belonging to each player
     * @param amt - the amount of troops to be spawned
     * @return - the list of spawned troops
     */
    private void spawnTroops(int amt) {
        this.troops.ClearList();

        for (int i = 0; i < amt; i++) {
            Player currPlayer = player1;
            
            if (i > (amt / 2))
                currPlayer = player2;

            Pos startPos;
            
            while (true) {
                if (currPlayer.GetPlayerNum() == PLAYER1_REGION)
                    startPos = new Pos((int) (Math.random() * (worldGrid[0].length / 2)), (int) (Math.random() * worldGrid.length));
                else
                    startPos = new Pos((int) ((Math.random() * (worldGrid[0].length / 2) ) + (worldGrid[0].length / 2)), (int) (Math.random() * worldGrid.length));
                    
                if (this.GetCell(startPos).GetObject() instanceof TileFloor)
                    break;
            }

            this.troops.append(newTroop("barbarian", startPos, currPlayer));
        }
    }

    // DEVELOPED BY: Sheldon
    /* check if a character is in a character array
     * @param arr - the character array to be checked
     * @param c - the character to be checked
     * @return - true if the character is in the array, false otherwise
     */
    private boolean isInCharArr(char []arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c)
                return true;
        }
        return false;
    }

    // DEVELOPED BY: Sheldon
    private int GetCellSideType(char []subject, 
                                char adjLeft, 
                                char adjRight, 
                                char adjUp, 
                                char adjDown) {

        boolean nLeft   = isInCharArr(subject, adjLeft);
        boolean nRight  = isInCharArr(subject, adjRight);
        boolean nUp     = isInCharArr(subject, adjUp);
        boolean nDown   = isInCharArr(subject, adjDown);
        
        int wallCount = 0;

        if (nLeft)  wallCount++;
        if (nRight) wallCount++;
        if (nUp)    wallCount++;
        if (nDown)  wallCount++;

        if (wallCount == 0) return Tile.INDEPENDANT;
        if (wallCount == 4) return Tile.INSIDE;

        // sides
        if ((nLeft && nRight) || (nUp && nDown)) {

            if (nLeft && nRight) {
                if (nUp && !nDown) return Tile.SIDE_BOTTOM;
                if (nDown && !nUp) return Tile.SIDE_TOP;
                
                return Tile.PIPE_H;
            } 

            else {
                if (nLeft && !nRight) return Tile.SIDE_RIGHT;
                if (nRight && !nLeft) return Tile.SIDE_LEFT;
                
                return Tile.PIPE_V;
            }

        } 
        
        // corners
        else {
            if (nRight && nDown) return Tile.CORNER_TOP_LEFT;
            if (nLeft && nDown)  return Tile.CORNER_TOP_RIGHT;
            if (nRight && nUp)   return Tile.CORNER_BOTTOM_LEFT;
            if (nLeft && nUp)    return Tile.CORNER_BOTTOM_RIGHT;
        }

        // inside
        return Tile.INSIDE;
    }

    // DEVELOPED BY: Daiki
    // Helper method to shuffle cards for a given player
    private void shuffleCards(Player player) {
        Random random = new Random();
        Card[] cards = player.getCardsOnHand();

        for (int i = cards.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }

        player.setCardsOnHand(cards);
    }

    // DEVELOPED BY: Sheldon
    /* initialize the game world by creating the grid, players, and troops
     */
    private void initWorld() {

        // initialize lists
        this.spellQueue = new ObjList();
        this.troops = new ObjList();

        // initialize players
        this.player1 = new Player(PLAYER1_REGION);
        this.player2 = new Player(PLAYER2_REGION);

        // initialize world grid
        FileHandler fHandler = new FileHandler();
        this.worldGrid = this.CharGrid2CellGrid(fHandler.readFile(FILENAME));

        // delete later
        this.spawnTroops(20);
        this.spellQueue.append(new WorldSpell(new Pos(3, 6), new Pos(7, 8), 10, 4));
        this.spellQueue.append(new WorldSpell(new Pos(4, 7), new Pos(10, 15), 10, 4));


        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {
                if (this.GetCell(new Pos(x, y)).GetObject() instanceof Tower) {
                    Cell []neighbours = this.GetCell(new Pos(x, y)).GetNeighbours();
                    Tower parent = (Tower) this.GetCell(new Pos(x, y)).GetObject();

                    for (int i = 0; i < 4; i++) {
                        if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileTower) {
                            TileTower wall = (TileTower) neighbours[i].GetObject();
                            wall.SetParent(parent);
                        }
                    }
                    
                    Pos[] diagonalPositions = {
                        new Pos(x, y).Add(1, 1),
                        new Pos(x, y).Add(-1, 1),
                        new Pos(x, y).Add(1, -1),
                        new Pos(x, y).Add(-1, -1)
                    };

                    for (int i = 0; i < diagonalPositions.length; i++) {
                        Cell cell = this.GetCell(diagonalPositions[i]);

                        if (cell != null && cell.GetObject() instanceof TileTower)
                            ((TileTower) cell.GetObject()).SetParent(parent);
                    }
                }
            }
        }
        this.UpdateWorld();
    }

    // DEVELOPED BY: Sheldon
    /* Convert a 2D char array to a 2D Cell array
     * @param grid - the 2D char array to be converted
     * @return - the converted 2D Cell array
     */
    public Cell[][] CharGrid2CellGrid(char[][] grid) {
        Cell [][]wGrid = new Cell[grid.length][grid[0].length];
    
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char currentTile = accessChar(row, col, grid);

                Obj tileContent = new TileFloor();

                // get the neighbouring characters
                char neighbourLeft  = accessChar(row, col - 1, grid);
                char neighbourRight = accessChar(row, col + 1, grid);
                char neighbourUp    = accessChar(row - 1, col, grid);
                char neighbourDown  = accessChar(row + 1, col, grid);
                
                // create the tile content based on the current character
                switch (currentTile) {
                    case P1_TOWER_PRINCESS:
                        tileContent = new TowerPrincess(this.player1);
                    break; case P1_TOWER_KING:
                        tileContent = new TowerKing(this.player1);
                        
                    break; case P2_TOWER_PRINCESS:
                        tileContent = new TowerPrincess(this.player2);
                    break; case P2_TOWER_KING:
                        tileContent = new TowerKing(this.player2);

                    break; case SYMBOL_TOWER:
                        tileContent = new TileTower(GetCellSideType(new char[]{SYMBOL_TOWER, 'K', 'k', 'p', 'P'}, neighbourLeft, neighbourRight, neighbourUp, neighbourDown));
                    break; case SYMBOL_EMPTY:
                        tileContent = new TileEmpty(GetCellSideType(new char[] {SYMBOL_EMPTY}, neighbourLeft, neighbourRight, neighbourUp, neighbourDown));
                    break; case SYMBOL_FLOOR:
                        tileContent = new TileFloor();
                }
                
                wGrid[row][col] = new Cell(tileContent, wGrid, new Pos(col, row));
            }
        }

        return wGrid;
    }
}
