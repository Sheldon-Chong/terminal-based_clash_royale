// DEVELOPED BY : DAIKI

import java.util.Scanner;

/* 
 * Main class is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {
        FileHandler fHandler = new FileHandler();
        
        char[][] grid;

        grid = fHandler.readFile("game_grid.txt");

        GameSystem gameSys = new GameSystem();
        gameSys.CharGrid2CellGrid(grid);

        Scanner input     = new Scanner(System.in);
        Displayer display = new Displayer(gameSys);

        // Display title screen
        display.ShowTitleScreen();

        input.nextLine();

        // Get the players
        System.out.print("Enter Player 1 name: ");
        String player1Name = input.nextLine(); 
        
        System.out.print("Enter Player 2 name: ");
        String player2Name = input.nextLine();
        
        gameSys.GetPlayer1().SetName(player1Name);
        gameSys.GetPlayer2().SetName(player2Name);
        
        System.out.println("Welcome " + player1Name + " and " + player2Name + "!");
        
        // Wait for ENTER to start the game
        System.out.println("\nPress ENTER to begin game...");
        input.nextLine(); // Waits for ENTER input

        // Load and display the game board
        System.out.println("\nLoading the board...");
        display.PrintWorld(gameSys.GetGrid()); // Display the initial board


        // GAME LOOP
        
        while (true) {
            boolean skipTurn = false;

            display.PrintWorld(gameSys.GetGrid());
            display.DisplayCardDeck(gameSys.GetCurrentPlayer());  

            String userInput;

            while (true) { 

                System.out.print("Choose a card to deploy (1-4), or press ENTER to skip:");
                userInput = input.nextLine();

                if (userInput.length() == 0) { // if the input is empty
                    System.out.println("Skipping turn...");
                    skipTurn = true;
                    break;
                }
                if (userInput.length() != 1) // if the input is not a single character
                    System.out.println("Invalid input. Please enter a valid card number.");

                else if (userInput.charAt(0) < '1' || userInput.charAt(0) > '4') // if the input is not a number between 1 and 4
                    System.out.println("Invalid card number. Please select a number between 1 and 4.");
                
                else if (gameSys.ValidateDeploymentOfCard(Integer.parseInt(userInput) - 1) == -1) {
                    Card selectedCard = gameSys.GetCurrentPlayer().GetCard(Integer.parseInt(userInput) - 1);
                    System.out.printf("Not enough elixir to deploy %s. Required: %d, Available: %d\n", 
                        selectedCard.GetName(), 
                        selectedCard.GetElixirCost(), 
                        gameSys.GetCurrentPlayer().GetElixir());
                }
                else
                    break;
            }

            String positionRaw = "";

            while (true && !skipTurn) {
                System.out.print("Enter a deploy position (e.g., A5): ");
                positionRaw = input.nextLine();
                int parseStatus = gameSys.ValidatePositionString(positionRaw);

                if (parseStatus == gameSys.ERR_INVALID_FORMAT)
                    System.out.println("ERROR: Invalid format ");
                    
                else if (parseStatus == gameSys.ERR_INVALID_ROW)
                    System.out.println("ERROR: Invalid position");
                    
                else if (parseStatus == gameSys.ERR_INVALID_COL )
                    System.out.println("ERROR: Invalid position");
                    
                else if (parseStatus == gameSys.ERR_OUT_OF_BOUNDS)
                    System.out.println("ERROR: Out of bounds");
                
                else if (parseStatus == gameSys.ERR_INVALID_DEPLOY_REGION)
                    System.out.println("ERROR: You cannot place your troop in the enemy region");

                else if (parseStatus == gameSys.ERR_OCCUPIED_SPACE)
                    System.out.println("ERROR: Position already occupied");

                else if (parseStatus == 1)
                    break;
            }

            if (!skipTurn) {
                int cardIndex = Integer.parseInt(userInput) - 1;
    
                Pos deployPos = gameSys.parsePosition(positionRaw); // Assuming there's a method to convert user input into position
    
                gameSys.DeployCard(cardIndex, deployPos);
                display.PrintWorld(gameSys.GetGrid());
                
                System.out.printf("successfully deployed %s at (%d, %d)\n", gameSys.GetCurrentPlayer().GetCard(cardIndex).GetName(), deployPos.x, deployPos.y); 
            }

            System.out.print("Press ENTER to continue...");
            input.nextLine();
            
            gameSys.UpdateWorld();
    
            if (gameSys.isGameOver())
                break;
    
            gameSys.AlternatePlayer();
        }

        input.close();
    }
}

