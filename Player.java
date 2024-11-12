// Entire class DEVELOPED BY: Daiki

/*
 * The Player class represents a player in the game.
 * It stores player information such as the player's name, deck, elixir, and towers.
 */

import java.util.Random;

public class Player {
    private String name;
    private Card[] cardsOnHand; // Fixed-size card array
    private int elixir;
    private Tower king, princess1, princess2;
    private int region;
    private int playerNum;

    public Player(String name, int playerNum) {
        this.name = name;
        this.playerNum = playerNum;
        this.cardsOnHand = new Card[4];  // Fixed-size for simplicity
        this.elixir = 10;
        this.region = region; // This should be passed via the constructor or set method
        this.king = new TowerKing(this);
        this.princess1 = new TowerPrincess(this);
        this.princess2 = new TowerPrincess(this);
    }

    // Getters and setters remain unchanged

    public String GetName() { return this.name; }
    public void   SetName(String name) { this.name = name; }

    public int  GetElixir() { return this.elixir; }
    public void SetElixir(int elixir) { this.elixir = elixir; }
    public void RegenerateElixir() { this.elixir += 1; }

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

    public int GetPlayerNum() { return this.playerNum; }
    
    public Card[] getCardsOnHand() { return this.cardsOnHand; } 
    public void   setCardsOnHand(Card[] cards) { this.cardsOnHand = cards; }

    public Card   GetCard(int index) {
        if (index >= 0 && index < cardsOnHand.length)
            return cardsOnHand[index];
        else
            return null;
    }
    
    // Modified to work with an array
    public void DeployCard(int index, Pos targetPos, GameSystem gameSys) {
        
        if (index >= 0 && index < cardsOnHand.length && cardsOnHand[index] != null) {
            
            Card selectedCard = cardsOnHand[index];
            
            if (selectedCard.GetElixirCost() <= elixir) {
                elixir -= selectedCard.GetElixirCost();  // Reduce elixir
                selectedCard.deploy(targetPos, gameSys);  // Deploy at the specified position
                System.out.println("Card deployed!");
                cardsOnHand[index] = null;  // Set the slot to null after deploying
            } 
            else
                System.out.println("Not enough elixir to deploy this card.");
        }
    }

    public void initializeDeck() {
    // Example troop names, should be replaced with actual data loading if needed
    String[] troopNames = {"barbarian", "skeleton", "golem", "knight", "goblins"};
    String[] spellNames = {"zap", "fireball", "lightning"};
    Random rand = new Random();

    // for (int i = 0; i < cardsOnHand.length; i++) {
    //     if (rand.nextBoolean()) {
    //         // Assuming troop
    //         String troopName = troopNames[rand.nextInt(troopNames.length)];
    //         cardsOnHand[i] = TroopLoader.loadEntity(troopName); // Load troop from TroopLoader
    //     } else {
    //         // Assuming spell
    //         String spellName = spellNames[rand.nextInt(spellNames.length)];
    //         cardsOnHand[i] = TroopLoader.loadEntity(spellName); // Load spell from TroopLoader
    //     }
    // }
}

}
