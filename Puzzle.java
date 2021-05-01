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

    public Puzzle(Pane boardPane) {
        _allPieces = new ArrayList<ArrayList<Piece>>();
        _boardPane = boardPane;
        _board = new Board(_boardPane);
        _gameCircleArray = _board.getCircles();
        _board.cordonOffRightTriangle(0, 10,11,0);
        _piece = new Piece(_boardPane, _gameCircleArray, 6);
        _piece.addToBoard();
//        this.setupTimeline();
        ArrayList<int[]> possTranslations = this.getAllPossibleTranslations(_piece);
        for (int i = 0; i < possTranslations.size(); i++) {
            _piece.printMatrix1D(possTranslations.get(i));
        }
        System.out.println(possTranslations.size());
//
//
//        lis = piece.generateVariations();

//        this.generateAllPieces();
        System.out.println(_allPieces.size());


    }

    public ArrayList<int[]> getAllPossibleTranslations(Piece piece) {
        ArrayList<int[]> possTranslations = new ArrayList<int[]>();
        for (int row = 0; row < _gameCircleArray.length - 1; row++) {
            for (int col = 0; col < _gameCircleArray[row].length - 1; col++) {
                if (piece.isValidMove(col,row)) {
                    int[] valid = {col, row};
                    possTranslations.add(valid);
                }
            }
        }
        return possTranslations;
    }

    private void setupTimeline() {
        KeyFrame kf = new KeyFrame(
                Duration.seconds(Constants.DURATION),
                new TimePuzzleHandler());
        _timeline = new Timeline(kf);
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();
    }

    private class TimePuzzleHandler implements EventHandler<ActionEvent> {

        public void movePieceOverBoard(Piece piece) {
            for (int row = 0; row < _gameCircleArray.length - 1; row ++) {
                int col = 0;
                while ((col < _gameCircleArray[row].length - 1)
                        && (piece.isValidMove(0,0))) {
                    piece.translatePieceLocation(1,0);
//                    piece.addToBoard();
                    col += 1;
                    System.out.println("Col: " + col + " Row: " + row);
//                    if ((col == _gameCircleArray[row].length - 1) &&
//                            !(piece.isValidMove(0,0))) {
//                        break;
//                    }
                }
            }
        }

        /**
         * This is the handle method it runs each frame of the timeline.
         */

        public void handle(ActionEvent event) {
            this.movePieceOverBoard(_piece);
        }
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
//            System.out.println("i: " + i);
        }
    }



    public void search() {
        int outerLength = _allPieces.size();
        int innerLength = _allPieces.get(0).size();
    }
}
