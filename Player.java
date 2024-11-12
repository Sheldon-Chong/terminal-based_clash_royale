// Entire class DEVELOPED BY: Daiki

/*
 * The Player class represents a player in the game.
 * It stores player information such as the player's name, deck, elixir, and towers.
 */

public class Player {
    private String      name;
    private Card[]      cardsOnHand; // Fixed-size card array
    private Card        upcomingCard;
    private int         elixir;
    private Tower       king, princess1, princess2;
    private int         region;
    private int         playerNum;
    private GameSystem  gameSysRef;

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

    public String GetName() { return this.name; }
    public void   SetName(String name) { this.name = name; }

    public int  GetElixir() { return this.elixir; }
    public void SetElixir(int elixir) { this.elixir = elixir; }
    public void RegenerateElixir() { this.elixir += 1; }
    public void DeductElixir(int amount) { this.elixir -= amount; }

    public int GetRegion() { return this.region; }
    public void SetRegion(int region) { this.region = region; }

    public Tower GetTower(int index) {
        switch (index) {
            case 0: return king;
            case 1: return princess1;
            case 2: return princess2;
            default: return null;
        }
    }

    public int RemoveCard (int index) {
        this.cardsOnHand[index] = null;
        this.cardsOnHand[index] = this.upcomingCard;
        this.upcomingCard = this.FindRandomUniqueCard();
        return 0;
    } 

    public int GetPlayerNum() { return this.playerNum; }
    
    public Card[] getCardsOnHand() { return this.cardsOnHand; } 
    public void   setCardsOnHand(Card[] cards) { this.cardsOnHand = cards; }

    public Card   GetCard(int index) {
        if (index < 0 || index > 3)
            return null;

        else
            return cardsOnHand[index];
    }

    public boolean IsTroopInHand(String troopName) {
        for (int i = 0; i < 4; i++) {
            if (this.GetCard(i) != null && this.GetCard(i).GetName().equals(troopName))
                return true;
        }

        if (this.upcomingCard != null && this.upcomingCard.GetName().equals(troopName))
            return true;
        
        return false;
    }

    public Card getUpcomingCard() {
        return this.upcomingCard;
    }

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

    public void ShuffleCards() {
        this.cardsOnHand = new Card[4];

        // Shuffle the deck
        for (int i = 0; i < 4; i++)
            this.cardsOnHand[i] = this.FindRandomUniqueCard();
        
        this.upcomingCard = this.FindRandomUniqueCard();
    }
}