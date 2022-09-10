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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Viewer extends Application {

    private final Group root = new Group();
    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group controls = new Group();
    private final Group board = new Group();
    private final Group road = new Group();
    private final Group settlement = new Group();
    private final Group city = new Group();
    private final Group knight = new Group();
    private TextField playerTextField;
    private TextField boardTextField;

    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param The string representation of the board state.
     */
    void displayState(String board_state) {
        // FIXME Task 5: implement the state viewer
        this.makeBasicBoard();
        this.makeRoads();
        this.makeSettlements();
        this.makeCities();
//        this.dots();
    }
    class hexagon extends Polygon{
        private double x;
        private double y;
        private double side;
        public hexagon(double x, double y, double side){
            this.x = x;
            this.y = y;
            this.side = side;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.LIGHTGREY);
        }
        public void setAllPoints(){
            this.getPoints().addAll(
                    0.5*this.side, 0.5*Math.sqrt(3)*this.side,
                    this.side, 0.0,
                    0.5*this.side, -0.5*Math.sqrt(3)*this.side,
                    -0.5*this.side, -0.5*Math.sqrt(3)*this.side,
                    -this.side, 0.0,
                    -0.5*this.side, 0.5*Math.sqrt(3)*this.side
            );
        }
    }
    class road extends Polygon{
        private double x;
        private double y;
        private double rotation;
        public road(double x, double y, double rotation){
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.setRotate(this.rotation);
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.BLUE);
        }
        public void setAllPoints(){
            this.getPoints().addAll(
                    20.0,5.0,
                    20.0,-5.0,
                    -20.0,-5.0,
                    -20.0,5.0
            );
        }
    }
    class settlement extends Polygon{
        private double x;
        private double y;
        public settlement(double x, double y){
            this.x = x;
            this.y = y;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.GREEN);
        }
        public void setAllPoints(){
            this.getPoints().addAll(
                    11.0,-5.0,
                    7.0,-5.0,
                    7.0,12.0,
                    -7.0,12.0,
                    -7.0,-5.0,
                    -11.0,-5.0,
                    0.0,-16.0
            );
        }
    }

    class city extends Polygon{
        private double x;
        private double y;
        public city(double x, double y){
            this.x = x;
            this.y = y;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.RED);
            this.setMouseTransparent(false);
        }
        public void setAllPoints(){
            this.getPoints().addAll(
                    11.0,-5.0,
                    7.0,-5.0,
                    7.0,12.0,
                    -20.0,12.0,
                    -20.0,2.0,
                    -7.0,2.0,
                    -7.0,-5.0,
                    -11.0,-5.0,
                    0.0,-16.0
            );
        }
    }

    class knight extends Ellipse{
        
    }
    private void makeBasicBoard() {
        hexagon hexagon1 = new hexagon(600+75*Math.sqrt(3),275,50*Math.sqrt(3)-1);
        hexagon hexagon2 = new hexagon(600+75*Math.sqrt(3),425,50*Math.sqrt(3)-1);
        hexagon hexagon3 = new hexagon(600,500,50*Math.sqrt(3)-1);
        hexagon hexagon4 = new hexagon(600-75*Math.sqrt(3),425,50*Math.sqrt(3)-1);
        hexagon hexagon5 = new hexagon(600-75*Math.sqrt(3),275,50*Math.sqrt(3)-1);
        hexagon hexagon6 = new hexagon(600,200,50*Math.sqrt(3)-1);
        Map<Integer, hexagon> hexagons = new HashMap<>();
        hexagons.put(1,hexagon1);
        hexagons.put(2,hexagon2);
        hexagons.put(3,hexagon3);
        hexagons.put(4,hexagon4);
        hexagons.put(5,hexagon5);
        hexagons.put(6,hexagon6);
        for(hexagon hexagon: hexagons.values()){
            hexagon.setAllPoints();
            this.board.getChildren().add(hexagon);
        }
    }
    private void makeRoads(){
        road road0 = new road(600-37.5*Math.sqrt(3), 350-37.5,-60);
        road road1 = new road(600-75*Math.sqrt(3), 350.0,0);
        road road2 = new road(600-37.5*Math.sqrt(3), 350+37.5,60);
        road road3 = new road(600-37.5*Math.sqrt(3), 350+3*37.5,-60);
        road road4 = new road(600-75*Math.sqrt(3), 350+150,0);
        road road5 = new road(600-37.5*Math.sqrt(3), 500+37.5,60);
        road road6 = new road(600, 575,0);
        road road7 = new road(600+37.5*Math.sqrt(3), 500+37.5,-60);
        road road8 = new road(600+37.5*Math.sqrt(3), 500-37.5,60);
        road road9 = new road(600+37.5*Math.sqrt(3), 350+37.5,-60);
        road road10 = new road(600+37.5*Math.sqrt(3), 350-37.5,60);
        road road11 = new road(600+37.5*Math.sqrt(3), 350-112.5,-60);
        road road12 = new road(600+75*Math.sqrt(3), 500,0.0);
        road road13 = new road(600+112.5*Math.sqrt(3), 425+37.5,-60);
        road road14 = new road(600+112.5*Math.sqrt(3), 425-37.5,60);
        road road15 = new road(600+112.5*Math.sqrt(3), 275+37.5,-60);
        Map<Integer, road> roads = new HashMap<>();
        roads.put(0,road0);
        roads.put(1,road1);
        roads.put(2,road2);
        roads.put(3,road3);
        roads.put(4,road4);
        roads.put(5,road5);
        roads.put(6,road6);
        roads.put(7,road7);
        roads.put(8,road8);
        roads.put(9,road9);
        roads.put(10,road10);
        roads.put(11,road11);
        roads.put(12,road12);
        roads.put(13,road13);
        roads.put(14,road14);
        roads.put(15,road15);
        for(road road: roads.values()){
            road.setAllPoints();
            this.road.getChildren().add(road);
        }
    }

    private void makeSettlements(){
        settlement settlement3 = new settlement(600-75/Math.sqrt(3), 350-75);
        settlement settlement4 = new settlement(600-75/Math.sqrt(3), 500-75);
        settlement settlement5 = new settlement(600-75/Math.sqrt(3), 500+75);
        settlement settlement7 = new settlement(600+50*Math.sqrt(3), 500);
        settlement settlement9 = new settlement(600+50*Math.sqrt(3), 350);
        settlement settlement11 = new settlement(600+50*Math.sqrt(3), 200);
        Map<Integer, settlement> settlements = new HashMap<>();
        settlements.put(3,settlement3);
        settlements.put(4,settlement4);
        settlements.put(5,settlement5);
        settlements.put(7,settlement7);
        settlements.put(9,settlement9);
        settlements.put(11,settlement11);
        for(settlement settlement: settlements.values()){
            settlement.setAllPoints();
            this.settlement.getChildren().add(settlement);
        }
    }

    private void makeCities(){
        city city7 = new city(600-100*Math.sqrt(3), 350);
        city city12 = new city(600-100*Math.sqrt(3), 500);
        city city20 = new city(600+125*Math.sqrt(3), 425);
        city city30 = new city(600+125*Math.sqrt(3), 275);
        Map<Integer, city> cities = new HashMap<>();
        cities.put(7,city7);
        cities.put(12, city12);
        cities.put(20, city20);
        cities.put(30, city30);
        for(city city: cities.values()){
            city.setAllPoints();
            this.city.getChildren().add(city);
        }
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
        root.getChildren().add(board);
        root.getChildren().add(road);
        root.getChildren().add(settlement);
        root.getChildren().add(city);
        root.getChildren().add(knight);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void dots() {

        String s = boardTextField.getText();
        Double side = (Double) (50 * Math.sqrt(3));
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
        if (s.contains("R1,")) {
            Circle circle = new Circle();
            circle.setCenterX(0);
            circle.setCenterY(75.0);
            circle.setRadius(10);
            circle.setLayoutX(600 - 3 * side / 2);
            circle.setLayoutY(275);
            circle.setFill(Color.GREEN);
            root.getChildren().addAll(circle);
        }
        if (s.endsWith("R1")) {
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