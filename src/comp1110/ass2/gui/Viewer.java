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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private static final int HEX_HEIGHT = 50;

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
        this.dots();
    }

    private void makeBoard() {
        Double side = (Double) (HEX_HEIGHT * Math.sqrt(3));

        Polygon canvas = new Polygon();
        canvas.getPoints().addAll(
                50.0, 50.0,
                50.0, 700.0,
                1200.0 , 700.0,
                1200.0, 50.0);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        canvas.setFill(Color.WHITE);

        Polygon hex1 = new Polygon();
        hex1.getPoints().addAll(
                -side / 2, -75.0,
                -side, 0.0,
                -side / 2, 75.0,//S3
                side / 2, 75.0,
                side, 0.0,//S11
                side / 2, -75.0);
        hex1.setLayoutX(600);
        hex1.setLayoutY(200);
        hex1.setFill(Color.LIGHTGREY);

        Polygon hex2 = new Polygon();
        hex2.getPoints().addAll(
                -side / 2, -75.0,
                -side, 0.0,
                -side / 2, 75.0,//C12
                side / 2, 75.0,
                side, 0.0,//S4
                side / 2, -75.0);
        hex2.setLayoutX(600 - 3 * side / 2);
        hex2.setLayoutY(425);
        hex2.setFill(Color.LIGHTGREY);

        Polygon hex3 = new Polygon();
        hex3.getPoints().addAll(
                -side / 2, -75.0,
                -side, 0.0,
                -side / 2, 75.0,//S5
                side / 2, 75.0,
                side, 0.0,//S7
                side / 2, -75.0);
        hex3.setLayoutX(600);
        hex3.setLayoutY(500);
        hex3.setFill(Color.LIGHTGREY);

        Polygon hex4 = new Polygon();
        hex4.getPoints().addAll(
                -side / 2, -75.0,//S9
                -side, 0.0,
                -side / 2, 75.0,
                side / 2, 75.0,
                side, 0.0,//C20
                side / 2, -75.0);
        hex4.setLayoutX(600 + 3 * side / 2);
        hex4.setLayoutY(425);
        hex4.setFill(Color.LIGHTGREY);

        Polygon hex5 = new Polygon();
        hex5.getPoints().addAll(
                -side / 2, -75.0,//S11
                -side, 0.0,
                -side / 2, 75.0,
                side / 2, 75.0,
                side, 0.0,//C30
                side / 2, -75.0);
        hex5.setLayoutX(600 + 3 * side / 2);
        hex5.setLayoutY(275);
        hex5.setFill(Color.LIGHTGREY);

        Polygon hex6 = new Polygon();
        hex6.getPoints().addAll(
                -side / 2, -75.0,
                -side, 0.0,
                -side / 2, 75.0,
                side / 2, 75.0,
                side, 0.0,
                side / 2, -75.0);
        hex6.setLayoutX(600 - 3 * side / 2);
        hex6.setLayoutY(275);
        hex6.setFill(Color.LIGHTGREY);

        root.getChildren().addAll(canvas, hex6, hex1, hex2, hex3, hex4, hex5);
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

    private void dots() {

        String s = boardTextField.getText();
        Double side = (Double) (HEX_HEIGHT * Math.sqrt(3));
        if (s.contains("C7")) {
            Circle circle = new Circle();
            circle.setCenterX(-side / 2);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.BLACK);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S3")) {
            Circle circle = new Circle();
            circle.setCenterX(-side / 2);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(200);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S11")) {
            Circle circle = new Circle();
            circle.setCenterX(side);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(200);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("C12")) {
            Circle circle = new Circle();
            circle.setCenterX(-side / 2);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.BLACK);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S4")) {
            Circle circle = new Circle();
            circle.setCenterX(side);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S5")) {
            Circle circle = new Circle();
            circle.setCenterX(-side / 2);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S7")) {
            Circle circle = new Circle();
            circle.setCenterX(side);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S9")) {
            Circle circle = new Circle();
            circle.setCenterX(-side / 2);
            circle.setCenterY(-75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("C20")) {
            Circle circle = new Circle();
            circle.setCenterX(side);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.BLACK);
            root.getChildren().addAll(circle);
        }
        if (s.contains("S11")) {
            Circle circle = new Circle();
            circle.setCenterX(-side / 2);
            circle.setCenterY(-75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.RED);
            root.getChildren().addAll(circle);
        }
        if (s.contains("C30")) {
            Circle circle = new Circle();
            circle.setCenterX(side);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.BLACK);
            root.getChildren().addAll(circle);
        }
        if (s.contains("J1")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.BLUE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("K1")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.ORANGE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("J2")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.BLUE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("K2")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.ORANGE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("J3")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.BLUE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("K3")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.ORANGE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("J4")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.BLUE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("K4")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.ORANGE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("J5")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.BLUE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("K5")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.ORANGE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("J6")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(200);
            circle.setFill(Color.BLUE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("K6")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(0.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(200);
            circle.setFill(Color.ORANGE);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R0")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R1")) {
            Circle circle = new Circle();
            circle.setCenterX(0);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R2")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(-37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R3")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R4")) {
            Circle circle = new Circle();
            circle.setCenterX(0);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R5")) {
            Circle circle = new Circle();
            circle.setCenterX(-0.75*side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R6")) {
            Circle circle = new Circle();
            circle.setCenterX(0.0);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R7")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R8")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(-37.5);
            circle.setRadius(10);
            circle.setLayoutX(600);
            circle.setLayoutY(500);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R9")) {
            Circle circle = new Circle();
            circle.setCenterX(-0.75*side);
            circle.setCenterY(-37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R10")) {
            Circle circle = new Circle();
            circle.setCenterX(-0.75*side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R11")) {
            Circle circle = new Circle();
            circle.setCenterX(-0.75*side);
            circle.setCenterY(-37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R12")) {
            Circle circle = new Circle();
            circle.setCenterX(0);
            circle.setCenterY(75);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R13")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R14")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75*side);
            circle.setCenterY(-37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(425);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.contains("R15")) {
            Circle circle = new Circle();
            circle.setCenterX(0.75 * side);
            circle.setCenterY(37.5);
            circle.setRadius(10);
            circle.setLayoutX(600 + 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
    }
}