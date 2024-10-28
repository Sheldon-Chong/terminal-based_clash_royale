public abstract class Card {
    private int elixirCost;  // Cost to play this card

    public Card(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    public int getElixirCost() {
        return elixirCost;
    }

    public void setElixirCost(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    // Abstract method that each subclass should implement
    public abstract void deploy(Pos targetPos, GameSystem gameSysRef);
}
