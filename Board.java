package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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
                    _gameCircleArray[row][col] = null;
            }
        }
        // graphical setup
        this.setUpBoardPane();
    }

    /**
     * This is the getCircles helper method. It returns
     * a 2d array of all the Circles in the board.
     */
    public GameCircle[][] getCircles() {
        return _gameCircleArray;
    }

    /**
     * This sections the main board such that the desired triangle region
     * on which the puzzle takes place is null, while the other circles on
     * the board are occupied by borderCircles.
     */

    public void cordonOffRightTriangle(int colBegin, int rowBegin, int colEnd, int rowEnd) {
        int getRidOff = rowBegin - rowEnd;
        for (int col = colBegin; col < colEnd; col++) {
            for (int i = 0; i < getRidOff; i++) {
                if (_gameCircleArray[i][col] == null) {
                    _gameCircleArray[i][col] = new GameCircle(i, col,Constants.BOARD_COLOUR, _gameCircleArray);
                    _gameCircleArray[i][col].setLocation(i, col);
                    _boardPane.getChildren().add(
                            _gameCircleArray[i][col].getCirc());

                }
            }
            getRidOff -= 1;
        }
    }

    /**
     * This sets up specificially the puzzle board for
     * level 7 puzzle 14.
     */

    public void level7_14(ArrayList<Piece> allPieces) {
        this.hardPlacePiece(allPieces,1,9,3,0);
        this.hardPlacePiece(allPieces,3,9,5,1);
        this.hardPlacePiece(allPieces,7,7,1,1);
        this.hardPlacePiece(allPieces,10,8,8,7);
        this.hardPlacePiece(allPieces,11,10,0,0);

    }

    /**
     * This sets up specificially the puzzle board for
     * level 8 puzzle 11. This is the puzzle we couldn't solve, and thus
     * the cause for this program in its entirety.
     */

    public void level8_11(ArrayList<Piece> allPieces) {
        this.hardPlacePiece(allPieces,0,9,0,0);
        this.hardPlacePiece(allPieces,1,7,2,2);
        this.hardPlacePiece(allPieces,2,9,3,3);
        this.hardPlacePiece(allPieces,3,8,7,6);
    }

    /**
     * This places a piece in a fixed position on the board. Used for
     * initial puzzle position setup. The pieces placed will not be moved
     * when solving.
     */

    public void hardPlacePiece(ArrayList<Piece> allPieces, int i, int col, int row, int variation){
        Piece piece = allPieces.get(i);
        piece.setCurrentVariation(variation);
        piece = piece.changeVariation(piece.getCurrentVariation());
        piece.translatePieceLocation(col, row);
        piece.setFixed(true);

        allPieces.set(i, piece);
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

