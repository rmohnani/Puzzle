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
    private ArrayList<Piece> _allPieces2;
    private Timeline _timeline;
    private Piece _piece;
    private Piece _piece2;
    private ArrayList<int[]> _possTranslations;

    public Puzzle(Pane boardPane) {
//        _allPieces = new ArrayList<ArrayList<Piece>>();
        _allPieces2 = new ArrayList<Piece>();
        _boardPane = boardPane;
        _board = new Board(_boardPane);
        _gameCircleArray = _board.getCircles();
        _board.cordonOffRightTriangle(0, 10,11,0);
//        _piece = new Piece(_boardPane, _gameCircleArray, 7,1);
//        _piece.addToBoard();
//        ArrayList<int[]> trans = _piece.getAllPossibleTranslations();
//        _piece.translatePieceLocation(trans.get(0)[0],trans.get(0)[1]);
//        _piece.setPieceCol(trans.get(0)[0]);
//        _piece.setPieceRow(trans.get(0)[1]);
//        _piece.setPossTranslations(_piece.getAllPossibleTranslations());
//        _piece = _piece.changeVariation(1);
//        _piece.addToBoard();
//        _piece.removeFromBoard();
////        this.setupTimeline();
////        _possTranslations = this.getAllPossibleTranslations(_piece);
//        _piece2 = new Piece(_boardPane, _gameCircleArray, _piece.getNum(),_piece.generateVariations().get(1));
//        _piece2.addToBoard();
//        _possTranslations = _piece2.getAllPossibleTranslations();
//        _piece2.translatePieceLocation(_possTranslations.get(0)[0], _possTranslations.get(0)[1]);
//        this.movePiece(_piece, _possTranslations);
//        lis = piece.generateVariations();

//        this.generateAllPieces();
        this.generateAllPieces2();
//        this.setupTimeline();
        this.search();
//        this.test();
    }

    public void generateAllPieces() {
        for(int i = 0; i < 12; i++) {
            Piece piece = new Piece(_boardPane, _gameCircleArray, i, 0);
            ArrayList<Piece> piecePossibilities = new ArrayList<Piece>();
            ArrayList<int[][]> variations = piece.generateVariations();
            for(int j = 0; j < variations.size(); j++) {
                piecePossibilities.add(piece.changeVariation(j));
            }
            _allPieces.add(piecePossibilities);
        }
    }

    public void generateAllPieces2() {
        for(int i = 0; i < 12; i++) {
            Piece piece = new Piece(_boardPane, _gameCircleArray, i, 0);
            _allPieces2.add(piece);
        }
    }

    public void search() {

        int i = 0;
        while (i < _allPieces2.size() - 4) {
            System.out.println("Current i: " + i);
            Piece currPiece = _allPieces2.get(i);
//            currPiece.addToBoard();
            if (currPiece.getChangeVariationBool()) {
                int curVar = currPiece.getCurrentVariation();
                int varLength = currPiece.getVariations().size();

                if (curVar < varLength - 1) {
                    curVar += 1;
                    currPiece.setCurrentVariation(curVar);
                    currPiece = currPiece.changeVariation(currPiece.getCurrentVariation());
                    while (currPiece.getAllPossibleTranslations().size() == 0 &&
                            currPiece.getCurrentVariation() < varLength - 1) {
                        curVar += 1;
                        currPiece.setCurrentVariation(curVar);
                        currPiece = currPiece.changeVariation(currPiece.getCurrentVariation());
                    }
                    _allPieces2.set(i, currPiece);
                    if (currPiece.getAllPossibleTranslations().size() > 0) {
                        currPiece.setPossTranslations(currPiece.getAllPossibleTranslations());
                        currPiece.setChangeVariationBool(false);
                    }
//                    if (currPiece.getAllPossibleTranslations().size() == 1 &&
//                            (currPiece.getAllPossibleTranslations().get(0)[0] == 0 &&
//                                    currPiece.getAllPossibleTranslations().get(0)[1] == 0)) {
//                        currPiece.removeFromBoard();
//                        currPiece.setCurrentVariation(0);
//                        currPiece = currPiece.changeVariation(currPiece.getCurrentVariation());
//                        currPiece.setChangeVariationBool(false);
//
//
//                        i -= 1;
//                        currPiece = _allPieces2.get(i);
//                        if (currPiece.getCurrentTranslation() < currPiece.getPossTranslations().size()) {
//                            currPiece.setChangeTranslationBool(true);
//                        } else {
//                            currPiece.setChangeVariationBool(true);
//                        }
//
//                    }
                    else {

                        /**
                         * If need to change piece variation but no other variation exists
                         * then piece has no valid placement on current board, so need to go
                         * back to piece before (i - 1) and change its variation to next one.
                         */
                        currPiece.removeFromBoard();
                        currPiece.setCurrentVariation(0);
                        currPiece = currPiece.changeVariation(currPiece.getCurrentVariation());
                        currPiece.setChangeVariationBool(false);


                        i -= 1;
                        currPiece = _allPieces2.get(i);
                        if (currPiece.getCurrentTranslation() < currPiece.getPossTranslations().size()) {
                            currPiece.setChangeTranslationBool(true);
                        } else {
                            currPiece.setChangeVariationBool(true);
                        }
                    }

                }

            }


            if (currPiece.getChangeTranslationBool()) {
//                currPiece.translatePieceLocation(- currPiece.getPieceCol(),- currPiece.getPieceRow());
//                currPiece.setPieceCol(currPiece.getPieceCol());
//                currPiece.setPieceRow(currPiece.getPieceRow());

                currPiece.setPossTranslations(currPiece.getAllPossibleTranslations());
                ArrayList<int[]> possTranslations = currPiece.getPossTranslations();
                if (possTranslations.size() == 0) {
                    currPiece.setChangeVariationBool(true);
                }
                else {
                    if ((possTranslations.get(currPiece.getCurrentTranslation())[0] == 0) &&
                            (possTranslations.get(currPiece.getCurrentTranslation())[1] == 0)) {
                        currPiece.setCurrentTranslation(currPiece.getCurrentTranslation() + 1);
                    }
                    while ((!(currPiece.isValidMove(possTranslations.get(currPiece.getCurrentTranslation())[0],
                            possTranslations.get(currPiece.getCurrentTranslation())[1])))
                            && (currPiece.getCurrentTranslation() < possTranslations.size() - 1) ) {

                        currPiece.setCurrentTranslation(currPiece.getCurrentTranslation() + 1);

                    }
                    currPiece.setPieceCol(currPiece.getPieceCol() +
                            possTranslations.get(currPiece.getCurrentTranslation())[0]);
                    currPiece.setPieceRow(currPiece.getPieceRow() +
                            possTranslations.get(currPiece.getCurrentTranslation())[1]);

//                    currPiece.setPossTranslations(currPiece.getAllPossibleTranslations());
                    if (currPiece.getCurrentTranslation() > possTranslations.size() - 1) {
                        currPiece.setChangeVariationBool(true);
//                        break;
                    }
                    else {
                        int j = currPiece.getCurrentTranslation();
                        currPiece.translatePieceLocation(possTranslations.get(j)[0], possTranslations.get(j)[1]);
                        currPiece.addToBoard();
                        currPiece.setChangeTranslationBool(false);
                        i += 1;
                    }
                }
//                }
            }
        }
    }


