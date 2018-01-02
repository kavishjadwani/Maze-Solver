package pa3;

// Name: Kavish Jadwani 
// USC loginid:jadwani 
// CS 455 PA3 
// Fall 2017
/**
 * MazeViewer class{
 *
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 *
 * How to call it from the command line:
 *
 * java MazeViewer mazeFile
 *
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start
 * location, and ending with the exit location. Each maze location is either a
 * wall (1) or free (0). Here is an example of contents of a file for a 3x4
 * maze, with start location as the top left, and exit location as the bottom
 * right (we count locations from 0, similar to Java arrays):
 *
 * 3 4
 * 0111 0000 1110 0 0 2 3
 *
 */
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import java.io.File;
import java.util.Scanner;

public class MazeViewer {

    private static final char WALL_CHAR = '1';
    private static final char FREE_CHAR = '0';

    public static void main(String[] args) {

        String fileName = "";

        try {

            if (args.length < 1) {
                System.out.println("ERROR: missing file name command line argument");
            } else {
                fileName = args[0];

                JFrame frame = readMazeFile(fileName);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setVisible(true);
            }

        } catch (FileNotFoundException exc) {
            System.out.println("ERROR: File not found: " + fileName);
        } catch (IOException exc) {
            exc.printStackTrace();
        }


        /**
         * readMazeFile reads in maze from the file whose name is given and
         * returns a MazeFrame created from it.
         *
         * @param fileName the name of a file to read from (file format shown in
         * class comments, above)
         * @returns a MazeFrame containing the data from the file.
         *
         * @throws FileNotFoundException if there's no such file (subclass of
         * IOException)
         * @throws IOException (hook given in case you want to do more
         * error-checking -- that would also involve changing main to catch
         * other exceptions)
         *
         */
    }

    private static MazeFrame readMazeFile(String fileName) throws IOException {
        File inFile = new File(fileName);
        try (Scanner in = new Scanner(inFile)) {
            int numberOfRows = in.nextInt();
            int numberOfColumns = in.nextInt();
            boolean[][] mazeWallFreeIndicator = new boolean[numberOfRows][numberOfColumns];
            String blankLine = in.nextLine();//Reads a blank line 
            for (int i = 0; i < numberOfRows; i++) { //Reads data of each row from the input file 
                String row = in.nextLine();
                char[] rowAsArray = row.toCharArray();
                for (int j = 0; j < rowAsArray.length; j++) { // Get's data from file and store it in maze
                    if (rowAsArray[j] == WALL_CHAR) {
                        mazeWallFreeIndicator[i][j] = true;
                    } else {
                        mazeWallFreeIndicator[i][j] = false;
                    }
                }
            }
            int startRow = in.nextInt(); //Get's the x coordnate of the start location 
            int startColumn = in.nextInt();//Get's the y coordinate of the start location 
            MazeCoord startLoc = new MazeCoord(startRow, startColumn); //Creates MazeCoord startLoc
            int endRow = in.nextInt();//Get's the x coordinate of the end location 
            int endColumn = in.nextInt();//Get's the y coordinate of the end location 
            MazeCoord endLoc = new MazeCoord(endRow, endColumn); //Creates MazeCoord endLoc
            return new MazeFrame(mazeWallFreeIndicator, startLoc, endLoc);
        }
    }
}
