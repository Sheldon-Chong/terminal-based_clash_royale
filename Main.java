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

		while (true) {
			gameSys.PrintWorldGridRaw(gameSys.GetGrid());
			gameSys.UpdateWorldBuffer();
			display.printWorld(gameSys.GetGrid());
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