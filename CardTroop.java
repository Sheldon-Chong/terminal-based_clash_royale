public class CardTroop extends Card {
    private String troopType;
    private Player player;
    private Pos pos;

    public CardTroop(String troopType, Pos pos, Player player) {
        super(5);  // set a default elixir cost or modify accordingly
        this.troopType = troopType;
        this.player = player;
        this.pos = pos;
    }

    @Override
    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        // Logic to deploy the troop in the game system.
    }

    public String getTroopType() {
        return troopType;
    }
}
