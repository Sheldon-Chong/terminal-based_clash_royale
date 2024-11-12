// entire class by Sheldon

/*
 * The Card class is the parent class for all cards in the game
 * It contains the elixir cost of the card and an abstract method deploy
 */

public abstract class Card {
    
    // ATTRIBUTES
    private String  name;
    private int     elixirCost;  // Cost to play this card

    // CONSTRUCTOR
    public Card(String name, int elixirCost) { 
        this.name = name;
        this.elixirCost = elixirCost; 
    }

    // GETTERS AND SETTERS
    public int  GetElixirCost() { return elixirCost; }
    public void SetElixirCost(int elixirCost) { this.elixirCost = elixirCost;  }
 
    public String GetName() { return name; }
    public void SetName()   { this.name = name; }

    // Abstract method that each subclass should implement
    public abstract void deploy(Pos targetPos, GameSystem gameSysRef);
}
