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
    private ArrayList<int[]> _possTranslations;
    private int _num;
    private int _currentTranslation;
    private boolean _changeToNextVariation;

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
        _num = num;
        _symmetric = false;
        _currentTranslation = 0;
        _changeToNextVariation = false;

        // helper methods.
        this.determinePiece();
        this.generatePiece(_boardPane);
        _possTranslations = this.getAllPossibleTranslations();
//        this.addToBoard();

    }

    public Piece(Pane boardPane, GameCircle[][] board, int num, int[][] coordinates) {
        _gameCircleArray = board;
        _boardPane = boardPane;
        _piece = null;
        _colour = null;
        _symmetric = false;
        _num = num;
        _currentTranslation = 0;
        _changeToNextVariation = false;

        // helper methods.
        this.determinePiece();
        _type = coordinates;
        this.generatePiece(_boardPane);
        _possTranslations = this.getAllPossibleTranslations();
//        this.addToBoard();

    }

    public boolean getChange() {
        return _changeToNextVariation;
    }

    public void setChange(boolean toChange) {
        _changeToNextVariation = toChange;
    }

    public void setCurrentTranslation(int j) {
        _currentTranslation = j;
    }

    public int getCurrentTranslation() {
        return _currentTranslation;
    }

    public int getNum() {
        return _num;
    }

    public ArrayList<int[]> getPossTranslations() {
        return _possTranslations;
    }

    public void setPossTranslations(ArrayList<int[]> possTranslations) {
        _possTranslations = possTranslations;
    }

    /**
     * This is the determinePiece helper method. it determines
     * randomly which of the 7 pieces it'll be and its colour.
     */
    public void determinePiece() {
        switch (_num) {

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
     * This is the generatePiece method. It adds the piece
     * graphically to the pane.
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
     * This is the getSquare accessor method, it returns
     * the ith square of the piece.
     */

//    public GameCircle getGameCircle(int i) {
//        return _piece[i];
//    }

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
     * Dot product code used as helper method for the rotation of coordinates.
     */

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

    /**
     * Some debugging code to print 2d or 1d matrices to compare against
     * predicted values.
     */

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

    /**
     * Currently used implementations of rotation and flipping of pieces.
     */

    public int[][] rotateCoordinates(int[][] coordinates, double theta) {
        int[][] rotationMatrix = {{(int) Math.cos(theta), (int) -Math.sin(theta)},
                {(int) Math.sin(theta), (int) Math.cos(theta)}};
        this.printMatrix2D(rotationMatrix);
        int[][] ans = this.dotProduct(coordinates, rotationMatrix);
//        this.printMatrix2D(ans);
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
//        this.printMatrix2D(flipCoords);
        return flipCoords;
    }

    /**
     * Other debugging code to change piece colour to visually show something has worked
     * or hasnt worked.
     *
     * @param colour
     */

    public void setColour(Color colour) {
        for (int i = 0; i < _piece.length; i++) {
            _piece[i].setColour(colour);
        }
    }

    /**
     * This is the getType accessor method. it returns a 2d int array of
     * the type of the piece, which gives the coordinates of the piece.
     */

    public int[][] getType() {
        return _type;
    }

    /**
     * This for the current piece returns an Arraylist of 1D matrices of
     * length 2 containing the col, row change for the piece from its
     * initial position of 0,0 that yields a valid position of the piece.
     * It returns a list identifying all possible valid positions the piece
     * can take on the board.
     */

    public ArrayList<int[]> getAllPossibleTranslations() {
        ArrayList<int[]> possTranslations = new ArrayList<int[]>();
        for (int row = 0; row < _gameCircleArray.length - 1; row++) {
            for (int col = 0; col < _gameCircleArray[row].length - 1; col++) {
                if (this.isValidMove(col, row)) {
                    int[] valid = {col, row};
                    possTranslations.add(valid);
                }
            }
        }
        return possTranslations;
    }

    /**
     * This generates all the different variations (rotations and
     * orientations of the piece that we shall consider distinct).
     * This is a list of 8 pieces coming from an original, a flipped
     * in the y-axis and 3 rotations for each. There are some duplicates
     * for the symmetric pieces, which may be dealt with at a later time
     * to reduce the computation as an optimization. But not concerned with
     * this problem as of yet.
     */

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

        // make all coordinates positive
        for (int[][] coords : variations) {
            for (int i = 0; i < coords.length; i++) {
                while (coords[i][0] < 0) {
                    for (int j = 0; j < coords.length; j++) {
                        coords[j][0] += 2 * Constants.CIRCLE_WIDTH;
                    }
                }
                while (coords[i][1] < 0) {
                    for (int k = 0; k < coords.length; k++) {
                        coords[k][1] += 2 * Constants.CIRCLE_WIDTH;
                    }
                }
            }
        }
        return variations;
    }
}

    /**
     * Code for switching x and y coordiantes of piece and negating or not.
     * Was used in the unique piece generation, but doesnt work generally so abandoned all
     * together along with the other unique piece code.
     */

