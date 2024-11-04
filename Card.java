// entire class by Sheldon

/*
 * The Card class is the parent class for all cards in the game
 * It contains the elixir cost of the card and an abstract method deploy
 */

public abstract class Card {
    
    // ATTRIBUTES
    private int elixirCost;  // Cost to play this card

    // CONSTRUCTOR
    public Card(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    // GETTERS AND SETTERS
    public int  GetElixirCost() { return elixirCost; }
    public void SetElixirCost(int elixirCost) { this.elixirCost = elixirCost; }

    // Abstract method that each subclass should implement
    public abstract void deploy(Pos targetPos, GameSystem gameSysRef);
}
