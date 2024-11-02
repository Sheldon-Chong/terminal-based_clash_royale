import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		FileHandler fHandler = new FileHandler();
		char [][] grid = fHandler.readFile("game_grid.txt");
		//fHandler.print2DCharArr(grid);

		GameSystem gameSys = new GameSystem();
		gameSys.ConvertChar2DtoTile2D(grid);
		

		Scanner scanner = new Scanner(System.in);
		String input;
		Displayer display = new Displayer(gameSys);

		// Tile tile = gameSys.GetTile(new Pos(1, 0)).GetNeighbours()[Tile.NEIGHBOUR_LEFT];
		// if (tile != null)
		// 	System.out.println(tile.getObject());
		// else
		// 	System.out.println("null");
		
		 // Written by Daiki
		 // Display title screen
		 display.ShowTitleScreen();
		 
		 // Written by Daiki
		 // Prompt for player names
		 String dummy = scanner.nextLine(); // Dummy user input

		 System.out.print("Enter Player 1 name: ");
		 String player1Name = display.getPlayerName(1);
		 System.out.print("Enter Player 2 name: ");
		 String player2Name = display.getPlayerName(2);
 
		 System.out.println("Welcome " + player1Name + " and " + player2Name + "!");

		 // Initialize current player
		 String currentPlayer = player1Name;
		 int currentPlayerTurn = 1;

		 // Written by Daiki
		 // Wait for ENTER to start the game
		 System.out.println("\nPress ENTER to begin game...");
		 scanner.nextLine();  // Waits for ENTER input
 
		 // Written by Daiki
		 // Load and display the game board
		 System.out.println("\nLoading the board...");
		 display.printWorld(gameSys.GetGrid());  // Display the initial board

		
		while (true) {
			//gameSys.PrintWorldGridRaw(gameSys.GetGrid());
			gameSys.UpdateWorldBuffer();
			display.printWorld(gameSys.GetGrid()); // Dispalys the board
			System.out.println(currentPlayer + "");
			display.ShowGameInfo(gameSys.GetPlayer1()); // Display Player 1 info right below the board
			System.out.println("Enter 'q' to quit or any other key to simulate a move: ");
			input = scanner.nextLine();

			if (input.equals("q"))
				break;

		}
		scanner.close();
		

		
		
		
	}

	


}



// System.out.print(String.format("\033[%dA",count)); // Move up
// System.out.print("\033[2K"); // Erase line content