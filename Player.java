class Player {
    private int region;
    
    public int GetRegion() {
        return this.region;
    }

    public void SetRegion(int region) {
        this.region = region;
    }

    public Player() {

    }

    public Player(int region) {
        this.SetRegion(region);
    }
}