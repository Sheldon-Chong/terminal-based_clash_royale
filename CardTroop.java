public class CardTroop extends Card {
    private String troopType;
    private Player player;
    private Pos pos;

    public CardTroop(String troopType, Pos pos, Player player) {
        super(troopType, 5); // Assuming elixir cost is 5
        this.troopType = troopType;
        this.player = player;
        this.pos = pos;
    }

    public void deploy(Pos targetPos, GameSystem gameSysRef) {
        // Logic to deploy the troop in the game system.
    }

    public String getTroopType() {
        return troopType;
    }
}
