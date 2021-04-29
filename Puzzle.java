package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Puzzle {

    private Pane _boardPane;
    private Board _board;
    private GameCircle[][] _gameCircleArray;
    private ArrayList<ArrayList<Piece>> _allPieces;

    public Puzzle(Pane boardPane) {
        _allPieces = new ArrayList<ArrayList<Piece>>();
        _boardPane = boardPane;
        _board = new Board(_boardPane);
        _gameCircleArray = _board.getCircles();
        _board.cordonOffRightTriangle(0, 10,11,0);
//        Piece piece = new Piece(_boardPane, _gameCircleArray, 0);
//        ArrayList<int[][]> lis = piece.generateVariations();
//        piece.translatePieceLocation(3,5);
//        piece.rotate();

//        Piece piece2 = new Piece(_boardPane, _gameCircleArray, 11);
//        piece2.translatePieceLocation(1,1);
//        piece.rotate();
//        piece.rotate();
//        piece.rotate();
//        piece.flipPosY();
//        piece.flipPosY();
//        piece2.rotate();

//        piece.translatePieceLocation(0,-2);
//        piece.rotateCoordinates(3 * Constants.DEGREES_90);
//        piece.flipInYCoordinates(piece.getType());
//        piece.flipInYCoordinates(piece.rotateCoordinates(piece.getType(), Constants.DEGREES_90));
        this.generateAllPieces();
        System.out.println(_allPieces.size());


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
}
