/*
 * The Player class represents a player in the game.
 * It stores player information such as the player's name, deck, elixir, and towers.
 */

public class Player {

    // -- ATTRIBUTES --

    private String      name;
    private Card[]      cardsOnHand; // Fixed-size card array
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
    public String GetName() {
        return this.name;
    }

    // DEVELOPED BY: Daiki
    public void SetName(String name) {
        this.name = name;
    }

    // DEVELOPED BY: Daiki
    public int GetElixir() {
        return this.elixir;
    }

    // DEVELOPED BY: Daiki
    public void SetElixir(int elixir) {
        this.elixir = elixir;
    }

    // DEVELOPED BY: Daiki
    public void RegenerateElixir() {
        this.elixir += 1;
    }

    // DEVELOPED BY: Daiki
    public void DeductElixir(int amount) {
        this.elixir -= amount;
    }

    // DEVELOPED BY: Daiki
    public int GetRegion() {
        return this.region;
    }

    // DEVELOPED BY: Daiki
    public void SetRegion(int region) {
        this.region = region;
    }

    // DEVELOPED BY: Daiki
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

     // Getter for King Tower
     public Tower GetKingTower() {
        return this.king;
    }

    public void SetKingTower(Tower king) {
        this.king = king;
    }

    // DEVELOPED BY: Daiki
    public int RemoveCard (int index) {
        this.cardsOnHand[index] = null;
        this.cardsOnHand[index] = this.upcomingCard;
        this.upcomingCard = this.FindRandomUniqueCard();
        return 0;
    } 

    // DEVELOPED BY: Daiki
    public int GetPlayerNum() {
        return this.playerNum;
    }

    // DEVELOPED BY: Daiki
    public Card[] getCardsOnHand() {
        return this.cardsOnHand;
    }

    // DEVELOPED BY: Daiki
    public void setCardsOnHand(Card[] cards) {
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
    public Card getUpcomingCard() {
        return this.upcomingCard;
    }

    // DEVELOPED BY: Sheldon
    /* FindRandomUniqueCard finds a random card that is not in the player's hand 
     * @return - the unique card */
    public Card FindRandomUniqueCard() {
        Card    card = null;
        boolean found = false;
        
        while (!found) {
            int randomIndex = (int) (Math.random() * gameSysRef.getCards().length);
            card = gameSysRef.getCards()[randomIndex];
            if (!IsTroopInHand(card.GetName()))
                found = true;
        }
        return card;
    }

    // DEVELOPED BY: Sheldon
    /* ShuffleCards shuffles the cards in the player's hand and sets the upcoming card */
    public void ShuffleCards() {
        this.cardsOnHand = new Card[4];

        // Shuffle the deck
        for (int i = 0; i < 4; i++)
            this.cardsOnHand[i] = this.FindRandomUniqueCard();
        
        this.upcomingCard = this.FindRandomUniqueCard();
    }
}