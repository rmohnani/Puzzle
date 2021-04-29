package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This is the Piece Class. It is used to setup the
 * Different Tetromino pieces, and contains all necessary
 * accessor and mutator methods, as well as methods to
 * translate the piece, rotate, and check if valid move.
 */

public class Piece {
    // declares instance variables.
    private GameCircle[] _piece;
    private Color _colour;
    private int[][] _type;
    private Pane _boardPane;
    private GameCircle[][] _gameCircleArray;

    /**
     * This is the Piece constructor class. It takes in a pane
     * to add the piece graphically to. It uses helper methods to determine
     * which of the 12 types of pieces it is, and to add the piece graphically.
     */

    public Piece(Pane boardPane, GameCircle[][] board, int num) {
        _gameCircleArray = board;
        _boardPane = boardPane;
        _piece = null;
        _colour = null;
        _type = null;

        // helper methods.
        this.determinePiece(num);
        this.generatePiece(_boardPane);
        this.addToBoard();

    }

    /**
     * This is the determinePiece helper method. it determines
     * randomly which of the 7 pieces it'll be and its colour.
     */
    public void determinePiece(int num) {
        switch (num) {

            case 0:
                _type = Constants.WHITE_RIGHT_TRIANGLE;
                _colour = Color.WHITE;
                break;
            case 1:
                _type = Constants.DGREEN_Z_PIECE;
                _colour = Color.DARKGREEN;
                break;
            case 2:
                _type = Constants.BLUE_L_PIECE;
                _colour = Color.BLUE;
                break;
            case 3:
                _type = Constants.ORANGE_L_PIECE;
                _colour = Color.ORANGE;
                break;
            case 4:
                _type = Constants.SKYBLUE_L_PIECE;
                _colour = Color.SKYBLUE;
                break;
            case 5:
                _type = Constants.YELLOW_U_PIECE;
                _colour = Color.YELLOW;
                break;
            case 6:
                _type = Constants.GRAY_PLUS_PIECE;
                _colour = Color.GRAY;
                break;
            case 7:
                _type = Constants.PINK_W_PIECE;
                _colour = Color.PINK;
                break;
            case 8:
                _type = Constants.GREEN_SQUARE_PIECE;
                _colour = Color.GREEN;
                break;
            case 9:
                _type = Constants.CREAM_LINEISH_PIECE;
                _colour = Color.BISQUE;
                break;
            case 10:
                _type = Constants.RED_SQUAREISH_PIECE;
                _colour = Color.CYAN;
                break;
            case 11:
                _type = Constants.PURPLE_LINE_PIECE;
                _colour = Color.PURPLE;
                break;
        }
    }

    /**
     * This is the changePane helper method. It simply moves
     * the piece from one pane to another, which is passed in
     * as an argument.
     */

    public void changePane(Pane newPane) {
        // uses generatePiece method as same process.
        this.generatePiece(newPane);
    }

    /**
     * This is the generatePiece method. It adds the piece
     * graphically to the pane.
     * @param boardPane
     */

    public void generatePiece(Pane boardPane) {
        _piece = new GameCircle[_type.length];
        for (int i = 0; i < _type.length; i++) {
            int col = (_type[i][0] + Constants.X_OFFSET) / (2 * Constants.CIRCLE_WIDTH);
            int row = (_type[i][1] +  Constants.Y_OFFSET) / (2 * Constants.CIRCLE_WIDTH);
            _piece[i] = new GameCircle(row, col, _colour, _gameCircleArray);
            System.out.println("i: " + i + " col: " + col + " row: " + row);
            boardPane.getChildren().addAll(_piece[i].getCirc());
        }
    }

    /**
     * This is the getLength accessor method, it returns
     * the length of the piece.
     */

    public int getLength() {
        return _piece.length;
    }

    /**
     * This is the getSquare accessor method, it returns
     * the ith square of the piece.
     */

    public GameCircle getGameCircle(int i) {
        return _piece[i];
    }

    /**
     * This is the translatePieceLocation method, it moves
     * the piece by the colChange and rowChange passed in as
     * arguments.
     */
//
    public void translatePieceLocation(int colChange, int rowChange) {
        for (int i = 0; i < _piece.length; i++) {
            _piece[i].translateCircleLocation(colChange, rowChange);
        }
    }

