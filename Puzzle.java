package Puzzle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class Puzzle {

    private Pane _boardPane;
    private Board _board;
    private GameCircle[][] _gameCircleArray;
    private ArrayList<ArrayList<Piece>> _allPieces;
    private Timeline _timeline;
    private Piece _piece;
    private Piece _piece2;
    private ArrayList<int[]> _possTranslations;

    public Puzzle(Pane boardPane) {
        _allPieces = new ArrayList<ArrayList<Piece>>();
        _boardPane = boardPane;
        _board = new Board(_boardPane);
        _gameCircleArray = _board.getCircles();
        _board.cordonOffRightTriangle(0, 10,11,0);
        _piece = new Piece(_boardPane, _gameCircleArray, 0);
//        _piece.addToBoard();
//        _piece.removeFromBoard();
////        this.setupTimeline();
////        _possTranslations = this.getAllPossibleTranslations(_piece);
        _piece2 = new Piece(_boardPane, _gameCircleArray, _piece.getNum(),_piece.generateVariations().get(1));
        _piece2.addToBoard();
        _possTranslations = _piece2.getAllPossibleTranslations();
        _piece2.translatePieceLocation(_possTranslations.get(0)[0], _possTranslations.get(0)[1]);
//        this.movePiece(_piece, _possTranslations);
        this.setupTimeline();
//        lis = piece.generateVariations();

//        this.generateAllPieces();
//        this.search();
//        this.test();
    }

    public void test() {

    }

    public void generateAllPieces() {
        for(int i = 0; i < 12; i++) {
            Piece piece = new Piece(_boardPane, _gameCircleArray, i);
            ArrayList<Piece> piecePossibilities = new ArrayList<Piece>();
            ArrayList<int[][]> variations = piece.generateVariations();
            for(int j = 0; j < variations.size(); j++) {
                piecePossibilities.add(new Piece(_boardPane,_gameCircleArray,i,variations.get(j)));
            }
            _allPieces.add(piecePossibilities);
        }
    }

//    public void search() {
//        int outerLength = _allPieces.size();
//        int innerLength = _allPieces.get(0).size();
//        System.out.println(outerLength + "  " + innerLength);
//
//        ArrayList<Piece> workingPieces = new ArrayList<Piece>();
//
//        for (int piece = 0; piece < outerLength; piece++) {
//            workingPieces.add(_allPieces.get(piece).get(0));
//        }
//
//        for (int i = 0; i < workingPieces.size(); i++) {
//            Piece currPiece = workingPieces.get(i);
//            currPiece.addToBoard();
//            ArrayList<int[]> possTranslations = currPiece.getPossTranslations();
//            int j = 0;
//            while (!(currPiece.isValidMove(possTranslations.get(j)[0],possTranslations.get(j)[1]))) {
//                j += 1;
//            }
//            currPiece.translatePieceLocation(possTranslations.get(j)[0],possTranslations.get(j)[1]);
//
//        }
//
//    }

//    public void movePiece(Piece piece, ArrayList<int[]> possTranslations) {
//        piece.translatePieceLocation(possTranslations.get(0)[0], possTranslations.get(0)[1]);
//        for(int i = 1; i < possTranslations.size(); i++) {
//            int newCol = possTranslations.get(i)[0];
//            int oldCol = possTranslations.get(i-1)[0];
//            int newRow = possTranslations.get(i)[1];
//            int oldRow = possTranslations.get(i-1)[1];
//
//            piece.translatePieceLocation(newCol - oldCol, newRow - oldRow);
//        }
//    }

    private void setupTimeline() {
        KeyFrame kf = new KeyFrame(
                Duration.seconds(Constants.DURATION),
                new TimePuzzleHandler());
        _timeline = new Timeline(kf);
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();
    }

    private class TimePuzzleHandler implements EventHandler<ActionEvent> {

//        public void movePieceOverBoard(Piece piece) {
//            for (int row = 0; row < _gameCircleArray.length - 1; row ++) {
//                int col = 0;
//                while ((col < _gameCircleArray[row].length - 1)
//                        && (piece.isValidMove(0,0))) {
//                    piece.translatePieceLocation(1,0);
////                    piece.addToBoard();
//                    col += 1;
//                    System.out.println("Col: " + col + " Row: " + row);
////                    if ((col == _gameCircleArray[row].length - 1) &&
////                            !(piece.isValidMove(0,0))) {
////                        break;
////                    }
//                }
//            }
//        }

        /**
         * This is the handle method it runs each frame of the timeline.
         */

        private int _i = 1;
//        private int _j = 0;
//        private boolean _create_new_piece = true;
//        private Piece _piece;
//        private ArrayList<int[]> _possTranslations;

        public void handle(ActionEvent event) {
//            if (_create_new_piece) {
//
//                _piece = new Piece(_boardPane, _gameCircleArray, _j);
//                _possTranslations = _piece.getAllPossibleTranslations();
//                _piece.translatePieceLocation(_possTranslations.get(0)[0], _possTranslations.get(0)[1]);
//                _piece.addToBoard();
//                _create_new_piece = false;
//            }

            if (_i < _possTranslations.size() ) {
                int newCol = _possTranslations.get(_i)[0];
                int oldCol = _possTranslations.get(_i-1)[0];
                int newRow = _possTranslations.get(_i)[1];
                int oldRow = _possTranslations.get(_i-1)[1];

                _piece2.translatePieceLocation(newCol - oldCol, newRow - oldRow);
                _i += 1;
            }
//            if (_i >= _possTranslations.size()) {
//                _piece.removeFromBoard();
//                _piece = null;
//                _j += 1;
//                _i = 1;
//                _create_new_piece = true;
//            }
//


        }
    }
}
