public abstract class Card {
    private int elixirCost;  // Cost to play this card

    public Card(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    public int GetElixirCost() {
        return elixirCost;
    }

    public void SetElixirCost(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    // Abstract method that each subclass should implement
    public abstract void deploy(Pos targetPos, GameSystem gameSysRef);
}
