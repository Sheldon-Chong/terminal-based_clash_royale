public class Displayer {

    private GameSystem gameRef;

    public Displayer(GameSystem gameSystem) {
        this.gameRef = gameSystem;
    }

    
    public void printWorld(Tile[][] grid) {

        System.out.print("     ");
        for (int x = 0; x < grid[0].length; x++)
            System.out.printf(" %2d  ", x);
        
        System.out.println();


        // iterate for every row
        System.out.print("   _");
        for (int x = 0; x < grid[0].length; x++)
        {
            if (grid[0][x].getObject() instanceof Empty)
                System.out.print("     ");

            else
                System.out.print("_____");

        }
        System.out.println();
        

		for (int y = 0; y < grid.length; y++) {
			int edgeType = 0;

            this.printEdgeRow(grid, y);
			this.printContentRow(grid, y);
		}

        this.printEdgeRow(grid, grid.length - 1);
        System.out.print("  |_");
        for (int x = 0; x < grid[0].length; x++)
        {
            if (grid[grid.length - 1][x].bottom_side == '|')
            {
                if (grid[grid.length - 1][x-1].left_side == ' ') 
                    System.out.print("|    ");

                else
                    System.out.print("     ");
            }

            else if (x > 0 && grid[grid.length - 1][x-1].getObject() instanceof Empty && grid[grid.length - 1][x].left_side == ' ') 
                System.out.print("|____");

            else
                System.out.print("_____");

        }
        System.out.println();
	}

    
    private Tile getTile(int x, int y) {
        if ( x < 0 || x >= gameRef.GetGrid()[0].length || y < 0 || y >= gameRef.GetGrid().length)
            return new Tile();

        if (y < 0 || y >= gameRef.GetGrid().length)
            return new Tile();

        Tile tile = gameRef.GetGrid()[y][x];

        if (tile == null)
            return new Tile();
            
        return tile;
    }

    
    private void printEdgeRow(Tile[][]grid, int y) {

        // print columns
        System.out.printf("  | ", (char)(y + 'A'));
        String buffer = "";
       
        for (int x = 0; x < grid[y].length; x++)
        {
            // if (y< grid.length && grid[y][x].getObject() instanceof TowerWall)
            // {
            //     TowerWall towerwall = (TowerWall)grid[y][x].getObject();
            //     if (towerwall.getType() == 1) {
            //         buffer += "   | ";
            //         continue;
            //     }

            //     if (towerwall.getType() == 2) {
            //         buffer += " |   ";
            //         continue;
            //     }
            // }


            if (y-1 > 0 && grid[y-1][x].getObject() instanceof TowerWall)
            {
                TowerWall towerwall = (TowerWall)grid[y - 1][x].getObject();
                if (towerwall.getType() == 3) {
                    buffer += "   |_";
                    continue;
                }

                if (towerwall.getType() == 4) {
                    buffer += "|_|  ";
                    continue;
                }

                if (towerwall.getType() == 2) {
                    buffer += "|___|";
                    continue;
                }

                if (towerwall.getType() == 1) {
                    buffer += "_|___";
                    continue;
                }
            }

            char corner = '.';

            if (getTile(x, y - 1).left_side == '|' || getTile(x - 1, y - 1).right_side == '|')
                corner = '|';

            String edge = "    ";

            if (getTile(x, y).top_side == '|' || getTile(x, y - 1).bottom_side == '|')
                edge = "____";

            buffer += String.format("%c%s", corner, edge);
        }

        System.out.print(buffer);
        System.out.println();
    }

    
    private void printContentRow(Tile [][]grid, int y) {

        System.out.printf("%c | ", (char)(y + 'A'));
        String buffer = "";

        // print columns
        for (int x = 0; x < grid[y].length; x++) {

            if (y-1 > 0 && grid[y-1][x].getObject() instanceof TowerWall)
            {
                TowerWall towerwall = (TowerWall)grid[y - 1][x].getObject();

                if (towerwall.getType() == 3) {
                    buffer += "_|___";
                    continue;
                }
            }
            

            char health = ' ';
            char icon   = ' ';
            char prefix = ' ';
            char suffix = ' ';
            char edge;
            
            if (grid[y][x].getObject() instanceof Tower) {
                health = '5';
                icon = 'T';
            }

            if (grid[y][x].getObject() instanceof Troop) {
                Troop troop = (Troop)grid[y][x].getObject();

                health = (char)(troop.GetHP() + '0');
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

            if (grid[y][x].left_side == '|' || (x > 0 && grid[y][x - 1].right_side == '|'))
                edge = '|';
			else
                edge = ' ';

            buffer += String.format("%s%s%c%c%c", edge, prefix,  icon, health, suffix);
        }
        System.out.print(buffer);
        System.out.println();
    }
}
