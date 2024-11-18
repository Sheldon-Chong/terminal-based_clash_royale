// DEVELOPED BY : DAIKI

import java.util.Scanner;

/* 
 * Main class is the entry point of the program
 */
public class Main {
    public static void main(String[] args) {

        // - CHECK DEPENDENCIES -
        
        FileHandler fHandler = new FileHandler();
        
        // initial check to see if resource files are present
        String file = fHandler.filesExist(new String[] {
            "MsgGameOver.txt", 
            "MsgPlayerOverlay.txt",
            "GameMap.txt",
            "MsgTitleScreen.txt",
        });

        // if a resource file isn't present
        if (file != null) {
            
            // print the resource file
            System.out.printf("ERROR: resource '%s' doesn't exist\n", file);
            return ;
        }
        

        // - LOAD MAP FROM FILE  -

        char[][] grid      = fHandler.readFile2Grid("GameMap.txt");
        GameSystem gameSys = new GameSystem();
        Scanner input      = new Scanner(System.in);
        Displayer display  = new Displayer(gameSys);

        // Set the game map in the game system.
        gameSys.CharGrid2CellGrid(grid);

        display.ShowTitleScreen();


        // - GET PRELIMINARY INFO -

        input.nextLine();

        // Get the player's name
        System.out.print("Enter Player 1 name: ");
        String player1Name = input.nextLine(); 
        System.out.print("Enter Player 2 name: ");
        String player2Name = input.nextLine();
        
        // Set player names in the game system.
        gameSys.GetPlayer1().SetName(player1Name);
        gameSys.GetPlayer2().SetName(player2Name);
        
        System.out.println("Welcome " + player1Name + " and " + player2Name + "!");
        
        // Wait for ENTER to start the game
        System.out.println("\nPress ENTER to begin game...");
        input.nextLine();

        // Load and display the game board
        System.out.println("\nLoading the board...");
        display.PrintWorld(gameSys.GetGrid()); // Display the initial board


        // - GAME LOOP -
        
        while (true) {

            // a flag to skip the turn
            boolean skipTurn = false;


            // - DISPLAY GAME STATE -

            System.out.println("\n\n");
            display.DisplayRound();
            display.PrintPlayerOverlay();
            display.PrintWorld(gameSys.GetGrid());
            display.DisplayCardDeck(gameSys.GetCurrentPlayer());  

            
            
            // - READ FROM USER INPUT -
            
            String userInput;
            while (true) { 

                System.out.print("Choose a card to deploy (1-4), or press ENTER to skip:");
                userInput = input.nextLine();

                // if the input is empty
                if (userInput.length() == 0) { 
                    System.out.println("Skipping turn...");
                    skipTurn = true;
                    break;
                }
                
                int status = gameSys.ValidateDeploymentOfCard(userInput);

                if (status == GameSystem.ERR_INVALID_FORMAT)
                    System.out.println("ERROR: Invalid input. Please enter a valid card number.");

                else if (status == GameSystem.ERR_INVALID_CARD)
                    System.out.println("ERROR: Invalid card number. Please select a number between 1 and 4.");
                
                else if (status == GameSystem.ERR_ELIXIR_COST) {
                    Card selectedCard = gameSys.GetCurrentPlayer().GetCard(Integer.parseInt(userInput) - 1);
                    System.out.printf("ERROR: Not enough elixir to deploy %s. Required: %d, Available: %d\n", 
                        selectedCard.GetName(), 
                        selectedCard.GetElixirCost(), 
                        gameSys.GetCurrentPlayer().GetElixir());
                }
                else
                    break;
            }


            // - DEPLOY CARD -

            String positionRaw = "";

            // Handle position input for troop deployment.
            while (true && !skipTurn) {

                // Get the position to deploy the card.
                System.out.print("Enter a deploy position (e.g., A5): ");
                positionRaw = input.nextLine();

                // if user chooses to cancel deployment
                if (positionRaw.equals("cancel")) {
                    System.out.println("Cancelling deployment...");
                    skipTurn = true;
                    break;
                }

                int parseStatus = gameSys.ValidatePositionString(positionRaw);

                // error handling
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

            // Deploy the card if the user did not skip the turn.
            if (!skipTurn) {
                int cardIndex = Integer.parseInt(userInput) - 1;
    
                // Convert user input to position.
                Pos deployPos = gameSys.parsePosition(positionRaw); 
    
                // deploy card onto the world
                gameSys.DeployCard(cardIndex, deployPos);
                
                // print confirmation
                display.PrintWorld(gameSys.GetGrid());
                System.out.printf("successfully deployed %s at (%d, %d)\n", gameSys.GetCurrentPlayer().GetCard(cardIndex).GetName(), deployPos.x, deployPos.y); 
            }


            // - PROCEED TO NEXT TURN -

            System.out.print("Press ENTER to continue...");
            input.nextLine();
            
            // Update game state for the next round.
            gameSys.UpdateWorld();
    
            // check if game over
            if (gameSys.isGameOver())
                break;

            // Switch to the next player.
            gameSys.AlternatePlayer();
        }


        // - GAME OVER -

        // End game and display game over message.
        display.PrintWorld(gameSys.GetGrid());
        display.PrintGameOver(gameSys.GetWinner());

        input.close();
    }
}

