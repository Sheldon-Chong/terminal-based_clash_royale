class GameSystem {

    private Tile [][]worldStatic;

    public static final String FILENAME = "game_grid.txt";

    public final static char TOWER = 'T';
    public final static char FLOOR = '.';
    public final static char EMPTY = ' ';


    public final static int NO_REGION = 0;
    public final static int PLAYER1_REGION = 1;
    public final static int PLAYER2_REGION = 2;

    private Troop[] troops = new Troop[5];
    
    private Pos WorldDimensions;

    private Player player1;
    private Player player2;

    public Player GetPlayer1 () { return this.player1; }
    public Player GetPlayer2 () { return this.player2; }


    // GETTERS AND SETTERS
    public Troop []GetTroops() { return this.troops; }

    public Tile [][]GetGrid() { return this.worldStatic; }

    public Tile GetTile(int row, int col) { return this.worldStatic[row][col]; }
    public Tile GetTile(Pos pos) {
        if (pos.x < 0 || pos.x >= this.worldStatic[0].length || pos.y < 0 || pos.y >= this.worldStatic.length)
            return null;
        
        return this.worldStatic[pos.y][pos.x];
    }

    public void SetTile(int row, int col, Tile tile) { this.worldStatic[row][col] = tile; }


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

    private void initWorld() {
        this.player1 = new Player(PLAYER1_REGION);
        this.player2 = new Player(PLAYER2_REGION);

        this.troops = new Troop[20];

        FileHandler fHandler = new FileHandler();
        char [][] grid = fHandler.readFile(FILENAME);
        
        this.worldStatic = this.ConvertChar2DtoTile2D(grid);

        for (int i = 0; i < troops.length; i++) {
            Player currPlayer = player1;
            
            if (i > (troops.length / 2))
                currPlayer = player2;

            Pos startPos;
            
            while (true) {
                if (currPlayer.GetPlayerNum() == PLAYER1_REGION)
                    startPos = new Pos((int) (Math.random() * (worldStatic[0].length / 2)), (int) (Math.random() * worldStatic.length));
                else
                    startPos = new Pos((int) ((Math.random() * (worldStatic[0].length / 2) ) + (worldStatic[0].length / 2)), (int) (Math.random() * worldStatic.length));

                if (worldStatic[startPos.y][startPos.x].getObject() instanceof Floor)
                    break;
            }

            this.troops[i] = SpawnTroop(startPos, (char) ('A' + i), currPlayer, 9);
        }

        for (int y = 0; y < this.worldStatic.length; y++) {
            for (int x = 0; x < this.worldStatic[y].length; x++) {
                setTileType(this.worldStatic[y][x]);
            }
        }

        this.UpdateWorldBuffer();
    }

    private Troop SpawnTroop(Pos startPos, char initial, Player playerRef, int hp) {
        Troop troop;
        
        troop = new Troop(startPos, initial, playerRef);
        troop.setHP(9);
        troop.SetGameSysRef(this);

        return troop;
    }


    private char access(int row, int col, char [][]grid) {
        if (row < 0 || row >= grid.length)
            return 0;

        if (col < 0 || col >= grid[row].length)
            return 0;

        return grid[row][col];
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
        // NOTE: migrate this function to the tile class
        Tileset tileset = (Tileset) currentTile.getObject();
        
        switch (tileset.getType()) {
            case Tileset.CORNER_TOP_LEFT:
                currentTile.right_side  = '|';
                currentTile.bottom_side = '|';
                
            break; case Tileset.CORNER_TOP_RIGHT:
                currentTile.left_side   = '|';
                currentTile.bottom_side = '|';

            break; case Tileset.CORNER_BOTTOM_LEFT:
                currentTile.right_side  = '|';
                currentTile.top_side    = '|';

            break; case Tileset.CORNER_BOTTOM_RIGHT:
                currentTile.left_side   = '|';
                currentTile.top_side    = '|';

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
            if (nLeft && nUp)    return Tileset.CORNER_TOP_LEFT;
            if (nRight && nUp)   return Tileset.CORNER_TOP_RIGHT;
            if (nLeft && nDown)  return Tileset.CORNER_BOTTOM_LEFT;
            if (nRight && nDown) return Tileset.CORNER_BOTTOM_RIGHT;
        }

        // inside
        return Tileset.INSIDE;
    }


    public int getObjRegion(Obj object) {
        Pos pos = object.getPos();

        if (pos.x > 0 && pos.x < 14)
            return PLAYER1_REGION;
        else
            return PLAYER2_REGION;
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
    
    public Tile[][] ConvertChar2DtoTile2D(char[][] grid) {
        Tile [][]worldStatic = new Tile[grid.length][grid[0].length];
    
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                char currentTile = access(row, col, grid);

                Obj tileContent = new Floor();

                char neighbourLeft  = access(row, col - 1, grid);
                char neighbourRight = access(row, col + 1, grid);
                char neighbourUp    = access(row - 1, col, grid);
                char neighbourDown  = access(row + 1, col, grid);

                if (currentTile == TOWER) {
                    int type = getTileSideType(TOWER, neighbourLeft, neighbourRight, neighbourUp, neighbourDown);
                    tileContent = new TowerWall(type);
                }
                
                else if (currentTile == EMPTY) {
                    int type = getTileSideType(EMPTY, neighbourLeft, neighbourRight, neighbourUp, neighbourDown);
                    tileContent = new Empty(type);
                }
                
                else if (currentTile == FLOOR) {
                    tileContent = new Floor();
                }

                // You can now use the isCorner, isSide, and cornerType variables as needed
                Tile tile = new Tile(tileContent, worldStatic, new Pos(col, row));
                worldStatic[row][col] = tile;
            }
    
        }
        return worldStatic;
    }


    public void PrintWorldGridRaw(Tile[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Object currentContents = grid[row][col].getObject();

                if (currentContents instanceof TowerWall)
                    System.out.print('T');
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
