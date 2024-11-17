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
        this.SetDest(null);
    }

    public Troop(Pos startingPos, String name, Player parent) {
        this.SetPos(startingPos);
        this.name = name;
        this.player = parent;
        this.SetDest(null);
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
        
        // if destination isn't set
        if (dest == null)
            return false;

        Pos currentPos = this.GetPos().Copy();
        this.SetAction(ACTION_MOVE);
        int status;
            
        Pos moveVector = new Pos(0, 0);

        
        // GET X DIRECTION TO MOVE IN

        // Down
        if (this.GetPos().x < dest.x) moveVector.x = 1;
        
        // Up
        if (this.GetPos().x > dest.x) moveVector.x = -1;

        // move forward
        status = MoveTo(new Pos(moveVector.x, 0));

        // if movement was unsucessful due to a troop blocking, or being blocked by the world
        if (status == DEST_TROOP_BLOCKING || status == DEST_COLLISION_WORLD)
            this.attack(gameSysRef.GetCell(currentPos.Add(moveVector)).GetObject());
        
        moveVector = new Pos(0, 0);
        

        // GET Y DIRECTION TO MOVE IN

        // Down
        if (this.GetPos().y < dest.y) moveVector.y = 1;
        
        // Up
        if (this.GetPos().y > dest.y) moveVector.y = -1;
        
        // move forward
        status = MoveTo(new Pos (0, moveVector.y));

        // if movement was unsucessful due to a troop blocking, or being blocked by the world
        if (status == DEST_TROOP_BLOCKING || status == DEST_COLLISION_WORLD)
            this.attack(gameSysRef.GetCell(currentPos.Add(moveVector)).GetObject());

        return true;
    }
    
    /* moves the troop to a specified position, and checks for collisions with other objects */
    public int MoveTo(Pos destPos) {
        
        // create the position that the troop would be if the troop moved hypothetically 
        Pos movedPosition = this.GetPos().Add(destPos);


        // - CHECK IF POSITION IS OUTSIDE THE GRID -
        
        // if the moved-to position is out of bounds
        if (gameSysRef.isOutOfBounds(movedPosition))
            return DEST_OUT_OF_BOUNDS;


        // - CHECK IF POSITION COLLIDES WITH A WALL -

        Obj object = gameSysRef.GetGrid()[movedPosition.y][movedPosition.x].GetObject();

        // if the position the troop moves to happens to be occupied by a static object
        if (object instanceof TileTower || object instanceof TileEmpty)
            return DEST_COLLISION_WORLD;
            
            
        // - CHECK IF POSITION COLLIDES WITH TROOP -
            
        Troop[] troops = gameSysRef.GetTroops();
        
        // iterate for every troop in the world
        for (int troopIndex = 0; troopIndex < troops.length; troopIndex++) {
            Troop currentTroop = troops[troopIndex];

            // if the current troop matches position with this troop
            if (currentTroop.GetPos().IsEquals(movedPosition) && !troops[troopIndex].GetPos().IsEquals(this.GetPos()))
                return DEST_TROOP_BLOCKING;
        }

        // set position if all is sucessfull
        this.SetPos(movedPosition.Copy());
        
        return 1;
    }

    /* recalculates the destination of the troop */
    public void  RecalcDest() {
        
        Pos destination = null;
        int playerNum = this.player.GetPlayerNum(); 

        // - DECIDE WHERE TO SET DESTINATION -

        // if troop in enemy region
        if (playerNum != this.GetRegion()) { 
            destination = this.findNearestAccessPoint(gameSysRef.FindAllCellContaining("TileTower"));
        }

        // if this troop belongs player 1 and in player 2's region
        else if (playerNum == GameSystem.PLAYER1_REGION) {
            destination = this.findNearestAccessPoint(gameSysRef.GetP1NavMarkers(), this.GetPos(), this.gameSysRef.GetDimensions());
        }

        // if this troop belongs player 2 and in player 2's region
        else if (playerNum == GameSystem.PLAYER2_REGION) {
            destination = this.findNearestAccessPoint(gameSysRef.GetP2NavMarkers(), new Pos (0, 0), this.GetPos());
        }

        this.SetDest(destination);
    }

    /* checks if the troop is adjacent to a tower
     * @return Cell - the tower tile that the troop is adjacent to */
    public Cell IsAdjTower() {
        Cell []neighbours = gameSysRef.GetCell(this.GetPos()).GetNeighbours();

        // - CHECK IF TROOP IS ADJACENT TOA A TOWER -

        // iterate for each neighbour
        for (int i = 0; i < 4; i++) {
            
            // if the neighbour happens to be a tower tile
            if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileTower) {

                // if parent isn't null, return the parent
                if (((TileTower)neighbours[i].GetObject()).GetParent() != null)
                    return neighbours[i];
            }
        }

        return null;
    }

    /* performs an action (either moving or attacking), and sets the state of the troop */
    public void Action() {
        if (this.GetPos() == null)
            return;

        Cell adjTower = IsAdjTower();
        
        // if Troop is adjacent to tower
        if (adjTower != null) {

            // get parent that belongs to tower
            Tower parent = ((TileTower)adjTower.GetObject()).GetParent();

            // if the tower is an enemy tower
            if (parent != null && isEnemy(parent)) {

                this.SetAction(ACTION_ATTACK);
                parent.subtractHealth(this.GetAttack());
                return;
            }
        }
        
        this.moveTowards(this.GetDest());
        this.SetRegion(gameSysRef.GetObjRegion(this));
        this.RecalcDest();
    }


    // -- HELPER METHODS --

    /* locate available adjacent cells that allow the troop to reach a specified position, that aren't occupied */
    private Cell []findAccessPoint(Pos pos) {
        
        Cell []neighbours = this.gameSysRef.GetCell(pos).GetNeighbours();
        Cell[] accessPoints = new Cell[0];
        
        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null && neighbours[i].GetObject() instanceof TileFloor)
                    accessPoints = gameSysRef.AppendCell(accessPoints, neighbours[i]);
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

                if (((closestPoint == null) || this.GetPos().DistanceFrom(currentAccessPoint.GetPos()) < this.GetPos().DistanceFrom(closestPoint))
                    && currentAccessPoint.GetPos().x > start.x 
                    && currentAccessPoint.GetPos().x < end.x)
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
        } 

        else if (object instanceof Tower && isEnemy(object)) {
            Tower enemyTower = (Tower) object;
            enemyTower.subtractHealth(this.GetAttack());  // Apply attack to the tower
        }

        this.SetAction(ACTION_ATTACK);
    }
    
}