package Puzzle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

    /**
     * This is the Square Class. It is a wrapper class
     * for the squares that make up the tetris board and
     * the tetromino pieces.
     */

public class GameCircle {

    // declares instance variables.
    private Circle _circ;
    private GameCircle[][] _gameCircleArray;

    /**
     * This is the square's constructor. Takes in 4 parameters.
     * row, col specify position in the board. Square[][] board
     * so it knows of the board and can access it. colour for its
     * colour. Sets up a rectangle with desired properties and graphically
     * adds itself.
     */

    public GameCircle(int row, int col, Color colour, GameCircle[][] board) {
        _gameCircleArray = board;
        _circ = new Circle();
        _circ.setRadius(Constants.CIRCLE_WIDTH);
        _circ.setFill(colour);
        _circ.setStrokeType(StrokeType.INSIDE);
        _circ.setStroke(Color.BLACK);
        this.setLocation(row, col);
    }

    /**
     * This is the setLocation mutator method. It moves the square
     * (rect) to the row and col specified in the arguments.
     */

    public void setLocation(int row, int col) {
        _circ.setCenterX((col * 2 * Constants.CIRCLE_WIDTH) + Constants.BOARD_X_OFFSET) ;
        _circ.setCenterY((row * 2 * Constants.CIRCLE_WIDTH) + Constants.BOARD_Y_OFFSET);
    }

    /**
     * This is the translateSquareLocation which takes in colChange
     * and rowChange as arguments. It then moves the square by this amount.
     */

    public void translateCircleLocation(int colChange, int rowChange) {
        this.setLocation(this.getRow() + rowChange, this.getCol() + colChange);
    }

    /**
     * This is the getCol accessor method, which returns the column of the square.
     */

    public int getCol() {
        return (int) (( _circ.getCenterX()) - Constants.CIRCLE_WIDTH) / (2 * Constants.CIRCLE_WIDTH);
    }

    /**
     * This is the getRow accessor method, which returns the row of the square.
     */

    public int getRow() {
        return (int) ( _circ.getCenterY() - Constants.CIRCLE_WIDTH )/ (2 * Constants.CIRCLE_WIDTH);
    }

    /**
     * This is the getRect accessor method, which returns the rectangle the square
     * is made of.
     */

    public Circle getCirc() {
        return _circ;
    }

    public void setColour(Color color) {
        _circ.setFill(color);
    }

    /**
     * This is the canMoveTo helper method. It takes in a colChange
     * and rowChange argument and checks if the square can move there
     * by checking if there is not already another square there and if
     * this position exceeds the x and y bouns of the game screen.
     */

    public boolean canMoveTo(int colChange, int rowChange) {
        boolean canMove = true;
        int col = this.getCol();
        int row = this.getRow();

        if ((col >= Constants.COLS - 1) ||
                (col <= 0)) {
            canMove = false;
        }
        if ((row >= Constants.ROWS - 1)) {
            canMove = false;
        }
        if (_gameCircleArray[row + rowChange][col + colChange] != null) {
            canMove = false;
        }
        return canMove;
    }
}