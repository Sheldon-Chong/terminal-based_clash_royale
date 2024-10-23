
class Troop extends Obj {
    private Pos Dest;
    private char nameInitial = 't';
    private int hp;
    private GameSystem gameSysRef;
    int region = -1;
    private Player player;
    private int priority;
    
    private int currentAction;


    public static final int PRIORITY_TOWERS = 0;
    public static final int PRIORITY_TROOPS = 1;

    public static final int ACTION_ATTACK = 0;
    public static final int ACTION_MOVE = 1;

    // GETTER AND SETTERS
    public void SetRegion(int region) { this.region = region; }
    public int  GetRegion() { return this.region; }

    public int GetAction() { return this.currentAction; }
    public void SetAction(int action) { this.currentAction = action; }

    public int  getHP() { return this.hp; }
    public void setHP(int hp) { this.hp = hp; }

    public void   SetPlayer(Player player) { this.player = player; }
    public Player GetPlayer() { return this.player; }

    public Pos  GetDest() { return this.Dest; }
    public void SetDest(Pos dest) { this.Dest = dest; }

    public char getNameInitial() { return this.nameInitial; }
    public void setNameInitial(char nameInitial) { this.nameInitial = nameInitial; }

    public void SetGameSysRef(GameSystem gameSysRef) { this.gameSysRef = gameSysRef; }


    // CONSTRUCTORS
    public Troop() {
        this.setPos(new Pos(0, 0));
    }


    public Troop(Pos startingPos, char nameInitial, Player player) {
        this.setPos(startingPos);
        this.Dest = new Pos(5, 5);
        this.nameInitial = nameInitial;
        this.player = player;
    }

    public static final int DEST_TROOP_BLOCKING     = -1;
    public static final int DEST_OUT_OF_BOUNDS      = -2;
    public static final int DEST_COLLISION_WORLD    = -3;


    public int travel(Pos vect) {
        Pos dest = this.getPos().Add(vect);
        
        Troop[] troops = gameSysRef.GetTroops();
        
        for (int troopIndex = 0; troopIndex < troops.length; troopIndex++) {
            Troop currentTroop = troops[troopIndex];
            if (currentTroop.getPos().isEquals(dest) && !troops[troopIndex].getPos().isEquals(this.getPos()))
                return DEST_TROOP_BLOCKING;
        }
        
        if (dest.x < 0 || dest.x >= gameSysRef.GetGrid()[0].length
        || dest.y < 0 || dest.y >= gameSysRef.GetGrid().length)
            return DEST_OUT_OF_BOUNDS;

        Obj object = gameSysRef.GetGrid()[dest.y][dest.x].getObject();
        if (object instanceof TowerWall || object instanceof Empty) {
            System.out.println("collision with world");
            return DEST_COLLISION_WORLD;
        }

        this.setPos(dest.copy());
        return 1;
    }

    // private int countTroops() {
    //     int count = 0;

    //     for (int y = 0; y < this.gameSysRef.GetGrid().length; y++) {
    //         for (int x = 0; x < this.gameSysRef.GetGrid()[0].length; x++) {
    //             if (this.gameSysRef.GetTile(new Pos(x, y)).getObject() instanceof Troop)
    //                 count++;
    //         }
    //     }

    //     return count;
    // }

    private int isTroopPresent(Pos pos) {
        for (int i = 0; i < this.gameSysRef.GetTroops().length; i++) {
            if (this.gameSysRef.GetTroops()[i].getPos().isEquals(pos))
                return i;
        }

        return -1;
    }

    public void  recalcDest() {


        if (this.player.GetRegion() == GameSystem.PLAYER1_REGION)
        {
            // if (this.GetRegion() == GameSystem.PLAYER1_REGION)
            //     this.SetDest(new Pos(14, 3));
                
            // else if (this.GetRegion() == GameSystem.PLAYER2_REGION)
            //     this.SetDest(new Pos(25, 7));

            this.SetDest(new Pos(25, 7));
        }

        else if (this.player.GetRegion() == GameSystem.PLAYER2_REGION) 
        {
            if (this.GetRegion() == GameSystem.PLAYER2_REGION)
                this.SetDest(new Pos(12, 3));
                
            else if (this.GetRegion() == GameSystem.PLAYER1_REGION)
            {
                Pos closestTowerWall = null;

                Tile []neighbours = gameSysRef.GetTile(this.getPos()).GetNeighbours();
                
                for (int i = 0; i < 4; i++)
                {
                    if (neighbours[i] != null)
                    {
                        if (neighbours[i].getObject() instanceof TowerWall) {
                            return;
                        }
                    }
                }

                for (int y = 0; y < this.gameSysRef.GetGrid().length; y++) {
                    for (int x = 0; x < this.gameSysRef.GetGrid()[0].length; x++)
                    {
                        if (this.gameSysRef.GetTile(new Pos (x, y)).getObject() instanceof TowerWall) {
                                

                            neighbours = this.gameSysRef.GetTile(new Pos(x, y)).GetNeighbours();
                            
                            for (int i = 0; i < 4; i++)
                            {
                                if (neighbours[i] != null)
                                {
                                    if (neighbours[i].getObject() instanceof Floor ) {

    
                                        if (closestTowerWall == null) {
                                            closestTowerWall = neighbours[i].getPos();
                                            continue;
                                        }
                                        
                                        if (this.getPos().calcDistance(neighbours[i].getPos()) < this.getPos().calcDistance(closestTowerWall)) {
                                            closestTowerWall = neighbours[i].getPos();
                                            this.SetDest(closestTowerWall);
                                        }

                                    }
                                }
                            }
                        }    
                    }
                }
                
            }
        }
    }

    private int getEntryPoints(Pos pos) { 
        int entryPoints = 0;
    
        Tile neighbourLeft = this.gameSysRef.GetTile(new Pos (pos.x - 1, pos.y));
        Tile neighbourRight = this.gameSysRef.GetTile(new Pos (pos.x + 1, pos.y));
        Tile neighbourUp = this.gameSysRef.GetTile(new Pos (pos.x, pos.y - 1));
        Tile neighbourDown = this.gameSysRef.GetTile(new Pos (pos.x, pos.y + 1));

        if (neighbourLeft != null && neighbourLeft.getObject() instanceof Floor)
            entryPoints++;
        if (neighbourRight != null && neighbourRight.getObject() instanceof Floor)
            entryPoints++;
        if (neighbourUp != null && neighbourUp.getObject() instanceof Floor)
            entryPoints++;
        if (neighbourDown != null && neighbourDown.getObject() instanceof Floor)
            entryPoints++;
            
        return entryPoints;
    }

    public void move() {
        this.moveTowards( this.GetDest() );

        this.SetRegion(gameSysRef.getObjRegion(this));

        this.recalcDest();
    }

    public boolean moveTowards(Pos dest) {
        Pos moveVector;
        
        moveVector = new Pos(0, 0);
        System.out.printf("%c = (%d, %d), -> (%d, %d)\n",
            this.nameInitial, 
            this.getPos().x,
            this.getPos().y,
            dest.x, 
            dest.y);

        if (this.getPos().x < dest.x)
            moveVector.x = 1;

        else if (this.getPos().x > dest.x)
            moveVector.x = -1;

        travel(moveVector);
        moveVector = new Pos(0, 0);

        if (this.getPos().y < dest.y)
            moveVector.y = 1;

        else if (this.getPos().y > dest.y)
            moveVector.y = -1;

        int status = travel(moveVector);
        
        return (status == 1);
    }
}
// public class vec2 {
// public int x;
// public int y;
// public vec2(int x, int y) {
// this.x = x;
// this.y = y;
// }
// }
