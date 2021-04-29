package Puzzle;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * This is the top-level PaneOrganizer Class, which sets up
 * the necessary panes for the Tetris game, and is responsible
 * for graphical aspects of game. It delegates the game logic to
 * the Tetris class which the PaneOrganizer calls.
 */

public class PaneOrganizer {
    // declares instance variable for root pane.
    private BorderPane _root;

    /**
     * This is PaneOrganizer's Constructor, which creates an instance
     * of PaneOrganizer. It creates a root Pane, sets its background
     * colour to Black, and calls the createPanes helper method
     * to setup all the necessary panes.
     */

    public PaneOrganizer() {
        _root = new BorderPane();
        _root.setStyle("-fx-background-color: BLACK");
        this.createPanes();
    }

    /**
     * This is the createPanes helper method. It creates a pane
     * for the Board, another to display the next tetromino piece,
     * another for the buttons, and a final pane for the labels.
     * It instantiates the top-level Tetris Object.
     */

    public void createPanes() {
        // Sets up pane for Tetris Board, places it in center
        // of root, and sets focus on it for KeyEvents.
        Pane boardPane = new Pane();
        boardPane.setFocusTraversable(true);
        _root.setCenter(boardPane);

        // Sets up Pane for labels, places it in top of root.
        HBox labelPane = new HBox();
        labelPane.setFocusTraversable(false);
        labelPane.setStyle("-fx-background-color: SKYBLUE");
        labelPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
        labelPane.setPrefWidth(Constants.BOARD_WIDTH);
        labelPane.setPrefHeight(Constants.LABEL_HEIGHT);
        labelPane.setAlignment(Pos.CENTER);
        labelPane.setSpacing(Constants.LABEL_SPACING);
        _root.setTop(labelPane);

        // Sets up Pane for next piece, places it right of root.
//        BorderPane nextPiecePane = new BorderPane();
//        nextPiecePane.setFocusTraversable(false);
//        nextPiecePane.setStyle("-fx-background-color: LIGHTSTEELBLUE");
//        nextPiecePane.setPrefWidth(Constants.PIECE_PANE_WIDTH);
//        nextPiecePane.setPrefHeight(Constants.BOARD_HEIGHT);
//        _root.setRight(nextPiecePane);

        // Sets up Pane for buttons, places it in bottom right
        // of nextPiece Pane.
//        HBox buttonPane = new HBox();
//        buttonPane.setFocusTraversable(false);
//        buttonPane.setAlignment(Pos.BOTTOM_RIGHT);
//        nextPiecePane.setBottom(buttonPane);

        // Instantiates top-level Tetris Object
//        new Tetris(boardPane, labelPane, nextPiecePane, buttonPane);
        new Puzzle(boardPane);
    }

    /**
     * This is the getRoot accessor method. it returns the root pane.
     */

    public BorderPane getRoot() {
        return _root;
    }

}