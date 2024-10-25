class GameSystem {

    private Tile [][]worldGrid;

    public static final String FILENAME = "game_grid.txt";

    public final static char TOWER_WALL = 'T';
    public final static char FLOOR = '.';
    public final static char EMPTY = ' ';
    
    public final static char P1_TOWER_PRINCESS = 'Q';
    public final static char P1_TOWER_KING = 'K';

    public final static char P2_TOWER_PRINCESS = 'q';
    public final static char P2_TOWER_KING = 'k';

    public final static int NO_REGION = 0;
    public final static int PLAYER1_REGION = 1;
    public final static int PLAYER2 = 2;

    private Troop[] troops = new Troop[5];
    
    private Pos WorldDimensions;

    private Player player1;
    private Player player2;

    public Player GetPlayer1 () { return this.player1; }
    public Player GetPlayer2 () { return this.player2; }


    // GETTERS AND SETTERS
    public Troop []GetTroops() { return this.troops; }

    public Tile [][]GetGrid() { return this.worldGrid; }

    public Tile GetTile(int row, int col) { return this.worldGrid[row][col]; }
    public Tile GetTile(Pos pos) {
        if (pos.x < 0 || pos.x >= this.worldGrid[0].length || pos.y < 0 || pos.y >= this.worldGrid.length)
            return null;
        
        return this.worldGrid[pos.y][pos.x];
    }

    public void SetTile(int row, int col, Tile tile) { this.worldGrid[row][col] = tile; }


    // CONSTRUCTOR
    public GameSystem() {
        this.initWorld();
    }

    public void destroyTroop(Troop troop) {
        for (int i = 0; i < troops.length; i++) {
            if (troops[i] == troop) {
                System.out.println(troops[i] + " diead");
                
                Troop []newTroops = new Troop[troops.length - 1];
                
                int index = 0;

                for (int j = 0; j < troops.length; j++) {
                    if (troops[j] != troop) {
                        newTroops[index] = troops[j];
                        index++;
                    }
                }

                troops = newTroops;

                break;
            }
        }
    }

    private void spawnTroops(int amt) {
        this.troops = new Troop[amt];

        for (int i = 0; i < troops.length; i++) {
            Player currPlayer = player1;
            
            if (i > (troops.length / 2))
                currPlayer = player2;

            Pos startPos;
            
            while (true) {
                if (currPlayer.GetPlayerNum() == PLAYER1_REGION)
                    startPos = new Pos((int) (Math.random() * (worldGrid[0].length / 2)), (int) (Math.random() * worldGrid.length));
                
                else
                    startPos = new Pos((int) ((Math.random() * (worldGrid[0].length / 2) ) + (worldGrid[0].length / 2)), (int) (Math.random() * worldGrid.length));

                if (worldGrid[startPos.y][startPos.x].getObject() instanceof Floor)
                    break;
            }

            this.troops[i] = spawnTroop(startPos, (char) ('A' + i), currPlayer, 9);
        }
    }

    private void initWorld() {
        this.player1 = new Player(PLAYER1_REGION);
        this.player2 = new Player(PLAYER2);

        FileHandler fHandler = new FileHandler();
        char [][] grid = fHandler.readFile(FILENAME);
        
        this.worldGrid = this.ConvertChar2DtoTile2D(grid);

        this.spawnTroops(20);

        for (int y = 0; y < this.worldGrid.length; y++) {
            for (int x = 0; x < this.worldGrid[y].length; x++) {
                setTileType(this.worldGrid[y][x]);
            }
        }

        this.UpdateWorldBuffer();
    }


    private Troop spawnTroop(Pos startPos, char initial, Player playerRef, int hp) {
        Troop troop;
        
        troop = new Troop(startPos, initial, playerRef);
        troop.SetHP(9);
        troop.SetGameSysRef(this);

        return troop;
    }


    


    private Tile[][] copyTiledGrid(Tile[][] grid) {
        Tile [][]newGrid = new Tile[grid.length][grid[0].length];

        for (int row = 0; row < grid.length; row++) {

            for (int col = 0; col < grid[row].length; col++)
                newGrid[row][col] = grid[row][col].CopySelf();
        }
        return newGrid;
    }


    private void setTileType(Tile currentTile) {
        if (!(currentTile.getObject() instanceof Tileset))
            return;

        // NOTE: migrate this function to the tile class
        Tileset tileset = (Tileset) currentTile.getObject();
        
        switch (tileset.getType()) {
            case Tileset.CORNER_TOP_LEFT:
                currentTile.left_side  = '|';
                currentTile.top_side = '|';
                
            break; case Tileset.CORNER_TOP_RIGHT:
                currentTile.right_side   = '|';
                currentTile.top_side = '|';

            break; case Tileset.CORNER_BOTTOM_LEFT:
                currentTile.bottom_side  = '|';
                currentTile.left_side    = '|';

            break; case Tileset.CORNER_BOTTOM_RIGHT:
                currentTile.right_side   = '|';
                currentTile.bottom_side    = '|';

            break; case Tileset.PIPE_H:
                currentTile.top_side    = '|';
                currentTile.bottom_side = '|';

            break; case Tileset.PIPE_V:
                currentTile.right_side  = '|';
                currentTile.left_side   = '|';

            break; case Tileset.SIDE_LEFT:
                currentTile.left_side   = '|';

            break; case Tileset.SIDE_RIGHT:
                currentTile.right_side  = '|';

            break; case Tileset.SIDE_TOP:
                currentTile.top_side    = '|';

            break; case Tileset.SIDE_BOTTOM:
                currentTile.bottom_side = '|';

            break; case Tileset.INDEPENDANT:
                currentTile.bottom_side = '|';
                currentTile.top_side    = '|';
                currentTile.left_side   = '|';
                currentTile.right_side  = '|';
        }
    }


    private int getTileSideType(char subject, 
                                char neighbourLeft, 
                                char neighbourRight,
                                char neighbourUp,
                                char neighbourDown) {

        boolean nLeft   = (neighbourLeft == subject);
        boolean nRight  = (neighbourRight == subject);
        boolean nUp     = (neighbourUp == subject);
        boolean nDown   = (neighbourDown == subject);
        
        int wallCount = 0;

        if (nLeft)  wallCount++;
        if (nRight) wallCount++;
        if (nUp)    wallCount++;
        if (nDown)  wallCount++;

        if (wallCount == 0) return Tileset.INDEPENDANT;
        if (wallCount == 4) return Tileset.INSIDE;

        // sides
        if ((nLeft && nRight) || (nUp && nDown)) {

            if (nLeft && nRight) {
                if (nUp && !nDown)  return Tileset.SIDE_BOTTOM;
                if (nDown && !nUp)  return Tileset.SIDE_TOP;
                                    return Tileset.PIPE_H;
            } 

            else {
                if (nLeft && !nRight)   return Tileset.SIDE_RIGHT;
                if (nRight && !nLeft)   return Tileset.SIDE_LEFT;
                                        return Tileset.PIPE_V;
            }

        } 
        
        // corners
        else {
            if (nRight && nDown)    return Tileset.CORNER_TOP_LEFT;
            if (nLeft && nDown)   return Tileset.CORNER_TOP_RIGHT;
            if (nRight && nUp)  return Tileset.CORNER_BOTTOM_LEFT;
            if (nLeft && nUp) return Tileset.CORNER_BOTTOM_RIGHT;
        }

        // inside
        return Tileset.INSIDE;
    }


    public int getObjRegion(Obj object) {
        Pos pos = object.getPos();

        if (pos.x > 0 && pos.x < 14)
            return PLAYER1_REGION;
        else
            return PLAYER2;
    }


    public void UpdateWorldBuffer() { 
        for (int i = 0; i < troops.length; i++) {
            if (troops[i] == null)
                continue;

            Troop currentTroop = troops[i];
            Pos tempPos = currentTroop.getPos().copy();
            currentTroop.move();
            GetTile(tempPos).SetObject(new Floor());
            GetTile(currentTroop.getPos()).SetObject(currentTroop);
        }
    }
    

    private char getCharTile(int row, int col, char [][]grid) {
        if (row < 0 || row >= grid.length)
            return 0;

        if (col < 0 || col >= grid[row].length)
            return 0;

        return grid[row][col];
    }


    public Tile[][] ConvertChar2DtoTile2D(char[][] grid) {
        Tile [][]worldGrid = new Tile[grid.length][grid[0].length];
    
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char currentTile = getCharTile(row, col, grid);

                Obj tileContent = new Floor();

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

                    break; case TOWER_WALL:
                        tileContent = new TowerWall(getTileSideType(TOWER_WALL, neighbourLeft, neighbourRight, neighbourUp, neighbourDown));
                    break; case EMPTY:
                        tileContent = new Empty(getTileSideType(EMPTY, neighbourLeft, neighbourRight, neighbourUp, neighbourDown));
                    break; case FLOOR:
                        tileContent = new Floor();
                }

                Tile tile = new Tile(tileContent, worldGrid, new Pos(col, row));
                worldGrid[row][col] = tile;
            }
        }
        return worldGrid;
    }


    public void PrintWorldGridRaw(Tile[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Object currentContents = grid[row][col].getObject();

                if (currentContents instanceof TowerWall)
                    System.out.print(((Tileset)(currentContents)).getType());

                else if (currentContents instanceof Empty)
                    System.out.print(' ');

                else if (currentContents instanceof Floor)
                    System.out.print('.');

                else
                    System.out.print('?');
                    
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
