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

    // DEVELOPED BY: Daiki
    public Card() {
        this.name = "Card";
        this.elixirCost = 0;
        this.type = 0;
    }
    
    // DEVELOPED BY: Sheldon
    /* Constructor for Card
     * @param - name: the name of the card
     * @param - elixirCost: the elixir cost of the card
     * @param - type: the type of the card */
    public Card(String name, int elixirCost, int type) { 
        this.name = name;
        this.elixirCost = elixirCost; 
        this.type = type;
    }


    // -- GETTERS AND SETTERS --

    // DEVELOPED BY: Daiki
    public int GetElixirCost() {
        return elixirCost;
    }
    
    public void SetElixirCost(int elixirCost) {
        this.elixirCost = elixirCost;
    }
    
    // DEVELOPED BY: Daiki
    public String GetName() {
        return name;
    }
    
    // DEVELOPED BY: Daiki
    /* SetName sets the name of the card
     * @param - name: the new name of the card */
    public void SetName() {
        this.name = name;
    }
    
    // DEVELOPED BY: Sheldon
    /* CopySelf creates a new card with the same attributes as the current card
     * @return - Card */
    public Card CopySelf() {
        Card newCard = new Card(this.name, this.elixirCost, this.type);
        
        return newCard; 
    }

    // Abstract method that each subclass should implement
    public void deploy(Pos targetPos, GameSystem gameSysRef) {

    }

    // DEVELOPED BY: Daiki
    public int GetType() {
        return this.type;
    }
    

    // -- METHODS --

    public String GetRepr() {
        return this.name + " (" + this.elixirCost + ")";
    }
}
