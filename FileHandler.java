// ENTIRE CLASS DEVELOPED BY : SHELDON

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * The FileHandler class is responsible for reading and writing files
 */
class FileHandler {

    // -- CONSTRUCTORS --

    // DEVELOPED BY: Sheldon
    /* Default constructor */
    public FileHandler() {

    }


    // -- HELPER METHODS --

    // DEVELOPED BY: Sheldon
    /* Gets the dimensions of a file
     * @param fileName - the name of the file to get the dimensions of
     * @return - an array containing the number of rows and columns in the file */
    private int[] getFileDimensions(String fileName) {
        
        File file = new File(fileName);

        // read the lines of the file
        try {
            int rows = 0;
            int cols = 0;

            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                rows++;
                if (line.length() > cols)
                    cols = line.length();
            }
            input.close();
    
            return new int[] {rows, cols};
        }

        // if failed, return null and print the stack trace
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    // -- PUBLIC METHODS --

    // DEVELOPED BY: Sheldon
    /* Checks if a file exists
     * @param filename - the name of the file to check
     * @return - true if the file exists, false otherwise */
    public boolean fileExists(String filename) {
        File file = new File(filename);

        // tries to read from the file
        try {
            Scanner input = new Scanner(file);

            input.close();
        } 

        // if failed, we may assume that the file doesn't exist
        catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    // DEVELOPED BY: Sheldon
    /* Checks if all files exist
     * @param filenames - an array of filenames to check
     * @return - the name of the first file that does not exist, or null if all files exist */
    public String filesExist(String [] filenames) {

        // iterate through the array of filenames
        for (int i = 0; i < filenames.length; i ++) {

            // if a file does not exist, return the name of the file
            if (!this.fileExists(filenames[i]))
                return filenames[i];
        }
        
        return null;
    }

    // DEVELOPED BY: Sheldon
    /* Reads a file and returns a 1D string array
     * @param fileName - the name of the file to read
     * @return - a 1D string array representation of the file */
    public String[] readFile2Lines(String fileName) {

        File file = new File(fileName);
        int[] dimensions = getFileDimensions(fileName);
    
        int rows = dimensions[0];
        String[] stringArray = new String[rows];
    
        // tries reading the file
        try {
            Scanner input = new Scanner(file);

            int row = 0;

            while (input.hasNextLine()) {
                String line = input.nextLine();
                stringArray[row] = line;
                row++;
            }

            input.close();
        } 

        // if failed, return null and print the stack trace
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    
        return stringArray;
    }
    
    // DEVELOPED BY: Sheldon
    /* Reads a file and returns a 2D char array
     * @param fileName - the name of the file to read
     * @return - a 2D char array representation of the file */
    public char[][] readFile2Grid(String fileName) {
        String[] lines = readFile2Lines(fileName);
        if (lines == null) {
            return null;
        }
    
        int rows = lines.length;
        int cols = lines[0].length();
        char[][] charArray = new char[rows][cols];
    
        for (int row = 0; row < rows; row++) {
            
            String line = lines[row];

            for (int col = 0; col < line.length(); col++) {
                charArray[row][col] = line.charAt(col);
            }
        }
    
        return charArray;
    }

    // DEVELOPED BY: Sheldon
    /* Writes a 2D char array to a file
     * @param fileName - the name of the file to write to
     * @param arr - the 2D char array to write */
    public void print2DCharArr(char[][] arr) {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++)
                System.out.printf("%c", arr[row][col]);
            System.out.println();
        }
    }
}
