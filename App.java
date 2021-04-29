package Puzzle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the App class. It starts the Tetris game, in which
 * 7 different tetromino pieces are randomly created, falling
 * down, and the player has to carefully place them to survive
 * the longest by clearing lines.
 *
 * The Start method starts the game by instantiating a top-level
 * PaneOrganizer Object, setting up the scene, and shows the stage.
 */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        stage.setTitle("PUZZLE");
        PaneOrganizer paneOrganizer = new PaneOrganizer();
        Scene scene = new Scene(paneOrganizer.getRoot(),
                Constants.BOARD_WIDTH,
                Constants.BOARD_HEIGHT + Constants.LABEL_HEIGHT);
        stage.setScene(scene);
        stage.show();


    }

    /*
     * Here is the mainline! No need to change this.
     */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
