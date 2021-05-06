package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the Piece Class. It is used to setup the
 * Different Tetromino pieces, and contains all necessary
 * accessor and mutator methods, as well as methods to
 * translate the piece, rotate, and check if valid move.
 */

public class Piece{
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
    private boolean _changeToNextPossiblePosition;
    private ArrayList<int[][]> _variations;
    public int _currentVariation;
    private int _pieceCol;
    private int _pieceRow;
    private ArrayList<HashSet<int[]>> _listOfSets;

    /**
     * This is the Piece constructor class. It takes in a pane
     * to add the piece graphically to. It uses helper methods to determine
     * which of the 12 types of pieces it is, and to add the piece graphically.
     */

    public Piece(Pane boardPane, GameCircle[][] board, int num, int var) {
        _gameCircleArray = board;
        _boardPane = boardPane;
        _piece = null;
        _colour = null;
        _num = num;
        _symmetric = false;
        _currentTranslation = 0;
        _changeToNextVariation = false;
        _changeToNextPossiblePosition = true;
        _currentVariation = var;
        _pieceRow = 0;
        _pieceCol = 0;
        this.determinePiece();
        _variations = this.generateVariations();
//        this.getUniqueVariations();
        _type = _variations.get(_currentVariation);
        _listOfSets = new ArrayList<HashSet<int[]>>();


        this.generatePiece(_boardPane);
        _possTranslations = this.getAllPossibleTranslations();

        // helper methods.
//        this.addToBoard();

    }

    public void setPieceCol(int col) {
        _pieceCol = col;
    }

    public void setPieceRow(int row) {
        _pieceRow = row;
    }

    public int getPieceCol() {
        return _pieceCol;
    }

    public int getPieceRow() {
        return _pieceRow;
    }

    public Piece changeVariation(int i) {
        this.removeFromBoard();
        _currentVariation = i;
        return new Piece(_boardPane, _gameCircleArray, _num, _currentVariation);
    }

    public void setCurrentVariation(int i) {
        _currentVariation = i;
    }

    public int getCurrentVariation() {
        return _currentVariation;
    }

    public ArrayList<int[][]> getVariations() {
        return _variations;
    }

    public boolean getChangeTranslationBool() {
        return _changeToNextPossiblePosition;
    }

    public void setChangeTranslationBool(boolean toChange) {
        _changeToNextPossiblePosition = toChange;
    }

    public boolean getChangeVariationBool() {
        return _changeToNextVariation;
    }

