

public class Displayer {

    private GameSystem gameRef;
    private char[][] output;

    public Displayer(GameSystem gameSystem) {
        output = new char[0][]; // Initialize with an empty array
        this.gameRef = gameSystem;
    }

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

    private char getChar(Pos pos) {
        return output[pos.y][pos.x];
    }

    private void setChar(Pos pos, char newChar) {
        if (pos.y < 0 || pos.y >= output.length - 1 || pos.x < 0 || pos.x >=  output[0].length - 1)
            return;
        output[pos.y][pos.x] = newChar;
    }


    private void impose(String[] texture, Pos startingPos) {
        for (int y = 0; y < texture.length; y++) {
            for (int x = 0; x < texture[y].length(); x++) {
                Pos pos = new Pos(x, y).Add(startingPos);
                if (pos.y < 0 || pos.y >= output.length - 1 || pos.x < 0 || pos.x >=  output[0].length - 1)
                    continue;
                output[pos.y][pos.x] = texture[y].charAt(x);
            }
        }
    }

    public void printWorld(Tile[][] grid) {
        append("     ");
        for (int x = 0; x < grid[0].length; x++)
            add2LastItem(String.format(" %2d  ", x));

        // iterate for every row
        append("   _");
        for (int x = 0; x < grid[0].length; x++) {
            if (grid[0][x].getObject() instanceof Empty)
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
            else if (x > 0 && grid[grid.length - 1][x - 1].getObject() instanceof Empty && grid[grid.length - 1][x].left_side == ' ')
                add2LastItem("|____");
            else
                add2LastItem("_____");
        }
        
        
        
        
        Pos []corners2 = new Pos[4];
        
        Pos startingPos = new Pos(0, 0);
        corners2 = getCornersFromTile(startingPos);

        setChar(corners2[TOP_LEFT_CORNER], 'B');
        setChar(corners2[TOP_RIGHT_CORNER], 'B');
        setChar(corners2[BOTTOM_LEFT_CORNER], 'B');
        setChar(corners2[BOTTOM_RIGHT_CORNER], 'B');

        
        String [] test = {
            "| |__ ",
            "|____|",
        };

        String [] leftSide = {
            " :   ",
            " :   ",
        };


        String [] tower = {
            ".____.    .____.",
            "|  __|----|__  |",
            "|_|   ____   |_|",
            " :   | Q7 |   : ",
            " :   |____|   : ",
            "| |__      __| |",
            "|____|----|____|"
        };

        for (int y = 0; y < gameRef.GetGrid().length; y++) {
            for (int x = 0; x < gameRef.GetGrid()[0].length; x++) {
                Tile tile = gameRef.GetTile(new Pos (x,y));

                if (tile != null && tile.getObject() instanceof TowerWall)
                {
                    Pos []corners = new Pos[4];

                    corners = getCornersFromTile(new Pos (x, y));

                    // setChar(corners[TOP_LEFT_CORNER], '.');
                    // setChar(corners[TOP_RIGHT_CORNER], '.');
                    // setChar(corners[BOTTOM_LEFT_CORNER], '|');
                    // setChar(corners[BOTTOM_RIGHT_CORNER], '|');

                    int type = ((Tileset)(tile.getObject())).getType();
                    if (type == Tileset.CORNER_TOP_LEFT)
                        this.impose(tower, corners[TOP_LEFT_CORNER]);
                }
            }
        }
        
        

        for (int i = 0; i < output.length; i++)
            System.out.println(new String(output[i]));
        System.out.println();

    }


    private Pos convertPos2Corner( Pos pos ) {
        return (pos.Multiply(5,2).Add(4, 2));
    }

    
    final static int BOTTOM_LEFT_CORNER = 0;
    final static int BOTTOM_RIGHT_CORNER = 1;
    final static int TOP_LEFT_CORNER = 2;
    final static int TOP_RIGHT_CORNER = 3;

    private Pos[] getCornersFromTile (Pos pos) {
        Pos [] corners = new Pos[4];

        corners[TOP_LEFT_CORNER] = convertPos2Corner(pos.Add(0,0));
        corners[TOP_RIGHT_CORNER] = convertPos2Corner(pos.Add(1, 0));
        corners[BOTTOM_LEFT_CORNER] = convertPos2Corner(pos.Add(0,1));
        corners[BOTTOM_RIGHT_CORNER] = convertPos2Corner(pos.Add(1,1));
        
        return corners;
    }

    private Tile getTile(int x, int y) {
        if (x < 0 || x >= gameRef.GetGrid()[0].length || y < 0 || y >= gameRef.GetGrid().length)
            return new Tile();

        if (y < 0 || y >= gameRef.GetGrid().length)
            return new Tile();

        Tile tile = gameRef.GetGrid()[y][x];

        if (tile == null)
            return new Tile();

        return tile;
    }

    private void printEdgeRow(Tile[][] grid, int y) {
        // Initialize the buffer
        String buffer = String.format("  | ", (char) (y + 'A'));

        for (int x = 0; x < grid[y].length; x++) {
            char corner = '.';

            if (getTile(x, y - 1).left_side == '|' || getTile(x - 1, y - 1).right_side == '|')
                corner = '|';

            String edge = "    ";

            if (getTile(x, y).top_side == '|' || getTile(x, y - 1).bottom_side == '|')
                edge = "____";

            buffer += String.format("%c%s", corner, edge);
        }

        // Append the buffer to the output array
        this.append(buffer);
    }

    private void printContentRow(Tile[][] grid, int y) {
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
                icon = troop.getNameInitial();
                if (troop.GetPlayer().GetPlayerNum() == GameSystem.PLAYER1_REGION)
                    prefix = '1';
                else if (troop.GetPlayer().GetPlayerNum() == GameSystem.PLAYER2)
                    prefix = '2';

                if (troop.GetAction() == Troop.ACTION_MOVE)
                    suffix = '>';

                if (troop.GetAction() == Troop.ACTION_ATTACK)
                    suffix = '*';
            }

            if (this.getTile(x, y).left_side == '|' || (x > 0 && this.getTile(x - 1, y).right_side == '|'))
                edge = '|';
            else
                edge = ' ';

            buffer += String.format("%s%s%c%c%c", edge, prefix, icon, health, suffix);
        }

        // Append the buffer to the output array
        this.append(buffer);
    }
}



