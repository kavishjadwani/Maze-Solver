package pa3;
// Name:Kavish Jadwani
// USC loginid: jadwani
// CS 455 PA3
// Fall 2017
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * MazeComponent class
 *
 * A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {

    private static final int START_X = 10; // top left of corner of maze in frame
    private static final int START_Y = 10;
    private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
    private static final int BOX_HEIGHT = 20;
    private static final int INSET = 2;
    private static final Color WALLCOLOR = Color.BLACK;
    private static final Color FREECOLOR = Color.WHITE;
    private static final Color PATHCOLOR = Color.BLUE;
    private static final Color STARTCOLOR = Color.YELLOW;
    private static final Color EXITCOLOR = Color.GREEN;
    // how much smaller on each side to make entry/exit inner box
    private Maze MazeToBeDrawn;
    
    

    /**
     * Constructs the component.
     *
     * @param maze the maze to display
     */
    public MazeComponent(Maze maze) {
        MazeToBeDrawn = maze;

    }

    /**
     * Draws the current state of maze including the path through it if one has
     * been found.
     *
     * @param g the graphics context
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int rows = MazeToBeDrawn.numRows();
        int columns = MazeToBeDrawn.numCols();
        int startX;
        int startY = START_Y;
        for (int i = 0; i < rows; i++) {
            startX = START_X;
            for (int j = 0; j < columns; j++) {
                MazeCoord loc = new MazeCoord(i, j);
                if (MazeToBeDrawn.hasWallAt(loc)) {
                    Rectangle rectangle1 = new Rectangle(startX, startY, BOX_WIDTH, BOX_HEIGHT);
                    g2.setColor(WALLCOLOR);
                    g2.draw(rectangle1);
                    g2.fill(rectangle1);
                } else {
                    Rectangle rectangle1 = new Rectangle(startX, startY, BOX_WIDTH, BOX_HEIGHT);
                    g2.draw(rectangle1);
                    g2.setColor(FREECOLOR);
                    g2.fill(rectangle1);
                }
                startX = startX + BOX_WIDTH;
            }
            startY = startY + BOX_HEIGHT;
        }
        //Draw a black border around the maze 
        g2.setColor(Color.BLACK);
        Rectangle rectangleBorder = new Rectangle(START_X, START_Y, BOX_WIDTH * columns, BOX_HEIGHT * rows);
        g2.draw(rectangleBorder);
        //Draw a box to indicate the start location 
        MazeCoord startLoc = MazeToBeDrawn.getEntryLoc();
        g2.setColor(STARTCOLOR);
        Rectangle rectangleStartLoc = new Rectangle(INSET + START_X + startLoc.getCol() * BOX_WIDTH, INSET + START_Y + startLoc.getRow() * BOX_HEIGHT, BOX_WIDTH - 2 * INSET, BOX_HEIGHT - 2 * INSET);
        g2.draw(rectangleStartLoc);
        g2.fill(rectangleStartLoc);
        //Draw a box to indicate the exit location               
        MazeCoord exitLoc = MazeToBeDrawn.getExitLoc();
        g2.setColor(EXITCOLOR);
        Rectangle rectangleExitLoc = new Rectangle(INSET + START_X + (exitLoc.getCol()) * BOX_WIDTH, INSET + START_Y + exitLoc.getRow() * BOX_HEIGHT, BOX_WIDTH - 2 * INSET, BOX_HEIGHT - 2 * INSET);
        g2.draw(rectangleExitLoc);
        g2.fill(rectangleExitLoc);
        //Draw the path 
        LinkedList<MazeCoord> pathToBeDrawn = MazeToBeDrawn.getPath();
        int size = pathToBeDrawn.size();
        for (int i = 0; i < size - 1; i++) {
            MazeCoord startHere = pathToBeDrawn.removeFirst();
            MazeCoord endHere = pathToBeDrawn.peekFirst();
            g2.setColor(PATHCOLOR);
            g2.drawLine(START_X + (startHere.getCol()) * (BOX_WIDTH) + (BOX_WIDTH / 2), START_Y + (startHere.getRow()) * (BOX_HEIGHT) + BOX_HEIGHT / 2, START_X + (endHere.getCol()) * (BOX_WIDTH) + (BOX_WIDTH / 2), START_Y + (endHere.getRow()) * (BOX_HEIGHT) + (BOX_HEIGHT / 2));
        }
        pathToBeDrawn.clear();//Clear the linked list having the path 
    }
}
