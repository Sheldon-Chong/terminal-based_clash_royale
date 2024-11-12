// entire class by Sheldon

/*
 * The Card class is the parent class for all cards in the game
 * It contains the elixir cost of the card and an abstract method deploy
 */

public class Card {
    
    // -- CONSTANTS --

    public static final int SPELL = 0;
    public static final int TROOP = 1;
    

    // -- ATTRIBUTES --

    private String  name;
    private int     elixirCost;  // Cost to play this card
    private int     type; // Type of card (spell or troop)
    
    // -- CONSTRUCTOR --

    public Card(String name, int elixirCost, int type) { 
        this.name = name;
        this.elixirCost = elixirCost; 
        this.type = type;
    }


    // -- GETTERS AND SETTERS --

    public int  GetElixirCost() { return elixirCost; }
    public void SetElixirCost(int elixirCost) { this.elixirCost = elixirCost;  }
 
    public String GetName() { return name; }
    public void SetName()   { this.name = name; }

    public Card CopySelf() {
        Card newCard = new Card(this.name, this.elixirCost, this.type);
        
        return newCard; 
    }

    // Abstract method that each subclass should implement
    public void deploy(Pos targetPos, GameSystem gameSysRef) {

    }


    public int GetType() {
        return this.type;
    }
    
    // -- METHODS --

    public String GetRepr() {
        return this.name + " (" + this.elixirCost + ")";
    }
}
