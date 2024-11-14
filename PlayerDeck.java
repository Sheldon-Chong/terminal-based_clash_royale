// ENTIRE FILE DEVELOPED BY DAIKI

import java.util.Random;

public class PlayerDeck {
    private Card[] cardsOnHand = new Card[4];  // Array to hold the 4 deployable cards
    private Card nextCard;  // The card that will replace the deployed one
    private int elixir = 10;  // The player's current elixir amount
    private Random random = new Random();  // Random number generator for shuffling the deck

    public PlayerDeck() {
        loadNewDeck();  // Initializes the deck with random cards
    }

    // Load the deck with random cards from a predefined list
    private void loadNewDeck() {
        String[] allCards = {"Barbarian", "Skeleton", "Golem", "Knight", "ElixirGolem", "HogRider", "Goblins", "Giant", "Lumberjack", "PEKKA", "Zap", "Fireball", "Lightning"};
        
        // Randomly assign 4 cards from the allCards list
        for (int i = 0; i < cardsOnHand.length; i++) {
            cardsOnHand[i] = (Card) TroopLoader.loadEntity(allCards[random.nextInt(allCards.length)]);
        }
        setNextCard();  // Initialize the next card
    }

    // Set the next card to be added to the hand after a deployment
    private void setNextCard() {
        String[] allCards = {"Barbarian", "Skeleton", "Golem", "Knight", "ElixirGolem", "HogRider", "Goblins", "Giant", "Lumberjack", "PEKKA", "Zap", "Fireball", "Lightning"};
        nextCard = (Card) TroopLoader.loadEntity(allCards[random.nextInt(allCards.length)]);
    }

    // Deploy a card from the hand at a specified position on the game board
    public boolean deployCard(int index, Pos targetPos, GameSystem gameSysRef) {
        if (index < 0 || index >= cardsOnHand.length) {
            return false; // Return false if the index is out of range
        }

        Card selectedCard = cardsOnHand[index];
        if (selectedCard != null && selectedCard.GetElixirCost() <= elixir) {
            elixir -= selectedCard.GetElixirCost();
            selectedCard.deploy(targetPos, gameSysRef);  // Deploy the card
            System.out.println(selectedCard.getClass().getSimpleName() + " has been deployed at " + targetPos + " by player");

            cardsOnHand[index] = nextCard;  // Replace the deployed card with the next card
            setNextCard();  // Refresh the next card
            return true;
        } else {
            System.out.println("Not enough elixir to deploy this card.");
            return false;
        }
    }

    // Regenerate elixir over time, ensuring it does not exceed 10
    public void regenerateElixir() {
        if (elixir < 10) {
            elixir++;
        }
    }

    // Display the current deck and the next card for debugging purposes
    public void displayDeck() {
        System.out.println("Current Cards: ");
        for (Card card : cardsOnHand) {
            if (card != null) {
                System.out.println(card.getClass().getSimpleName() + " - Elixir cost: " + card.GetElixirCost());
            }
        }
        System.out.println("Next Card: " + nextCard.getClass().getSimpleName());
    }

    // Display the current elixir level
    public void displayElixirBar() {
        System.out.print("Elixir: ");
        for (int i = 0; i < elixir; i++) {
            System.out.print("/");
        }
        System.out.println(" (Max: 10)");
    }

    public int getElixir() {
        return elixir;
    }
}
