class Player {
    private int region;
    private TowerKing king;
    private TowerPrincess princess1;
    private TowerPrincess princess2;


    public int GetPlayerNum() {
        return this.region;
    }

    public void SetPlayerNum(int region) {
        this.region = region;
    }

    public Player() {

    }

    public Player(int region) {
        this.SetPlayerNum(region);
    }

    public void SetTowers(TowerKing king, TowerPrincess princess1, TowerPrincess princess2) {
        this.king = king;
        this.princess1 = princess1;
        this.princess2 = princess2;
    }
}