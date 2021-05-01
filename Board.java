package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Board {
    // declares instance variables.
    private GameCircle[][] _gameCircleArray;
    private Pane _boardPane;

    /**
     * This is the Board's Constructor. It setsup the board
     * graphically and logically. Takes in a pane parameter
     * to add board to that pane graphically.
     */
    public Board(Pane boardPane) {
        _boardPane = boardPane;
        // logical setup
        _gameCircleArray = new GameCircle[Constants.ROWS][Constants.COLS];
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLS; col++) {
//                if (!((row == 0) || (col == 0) || (row == Constants.ROWS - 1)
//                        || (col == Constants.COLS - 1))) {
////                    _gameCircleArray[row][col] = new GameCircle(row, col , Color.RED, _gameCircleArray);
//                }
//                else {
//                    _gameCircleArray[row][col] = new GameCircle(row, col , Color.RED, _gameCircleArray);
//                    _gameCircleArray[row][col].setLocation(row, col);
                    _gameCircleArray[row][col] = null;
//                }
            }

        }
        // graphical setup
        this.setUpBoardPane();
    }

    /**
     * This is the getRectangles helper method. It returns
     * a 2d array of all the squares in the board.
     */
    public GameCircle[][] getCircles() {
        return _gameCircleArray;
    }

    public void cordonOffRightTriangle(int colBegin, int rowBegin, int colEnd, int rowEnd) {
        int getRidOff = rowBegin - rowEnd;
        for (int col = colBegin; col < colEnd; col++) {
            for (int i = 0; i < getRidOff; i++) {
                if (_gameCircleArray[i][col] == null) {
                    _gameCircleArray[i][col] = new GameCircle(i, col, Color.RED, _gameCircleArray);
                    _gameCircleArray[i][col].setLocation(i, col);
                    _boardPane.getChildren().add(
                            _gameCircleArray[i][col].getCirc());

                }
            }
            getRidOff -= 1;
        }
    }


    /**
     * This is the setUpBoardPane helper method. It simply
     * graphically adds the board to the boardPane.
     */

    private void setUpBoardPane() {
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLS; col++) {
                if (_gameCircleArray[row][col] != null) {
                    _boardPane.getChildren().add(
                            _gameCircleArray[row][col].getCirc());
                }
            }
        }
    }
}

