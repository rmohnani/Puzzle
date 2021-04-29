package Puzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Puzzle {

    private Pane _boardPane;
    private Board _board;
    private GameCircle[][] _gameCircleArray;

    public Puzzle(Pane boardPane) {
        _boardPane = boardPane;
        _board = new Board(_boardPane);
        _gameCircleArray = _board.getCircles();
//        _board.cordonOffRightTriangle(0, 10,11,0);
        Piece piece = new Piece(_boardPane, _gameCircleArray, 1);
        piece.translatePieceLocation(5,5);
//        piece.rotate();

//        Piece piece2 = new Piece(_boardPane, _gameCircleArray, 11);
//        piece2.translatePieceLocation(1,1);
//        piece.rotate();
        piece.flipPosY();
//        piece2.rotate();

//        piece.translatePieceLocation(0,-2);


    }
}