//    public Piece checkPieceVariations(Piece currPiece, ArrayList<Piece> workingPieces, int i) {
//        int j = 0;
//        currPiece.setPossTranslations(currPiece.getAllPossibleTranslations());
//        ArrayList<int[]> possTranslations = currPiece.getPossTranslations();
//
//        if (possTranslations.size() == 0) {
//            int index = 0;
//            while (currPiece != _allPieces.get(i).get(index)) {
//                index += 1;
//                if (index >= _allPieces.get(i).size() - 1) {
//                    System.out.println("PIECE DOES NOT FIT: " + i);
//                    return null;
//                }
//            }
//            currPiece = _allPieces.get(i).get(index + 1);
//            workingPieces.set(i, currPiece);
//            j = 0;
//            checkPieceVariations(currPiece, workingPieces, i);
//        }
//        else {
//            while (!(currPiece.isValidMove(possTranslations.get(j)[0],possTranslations.get(j)[1]))) {
//                j += 1;
//                if (j >= possTranslations.size()) {
//                    int index = 0;
//                    while (currPiece != _allPieces.get(i).get(index)) {
//                        index += 1;
//                        if (index >= _allPieces.get(i).size() - 1) {
//                            System.out.println("PIECE DOES NOT FIT: " + i);
//                            return null;
//                        }
//                    }
//                    currPiece = _allPieces.get(i).get(index + 1);
//                    workingPieces.set(i, currPiece);
//                    j = 0;
//                    checkPieceVariations(currPiece, workingPieces, i);
//                }
//            }
//        }
//        currPiece.setCurrentTranslation(j);
//        return currPiece;
//    }
//
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
//        int i = 0;
//        while (i < workingPieces.size()) {
//
////        }
////
////        for (int i = 0; i < workingPieces.size(); i++) {
//            Piece currPiece = workingPieces.get(i);
//            if (currPiece.getChange()) {
//                int index = 0;
//                while (currPiece != _allPieces.get(i).get(index)) {
//                    index += 1;
//                }
//                currPiece = _allPieces.get(i).get(index + 1);
//                while (currPiece.getAllPossibleTranslations().size() == 0 && index < 7) {
//                    index += 1;
//                    currPiece = _allPieces.get(i).get(index);
//                }
////                currPiece = _allPieces.get(i).get(index + 1);
//                workingPieces.set(i, currPiece);
//            }
//
//            currPiece = this.checkPieceVariations(currPiece, workingPieces, i);
////            if (currPiece != null) {
//            int j = currPiece.getCurrentTranslation();
//            ArrayList<int[]> possTranslations = currPiece.getPossTranslations();
//            if (possTranslations.size() > 0) {
//                currPiece.translatePieceLocation(possTranslations.get(j)[0],possTranslations.get(j)[1]);
//                currPiece.addToBoard();
//                i += 1;
//            }
////            }
//            else {
//                i -= 1;
//                workingPieces.get(i).setChange(true);
//            }


