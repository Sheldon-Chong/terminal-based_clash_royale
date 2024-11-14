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

        while (true) {
            display.PrintWorld(gameSys.GetGrid());
            display.DisplayCardDeck(gameSys.GetCurrentPlayer());  
            System.out.println("Choose a card to deploy by number (1-4), or press ENTER to skip.");
            String userInput = input.nextLine();
            for (int i = 0; i < 4; i++)
                System.out.printf("Card %d: %s\n", i+1, gameSys.GetCurrentPlayer().GetCard(i));

            // Handle card deployment
            if (!userInput.isEmpty()) {
                try {
                    int cardIndex = Integer.parseInt(userInput) - 1;
                    if (cardIndex < 0 || cardIndex >= 4) {
                        System.out.println("Invalid card number. Please select a number between 1 and 4.");
                        continue; // Skip further processing and re-ask for input
                    }

                    System.out.println("Enter a deploy position (e.g., A5): ");
                    String positionRaw = input.nextLine();
                    Pos deployPos = gameSys.parsePosition(positionRaw); // Assuming there's a method to convert user input into position

                    while (deployPos == null) {
                        System.out.println("Invalid position. Please enter a valid position (e.g., A5): ");
                        positionRaw = input.nextLine();
                        deployPos = gameSys.parsePosition(positionRaw);
                    }

                    Player currentPlayer = gameSys.GetCurrentPlayer();
                    Card selectedCard = currentPlayer.GetCard(cardIndex);

                    if (selectedCard.GetElixirCost() > currentPlayer.GetElixir()) {
                        System.out.printf("Not enough elixir to deploy %s. Required: %d, Available: %d\n", 
                                        selectedCard.GetName(), selectedCard.GetElixirCost(), currentPlayer.GetElixir());
                        continue; // Skip further processing and re-ask for input
                    }

                    int status = gameSys.DeployCard(cardIndex, deployPos);

                    // Response based on deployment status
                    if (status == 1) {
                        System.out.printf("%s deployed at (%d, %d). Press ENTER to continue.\n", 
                                        selectedCard.GetName(), deployPos.x, deployPos.y);
                        display.PrintWorld(gameSys.GetGrid());
                        System.out.print("Press ENTER to continue...");
                        input.nextLine();
                    } else {
                        System.out.println("Failed to deploy the card. Please try again.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid card number.");
                }
            } else {
                System.out.println("Press ENTER to end your turn");
            }

            // Update the game
            gameSys.UpdateWorld();
            gameSys.RegenerateElixir();

            if (gameSys.IsEndGame())
                break;

            gameSys.AlternatePlayer();
        }

        input.close();
    }
}
