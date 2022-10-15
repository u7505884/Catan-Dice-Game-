package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.CatanDice;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Game extends Application {
    private static final Board board = new Board();

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final double BOARD_LOCATION_WIDTH = 600-125*Math.sqrt(3);
    private static final double BOARD_LOCATION_HEIGHT = 350-3*75;
    private static final String URI_BASE = "assets/";

    //basic board
    private static final double BOARD_LOCATION_WIDTH_ADAPTION = 18; //fit the background manually by ourselves
    private static final double BOARD_LOCATION_HEIGHT_ADAPTION = BOARD_LOCATION_WIDTH_ADAPTION*450/(250*Math.sqrt(3));
    private static final boolean WHETHER_SHOW_BOUNDARY = false; //modify whether show the boundary manually
    //recorder
    private static final double HORIZONTAL_SPACE_ADAPTION = 18;
    private static final double VERTICAL_SPACE_ADAPTION = 18;
    private static final double XOfRecorder = 18;
    private static final double YOfRecorder = 18;

    private final Group root = new Group();// basic group
    private final Group basicBoard = new Group();
    private final Group roads = new Group();
    private final Group settlements = new Group();
    private final Group cities = new Group();
    private final Group knights = new Group();
    private final Group controls = new Group();
    private final Group recorder = new Group();

    private TextField playerTextField;
    private TextField boardTextField;

    Map<Integer, hexagon> hexagonsMap = new HashMap<>();
    Map<Integer, road> roadsMap = new HashMap<>();
    Map<Integer, settlement> settlementsMap = new HashMap<>();
    Map<Integer, city> citiesMap = new HashMap<>();
    Map<Integer, knight> knightsMap = new HashMap<>();

    /**
     * use for create elements of boundary
     */
    class hexagon extends Polygon {
        private double x;
        private double y;
        private double side;
        public hexagon(double x, double y, double side){
            this.x = x;
            this.y = y;
            this.side = side;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(null);
            this.setStroke(WHETHER_SHOW_BOUNDARY? Color.BLACK:null);
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

    /**
     * use for create roads in map
     */
    class road extends Polygon{
        Group road = new Group();
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
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            Text text = new Text(x-3, y+3, "1");
            text.setFont(new Font(10));
            road.getChildren().addAll(this,text);

        }
        public void setAllPoints(){
            this.getPoints().addAll(
                    20.0,5.0,
                    20.0,-5.0,
                    -20.0,-5.0,
                    -20.0,5.0
            );
        }
        public void highlight(){
            this.setFill(Color.GREEN);
        }
    }

    /**
     * use for create settlements in map
     */
    class settlement extends Polygon{
        Group settlement = new Group();
        private double x;
        private double y;
        private int score;
        public settlement(double x, double y, int score){
            this.x = x;
            this.y = y;
            this.score = score;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            Text text = new Text(x-5, y+5, String.valueOf(score));
            text.setFont(new Font(10));
            settlement.getChildren().addAll(this,text);
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
        public void highlight(){
            this.setFill(Color.GREEN);
        }
    }

    /**
     * use for create cities in map
     */
    class city extends Polygon{
        Group city = new Group();
        private double x;
        private double y;
        private int score;
        public city(double x, double y, int score){
            this.x = x;
            this.y = y;
            this.score = score;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            Text text = new Text(x-5, y+5, String.valueOf(score));
            text.setFont(new Font(10));
            city.getChildren().addAll(this,text);
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
        public void highlight(){
            this.setFill(Color.GREEN);
        }
    }

    /**
     * use for create knights in map
     */
    class knight extends Ellipse {
        Group knight = new Group();
        private double x;
        private double y;
        private double radiusX;
        private double radiusY;
        private int score;
        private Circle circle;

        public knight(double x, double y, double radiusX, double radiusY, int score){
            this.x = x;
            this.y = y;
            this.radiusX = radiusX;
            this.radiusY = radiusY;
            this.score = score;
            this.setCenterX(x);
            this.setCenterY(y);
            this.setRadiusX(radiusX);
            this.setRadiusY(radiusY);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            this.circle = new Circle(x,y-17,8, Color.WHITE);
            this.circle.setStroke(Color.BLACK);
            Text text = new Text(x-3, y+5, String.valueOf(score));
            text.setFont(new Font(10));
            knight.getChildren().addAll(this,circle,text);
        }
        public void highlightJ(){
            this.circle.setFill(Color.GREEN);
            this.setFill(Color.GREEN);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),knight);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0.3);
            fadeTransition.setCycleCount(Timeline.INDEFINITE);
            fadeTransition.setAutoReverse(true);
            fadeTransition.play();
        }
        public void highlightK(){
            this.circle.setFill(Color.GREEN);
            this.setFill(Color.GREEN);
        }
    }

    /**
     * use for create the start sign in map
     */
    class startSign extends Polygon{
        private double x;
        private double y;
        public startSign(double x, double y){
            this.x = x;
            this.y = y;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
        }
        public void setAllPoints(){
            this.getPoints().addAll(
                    4.0,-13.0,
                    2.0,-13.0,
                    2.0,17.0,
                    -2.0,17.0,
                    -2.0,-13.0,
                    -4.0,-13.0,
                    0.0,-19.0
            );
        }
    }

    /**
     * use for combined hexagon and startSign components into board group
     */
    private void makeBasicBoard() {
        //set background
        Image backgroundImage = new Image(Viewer.class.getResource(URI_BASE + "background.JPG").toString());
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(250*Math.sqrt(3)+2*BOARD_LOCATION_WIDTH_ADAPTION);
        background.setFitHeight(450+2*BOARD_LOCATION_HEIGHT_ADAPTION);
        background.setLayoutX(BOARD_LOCATION_WIDTH-BOARD_LOCATION_WIDTH_ADAPTION);
        background.setLayoutY(BOARD_LOCATION_HEIGHT-BOARD_LOCATION_HEIGHT_ADAPTION);
        this.basicBoard.getChildren().add(background);
        //set basic edge model
        hexagon hexagon1 = new hexagon(600+75*Math.sqrt(3),275,50*Math.sqrt(3)-1);
        hexagon hexagon2 = new hexagon(600+75*Math.sqrt(3),425,50*Math.sqrt(3)-1);
        hexagon hexagon3 = new hexagon(600,500,50*Math.sqrt(3)-1);
        hexagon hexagon4 = new hexagon(600-75*Math.sqrt(3),425,50*Math.sqrt(3)-1);
        hexagon hexagon5 = new hexagon(600-75*Math.sqrt(3),275,50*Math.sqrt(3)-1);
        hexagon hexagon6 = new hexagon(600,200,50*Math.sqrt(3)-1);

        hexagonsMap.put(1,hexagon1);
        hexagonsMap.put(2,hexagon2);
        hexagonsMap.put(3,hexagon3);
        hexagonsMap.put(4,hexagon4);
        hexagonsMap.put(5,hexagon5);
        hexagonsMap.put(6,hexagon6);
        for(hexagon hexagon: hexagonsMap.values()){
            hexagon.setAllPoints();
            this.basicBoard.getChildren().add(hexagon);
        }
        //create start sign
        startSign startSign = new startSign(600-37.5*Math.sqrt(3), 275-37.5);
        startSign.setAllPoints();
        startSign.setRotate(150);
        road specialUseOfRoad = new road(600-37.5*Math.sqrt(3), 275-37.5,60);
        specialUseOfRoad.setFill(Color.PURPLE);
        specialUseOfRoad.setAllPoints();
        this.basicBoard.getChildren().addAll(specialUseOfRoad, startSign);
    }

    /**
     * use for combined all road components into roads group
     */
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

        roadsMap.put(0,road0);
        roadsMap.put(1,road1);
        roadsMap.put(2,road2);
        roadsMap.put(3,road3);
        roadsMap.put(4,road4);
        roadsMap.put(5,road5);
        roadsMap.put(6,road6);
        roadsMap.put(7,road7);
        roadsMap.put(8,road8);
        roadsMap.put(9,road9);
        roadsMap.put(10,road10);
        roadsMap.put(11,road11);
        roadsMap.put(12,road12);
        roadsMap.put(13,road13);
        roadsMap.put(14,road14);
        roadsMap.put(15,road15);
        for(road road: roadsMap.values()){
            road.setAllPoints();
            this.roads.getChildren().add(road.road);
        }
    }

    /**
     * use for combined all settlement components into settlements group
     */
    private void makeSettlements(){
        settlement settlement3 = new settlement(600-75/Math.sqrt(3), 350-75, 3);
        settlement settlement4 = new settlement(600-75/Math.sqrt(3), 500-75,4);
        settlement settlement5 = new settlement(600-75/Math.sqrt(3), 500+75,5);
        settlement settlement7 = new settlement(600+50*Math.sqrt(3), 500,7);
        settlement settlement9 = new settlement(600+50*Math.sqrt(3), 350,9);
        settlement settlement11 = new settlement(600+50*Math.sqrt(3), 200,11);

        settlementsMap.put(3,settlement3);
        settlementsMap.put(4,settlement4);
        settlementsMap.put(5,settlement5);
        settlementsMap.put(7,settlement7);
        settlementsMap.put(9,settlement9);
        settlementsMap.put(11,settlement11);
        for(settlement settlement: settlementsMap.values()){
            settlement.setAllPoints();
            this.settlements.getChildren().add(settlement.settlement);
        }
    }

    /**
     * use for combined all city components into cities group
     */
    private void makeCities(){
        city city7 = new city(600-100*Math.sqrt(3), 350,7);
        city city12 = new city(600-100*Math.sqrt(3), 500, 12);
        city city20 = new city(600+125*Math.sqrt(3), 425, 20);
        city city30 = new city(600+125*Math.sqrt(3), 275, 30);

        citiesMap.put(7,city7);
        citiesMap.put(12, city12);
        citiesMap.put(20, city20);
        citiesMap.put(30, city30);
        for(city city: citiesMap.values()){
            city.setAllPoints();
            this.cities.getChildren().add(city.city);
        }
    }

    /**
     * use for combined all knight components into knights group
     */
    private void makeKnights(){
        knight knight1 = new knight(600-75*Math.sqrt(3), 275-35, 10, 15,1);
        knight knight2 = new knight(600-75*Math.sqrt(3), 425-35, 10, 15,2);
        knight knight3 = new knight(600, 500-35, 10, 15,3);
        knight knight4 = new knight(600+75*Math.sqrt(3), 425-35, 10, 15,4);
        knight knight5 = new knight(600+75*Math.sqrt(3), 275-35, 10, 15,5);
        knight knight6 = new knight(600, 200-35, 10, 15,6);
        knightsMap.put(1,knight1);
        knightsMap.put(2,knight2);
        knightsMap.put(3,knight3);
        knightsMap.put(4,knight4);
        knightsMap.put(5,knight5);
        knightsMap.put(6,knight6);
        for(knight knight:knightsMap.values()){
            this.knights.getChildren().add(knight.knight);
        }

    }

    /**
     * Create a basic text field for input and a refresh button
     */
    private void makeControls() {
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(500);
        Button button = new Button("Show");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                displayState(boardTextField.getText());
//            }
//        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        controls.getChildren().add(hb);
    }

    /**
     * Create 16 labels to display score for each round
     */
    private void makeRecorder(){
        //create labels to display score in each round
        Label score;
        for(int i = 0; i < 15; i++){
            //set content of label
            if(i<board.getRound()){
                score = new Label(String.valueOf(board.getScoresRecorder()[i]));
            }else {
                score = new Label("");
            }
            //set position of label
            if(i<5){
                score.setLayoutX(XOfRecorder + i * HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(YOfRecorder);
            }else if (i == 5){
                score.setLayoutX(XOfRecorder + 4 * HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(YOfRecorder + VERTICAL_SPACE_ADAPTION);
            } else if (i>5 && i<= 10){
                score.setLayoutX(XOfRecorder + (10-i) * HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(YOfRecorder + 2 * VERTICAL_SPACE_ADAPTION);
            } else if (i == 11){
                score.setLayoutX(XOfRecorder);
                score.setLayoutY(YOfRecorder + 3 * VERTICAL_SPACE_ADAPTION);
            } else {
                score.setLayoutX(XOfRecorder + (12-i) * HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(YOfRecorder + 4 * VERTICAL_SPACE_ADAPTION);
            }
            recorder.getChildren().add(score);
        }
        //create a label to display current final score in each round
        int currentFinalScore = board.calculateCurrentFinalScore();
        Label finalScore = new Label(String.valueOf(currentFinalScore));
        finalScore.setLayoutX(XOfRecorder + 3.75 * HORIZONTAL_SPACE_ADAPTION);
        finalScore.setLayoutY(YOfRecorder + 4 * VERTICAL_SPACE_ADAPTION);
        recorder.getChildren().add(finalScore);
    }
    /**
     * highlight the selected component in Group roads, settlements, cities, knights
     *
     * @param board_state represent the component we selected
     */
    public void highlight(String board_state){
        //FIXME: need to modify its parameter, so that it will be more suitable here.
        String[] board_state_collections = board_state.split(",");
        for(String board_state_element:board_state_collections){
            board_state_element = board_state_element.strip();
            try{
                switch (board_state_element.charAt(0)){
                    case('R')->roadsMap.get(Integer.valueOf(board_state_element.substring(1))).highlight();
                    case('S')->settlementsMap.get(Integer.valueOf(board_state_element.substring(1))).highlight();
                    case('C')->citiesMap.get(Integer.valueOf(board_state_element.substring(1))).highlight();
                    case('K')->knightsMap.get(Integer.valueOf(board_state_element.substring(1))).highlightK();
                    case('J')->knightsMap.get(Integer.valueOf(board_state_element.substring(1))).highlightJ();
                }
            }catch(StringIndexOutOfBoundsException ex){
                break;
            }

        }
    }

    /**
     * add Group board, roads, settlements, cities, knights to Group root and show the initialized board on stage
     */
    void initializeBoard() {
        root.getChildren().add(basicBoard);
        root.getChildren().add(roads);
        root.getChildren().add(settlements);
        root.getChildren().add(cities);
        root.getChildren().add(knights);
        this.makeBasicBoard();
        this.makeRoads();
        this.makeSettlements();
        this.makeCities();
        this.makeKnights();
    }

    /**
     * add Group recorder to Group root and show the initialized board on stage
     */
    void initializeRecorder() {
        root.getChildren().add(recorder);
        this.makeRecorder();
    }
    /**
     * add Group controls to Group root and show the initialized controls on stage
     */
    void initializeControls() {
        root.getChildren().add(controls);
        this.makeControls();
    }
    @Override
    public void start(Stage stage) throws Exception {
        //set basic configuration of stage
        stage.setTitle("Game");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        //add different part of the game into stage
        initializeBoard();
        initializeRecorder();
        //initializeControls();

        //show stage
        stage.setScene(scene);
        stage.show();
    }
}
