package pa3;
//Name:Kavish Jadwani 
// USC loginid: jadwani 
// CS 455 PA3
// Fall 2017

/**
 * Maze class
 *
 * Stores information about a maze and can find a path through the maze (if
 * there is one).
 *
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and
 * endLoc (parameters to constructor), and the path: -- no outer walls given in
 * mazeData -- search assumes there is a virtual border around the maze (i.e.,
 * the maze path can't go outside of the maze boundaries) -- start location for
 * a path is maze coordinate startLoc -- exit location is maze coordinate
 * exitLoc -- mazeData input is a 2D array of booleans, where true means there
 * is a wall at that location, and false means there isn't (see public FREE /
 * WALL constants below) -- in mazeData the first index indicates the row. e.g.,
 * mazeData[row][col] -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 *
 */
import java.util.LinkedList;

public class Maze {

    public static final boolean FREE = false;
    public static final boolean WALL = true;
    private boolean[][] mazeData;
    private MazeCoord startLoc;
    private MazeCoord exitLoc;
    private LinkedList<MazeCoord> mazePath = new LinkedList<MazeCoord>();

    /**
     * Representation invariant: FREE represents a free node at the maze
     * coordinate. WALL represents a wall at the maze coordinate. mazeData is a
     * two dimensional boolean array representing walls and free nodes of maze.
     * mazeData represents the actual maze without virtual walls around it. maze
     * is padded with virtual walls by a helper method. maze padded with virtual
     * walls is used only for detecting existence of a path and finding the path
     * coordinates. 0 <= startLoc.getRow() < mazeData.length 0 <=
     * startLoc.getCol() < mazeData[0].length 0 <= endLoc.getRow() <
     * mazeData.length 0 <= endLoc.getCol() < mazeData[0].length
     */
    /**
     * Constructs a maze.
     *
     * @param mazeData the maze to search. See general Maze comments above for
     * what goes in this array.
     * @param startLoc the location in maze to start the search (not necessarily
     * on an edge)
     * @param exitLoc the "exit" location of the maze (not necessarily on an
     * edge) PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <=
     * startLoc.getCol() < mazeData[0].length and 0 <= endLoc.getRow() <
     * mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length
     *
     */
    public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
        this.mazeData = mazeData;
        this.startLoc = startLoc;
        this.exitLoc = exitLoc;
    }

    /**
     * Returns the number of rows in the maze
     *
     * @return number of rows
     */
    public int numRows() {
        return mazeData.length;   // DUMMY CODE TO GET IT TO COMPILE
    }

    /**
     * Returns the number of columns in the maze
     *
     * @return number of columns
     */
    public int numCols() {
        return mazeData[0].length;
    }

    /**
     * Returns true iff there is a wall at this location
     *
     * @param loc the location in maze coordinates
     * @return whether there is a wall here PRE: 0 <= loc.getRow() < numRows()
     * and 0 <= loc.getCol() < numCols()
     */
    public boolean hasWallAt(MazeCoord loc) {
        return mazeData[loc.getRow()][loc.getCol()];
    }

    /**
     * Returns the entry location of this maze.
     */
    public MazeCoord getEntryLoc() {
        return startLoc;
    }

    /**
     * Returns the exit location of this maze.
     */
    public MazeCoord getExitLoc() {
        return exitLoc;
    }

    /**
     * Returns the path through the maze. First element is start location, and
     * last element is exit location. If there was not path, or if this is
     * called before a call to search, returns empty list.
     *
     * @return the maze path
     */
    public LinkedList<MazeCoord> getPath() {
        return mazePath;
    }

    /**
     * Find a path from start location to the exit location (see Maze
     * constructor parameters, startLoc and exitLoc) if there is one. Client can
     * access the path found via getPath method.
     *
     * @return whether a path was found.
     */
    public boolean search() {
        if(mazePath.isEmpty()){
        MazeCoord startLocation = this.getEntryLoc();
        MazeCoord modifiedStartLocation = new MazeCoord(startLocation.getRow() + 1, startLocation.getCol() + 1);
        boolean[][] pathFindingMaze = putVirtualBorderAroundsMaze();
        return isPath(modifiedStartLocation, pathFindingMaze);
        }
        return true;
    }

    /**
     * Makes a recursive search for available path. If a path from start
     * location doesn't exist, the method returns false. Otherwise, the method
     * adds the start location coordinates to the linked list of path and checks
     * for the path from the adjacent coordinate. The method is called
     * recursively from adjacent locations until a path is found or until all
     * adjacent locations are checked for a valid path
     *
     * @param startLoc is the starting location of the maze
     * @param pathMaze is the maze with virtual walls around it. The path is
     * searched on this maze.
     * @return whether a path exists in the modified maze ie, the maze with
     * virtual walls around it
     */
    private boolean isPath(MazeCoord startLoc, boolean[][] pathMaze) {
        mazePath.add(new MazeCoord(startLoc.getRow() - 1, startLoc.getCol() - 1));
        boolean success = false;
        int xCoord = startLoc.getRow();
        int yCoord = startLoc.getCol();
        MazeCoord endLocation = this.getExitLoc();
        MazeCoord newEndLocation = new MazeCoord(endLocation.getRow() + 1, endLocation.getCol() + 1);
        if (startLoc.equals(newEndLocation)) {
            return true;
        }
        if (pathMaze[xCoord][yCoord] == true) {
            return false;
        }
        boolean[][] modifiedMaze = pathMaze;
        modifiedMaze[xCoord][yCoord] = true;
        success = checkAdjacent(pathMaze, xCoord, yCoord, 3, modifiedMaze, success);
        if (success == false) {
            success = checkAdjacent(pathMaze, xCoord, yCoord, 4, modifiedMaze, success);
        }

        if (success == false) {
            success = checkAdjacent(pathMaze, xCoord, yCoord, 1, modifiedMaze, success);
        }
        if (success == false) {
            success = checkAdjacent(pathMaze, xCoord, yCoord, 2, modifiedMaze, success);
        }
        MazeCoord lastElement = mazePath.removeLast();
        return success;
    }

    /**
     * This method puts virtual walls around the maze. The virtual walls ensure
     * that our algorithm doesn't search for a path which is outside the maze.
     */
    private boolean[][] putVirtualBorderAroundsMaze() {
        int mazeRow = this.numRows() + 2;
        int mazeColumn = this.numCols() + 2;
        boolean[][] pathFindingMaze = new boolean[mazeRow][mazeColumn];
        for (int i = 0; i < mazeColumn; i++) {
            pathFindingMaze[0][i] = true;
            pathFindingMaze[mazeRow - 1][i] = true;
        }
        for (int i = 0; i < mazeRow; i++) {
            pathFindingMaze[i][0] = true;
            pathFindingMaze[i][mazeColumn - 1] = true;
        }
        for (int i = 1; i < mazeRow - 1; i++) {
            for (int j = 1; j < mazeColumn - 1; j++) {
                pathFindingMaze[i][j] = this.mazeData[i - 1][j - 1];

            }
        }
        return pathFindingMaze;
    }

    /**
     * This method moves the start location to the adjacent location in the
     * direction provided as the parameter. Whenever the start location is
     * updated, the new start location will search for a path on a modified
     * maze. This modified maze will have the previous start location being
     * turned into a wall.
     *
     * @param pathMaze is the maze with virtual walls padded around it
     * @param xCoord is the x coordinate of the current start position
     * @param yCoord is the y coordinate of the current start position
     * @param direction is the direction in which the start location is moved.
     * If direction is 1, the start location moves one step East. If direction
     * is 2, the start location moves one step West. If direction is 3, the
     * start location moves one step north. If direction is 4, the start
     * location moves one step south
     * @param modifiedMaze is the modified maze
     * @param success is set to true if a path exists from an adjacent location 
     * @return true if the path exist from an adjacent location 
     */
    private boolean checkAdjacent(boolean[][] pathMaze, int xCoord, int yCoord, int direction, boolean[][] modifiedMaze, boolean success) {
        if (direction == 1) {
            if (pathMaze[xCoord + 1][yCoord] == false) {
                success = isPath(new MazeCoord(xCoord + 1, yCoord), modifiedMaze);
                if (success == true) {
                    mazePath.add(new MazeCoord(xCoord + 1, yCoord));
                }
            }
        } else if (direction == 2) {
            if (pathMaze[xCoord - 1][yCoord] == false) {
                success = isPath(new MazeCoord(xCoord - 1, yCoord), modifiedMaze);
                if (success == true) {
                    mazePath.add(new MazeCoord(xCoord - 1, yCoord));
                }
            }
        } else if (direction == 3) {
            if (pathMaze[xCoord][yCoord + 1] == false) {
                success = isPath(new MazeCoord(xCoord, yCoord + 1), modifiedMaze);
                if (success == true) {
                    mazePath.add(new MazeCoord(xCoord, yCoord + 1));
                }
            }
        } else if (direction == 4) {
            if (pathMaze[xCoord][yCoord - 1] == false) {
                success = isPath(new MazeCoord(xCoord, yCoord - 1), modifiedMaze);
                if (success == true) {
                    mazePath.add(new MazeCoord(xCoord, yCoord - 1));
                }
            }
        }
        return success;
    }
}
