/*
 * The Player class represents a player in the game.
 * It stores player information such as the player's name, deck, elixir, and towers.
 */

public class Player {

    // -- ATTRIBUTES --

    private String      name;
    private Card[]      cardsOnHand; 
    private Card        upcomingCard;
    private int         elixir;
    private Tower       king, princess1, princess2;
    private int         region;
    private int         playerNum;
    private GameSystem  gameSysRef;


    // -- CONSTRUCTOR --

    // DEVELOPED BY: Daiki
    /* Construct a player with a name, player number, and game system reference
     * @param - name: the name of the player
     * @param - playerNum: the player number
     * @param - gameSysRef: the reference to the game system */
    public Player(String name, int playerNum, GameSystem gameSysRef) {
        this.name = name;
        this.playerNum = playerNum;
        this.cardsOnHand = new Card[4];  // Fixed-size for simplicity
        this.elixir = 10;
        this.region = region; // This should be passed via the constructor or set method
        this.king = new TowerKing(this);
        this.princess1 = new TowerPrincess(this);
        this.princess2 = new TowerPrincess(this);
        this.gameSysRef = gameSysRef;
    }


    // -- GETTERS AND SETTERS --

    // DEVELOPED BY: Daiki
    /* Retrieves the name of the player.
     * @return - the name of the player as a String. */
    public String GetName() {
        return this.name;
    }

    // DEVELOPED BY: Daiki
    /* Sets the player's name.
     * @param name - the new name to set for the player. */
    public void SetName(String name) {
        this.name = name;
    }

    // DEVELOPED BY: Daiki
    /* Gets the current amount of elixir the player has.
     * @return - the current elixir amount as an int. */
    public int GetElixir() {
        return this.elixir;
    }

    // DEVELOPED BY: Daiki
    /* Sets the elixir amount for the player.
     * @param elixir - the new elixir amount to set. */
    public void SetElixir(int elixir) {
        this.elixir = elixir;
    }

    // DEVELOPED BY: Daiki
    /* Regenerates elixir by incrementing the current elixir by 1. */
    public void RegenerateElixir() {
        if (this.elixir < 20)
            this.elixir += 1;
    }

    // DEVELOPED BY: Daiki
    /* Deducts a specified amount of elixir from the player's current elixir.
     * @param amount - the amount of elixir to deduct. */
    public void DeductElixir(int amount) {
        this.elixir -= amount;
    }

    // DEVELOPED BY: Daiki
    /* Gets the region code where the player is located.
     * @return - the region code as an int. */
    public int GetRegion() {
        return this.region;
    }

    // DEVELOPED BY: Daiki
    /* Sets the region for the player.
     * @param region - the new region code to set. */
    public void SetRegion(int region) {
        this.region = region;
    }

    // DEVELOPED BY: Daiki
    /* Retrieves a specific tower belonging to the player based on the provided index.
     * @param index - the index to fetch the tower (0 for King, 1 for Princess1, 2 for Princess2).
     * @return - the Tower object or null if an invalid index is provided. */
    public Tower GetTower(int index) {
        switch (index) {
            case 0: 
                return king;
            case 1: 
                return princess1;
            case 2: 
                return princess2;
            default: 
                return null;
        }
    }

    // DEVELOPED BY: Daiki
     /* Getter for the player's King Tower.
      * @return - the King Tower of the player. */
     public Tower GetKingTower() {
        return this.king;
    }

    // DEVELOPED BY: Daiki
    /* Sets the King Tower for the player.
     * @param king - the Tower object to set as the King Tower. */
    public void SetKingTower(Tower king) {
        this.king = king;
    }

    // DEVELOPED BY: Daiki
    /* Removes a card from the player's hand at the specified index and replaces it with an upcoming card.
     * @param index - the index of the card in the hand to replace.
     * @return - always returns 0 indicating the removal was processed. */
    public int RemoveCard (int index) {
        this.cardsOnHand[index] = null;
        this.cardsOnHand[index] = this.upcomingCard;
        this.upcomingCard = this.FindRandomUniqueCard();
        return 0;
    } 

    // DEVELOPED BY: Daiki
    /* Gets the player number.
     * @return - the player number as an int. */
    public int GetPlayerNum() {
        return this.playerNum;
    }

    // DEVELOPED BY: Daiki
    /* Retrieves the cards currently on the player's hand.
     * @return - an array of Card objects the player currently holds. */
    public Card[] GetCardsOnHand() {
        return this.cardsOnHand;
    }

    // DEVELOPED BY: Daiki
    /* Sets the array of cards that the player has on hand.
     * @param cards - an array of Card objects to set as the player's current hand. */
    public void SetCardsOnHand(Card[] cards) {
        this.cardsOnHand = cards;
    }

    // DEVELOPED BY: Daiki
    /* GetCard returns the card at the specified index
     * @param - index: the index of the card to get
     * @return - the card at the specified index */
    public Card   GetCard(int index) {
        if (index < 0 || index > 3)
            return null;

        else
            return cardsOnHand[index];
    }


    // -- PUBLIC METHODS --

    // DEVELOPED BY: Sheldon
    /* IsTroopInHand checks if a troop is in the player's hand
     * @param - troopName: the name of the troop to check
     * @return - true if the troop is in the player's hand, false otherwise */
    public boolean IsTroopInHand(String troopName) {
        for (int i = 0; i < 4; i++) {
            if (this.GetCard(i) != null && this.GetCard(i).GetName().equals(troopName))
                return true;
        }

        if (this.upcomingCard != null && this.upcomingCard.GetName().equals(troopName))
            return true;
        
        return false;
    }

    // DEVELOPED BY: Sheldon
    /* GetUpcomingCard returns the upcoming card
     * @return - the upcoming card */
    public Card GetUpcomingCard() {
        return this.upcomingCard;
    }

    // DEVELOPED BY: Sheldon
    /* FindRandomUniqueCard finds a random card that is not in the player's hand 
     * @return - the unique card */
    public Card FindRandomUniqueCard() {
        
        Card card;

        while (true) {
            // set a random index
            int randomIndex = (int) (Math.random() * gameSysRef.GetCards().length);

            // get the card at the random index
            card = gameSysRef.GetCards()[randomIndex];

            // check if the card is not in the player's hand
            if (!IsTroopInHand(card.GetName()))
                break;
        }

        return card;
    }

    // DEVELOPED BY: DAIKI
    /* ShuffleCards shuffles the cards in the player's hand and sets the upcoming card */
    public void ShuffleCards() {
        this.cardsOnHand = new Card[4];

        // Shuffle the deck
        for (int i = 0; i < 4; i++)
            this.cardsOnHand[i] = this.FindRandomUniqueCard();
        
        // Set the upcoming card
        this.upcomingCard = this.FindRandomUniqueCard();
    }
}