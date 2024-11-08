
class Player {

    // -- ATTRIBUTES --

    private Card[]  cardsOnHand;
    private int     region;
    private         TowerKing king;
    private int     elixir;
    private         TowerPrincess princess1;
    private         TowerPrincess princess2;


    // -- CONSTRUCTORS -- 
    
    // DEVELOPED BY: Sheldon
    public Player(int region) {
        this.cardsOnHand = new Card[4];
        this.SetPlayerNum(region);
    }


    // -- GETTERS AND SETTERS --
    
    // DEVELOPED BY: Sheldon
    public int    GetPlayerNum() { return this.region; }
    public void   SetPlayerNum(int region) { this.region = region; }
    
    // DEVELOPED BY: Daiki
    public Card[] getCardsOnHand() { return this.cardsOnHand; }
    public void   setCardsOnHand(Card[] cards) { this.cardsOnHand = cards;  }
    
    // DEVELOPED BY: Daiki
    public void SetElixir(int amt) { this.elixir = amt; }
    public int  GetElixir() { return this.elixir; }


    // -- METHODS --

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

    // DEVELOPED BY: Daiki
    public void DeployCard(int index) {
        if (index >= 0 && index < cardsOnHand.length) {
            Card selectedCard = cardsOnHand[index];
            if (selectedCard != null && selectedCard.GetElixirCost() <= elixir) {
                elixir -= selectedCard.GetElixirCost();
                System.out.println("Deployed card: " + selectedCard);
                cardsOnHand[index] = null; // Remove card from hand after deploying
            } else {
                System.out.println("Not enough elixir to deploy this card.");
            }
        }
    }
}
