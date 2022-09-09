package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private static final int HEX_HEIGHT = 150;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField playerTextField;
    private TextField boardTextField;

    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param The string representation of the board state.
     */
    void displayState(String board_state) {
        // FIXME Task 5: implement the state viewer
        this.makeBoard();
    }
    private void makeBoard(){
        Double side = (Double) (HEX_HEIGHT * Math.sqrt(3));
        Polygon hex1 = new Polygon();
        hex1.getPoints().addAll(-side / 2, -75.0,
                -side, 0.0,
                -side / 2, 75.0,
                side / 2, 75.0,
                side , 0.0,
                side / 2, -75.0);
        hex1.setLayoutX(600);
        hex1.setLayoutY(200);
        hex1.setFill(Color.GREEN);
        Polygon hex2 = new Polygon();
        hex2.getPoints().addAll(
                -side/2, -75.0,
                -side, 0.0,
                -side/2, 75.0,
                side/2, 75.0,
                side, 0.0,
                side/2, -75.0);
        hex2.setLayoutX(600 - 3 * side / 2);
        hex2.setLayoutY(425);
        hex2.setFill(Color.RED);
        Polygon hex3 = new Polygon();
        hex3.getPoints().addAll(
                -side/2, -75.0,
                -side, 0.0,
                -side/2, 75.0,
                side/2, 75.0,
                side, 0.0,
                side/2, -75.0);
        hex3.setLayoutX(600);
        hex3.setLayoutY(500);
        hex3.setFill(Color.YELLOW);

        Polygon hex4 = new Polygon();
        hex4.getPoints().addAll(
                -side/2, -75.0,
                -side, 0.0,
                -side/2, 75.0,
                side/2, 75.0,
                side, 0.0,
                side/2, -75.0);
        hex4.setLayoutX(600 + 3 * side / 2);
        hex4.setLayoutY(425);
        hex4.setFill(Color.LIGHTGREY);

        Polygon hex5 = new Polygon();
        hex5.getPoints().addAll(
                -side/2, -75.0,
                -side, 0.0,
                -side/2, 75.0,
                side/2, 75.0,
                side, 0.0,
                side/2, -75.0);
        hex5.setLayoutX(600 + 3 * side / 2);
        hex5.setLayoutY(275);
        hex5.setFill(Color.BLUE);

        Polygon hex6 = new Polygon();
        hex6.getPoints().addAll(
                -side/2, -75.0,
                -side, 0.0,
                -side/2, 75.0,
                side/2, 75.0,
                side, 0.0,
                side/2, -75.0);
        hex6.setLayoutX(600 - 3 * side / 2);
        hex6.setLayoutY(275);
        hex6.setFill(Color.BLACK);

        root.getChildren().addAll(hex6, hex1, hex2, hex3, hex4, hex5);
    }
    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(500);
        Button button = new Button("Show");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
