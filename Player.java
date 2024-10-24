class Player {
    private int region;
    
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
}