    /**
     * This is the isValidMove helper method. It checks if a colChange
     * and rowChange passed in, is a valid move for the piece and returns
     * a boolean depending. For each square in the piece, it checks if the
     * square can move there to determine if the piece overall can move.
     */

    public boolean isValidMove(int colChange, int rowChange) {
        boolean canMove = true;
        for (int i = 0; i < _piece.length; i++) {
            if(!(_piece[i].canMoveTo(colChange, rowChange))) {
                canMove = false;
                break;
            }
        }
        return canMove;
    }

    /**
     * This is the addToBoard helper method. It simply
     * adds the piece logically to the 2d array (board
     * data structure).
     */

    public void addToBoard() {
        for (int i= 0; i < _piece.length; i++) {
            int row = _piece[i].getRow();
            int col = _piece[i].getCol();
            _gameCircleArray[row][col] = _piece[i];
        }
    }

    /**
     * This is the rotate method. it returns a 2d int array
     * of the col and row positions of the squares in the piece
     * after a rotation. Complicated because written in terms of row
     * and col.
     */

    public int[][] rotPos() {
        int[][] newPos = new int[_piece.length][2];
        int centerOfRotationX = _piece[0].getCol() * 2 * Constants.CIRCLE_WIDTH;
        int centerOfRotationY = _piece[0].getRow() * 2 * Constants.CIRCLE_WIDTH;
        for (int i = 0; i < _piece.length; i++) {
            int oldXLocation = _piece[i].getCol() * 2 * Constants.CIRCLE_WIDTH;
            int oldYLocation = _piece[i].getRow() * 2 * Constants.CIRCLE_WIDTH;

            int oldRow = oldYLocation / (2 * Constants.CIRCLE_WIDTH);
            int oldCol = oldXLocation / (2 * Constants.CIRCLE_WIDTH);
            int newCol = (centerOfRotationX - centerOfRotationY + oldYLocation) / (2 * Constants.CIRCLE_WIDTH);
            int newRow = (centerOfRotationY + centerOfRotationX - oldXLocation) / (2 * Constants.CIRCLE_WIDTH);

            newPos[i][0] = newCol - oldCol;
            newPos[i][1] = newRow - oldRow;
        }
        return newPos;
    }

    public void flipPosY() {
        int[][] newPos = new int[_piece.length][2];
        int j = (int) Math.ceil(_piece.length/2);
        if (_piece.length % 2 == 1) {
            int yAxis = _piece[j].getCol();
            for(int i = 0; i < _piece.length; i++) {
                int diff = _piece[i].getCol() - yAxis;
                if (diff != 0) {
                    _piece[i].translateCircleLocation(-2 * diff, 0);
                }
            }
        }
        else {
            double yAxis = _piece[j].getCol() - 0.5;
            for(int i = 0; i < _piece.length; i++) {
                double diff = _piece[i].getCol() - yAxis;
                if (diff != 0) {
//                    System.out.println("BEFORE  i: " + i + " col: " + _piece[i].getCol() + " row: " + _piece[i].getRow());
                    _piece[i].translateCircleLocation((int) (-2 * diff), 0);
//                    System.out.println("AFTER  i: " + i + " col: " + _piece[i].getCol() + " row: " + _piece[i].getRow());
                }
            }
//            this.setColour(Color.RED);

        }
    }

    public void setColour(Color colour) {
        for (int i = 0; i < _piece.length; i++) {
            _piece[i].setColour(colour);
        }
    }

    public void rotate() {
        int[][] rot = this.rotPos();
        if (this.rotationValidityCheck(rot)) {
            for (int i = 0; i < _piece.length; i++) {
                _piece[i].translateCircleLocation(rot[i][0], rot[i][1]);
            }
        }
    }

    private boolean rotationValidityCheck(int[][] rotPos) {
        boolean rotValid = true;
        for (int i = 1; i < _piece.length; i++) {
            if (!(_piece[i].canMoveTo(rotPos[i][0], rotPos[i][1]))) {
                rotValid = false;
                break;
            }
        }
        return rotValid;
    }

    /**
     * This is the getType accessor method. it returns a 2d int array of
     * the type of the piece, which gives the coordinates of the piece.
     */

    public int[][] getType() {
        return _type;
    }

//    public int[][][] generateVariations() {
//
//    }

}
