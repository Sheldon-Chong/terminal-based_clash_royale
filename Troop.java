// entire class by Sheldon

/*
 * The troop class, Every troop instance will inherit from the Troop class
 * Troops are the main units in the game. They utilize specific algorithms to move towards the enemy's tower
 */

 class Troop extends Obj {

    // -- CONSTANTS --

    public static final int DEST_TROOP_BLOCKING     = -1;
    public static final int DEST_OUT_OF_BOUNDS      = -2;
    public static final int DEST_COLLISION_WORLD    = -3;

    public static final int PRIORITY_TOWERS = 0;
    public static final int PRIORITY_TROOPS = 1;

    public static final int ACTION_ATTACK = 0;
    public static final int ACTION_MOVE   = 1;

    
    // -- ATTRIBUTES --

    private Pos         Dest;
    private int         hp;
    private GameSystem  gameSysRef;
    private int         region = -1;
    private Player      player;
    private int         currentAction;
    private int         atk;
    private String      name;


    // -- CONSTRUCTORS --

    public Troop() {
        this.name = "troop";
        this.SetPos(new Pos(0, 0));
    }

    public Troop(Pos startingPos, String name, Player parent) {
        this.SetPos(startingPos);
        this.Dest = new Pos(5, 5);
        this.name = name;
        this.player = parent;
    }


    // -- GETTER AND SETTERS --

    public void SetRegion(int region) {
        this.region = region;
    }

    public int GetRegion() {
        return this.region;
    }

    public int GetAction() {
        return this.currentAction;
    }

    public void SetAction(int action) {
        this.currentAction = action;
    }

    public int GetHP() {
        return this.hp;
    }

    public void SetHP(int hp) {
        this.hp = hp;
    }

    public void IncreaseHP(int hp) {
        this.hp += hp;
    }

    public void DecreaseHP(int hp) {
        this.hp -= hp;

        if (this.hp <= 0)
            gameSysRef.destroyTroop(this);
    }

    public void SetAttack(int atk) {
        this.atk = atk;
    }

    public int GetAttack() {
        return this.atk;
    }

    public Player GetPlayer() {
        return this.player;
    }

    public void SetPlayer(Player player) {
        this.player = player;
    }

    public Pos GetDest() {
        return this.Dest;
    }

    public void SetDest(Pos dest) {
        this.Dest = dest;
    }

    public String GetNameShort() {
        return this.name.substring(0, 2);
    }

    public void SetGameSysRef(GameSystem gameSysRef) {
        this.gameSysRef = gameSysRef;
    }
    

    // -- METHODS --

    /* moves the troop towards a specified position
     * @param dest - the position to move towards
     * @return boolean - true if the troop has moved, false otherwise */
    private boolean moveTowards(Pos dest) {
        Pos moveVector = new Pos(0, 0);
        Pos currentPos = this.GetPos().Copy();
        int status;
        
        this.SetAction(ACTION_MOVE);

        // System.out.printf("%c = (%d, %d), -> (%d, %d)\n",
        //     this.nameInitial, 
        //     this.getPos().x,
        //     this.getPos().y,
        //     dest.x, 
        //     dest.y);

        if (this.GetPos().x < dest.x) moveVector.x = 1;
        if (this.GetPos().x > dest.x) moveVector.x = -1;
        status = MoveTo(new Pos(moveVector.x, 0));
        if (status == DEST_TROOP_BLOCKING || status == DEST_COLLISION_WORLD)
            this.attack(gameSysRef.GetCell(currentPos.Add(moveVector)).GetObject());
        
        moveVector = new Pos(0, 0);
        
        if (this.GetPos().y < dest.y) moveVector.y = 1;
        if (this.GetPos().y > dest.y) moveVector.y = -1;
        status = MoveTo(new Pos (0, moveVector.y));

        if (status == DEST_TROOP_BLOCKING || status == DEST_COLLISION_WORLD)
            this.attack(gameSysRef.GetCell(currentPos.Add(moveVector)).GetObject());

        return true;
    }
    
    /* moves the troop to a specified position, and checks for collisions with other objects */
    public int MoveTo(Pos destPos) {
        Pos movedPosition = this.GetPos().Add(destPos);
        
        Obj object = gameSysRef.GetGrid()[movedPosition.y][movedPosition.x].GetObject();

        if (object instanceof TileTower || object instanceof TileEmpty)
            return DEST_COLLISION_WORLD;

        Troop[] troops = gameSysRef.GetTroops();
        
        for (int troopIndex = 0; troopIndex < troops.length; troopIndex++) {
            Troop currentTroop = troops[troopIndex];
            if (currentTroop.GetPos().IsEquals(movedPosition) && !troops[troopIndex].GetPos().IsEquals(this.GetPos()))
                return DEST_TROOP_BLOCKING;
        }
        
        if (gameSysRef.isOutOfBounds(movedPosition))
            return DEST_OUT_OF_BOUNDS;

        this.SetPos(movedPosition.Copy());
        
        return 1;
    }

    private Pos startPos;
    private Pos endPos;

    /* recalculates the destination of the troop */
    public void  RecalcDest() {
        if (this.startPos == null)
            this.startPos = new Pos (0,0);
        
        if (this.endPos == null)
            this.endPos = new Pos (this.gameSysRef.GetGrid()[0].length, this.gameSysRef.GetGrid().length);

        Pos destination = null;

        Cell [] p1EntryPoints = new Cell[this.gameSysRef.GetP1AccessPoints().length];
        Cell [] p2EntryPoints = new Cell[this.gameSysRef.GetP2AccessPoints().length];

        for (int i = 0; i < p1EntryPoints.length; i++)
            p1EntryPoints[i] = ((Tile)(this.gameSysRef.GetP1AccessPoints()[i])).GetCell();

        for (int i = 0; i < p2EntryPoints.length; i++)
            p2EntryPoints[i] = ((Tile)(this.gameSysRef.GetP2AccessPoints()[i])).GetCell();
            

        int playerNum = this.player.GetPlayerNum(); 
        int currentRegion = this.GetRegion();


        boolean isAtAccessPoint = false;

        for (int i = 0; i < p1EntryPoints.length; i++) {
            if (this.GetPos().IsEquals(p1EntryPoints[i].GetPos())) {
                isAtAccessPoint = true;
                break;
            }
        }

        if (this.GetDest() == null || isAtAccessPoint) {
            if ((playerNum == GameSystem.PLAYER1_REGION && currentRegion == GameSystem.PLAYER1_REGION) 
                || (playerNum == GameSystem.PLAYER2_REGION && currentRegion == GameSystem.PLAYER2_REGION)) {
    
                if (playerNum == GameSystem.PLAYER1_REGION && currentRegion == GameSystem.PLAYER1_REGION)
                    destination = this.findNearestAccessPoint(p1EntryPoints, this.GetPos(), new Pos (this.gameSysRef.GetGrid()[0].length, this.gameSysRef.GetGrid().length));
        
                else if (playerNum == GameSystem.PLAYER2_REGION && currentRegion == GameSystem.PLAYER2_REGION)
                    destination = this.findNearestAccessPoint(p2EntryPoints, new Pos (this.gameSysRef.GetGrid()[0].length, this.gameSysRef.GetGrid().length), this.GetPos());
            }
                else {
                    Cell []neighbours = gameSysRef.GetCell(this.GetPos()).GetNeighbours();
                    
                    for (int i = 0; i < 4; i++) {
                        if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileTower)
                            return;
                    }
                    destination = this.findNearestAccessPoint(gameSysRef.FindAllCellContaining("TileTower"));
                }
                
            this.SetDest(destination);
        }
    }

    /* checks if the troop is adjacent to a tower
     * @return Cell - the tower tile that the troop is adjacent to */
    public Cell IsAdjTower() {
        Cell []neighbours = gameSysRef.GetCell(this.GetPos()).GetNeighbours();

        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileTower) {
                TileTower tileTower = (TileTower)neighbours[i].GetObject();

                if (tileTower.GetParent() != null)
                    return neighbours[i];
            }
        }

        return null;
    }

    /* performs an action (either moving or attacking), and sets the state of the troop */
    public void Action() {
        if (this.GetDest() == null || this.GetPos() == null)
            return;

        Cell adjTower = IsAdjTower();
        
        if (adjTower != null) {
            Tower parent = ((TileTower)adjTower.GetObject()).GetParent();

            if (parent != null && isEnemy(parent)) {
                this.SetAction(ACTION_ATTACK);
                parent.subtractHealth(1);
                return;
            }
        }
        
        this.moveTowards(this.GetDest());
        this.SetRegion(gameSysRef.GetObjRegion(this));
        this.RecalcDest();
    }


    // -- HELPER METHODS --

    /* locate available adjacent cells that allow the troop to reach a specified position, that isn't blocked by walls */
    private Cell []findAccessPoint(Pos pos) {
        
        Cell []neighbours = this.gameSysRef.GetCell(pos).GetNeighbours();

        int accessPointsLen = 0;
        
        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileFloor)
                    accessPointsLen ++;
        }
        
        Cell[] accessPoints = new Cell[accessPointsLen];
        
        accessPointsLen = 0;
        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileFloor)
                    accessPoints[accessPointsLen++] = neighbours[i];
        }

        return accessPoints;
    }

    /* locate the closest position to the troop that allows it to reach the specified position */
    private Pos findNearestAccessPoint(Cell []cells, Pos start, Pos end) {
        Pos closestPoint = null;

        // iterate for every cell in the given array
        for (int i = 0; i < cells.length; i++) {

            Cell []accessPoints = findAccessPoint(cells[i].GetPos());

            // iterate for every access point to the current cell
            for (int j = 0; j < accessPoints.length; j++) {
                Pos currentAccessPoint = accessPoints[j].GetPos();

                if (closestPoint == null)
                    closestPoint = currentAccessPoint;
                else if ((this.GetPos().DistanceFrom(currentAccessPoint.GetPos()) < this.GetPos().DistanceFrom(closestPoint))
                    && currentAccessPoint.GetPos().LargerThan(start) 
                    && currentAccessPoint.GetPos().SmallerThan(end))
                    closestPoint = currentAccessPoint;
            }
        }

        return closestPoint;
    }

    private Pos findNearestAccessPoint(Cell []cells) {
        return findNearestAccessPoint(cells, new Pos(0, 0), new Pos(this.gameSysRef.GetGrid()[0].length, this.gameSysRef.GetGrid().length));
    }

    /* checks if an object belongs to an enemy
     * @param object - the object to be checked */
    private boolean isEnemy(Obj object) {
        if (object == null)
            return false;

        if (object instanceof Troop && ((Troop)object).GetPlayer().GetPlayerNum() != this.GetPlayer().GetPlayerNum())
            return true;
        
        if (object instanceof Tower && ((Tower)object).GetPlayer().GetPlayerNum() != this.GetPlayer().GetPlayerNum())
            return true;

        return false;
    }


    // WRITTEN BY : DAIKI
    /*
     * 
     */
    private void attack(Obj object) {
        if (object instanceof Troop && isEnemy(object)) {
            Troop enemy = (Troop) object;
            enemy.DecreaseHP(this.GetAttack());  // Use this troop's specific attack value
        } else if (object instanceof Tower && isEnemy(object)) {
            Tower enemyTower = (Tower) object;
            enemyTower.subtractHealth(this.GetAttack());  // Apply attack to the tower
        }
        this.SetAction(ACTION_ATTACK);
    }
    
}