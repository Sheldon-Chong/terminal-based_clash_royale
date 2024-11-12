// entire class by Sheldon
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * The FileHandler class is responsible for reading and writing files
 */

class FileHandler {

    // DEVELOPED BY: Sheldon
    private int[] getFileDimensions (String fileName) {
        int rows = 0;
        int cols = 0;
        int []dimensions = new int[2];

        File file = new File(fileName);

        // First pass to determine the dimensions of the char array
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                rows++;
                if (line.length() > cols)
                    cols = line.length();
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dimensions[0] = rows;
        dimensions[1] = cols;
        return dimensions;
    }

    public int GetRowCount(String fileName) {
        return getFileDimensions(fileName)[0];
    }

    public String [] readFileLine(String fileName) {
        File file = new File(fileName);
        
        int []dimensions = getFileDimensions(fileName);

        int rows = dimensions[0];
        int cols = dimensions[1];

        String [] stringArray = new String[rows];

        // Second pass to populate

        try {
            Scanner input = new Scanner(file);
            int row = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                stringArray[row] = line;
                row++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stringArray;
    }

    // DEVELOPED BY: Sheldon
    public char[][] readFile(String fileName) {
        File file = new File(fileName);
        
        int []dimensions = getFileDimensions(fileName);

        int rows = dimensions[0];
        int cols = dimensions[1];

        char[][] charArray = new char[rows][cols];

        // Second pass to populate the char array
        try {
            Scanner input = new Scanner(file);
            int row = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                for (int col = 0; col < line.length(); col++) {
                    charArray[row][col] = line.charAt(col);
                }
                row++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return charArray;
    }

    // DEVELOPED BY: Sheldon
    public void print2DCharArr(char[][] arr) {
        for (int row = 0; row < arr.length; row ++) {
            for (int col = 0; col < arr[row].length; col ++)
                System.out.printf("%c", arr[row][col]);
            System.out.println("");
        }
    }
}