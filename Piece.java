package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    private boolean _symmetric;

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
        _symmetric = false;

        // helper methods.
        this.determinePiece(num);
        this.generatePiece(_boardPane);
//        this.addToBoard();

    }

    public Piece(Pane boardPane, GameCircle[][] board, int num, int[][] coordinates) {
        _gameCircleArray = board;
        _boardPane = boardPane;
        _piece = null;
        _colour = null;
        _symmetric = false;
        // helper methods.
        this.determinePiece(num);
        _type = coordinates;
        this.generatePiece(_boardPane);
//        this.addToBoard();

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
                _symmetric = true;
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
                _symmetric = true;
                break;
            case 5:
                _type = Constants.YELLOW_U_PIECE;
                _colour = Color.YELLOW;
                _symmetric = true;
                break;
            case 6:
                _type = Constants.GRAY_PLUS_PIECE;
                _colour = Color.GRAY;
                _symmetric = true;
                break;
            case 7:
                _type = Constants.PINK_W_PIECE;
                _colour = Color.PINK;
                _symmetric = true;
                break;
            case 8:
                _type = Constants.GREEN_SQUARE_PIECE;
                _colour = Color.GREEN;
                _symmetric = true;
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
                _symmetric = true;
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
     *
     * @param boardPane
     */

    public void generatePiece(Pane boardPane) {
        _piece = new GameCircle[_type.length];
        for (int i = 0; i < _type.length; i++) {
            int col = (_type[i][0] + Constants.BOARD_X_OFFSET) / (2 * Constants.CIRCLE_WIDTH);
            int row = (_type[i][1] + Constants.BOARD_Y_OFFSET) / (2 * Constants.CIRCLE_WIDTH);
            _piece[i] = new GameCircle(row, col, _colour, _gameCircleArray);
//            System.out.println("i: " + i + " col: " + col + " row: " + row);
//            boardPane.getChildren().addAll(_piece[i].getCirc());
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
            if (!(_piece[i].canMoveTo(colChange, rowChange))) {
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
        for (int i = 0; i < _piece.length; i++) {
            int row = _piece[i].getRow();
            int col = _piece[i].getCol();
            _gameCircleArray[row][col] = _piece[i];
            _boardPane.getChildren().addAll(_piece[i].getCirc());
        }
    }

    public void removeFromBoard() {
        for (int i = 0; i < _piece.length; i++) {
            int row = _piece[i].getRow();
            int col = _piece[i].getCol();
            _boardPane.getChildren().removeAll(_piece[i].getCirc());
            _gameCircleArray[row][col] = null;
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
        int j = (int) Math.ceil(_piece.length / 2);
        if (_piece.length % 2 == 1) {
            int yAxis = _piece[j].getCol();
            for (int i = 0; i < _piece.length; i++) {
                int diff = _piece[i].getCol() - yAxis;
                if (diff != 0) {
                    _piece[i].translateCircleLocation(-2 * diff, 0);
                }
            }
        } else {
            double yAxis = _piece[j].getCol() - 0.5;
            for (int i = 0; i < _piece.length; i++) {
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

    public int[][] dotProduct(int[][] A, int[][] B) {
        int rows_A = A.length;
        int rows_B = B.length;
        int cols_B = B[0].length;

        int[][] C = new int[rows_A][cols_B];

        for (int i = 0; i < rows_A; i++) {
            for (int j = 0; j < cols_B; j++) {
                for (int k = 0; k < rows_B; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public void printMatrix2D(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.printf("%4d", matrix[row][col]);
            }
            System.out.println();
        }
    }

    public void printMatrix1D(int[] matrix) {
        for (int col = 0; col < matrix.length; col++) {
                System.out.printf("%4d", matrix[col]);
        }
        System.out.println();
    }

    public int[][] rotateCoordinates(int[][] coordinates, double theta) {
        int[][] rotationMatrix = {{(int) Math.cos(theta), (int) -Math.sin(theta)},
                {(int) Math.sin(theta), (int) Math.cos(theta)}};
        this.printMatrix2D(rotationMatrix);
        int[][] ans = this.dotProduct(coordinates, rotationMatrix);
//        this.printMatrix(ans);
        return ans;
    }

    public int[][] flipInYCoordinates(int[][] coordinates) {
        int[][] flipCoords = new int[coordinates.length][coordinates[0].length];
        if (coordinates.length % 2 == 1) {
            int yAxis = coordinates[(int) Math.ceil(coordinates.length / 2)][0];
            for (int i = 0; i < coordinates.length; i++) {
                int diff = coordinates[i][0] - yAxis;
                flipCoords[i][0] = -diff;
                flipCoords[i][1] = coordinates[i][1];
            }
        } else {
            int y1 = (int) Math.ceil(coordinates.length / 2);
            int yAxis = coordinates[y1 + 1][0] - coordinates[y1][0];
            for (int i = 0; i < coordinates.length; i++) {
                int diff = coordinates[i][0] - yAxis;
                flipCoords[i][0] = -diff;
                flipCoords[i][1] = coordinates[i][1];
            }
//            this.setColour(Color.RED);
        }
//        this.printMatrix(flipCoords);
        return flipCoords;
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

    public int[][] switchXAndY(int[][] coordinates, boolean negate) {
        int[][] switched = new int[coordinates.length][coordinates[0].length];
        for (int i = 0; i < coordinates.length; i++) {
            if (negate) {
                switched[i][0] = -1 * coordinates[i][1];
                switched[i][1] = -1 * coordinates[i][0];
            }
            else {
                switched[i][0] = coordinates[i][1];
                switched[i][1] = coordinates[i][0];
            }
        }
        return switched;
    }

    public boolean isEqual(int[][] M1, int[][] M2) {
        if ((M1.length != M2.length) || (M1[0].length != M2[0].length)) {
            return false;
        }
        for (int i = 0; i < M1.length; i++) {
            if (!(Arrays.equals(M1[i], M2[i]))) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<int[][]> generateVariations() {
        ArrayList<int[][]> variations = new ArrayList<int[][]>();
        int[][] orig = _type;
        int[][] flip = this.flipInYCoordinates(_type);
        int[][] rot1 = this.rotateCoordinates(orig, Constants.DEGREES_90);
        int[][] rot2 = this.rotateCoordinates(orig, 2 * Constants.DEGREES_90);
        int[][] rot3 = this.rotateCoordinates(orig, 3 * Constants.DEGREES_90);
        int[][] flipRot1 = this.rotateCoordinates(flip, Constants.DEGREES_90);
        int[][] flipRot2 = this.rotateCoordinates(flip, 2 * Constants.DEGREES_90);
        int[][] flipRot3 = this.rotateCoordinates(flip, 3 * Constants.DEGREES_90);
        variations.addAll(Arrays.asList(orig, flip, rot1, rot2, rot3, flipRot1, flipRot2, flipRot3));

        ArrayList<int[][]> uniqueVars = new ArrayList<int[][]>();
//        uniqueVars.add(orig);
        for (int[][] coords : variations) {
            uniqueVars.add(coords.clone());
        }
        return uniqueVars;

//        for (int i = 0; i < uniqueVars.size(); i++) {
//            for (int j = 0; j < variations.size(); j++) {
////                if ((i != j) && ((this.isEqual(variations.get(j), uniqueVars.get(i))))) {
//////                    uniqueVars.add(variations.get(j));
//////                    variations.remove(j);
////                    variations.remove(j);
//////                    break;
////                }
//                if( i != j && _symmetric) {
//                    if ((this.isEqual(variations.get(j), this.switchXAndY(uniqueVars.get(i), false)))
//                    || (this.isEqual(variations.get(j), this.switchXAndY(uniqueVars.get(i), true)))){
//                        uniqueVars.remove(j);
//                        break;
//                    }
//                }
//            }
//        }
//        return uniqueVars;

//        for (int i = 0; i < uniqueVars.size(); i++) {
//            int freq = Collections.frequency(uniqueVars, uniqueVars.get(i));
//            while (freq > 1) {
//                uniqueVars.remove(uniqueVars.get(i));
//            }
//        }
//
//        return uniqueVars;

//        for (int i = 0; i < uniqueVars.size(); i++) {
//            for (int j = 0; j < variations.size(); j++) {
//                if (i != j && (this.isEqual(variations.get(j), uniqueVars.get(i)))) {
////                        || this.isEqual(this.switchXAndY(uniqueVars.get(i)), variations.get(j)))) {
////                    System.out.println("SOMETHING DONE");
//                    uniqueVars.remove(i);
//                    break;
//                }
//            }
//        }
//        return uniqueVars;
    }



//                System.out.println("i: " + i + " j: " + j);
//                System.out.println("MATRIX I");
//                this.printMatrix(variations.get(i));
//                System.out.println("MATRIX J");
//                this.printMatrix(variations.get(j));

//                if (i != j && (this.isEqual(variations.get(i), variations.get(j)) ||
//                                this.isEqual(this.switchXAndY(variations.get(i)),variations.get(j)))) {
//                    System.out.println("SOMETHING DONE");
//                    variations.remove(j);
//                }
//            }
//        }
//
//        return uniqueVars;
//    }
}