    public void setChangeVariationBool(boolean toChange) {
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
            if (!(_boardPane.getChildren().contains(_piece[i].getCirc()))) {
                _boardPane.getChildren().addAll(_piece[i].getCirc());
            }
        }
    }

    public void removeFromBoard() {
        for (int i = 0; i < _piece.length; i++) {
            int row = _piece[i].getRow();
            int col = _piece[i].getCol();
            _boardPane.getChildren().removeAll(_piece[i].getCirc());
            if (row + col <= 9) {
                _gameCircleArray[row][col] = new GameCircle(row, col, Color.RED, _gameCircleArray);
            }
            else {
                _gameCircleArray[row][col] = null;
            }
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
//        this.printMatrix2D(rotationMatrix);
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

    public int getVariationsLength() {
        return _variations.size();
    }

    public ArrayList<int[][]> getVariation() {
        return _variations;
    }

    /**
     * This for the current piece returns an Arraylist of 1D matrices of
     * length 2 containing the col, row change for the piece from its
     * initial position of 0,0 that yields a valid position of the piece.
     * It returns a list identifying all possible valid positions the piece
     * can take on the board.
     */

    public ArrayList<int[]> getAllPossibleTranslations() {
        this.removeFromBoard();
        ArrayList<int[]> possTranslations = new ArrayList<int[]>();
        for (int row = - _pieceRow; row < _gameCircleArray.length - 1 - _pieceRow; row++) {
            for (int col = - _pieceCol; col < _gameCircleArray[0].length - 1 - _pieceCol; col++) {
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

    public GameCircle[] getPiece() {
        return _piece;
    }

    public ArrayList<int[]> to1DList(int[][] coordinate) {
        ArrayList<int[]> coordinateList = new ArrayList<int[]>();
        for (int j = 0; j < coordinate.length; j++) {
            coordinateList.add(coordinate[j]);
        }
//        for (int[] row: coordinateSet) {
//            this.printMatrix1D(row);
//        }
        return coordinateList;
    }

//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return id.equals(((Item)obj).id);
//    }
//
//    public static boolean equals(HashSet<int[]> set1, HashSet<int[]> set2) {
//        for (int i = 0; i < set1.size(); i++) {
//            if (!set2.contains(set1.get(i))) {
//            }
//        }
//    }
//    public boolean isEqual(ArrayList<int[]> coordinate1,ArrayList<int[]> coordinate2) {
////        system.out.Println("array 1:");
////        for (int i = 0; i < coordinate1.length)
//        HashMap coordinates = new HashMap();
////        Map <int[], String> coordinates = new HashMap<int[], String>();
//        System.out.println("Coordinate1: ");
//        for (int l = 0; l < coordinate1.size(); l++) {
//            this.printMatrix1D(coordinate1.get(l));
//        }
//        System.out.println("Coordinat2: ");
//        for (int a = 0; a < coordinate1.size(); a++) {
//            this.printMatrix1D(coordinate2.get(a));
//        }
//        System.out.println("___________");
//        for (int i = 0; i < coordinate1.size(); i++) {
//            ArrayList<int[]> myList = new ArrayList<int[]>(Arrays.asList(coordinate1.get(i)));
//            coordinates.put(myList, i);
//        }
//
//        for (int j = 0; j < coordinate2.size(); j++) {
//            this.printMatrix1D(coordinate2.get(j));
//            ArrayList<int[]> myList = new ArrayList<int[]>(Arrays.asList(coordinate1.get(j)));
////            System.out.println(myList);
//            System.out.println("Boolean");
//            System.out.println((coordinates.containsKey(myList)));
//            if (!(coordinates.containsKey(myList))) {
//                System.out.println("its false!!!!!!!!!!!");
//                return false;
//            }
//        }
//        System.out.println("it's true!!!!!!!");
//        return true;
//    }

    public boolean compareCoordinates(ArrayList<int[]> coordinate1,ArrayList<int[]> coordinate2) {
        boolean areEquivalent = true;
        for (int i = 0; i < coordinate1.size(); i++) {
            boolean isSame = false;
            for (int j = 0; j < coordinate2.size(); j++) {
                if (Arrays.equals(coordinate1.get(i),coordinate2.get(j))) {
                    isSame = true;
                    break;
                }
            }
            areEquivalent = areEquivalent && isSame;
            if (!(areEquivalent)) {
                return false;
            }
        }
        return areEquivalent;
    }

    public void getUniqueVariations3() {
        for (int i = 0; i < _variations.size(); i++) {
            for (int j = 0; j < _variations.size(); j++) {
                if (i != j) {
                    ArrayList<int[]> listI = this.to1DList(_variations.get(i));
                    ArrayList<int[]> listJ = this.to1DList(_variations.get(j));
                    if (this.compareCoordinates(listI, listJ)) {
                        _variations.remove(j);
                        j -= 1;
                    }

                }
            }
        }
//        System.out.println(_variations.size());
    }


//    public void getUniqueVariations2() {
//        for (int i = 0; i < _variations.size(); i++) {
//            for (int j = 0; j < _variations.size(); j++) {
//                if (i != j) {
//                    ArrayList<int[]> listI = this.to1DList(_variations.get(i));
//                    ArrayList<int[]> listJ = this.to1DList(_variations.get(j));
//                    if (this.isEqual(listI, listJ)) {
//                        _variations.remove(j);
//                        j -= 1;
//                    }
//
//                }
//            }
//        }
//        System.out.println(_variations.size());
//    }

//    public boolean completelyContains(Piece otherPiece) {
//        boolean doesContain = true;
//        if (this.getType().length != otherPiece.getType().length) {
//            return false;
//        }
//        else {
//            GameCircle[] other = otherPiece.getPiece();
//            for (int i = 0; i < this.getType().length; i++) {
//                boolean isCircleInside = false;
//                for (int j = 0; j < this.getType().length; j++) {
//                    if (_piece[i].contains(other[j])) {
//                        isCircleInside = true;
//                        break;
//                    }
//                }
//                doesContain = doesContain && isCircleInside;
//                if (!(doesContain)) {
//                    return false;
//                }
//
//            }
//        }
//        return doesContain;
//    }

//    public void getUniqueVariations() {
//        ArrayList<Piece> allPieces = new ArrayList<Piece>();
//        for (int i = 0; i < _variations.size(); i++) {
////            System.out.println("I: " + i);
//            allPieces.add(new Piece (_boardPane, _gameCircleArray,_num, i));
////            this.printMatrix2D(_variations.get(i));
//        }
//        for (int j = 0; j < allPieces.size(); j++) {
//            for (int k = 0; k < allPieces.size(); k++) {
//                if (j != k) {
//                    if ((allPieces.get(j)).completelyContains((allPieces.get(k)))) {
//                        allPieces.remove(k);
//                        _variations.remove(k);
//                        k-= 1;
//                    }
//                }
//            }
//        }
//        System.out.println(allPieces.size());
//        System.out.println(_variations.size());
//        for (int l = 0; l < _variations.size(); l++) {
//            this.printMatrix2D(_variations.get(l));
//
//        }
//    }

}
