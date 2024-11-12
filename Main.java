// DEVELOPED BY : DAIKI

import java.util.Scanner;

/* 
 * Main class is the entry point of the program
 */

 public class Main {

    public static void main(String[] args) {

        FileHandler fHandler = new FileHandler();
        char[][] grid = fHandler.readFile("game_grid.txt");
        // fHandler.print2DCharArr(grid);

        GameSystem gameSys = new GameSystem();
        gameSys.CharGrid2CellGrid(grid);

        Scanner input = new Scanner(System.in);
        Displayer display = new Displayer(gameSys);

        // Display title screen
        display.ShowTitleScreen();

        // Prompt for player 3
		input.nextLine(); 
        
        // Get the current player
        System.out.print("Enter Player 1 name: ");
        String player1Name = input.nextLine();  // Collect Player 1's name
        System.out.print("Enter Player 2 name: ");
        String player2Name = input.nextLine();  // Collect Player 2's name

        System.out.println("Welcome " + player1Name + " and " + player2Name + "!");
        gameSys.GetPlayer1().SetName(player1Name);  // Set Player 1's name in GameSystem
        gameSys.GetPlayer2().SetName(player2Name);  // Set Player 2's name in GameSystem

        // Wait for ENTER to start the game
        System.out.println("\nPress ENTER to begin game...");
        input.nextLine();  // Waits for ENTER input

        // Load and display the game board
        System.out.println("\nLoading the board...");
        display.PrintWorld(gameSys.GetGrid());  // Display the initial board

        // Start the game loop

        
        gameSys.SetCurrentPlayer(gameSys.GetPlayer1());

        while (true) {
            // Show the game board
            display.PrintWorld(gameSys.GetGrid());
            
            // Refresh and display current player's deck
            display.DisplayCardDeck(gameSys.GetCurrentPlayer());  

            // Ask player to deploy a card or skip
            System.out.println("Choose a card to deploy by number (1-4), or press ENTER to skip.");
            String userInput = input.nextLine();
    
            // Handle card deployment
            if (!userInput.isEmpty()) {
                
                int cardIndex = Integer.parseInt(userInput) - 1;
                
                if (cardIndex >= 0 && cardIndex < 4) {
                    Player currentPlayer = gameSys.GetCurrentPlayer();

                    Card selectedCard = currentPlayer.GetCard(cardIndex);
                    // if (currentPlayer.GetElixir() >= selectedCard.GetElixirCost())
                    //     deployCard(currentPlayer, selectedCard, cardIndex);
                    // else
                    //     System.out.println("Not enough elixir to deploy this card.");
                } 
                else
                    System.out.println("Invalid card number.");
            } 
            else
                System.out.println("Press ENTER to end your turn");
                
            gameSys.UpdateWorld();
                
            if (gameSys.IsEndGame())
                break;

            // Regenerate elixir and shuffle cards
            gameSys.RegenerateElixir();
            gameSys.shufflePlayerCards();
    
            input.nextLine(); // Wait for user to press ENTER to skip their turn
            System.out.println("Skipping turn.....");
    
            gameSys.AlternatePlayer();
        }

        input.close();
    }
}