//            int j = 0;
//            while (!(currPiece.isValidMove(possTranslations.get(j)[0],possTranslations.get(j)[1]))) {
//                j += 1;
//                if (j >= possTranslations.size()) {
//                    int index = 0;
//                    while (currPiece != _allPieces.get(i).get(index)) {
//                        index += 1;
//                    }
//                    workingPieces.set(i, _allPieces.get(i).get(index + 1));
////                    return;
//                }
//            }
//
//        }
//        return;
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

//    private void setupTimeline() {
//        KeyFrame kf = new KeyFrame(
//                Duration.seconds(Constants.DURATION),
//                new TimePuzzleHandler());
//        _timeline = new Timeline(kf);
//        _timeline.setCycleCount(Animation.INDEFINITE);
//        _timeline.play();
//    }

//    private class TimePuzzleHandler implements EventHandler<ActionEvent> {

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

//        private int _i = 1;
//        private int _j = 0;
//        private boolean _create_new_piece = true;
//        private Piece _piece;
//        private ArrayList<int[]> _possTranslations;

//        public void handle(ActionEvent event) {
//            if (_create_new_piece) {
//
//                _piece = new Piece(_boardPane, _gameCircleArray, _j);
//                _possTranslations = _piece.getAllPossibleTranslations();
//                _piece.translatePieceLocation(_possTranslations.get(0)[0], _possTranslations.get(0)[1]);
//                _piece.addToBoard();
//                _create_new_piece = false;
//            }

//            if (_i < _possTranslations.size() ) {
//                int newCol = _possTranslations.get(_i)[0];
//                int oldCol = _possTranslations.get(_i-1)[0];
//                int newRow = _possTranslations.get(_i)[1];
//                int oldRow = _possTranslations.get(_i-1)[1];
//
//                _piece2.translatePieceLocation(newCol - oldCol, newRow - oldRow);
//                _i += 1;
//            }
//            if (_i >= _possTranslations.size()) {
//                _piece.removeFromBoard();
//                _piece = null;
//                _j += 1;
//                _i = 1;
//                _create_new_piece = true;
//            }
//


//        }
//    }
}
