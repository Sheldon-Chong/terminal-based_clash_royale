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
    

    // -- PUBLIC METHODS --

    // CONSTRUCTORS
    
    // Written by Sheldon
    public GameSystem() {
        this.initWorld();
    }

    
    // GETTERS AND SETTERS

    // written by Sheldon
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
    private char     getCharTile(int row, int col, char [][]grid) {
        if (row < 0 || row >= grid.length)
            return 0;

        if (col < 0 || col >= grid[row].length)
            return 0;

        return grid[row][col];
    }


    // Written by Daiki
    // Method to get the current round
    public int GetRound() { return this.currentRound; }
    public ObjList GetSpells () {
        return this.spellQueue;
    }

    // Written by Sheldon
    public Cell[][] ConvertChar2DtoTile2D(char[][] grid) {
        Cell [][]wGrid = new Cell[grid.length][grid[0].length];
    
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char currentTile = getCharTile(row, col, grid);

                Obj tileContent = new TileFloor();

                char neighbourLeft  = getCharTile(row, col - 1, grid);
                char neighbourRight = getCharTile(row, col + 1, grid);
                char neighbourUp    = getCharTile(row - 1, col, grid);
                char neighbourDown  = getCharTile(row + 1, col, grid);
                
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

    // Written by Daiki
    // Method to shuffle cards in each player's hand
    public void shufflePlayerCards() { 
        System.out.println("Shuffling player cards...");
        
        shuffleCards(player1);
        shuffleCards(player2);
    }

    // Written by Daiki
    // Method to check if troop/spell is deployed within the board
    public boolean IsWithinBoard(Pos pos) {
        return pos.x >= 0 && pos.x < worldGrid[0].length && pos.y >= 0 && pos.y < worldGrid.length;
    }

    // Written by Daiki
    // Method to increment the round count
    public void nextRound() { 
        this.currentRound++;
        System.out.println("Starting Round " + this.currentRound);
        shufflePlayerCards();
    }

    // Written by Daiki
    public int GetObjRegion(Obj object) {
        Pos pos = object.GetPos();

        if (pos.x > 0 && pos.x < 14)
            return PLAYER1_REGION;
        else
            return PLAYER2_REGION;
    }

    // Written by Daiki
    public void UpdateWorld() { 

        this.updateTroops();
        this.updateTiles();
        this.updateSpellQueue();
    }

    // Written by Sheldon
    public void destroyTroop(Troop troop) {
        this.troops.Pop(troop);
    }
    
    
    // -- HELPER METHODS --

    // Written by Sheldon
    private void updateTroops() {
        for (int i = 0; i < troops.GetLen(); i++) {
            Troop currentTroop = (Troop)troops.GetItem(i);
            Pos tempPos = currentTroop.GetPos().Copy();
            currentTroop.Move();
            GetCell(tempPos).SetObject(new TileFloor());
            GetCell(currentTroop.GetPos()).SetObject(currentTroop);
        }
    }

    // Written by Sheldon
    private void updateTiles() {
        for (int y = 0; y < this.GetGrid().length; y ++) {
            for (int x = 0; x < this.GetGrid()[y].length; x++) {
                
                if (this.GetCell(y, x).GetObject() instanceof Tower) {
                    Tower tower = (Tower) this.GetCell(y, x).GetObject();

                    if (tower.IsDestroyed())
                        this.GetCell(y, x).SetObject(new TileFloor());
                }

                if (this.GetCell(y, x).GetObject() instanceof TileTower) {
                    TileTower tower = (TileTower) this.GetCell(y, x).GetObject();

                    if (tower.GetParent().IsDestroyed())
                        this.GetCell(y, x).SetObject(new TileFloor());
                }
            }
        }
    }

    // Written by Sheldon
    private void updateSpellQueue() {
        for (int i = 0; i < this.spellQueue.GetLen(); i++) {
            WorldSpell spell = (WorldSpell)this.spellQueue.GetItem(i);
            spell.DeductCooldown();
            if (spell.GetCooldown() * -1 > spell.GetDuration()) {
                this.spellQueue.SetItem(i, null);
            }
            System.out.print("Spell " + i + " has " + spell.GetCooldown() + " turns left. Duration is " + spell.GetDuration() + "\n");
        }

        int index = 0;
        
        while(index < this.spellQueue.GetLen()) {
            if (this.spellQueue.GetItem(index) == null)
                this.spellQueue.Pop(index);
            else
                index++;
        }
    }

    // Written by Sheldon
    private Troop str2Troop(String str, Pos pos, Player player) {
        str = str.toLowerCase();
        
        if      (str.equals("barbarian"))   { return new TroopBarbarian(pos, player); }
        else if (str.equals("elixir golem")){ return new TroopElixirGolem(pos, player); }
        else if (str.equals("giant"))       { return new TroopGiant(pos, player); }
        else if (str.equals("goblins"))     { return new TroopGoblins(pos, player); }
        else if (str.equals("golem"))       { return new TroopGolem(pos, player); }
        else if (str.equals("hog rider"))   { return new TroopHogRider(pos, player); }
        else if (str.equals("knight"))      { return new TroopKnight(pos, player); }
        else if (str.equals("lumberjack"))  { return new TroopLumberjack(pos, player); }
        else if (str.equals("pekka"))       { return new TroopPEKKA(pos, player); }
        else if (str.equals("skeletons"))   { return new TroopSkeletons(pos, player); }
        else
            return null;
    }

    // Written by Sheldon
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

            this.troops.append(spawnTroop(startPos, (char) ('A' + i), currPlayer, 9));
        }
    }

    // Written by Sheldon
    private Troop spawnTroop(Pos startPos, char initial, Player playerRef, int hp) {
        Troop troop;
        
        troop = str2Troop("barbarian", startPos, playerRef);
        troop.SetHP(9);
        troop.SetGameSysRef(this);

        return troop;
    }

    // Written by Sheldon
    private boolean isInCharArr(char []arr, char c) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c)
                return true;
        }
        return false;
    }

    // Written by Sheldon
    private int GetCellSideType(char []subject, 
                                char neighbourLeft, 
                                char neighbourRight, 
                                char neighbourUp, 
                                char neighbourDown) {

        boolean nLeft   = isInCharArr(subject, neighbourLeft);
        boolean nRight  = isInCharArr(subject, neighbourRight);
        boolean nUp     = isInCharArr(subject, neighbourUp);
        boolean nDown   = isInCharArr(subject, neighbourDown);
        
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
                if (nUp && !nDown)  return Tile.SIDE_BOTTOM;
                if (nDown && !nUp)  return Tile.SIDE_TOP;
                                    return Tile.PIPE_H;
            } 

            else {
                if (nLeft && !nRight)   return Tile.SIDE_RIGHT;
                if (nRight && !nLeft)   return Tile.SIDE_LEFT;
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

    // Written by Daiki
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

    // Written by Sheldon
    private void initWorld() {
        this.spellQueue = new ObjList();
        this.spellQueue.append(new WorldSpell(new Pos(3, 6), new Pos(7, 8), 10));
        this.spellQueue.append(new WorldSpell(new Pos(4, 7), new Pos(10, 15), 10));

        ((WorldSpell)(spellQueue.GetItem(0))).SetDuration(4);
        ((WorldSpell)(spellQueue.GetItem(1))).SetDuration(4);

        this.troops = new ObjList();

        this.player1 = new Player(PLAYER1_REGION);
        this.player2 = new Player(PLAYER2_REGION);

        FileHandler fHandler = new FileHandler();
        char [][] grid = fHandler.readFile(FILENAME);
        
        this.worldGrid = this.ConvertChar2DtoTile2D(grid);

        this.spawnTroops(20);

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
}
