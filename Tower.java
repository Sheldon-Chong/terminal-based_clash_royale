public class Tower extends Obj{
    
    private Pos pos;
    private Player parentPlayer;

    Tower () {
    }

    Tower (Player player) {
        this.parentPlayer = player;
    }

    public void SetPos(Pos pos) {
        this.pos = pos;
    }

    public Pos GetPos() {
        return this.pos;
    }

    public void SetPlayer(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
    }

    public Player GetPlayer() {
        return this.parentPlayer;
    }

}
