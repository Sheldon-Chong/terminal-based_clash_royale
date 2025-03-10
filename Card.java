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
    /* Construct a default Card with predefined values
     * This constructor initializes a card with a default name of "Card", 
     * an elixir cost of 0, and a type of 0 */
    public Card() {
        this.name = "Card";
        this.elixirCost = 0;
        this.type = 0;
    }
    
    // DEVELOPED BY: Sheldon
    /* Constructor for Card
     * @param name - the name of the card
     * @param elixirCost - the elixir cost of the card
     * @param type - the type of the card */
    public Card(String name, int elixirCost, int type) { 
        this.name = name;
        this.elixirCost = elixirCost; 
        this.type = type;
    }


    // -- GETTERS AND SETTERS --

    // DEVELOPED BY: Daiki
    /* Gets the elixir cost of each card*/
    public int GetElixirCost() {
        return elixirCost;
    }
    
    // DEVELOPED BY: Daiki
    /* Sets the elixir cost of each card */
    public void SetElixirCost(int elixirCost) {
        this.elixirCost = elixirCost;
    }
    
    // DEVELOPED BY: Daiki
    /* GetName gets the name of the card*/
    public String GetName() {
        return name;
    }
    
    // DEVELOPED BY: Daiki
    /* SetName sets the name of the card*/
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

    // DEVELOPED BY: Daiki
    /* Gets the type of card */
    public int GetType() {
        return this.type;
    }
    

    // -- METHODS --

    // DEVELOPED BY: Sheldon
    /* Deploy is an abstract method that is implemented in the child classes
     * It is used to deploy the card onto the game board */
    public String GetRepr() {
        return this.name + " (" + this.elixirCost + ")";
    }
}
