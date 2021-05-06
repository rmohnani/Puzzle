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
import java.util.Collections;
import java.util.Comparator;

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
//        _piece = new Piece(_boardPane, _gameCircleArray, 1, 0);
//        ArrayList<int[]> coordinate1 = _piece.to1DList((_piece.getVariation()).get(0));
//        ArrayList<int[]> coordinate2 = _piece.to1DList((_piece.getVariation()).get(1));
//        Boolean val = _piece.compareCoordinates(coordinate1, coordinate2);
//        if(val) {
//            System.out.println("true");
//        }
//        else {
//            System.out.println("false");
//        }
//        _piece.getUniqueVariations2();
//        _piece.getUniqueVariations();
//        _board.hardCodedPuzzleInitial();
//        _board.level3_1();
//        _board.level6_06();
//        _board.level7_14();
        this.generateAllPieces2();
//        _board.level7_14_v2(_allPieces2);
        _board.level8_11_v2(_allPieces2);
        this.reduceAndSortAllPieces();
//        this.search();
    }

//    public void sort(ArrayList<Piece> allPieces) {
//        Comparator<Piece> compareByVariationsSize = new Comparator<Piece>() {
//
//            @Override
//            public int compare(Piece o1, Piece o2) {
//                return o1.getVariations().g;
//            }
//        }
//    }

    public class VariationComparator implements Comparator<Piece> {
        @Override
        public int compare(Piece o1, Piece o2) {
            return (o1.getVariationsLength() - o2.getVariationsLength());
        }
    }

    public void generateAllPieces2() {
        for(int i = 0; i < 12; i++) {
            Piece piece = new Piece(_boardPane, _gameCircleArray, i, 0);
//            piece.getUniqueVariations();
//            piece.getUniqueVariations2();
            piece.getUniqueVariations3();
            _allPieces2.add(piece);
        }
//        Collections.sort(_allPieces2, new VariationComparator());
    }

    public void reduceAndSortAllPieces() {
        for(int i = 0; i < _allPieces2.size(); i++) {
            Piece piece = _allPieces2.get(i);
            if (piece.getFixed()) {
                piece.addToBoard();
                _allPieces2.remove(i);
                i -= 1;
            }
        }
        Collections.sort(_allPieces2, new VariationComparator());
    }

    public void search() {

        int i = 0;
        while (i < _allPieces2.size()) {
            System.out.println("Current i: " + i);
            Piece currPiece = _allPieces2.get(i);

            if (currPiece.getChangeVariationBool()) {
                int curVar = currPiece.getCurrentVariation();
                int varLength = currPiece.getVariationsLength();

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
                    else {

                        /**
                         * If need to change piece variation but no other variation exists
                         * then piece has no valid placement on current board, so need to go
                         * back to piece before (i - 1) and change its variation to next one.
                         */
//                        currPiece.removeFromBoard();
                        currPiece.setCurrentVariation(0);
                        currPiece = currPiece.changeVariation(currPiece.getCurrentVariation());
                        currPiece.setChangeVariationBool(false);


                        i -= 1;
                        currPiece = _allPieces2.get(i);
                        if (currPiece.getCurrentTranslation() < currPiece.getPossTranslations().size() - 1) {
                            currPiece.setChangeTranslationBool(true);
                        } else {
                            currPiece.setChangeVariationBool(true);
                        }
                    }

                }
                else {

//                    currPiece.removeFromBoard();
                    currPiece.setCurrentVariation(0);
                    currPiece = currPiece.changeVariation(currPiece.getCurrentVariation());
                    currPiece.setChangeVariationBool(false);
                    _allPieces2.set(i,currPiece);


                    i -= 1;
                    currPiece = _allPieces2.get(i);
                    if (currPiece.getCurrentTranslation() < currPiece.getPossTranslations().size() - 1) {
                        currPiece.setChangeTranslationBool(true);
                    } else {
                        currPiece.setChangeVariationBool(true);
                    }
                }
            }


            if (currPiece.getChangeTranslationBool()) {

                currPiece.setPossTranslations(currPiece.getAllPossibleTranslations());
                ArrayList<int[]> possTranslations = currPiece.getPossTranslations();
                if (possTranslations.size() == 0) {
                    currPiece.setChangeVariationBool(true);
                }

                else {
                    if (((possTranslations.get(currPiece.getCurrentTranslation())[0] == 0) &&
                            (possTranslations.get(currPiece.getCurrentTranslation())[1] == 0))
                     && (currPiece.getCurrentTranslation() < possTranslations.size() - 1)) {
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

                    if (currPiece.getCurrentTranslation() > possTranslations.size() - 1) {
                        currPiece.setChangeVariationBool(true);
                    }
                    else {
                        int j = currPiece.getCurrentTranslation();
                        currPiece.translatePieceLocation(possTranslations.get(j)[0], possTranslations.get(j)[1]);
                        currPiece.addToBoard();
                        currPiece.setChangeTranslationBool(false);
                        i += 1;
                    }
                }
            }
        }
    }
}
