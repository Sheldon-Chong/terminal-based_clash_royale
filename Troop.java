


class Troop extends Obj {

    private Pos         Dest;
    private char        nameInitial = 't';
    private int         hp;
    private GameSystem  gameSysRef;
    private int         region = -1;
    private Player      player;
    private int         currentAction;

    public static final int DEST_TROOP_BLOCKING     = -1;
    public static final int DEST_OUT_OF_BOUNDS      = -2;
    public static final int DEST_COLLISION_WORLD    = -3;

    public static final int PRIORITY_TOWERS = 0;
    public static final int PRIORITY_TROOPS = 1;

    public static final int ACTION_ATTACK = 0;
    public static final int ACTION_MOVE = 1;

    // GETTER AND SETTERS
    public void SetRegion(int region) { this.region = region; }
    public int  GetRegion() { return this.region; }

    public int  GetAction() { return this.currentAction; }
    public void SetAction(int action) { this.currentAction = action; }

    public int  GetHP() { return this.hp; }
    public void SetHP(int hp) { this.hp = hp; }
    public void IncreaseHP(int hp) { this.hp += hp; }
    public void DecreaseHP(int hp) { this.hp -= hp; }

    public Player GetPlayer() { return this.player; }
    public void   SetPlayer(Player player) { this.player = player; }

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


    private Tile []FindAccessPoint(Pos pos) {
        Tile []neighbours = this.gameSysRef.GetTile(pos).GetNeighbours();

        int accessPointsLen =0;
        
        for (int i = 0; i < 4; i++) {
            if ( neighbours[i] != null 
                && neighbours[i].getObject() instanceof Floor)
                    accessPointsLen ++;
        }
        
        Tile[] accessPoints = new Tile[accessPointsLen];
        
        accessPointsLen = 0;
        for (int i = 0; i < 4; i++) {
            if ( neighbours[i] != null 
                && neighbours[i].getObject() instanceof Floor)
                    accessPoints[accessPointsLen++] = neighbours[i];
        }
        

        return accessPoints;
    }

    private Tile []findAllTowerTiles() {
        int towersLen = 0;
        
        for (int y = 0; y < this.gameSysRef.GetGrid().length; y++) {
            for (int x = 0; x < this.gameSysRef.GetGrid()[0].length; x++) {   
                if (this.gameSysRef.GetTile(new Pos (x, y)) != null &&
                    this.gameSysRef.GetTile(new Pos (x, y)).getObject() instanceof TowerWall)
                    towersLen++;
            }
        }

        Tile[] towersWalls = new Tile[towersLen];
        int index = 0;

        for (int y = 0; y < this.gameSysRef.GetGrid().length; y++) {
            for (int x = 0; x < this.gameSysRef.GetGrid()[0].length; x++) {   
                if (this.gameSysRef.GetTile(new Pos (x, y)) != null &&
                    this.gameSysRef.GetTile(new Pos (x, y)).getObject() instanceof TowerWall)
                    towersWalls[index++] = this.gameSysRef.GetTile(new Pos (x, y));
            }
        }

        return towersWalls;
    }

    private Pos findNearestAccessPoint(Tile []tile) {
        Pos closestPoint = null;

        for (int i = 0; i < tile.length; i++) {
            Tile []accessPoints = FindAccessPoint(tile[i].getPos());

            for (int j = 0; j < accessPoints.length; j++) {
                if (closestPoint == null)
                    closestPoint = accessPoints[j].getPos();

                else if (this.getPos().calcDistance(accessPoints[j].getPos()) < this.getPos().calcDistance(closestPoint))
                    closestPoint = accessPoints[j].getPos();
            }
        }

        return closestPoint;
    }


    public void  recalcDest() {
        Pos closestTowerWall = null;

        Tile [] p1EntryPoints = {
            gameSysRef.GetTile(new Pos(16, 3)), 
            gameSysRef.GetTile(new Pos(16, 14))
        };

        Tile [] p2EntryPoints = {
            gameSysRef.GetTile(new Pos(11, 3)), 
            gameSysRef.GetTile(new Pos(11, 14))
        };

        int playerNum = this.player.GetPlayerNum(); 
        int currRegion = this.GetRegion();

        if (playerNum == GameSystem.PLAYER1_REGION && currRegion == GameSystem.PLAYER1_REGION)
            closestTowerWall = findNearestAccessPoint(p1EntryPoints);

        else if (playerNum == GameSystem.PLAYER2 && currRegion == GameSystem.PLAYER2)
            closestTowerWall = findNearestAccessPoint(p2EntryPoints);

        else {
            Tile []neighbours = gameSysRef.GetTile(this.getPos()).GetNeighbours();
            
            for (int i = 0; i < 4; i++) {
                if (neighbours[i] != null && neighbours[i].getObject() instanceof TowerWall)
                    return;
            }
            closestTowerWall = findNearestAccessPoint(findAllTowerTiles());
        }
        
        this.SetDest(closestTowerWall);
    }


    public void move() {
        if (this.GetDest() == null || this.getPos() == null)
            return;

        this.moveTowards( this.GetDest() );
        this.SetRegion(gameSysRef.getObjRegion(this));
        this.recalcDest();
    }


    public void attack(Pos pos) {
        System.out.println("collision with troop");
        Obj object = this.gameSysRef.GetTile(pos).getObject();

        if (object != null && object instanceof Troop && ((Troop)object).GetPlayer().GetPlayerNum() != this.GetPlayer().GetPlayerNum()) {
            Troop enemy = (Troop)object;
            System.out.println("attack");

            enemy.DecreaseHP(1);

            if (enemy.GetHP() <= 0)
                this.gameSysRef.destroyTroop(enemy);
        }

        this.SetAction(ACTION_ATTACK);
    }


    public boolean moveTowards(Pos dest) {
        Pos moveVector = new Pos(0, 0);
        Pos currentPos = this.getPos().copy();
        int status;
        
        this.SetAction(ACTION_MOVE);

        // System.out.printf("%c = (%d, %d), -> (%d, %d)\n",
        //     this.nameInitial, 
        //     this.getPos().x,
        //     this.getPos().y,
        //     dest.x, 
        //     dest.y);

        if (this.getPos().x < dest.x) moveVector.x = 1;
        if (this.getPos().x > dest.x) moveVector.x = -1;
        status = travel(new Pos(moveVector.x, 0));
        if (status == DEST_TROOP_BLOCKING)
            this.attack(currentPos.Add(moveVector));
        
        moveVector = new Pos(0, 0);
        
        if (this.getPos().y < dest.y) moveVector.y = 1;
        if (this.getPos().y > dest.y) moveVector.y = -1;
        status = travel(new Pos (0, moveVector.y));

        if (status == DEST_TROOP_BLOCKING)
            this.attack(currentPos.Add(moveVector));

        return true;
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
