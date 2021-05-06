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
                    _gameCircleArray[i][col] = new GameCircle(i, col,Constants.BOARD_COLOUR, _gameCircleArray);
                    _gameCircleArray[i][col].setLocation(i, col);
                    _boardPane.getChildren().add(
                            _gameCircleArray[i][col].getCirc());

                }
            }
            getRidOff -= 1;
        }
    }

    public void hardCodedPuzzleInitial() {
        for (int i = 0; i < _gameCircleArray.length; i++) {
            _gameCircleArray[i][10] = new GameCircle(i, 10, Constants.BOARD_COLOUR, _gameCircleArray);
            _gameCircleArray[i][10].setLocation(i, 10);
            _boardPane.getChildren().add(
                    _gameCircleArray[i][10].getCirc());
        }
        for (int col = 0; col < _gameCircleArray[0].length; col++) {
            for (int row = 0; row < 4; row++) {
                if (_gameCircleArray[row][col] == null) {
                    _gameCircleArray[row][col] = new GameCircle(row, col, Constants.BOARD_COLOUR, _gameCircleArray);
                    _gameCircleArray[row][col].setLocation(row, col);
                    _boardPane.getChildren().add(
                            _gameCircleArray[row][col].getCirc());

                }
            }
        }
        _gameCircleArray[7][9] = new GameCircle(7, 9, Constants.BOARD_COLOUR, _gameCircleArray);
        _gameCircleArray[7][9].setLocation(7, 9);
        _boardPane.getChildren().add(
                _gameCircleArray[7][9].getCirc());

    }

    public void level3_1() {
        for (int j = 0; j < _gameCircleArray.length; j++) {
            for (int i = 6; i < _gameCircleArray[0].length; i++) {

                _gameCircleArray[j][i] = new GameCircle(j, i, Constants.BOARD_COLOUR, _gameCircleArray);
                _gameCircleArray[j][i].setLocation(j, i);
                _boardPane.getChildren().add(
                        _gameCircleArray[j][i].getCirc());
            }
        }
    }



    public void level6_06() {
        for (int j = 0; j < _gameCircleArray.length; j++) {
            for (int i = 8; i < _gameCircleArray[0].length; i++) {

                _gameCircleArray[j][i] = new GameCircle(j, i, Constants.BOARD_COLOUR, _gameCircleArray);
                _gameCircleArray[j][i].setLocation(j, i);
                _boardPane.getChildren().add(
                        _gameCircleArray[j][i].getCirc());
            }
        }
        _gameCircleArray[3][7] = new GameCircle(3, 7, Constants.BOARD_COLOUR, _gameCircleArray);
        _gameCircleArray[3][7].setLocation(3, 7);
        _boardPane.getChildren().add(
                _gameCircleArray[3][7].getCirc());
    }

    public void level7_14() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < _gameCircleArray[0].length; i++) {

                _gameCircleArray[j][i] = new GameCircle(j, i, Constants.BOARD_COLOUR, _gameCircleArray);
                _gameCircleArray[j][i].setLocation(j, i);
                _boardPane.getChildren().add(
                        _gameCircleArray[j][i].getCirc());
            }
        }
        for (int j = 0; j < _gameCircleArray.length; j++) {
            for (int i = 9; i < _gameCircleArray[0].length; i++) {

                _gameCircleArray[j][i] = new GameCircle(j, i, Constants.BOARD_COLOUR, _gameCircleArray);
                _gameCircleArray[j][i].setLocation(j, i);
                _boardPane.getChildren().add(
                        _gameCircleArray[j][i].getCirc());
            }
        }
        _gameCircleArray[9][8] = new GameCircle(9, 8, Constants.BOARD_COLOUR, _gameCircleArray);
        _gameCircleArray[9][8].setLocation(9, 8);
        _boardPane.getChildren().add(
                _gameCircleArray[9][8].getCirc());

    }

    public void level7_14_v2(ArrayList<Piece> allPieces) {
        this.hardPlacePiece(allPieces,1,9,3,0);
        this.hardPlacePiece(allPieces,3,9,5,1);
        this.hardPlacePiece(allPieces,7,7,1,1);
        this.hardPlacePiece(allPieces,10,8,8,7);
        this.hardPlacePiece(allPieces,11,10,0,0);

//        allPieces.set(1, allPieces.get(1).hardPlacePiece(9,3,0));
//        allPieces.set(3, allPieces.get(3).hardPlacePiece(9,5,1));
//        allPieces.set(7, allPieces.get(7).hardPlacePiece(7,1,1));
//        allPieces.set(10, allPieces.get(10).hardPlacePiece(8,8,7));
//        allPieces.set(11, allPieces.get(11).hardPlacePiece(10,0,0));
    }

    public void level8_11_v2(ArrayList<Piece> allPieces) {
        this.hardPlacePiece(allPieces,0,9,0,0);
        this.hardPlacePiece(allPieces,1,7,2,2);
        this.hardPlacePiece(allPieces,2,9,3,3);
        this.hardPlacePiece(allPieces,3,8,7,6);

//        allPieces.set(0, allPieces.get(0).hardPlacePiece(9,0,0));
//        allPieces.set(1, allPieces.get(1).hardPlacePiece(7,2,2));
//        allPieces.set(2, allPieces.get(2).hardPlacePiece(9,3,3));
//        allPieces.set(3, allPieces.get(3).hardPlacePiece(8,7,6));
    }

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