//    public int[][] switchXAndY(int[][] coordinates, boolean negate) {
//        int[][] switched = new int[coordinates.length][coordinates[0].length];
//        for (int i = 0; i < coordinates.length; i++) {
//            if (negate) {
//                switched[i][0] = -1 * coordinates[i][1];
//                switched[i][1] = -1 * coordinates[i][0];
//            }
//            else {
//                switched[i][0] = coordinates[i][1];
//                switched[i][1] = coordinates[i][0];
//            }
//        }
//        return switched;
//    }
//
//    public boolean isEqual(int[][] M1, int[][] M2) {
//        if ((M1.length != M2.length) || (M1[0].length != M2[0].length)) {
//            return false;
//        }
//        for (int i = 0; i < M1.length; i++) {
//            if (!(Arrays.equals(M1[i], M2[i]))) {
//                return false;
//            }
//        }
//        return true;
//    }

    //        ArrayList<int[][]> uniqueVars = new ArrayList<int[][]>();
//        uniqueVars.add(orig);
//        for (int[][] coords : variations) {
//            uniqueVars.add(coords.clone());
//        }
//        return uniqueVars;

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


    /**
     * This section of commented code kind of useless, since I redid the implementation
     * to not rotate or flip an instance of a piece but just the coordiantes of the piece
     * so that it is more abstract and more usable than having to manage an instance of the
     * piece class.
     */

//        public int[][] rotPos() {
//            int[][] newPos = new int[_piece.length][2];
//            int centerOfRotationX = _piece[0].getCol() * 2 * Constants.CIRCLE_WIDTH;
//            int centerOfRotationY = _piece[0].getRow() * 2 * Constants.CIRCLE_WIDTH;
//            for (int i = 0; i < _piece.length; i++) {
//                int oldXLocation = _piece[i].getCol() * 2 * Constants.CIRCLE_WIDTH;
//                int oldYLocation = _piece[i].getRow() * 2 * Constants.CIRCLE_WIDTH;
//
//                int oldRow = oldYLocation / (2 * Constants.CIRCLE_WIDTH);
//                int oldCol = oldXLocation / (2 * Constants.CIRCLE_WIDTH);
//                int newCol = (centerOfRotationX - centerOfRotationY + oldYLocation) / (2 * Constants.CIRCLE_WIDTH);
//                int newRow = (centerOfRotationY + centerOfRotationX - oldXLocation) / (2 * Constants.CIRCLE_WIDTH);
//
//                newPos[i][0] = newCol - oldCol;
//                newPos[i][1] = newRow - oldRow;
//            }
//            return newPos;
//        }
//
//        public void flipPosY() {
//            int[][] newPos = new int[_piece.length][2];
//            int j = (int) Math.ceil(_piece.length / 2);
//            if (_piece.length % 2 == 1) {
//                int yAxis = _piece[j].getCol();
//                for (int i = 0; i < _piece.length; i++) {
//                    int diff = _piece[i].getCol() - yAxis;
//                    if (diff != 0) {
//                        _piece[i].translateCircleLocation(-2 * diff, 0);
//                    }
//                }
//            } else {
//                double yAxis = _piece[j].getCol() - 0.5;
//                for (int i = 0; i < _piece.length; i++) {
//                    double diff = _piece[i].getCol() - yAxis;
//                    if (diff != 0) {
//    //                    System.out.println("BEFORE  i: " + i + " col: " + _piece[i].getCol() + " row: " + _piece[i].getRow());
//                        _piece[i].translateCircleLocation((int) (-2 * diff), 0);
//    //                    System.out.println("AFTER  i: " + i + " col: " + _piece[i].getCol() + " row: " + _piece[i].getRow());
//                    }
//                }
//    //            this.setColour(Color.RED);
//            }
//        }
//
//        public void rotate() {
//            int[][] rot = this.rotPos();
//            if (this.rotationValidityCheck(rot)) {
//                for (int i = 0; i < _piece.length; i++) {
//                    _piece[i].translateCircleLocation(rot[i][0], rot[i][1]);
//                }
//            }
//        }
//
//        private boolean rotationValidityCheck(int[][] rotPos) {
//            boolean rotValid = true;
//            for (int i = 1; i < _piece.length; i++) {
//                if (!(_piece[i].canMoveTo(rotPos[i][0], rotPos[i][1]))) {
//                    rotValid = false;
//                    break;
//                }
//            }
//            return rotValid;
//        }



