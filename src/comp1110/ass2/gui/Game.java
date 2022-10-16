package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Resource;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.text.TextAlignment.CENTER;

public class Game extends Application {
    private static final Board board = new Board();
    private static final Game game = new Game();

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final double BOARD_LOCATION_WIDTH = 600-125*Math.sqrt(3);
    private static final double BOARD_LOCATION_HEIGHT = 350-3*75;
    private static final String URI_BASE = "assets/";

    //basic board
    private static final double BOARD_LOCATION_WIDTH_ADAPTION = 18; //fit the background manually by ourselves
    private static final double BOARD_LOCATION_HEIGHT_ADAPTION = BOARD_LOCATION_WIDTH_ADAPTION*450/(250*Math.sqrt(3));
    private static final boolean WHETHER_SHOW_BOUNDARY = false; //modify whether show the boundary manually

    //catan logo
    private static final double CATAN_LOCATION_WIDTH_ADAPTION = 250;
    private static final double CATAN_LOCATION_HEIGHT_ADAPTION = 100;
    private static final double XOfCatan = 0;
    private static final double YOfCatan = 0;
    //recorder
    private static final double RECORDER_HORIZONTAL_SPACE_ADAPTION = 59;
    private static final double RECORDER_VERTICAL_SPACE_ADAPTION = 57;
    private static final double XOfRecorder = 865;
    private static final double YOfRecorder = 20;
    //resource
    private static final double RESOURCE_HORIZONTAL_SPACE_ADAPTION = 30;
    private static final double XOfResource = 180;
    private static final double YOfResource = 200;
    private static final double RESOURCE_RADIUS = 25;
    //dice roller
    private static final double XOfDiceRoller = 30;
    private static final double YOfDiceRoller = 300;
    private static final double DICE_ROLLER_WIDTH = 300;
    private static final double DICE_ROLLER_HEIGHT = 180;
    private static final double DICE_ROLLER_RADIUS = 25;

    private final Group root = new Group();// basic group
    private final Group basicBoard = new Group();
    private final Group roads = new Group();
    private final Group settlements = new Group();
    private final Group cities = new Group();
    private final Group knights = new Group();
    private final Group controls = new Group();
    private final Group recorder = new Group();
    private final Group resources = new Group();
    private final Group diceRoller = new Group();

    private TextField playerTextField;
    private TextField boardTextField;

    Map<Integer, hexagon> hexagonsMap = new HashMap<>();
    Map<Integer, road> roadsMap = new HashMap<>();
    Map<Integer, settlement> settlementsMap = new HashMap<>();
    Map<Integer, city> citiesMap = new HashMap<>();
    Map<Integer, knight> knightsMap = new HashMap<>();
    Map<Integer, DraggableResource> resourcesMap = new HashMap<>();
    ArrayList<Circle> circles = new ArrayList<>();

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
            text.setTextAlignment(CENTER);
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
            text.setTextAlignment(CENTER);
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
            text.setTextAlignment(CENTER);
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
            text.setTextAlignment(CENTER);
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
        Image boardImage = new Image(Viewer.class.getResource(URI_BASE + "Board.JPG").toString());
        ImageView boardView = new ImageView(boardImage);
        boardView.setFitWidth(250*Math.sqrt(3)+2*BOARD_LOCATION_WIDTH_ADAPTION);
        boardView.setFitHeight(450+2*BOARD_LOCATION_HEIGHT_ADAPTION);
        boardView.setLayoutX(BOARD_LOCATION_WIDTH-BOARD_LOCATION_WIDTH_ADAPTION);
        boardView.setLayoutY(BOARD_LOCATION_HEIGHT-BOARD_LOCATION_HEIGHT_ADAPTION);
        this.basicBoard.getChildren().add(boardView);
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
            if(i<=board.getRound()){
                score = new Label(String.valueOf(board.getScoresRecorder()[i]));
            }else {
                score = new Label("");
            }
            //set position of label
            if(i<5){
                score.setLayoutX(i * RECORDER_HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(0);
            }else if (i == 5){
                score.setLayoutX(4 * RECORDER_HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(RECORDER_VERTICAL_SPACE_ADAPTION);
            } else if (i>5 && i<= 10){
                score.setLayoutX((10-i) * RECORDER_HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(2 * RECORDER_VERTICAL_SPACE_ADAPTION);
            } else if (i == 11){
                score.setLayoutX(0);
                score.setLayoutY(3 * RECORDER_VERTICAL_SPACE_ADAPTION);
            } else {
                score.setLayoutX((i-12) * RECORDER_HORIZONTAL_SPACE_ADAPTION);
                score.setLayoutY(4 * RECORDER_VERTICAL_SPACE_ADAPTION);
            }
            score.setFont(Font.font("TimesRomes", FontWeight.BOLD, 22));
            score.setAlignment(Pos.CENTER);
            recorder.getChildren().add(score);
        }
        //create a label to display current final score in each round
        int currentFinalScore = board.calculateCurrentFinalScore();
        System.out.println(currentFinalScore);
        Label finalScore = new Label(String.valueOf(currentFinalScore));
        finalScore.setLayoutX(3.65 * RECORDER_HORIZONTAL_SPACE_ADAPTION);
        finalScore.setLayoutY(4 * RECORDER_VERTICAL_SPACE_ADAPTION);
        finalScore.setFont(Font.font("TimesRomes", FontWeight.BOLD, 22));
        finalScore.setAlignment(Pos.CENTER);
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
     * set background
     */
    void initializeBackground(){
        Image backgroundImage = new Image(Viewer.class.getResource(URI_BASE + "Background.JPG").toString());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1200);
        backgroundView.setFitHeight(700);
        backgroundView.setLayoutX(0);
        backgroundView.setLayoutY(0);
        this.root.getChildren().add(backgroundView);
    }

    /**
     * set catan logo
     */
    void initializeCatan(){
        Image catanImage = new Image(Viewer.class.getResource(URI_BASE + "Catan.png").toString());
        ImageView catanView = new ImageView(catanImage);
        catanView.setFitWidth(CATAN_LOCATION_WIDTH_ADAPTION);
        catanView.setFitHeight(CATAN_LOCATION_HEIGHT_ADAPTION);
        catanView.setLayoutX(XOfCatan);
        catanView.setLayoutY(YOfCatan);
        this.root.getChildren().add(catanView);
    }

    /**
     * set board: add Group board, roads, settlements, cities, knights to Group root and show the initialized board on stage
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
     * set recorder: add Group recorder to Group root and show the initialized board on stage
     */
    void initializeRecorder() {
        Image recorderImage = new Image(Viewer.class.getResource(URI_BASE + "Recorder.png").toString());
        ImageView recorderView = new ImageView(recorderImage);
        recorderView.setFitWidth(300);
        recorderView.setFitHeight(300);
        recorderView.setLayoutX(XOfRecorder+8);
        recorderView.setLayoutY(YOfRecorder+5);
        this.root.getChildren().add(recorderView);
        recorder.setLayoutX(XOfRecorder+30);
        recorder.setLayoutY(YOfRecorder+30);
        root.getChildren().add(recorder);
        this.makeRecorder();
    }

    /**
     * set resources: add Group resources to Group root
     */
    void initializeResources(){
        root.getChildren().add(resources);
        this.makeResources();
    }

    /**
     * set dice roller: add Group diceRoller to Group root
     */
    void initializeDiceRoller(){
        root.getChildren().add(diceRoller);
        this.makeDiceRoller();
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
        initializeBackground();
        initializeCatan();
        initializeBoard();
        initializeRecorder();
        initializeResources();
        initializeDiceRoller();
        //initializeControls();

        //show stage
        stage.setScene(scene);
        stage.show();
    }






























    class resource extends Circle {
        private double x;
        private double y;
        private double radius;
        private Resource currentResource = Resource.Brick;
        protected Group resource = new Group();

        public resource(double x, double y, double radius, Resource currentResource){
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.currentResource = currentResource;
            this.setCenterX(x);
            this.setCenterY(y);
            this.setRadius(radius);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            //set image
            Image resourceImage = new Image(Viewer.class.getResource(URI_BASE + currentResource.getName() + ".png").toString());
            ImageView resourceView = new ImageView(resourceImage);
            resourceView.setFitWidth(3*radius);
            resourceView.setFitHeight(2.4*radius);
            resourceView.setLayoutX(x-1.3*radius);
            resourceView.setLayoutY(y-radius);
            this.resource.getChildren().addAll(this, resourceView);
//            this.resource.getChildren().add(this);
        }
        protected void showText(){
            Text text = new Text(x, y+2.3*radius, String.valueOf(board.getCurrentResource()[this.currentResource.getIndex()-1]));
            text.setFont(new Font(15));
            text.setStyle("-fx-font-weight:bold");
            text.setTextAlignment(CENTER);
            resources.getChildren().add(text);
        }
        private double distance(double x, double y){
            return Math.sqrt(Math.pow(x-this.x,2)+Math.pow(y-this.y,2));
        }
    }

    class DraggableResource extends resource {
        private Game game;
        private double mouseX;
        private double mouseY;
        private Circle highlighted = null;
        public DraggableResource(double x, double y, double radius, Resource currentResource, Game game) {
            super(x, y, radius, currentResource);
            this.game = game;
            this.setCenterX(x);
            this.setCenterY(y);

            this.setOnMousePressed(event -> {
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
                this.toFront();
            });
            this.setOnMouseDragged(event->{
                this.setLayoutX(mouseX);
                this.setLayoutY(mouseY);
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
                highlightNearestCircle(mouseX,mouseY);
            });
            this.setOnMouseReleased(event->{
                Circle snapCircle = findNearestCircle(mouseX,mouseY);
                this.setCenterX(snapCircle.getCenterX());
                this.setCenterY(snapCircle.getCenterY());
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });
        }

        public Circle findNearestCircle(double x, double y){
            double minDistance = Integer.MAX_VALUE;
            Circle currentCircle = null;
//            System.out.println("------------------------start-----------------");
            for(Circle circle:circles){
                double currentDistance = Math.sqrt(Math.pow((circle.getCenterX()-x),2)+Math.pow((circle.getCenterY()-y),2));
                if(currentDistance<minDistance){
                    minDistance = currentDistance;
                    currentCircle = circle;
//                    System.out.println("--------------circle-------------");
//                    System.out.println("For layout, " + circle.getLayoutX()+", " +circle.getLayoutY());
//                    System.out.println("For center, " + circle.getCenterX()+", " +circle.getCenterY());
//                    System.out.println("-----------------------------");
//                    System.out.println("---------------currentCircle------------");
//                    System.out.println("For layout, " + currentCircle.getLayoutX()+", " +currentCircle.getLayoutY());
//                    System.out.println("For center, " + currentCircle.getCenterX()+", " +currentCircle.getCenterY());
//                    System.out.println("-----------------------------");
                }

            }

            return currentCircle;
        };

        public void highlightNearestCircle(double x, double y){
            if(highlighted != null){
                highlighted.setFill(Color.LIGHTGRAY);
            }
            highlighted = findNearestCircle(x,y);
            highlighted.setFill(Color.GREEN);
        }
    }

    /**
     * Create 6 circle to display 6 types of resource
     */
    private void makeResources() {
        //set basic edge model
        DraggableResource ore = new DraggableResource(XOfResource - 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Ore, game);
        DraggableResource grain = new DraggableResource(XOfResource - 3*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Grain, game);
        DraggableResource wool = new DraggableResource(XOfResource - RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Wool, game);
        DraggableResource timber = new DraggableResource(XOfResource + RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Timber, game);
        DraggableResource brick = new DraggableResource(XOfResource + 3*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Brick, game);
        DraggableResource gold = new DraggableResource(XOfResource + 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Gold, game);
        resourcesMap.put(1,ore);
        resourcesMap.put(2,grain);
        resourcesMap.put(3,wool);
        resourcesMap.put(4,timber);
        resourcesMap.put(5,brick);
        resourcesMap.put(6,gold);
        for(DraggableResource resource: resourcesMap.values()){
            resource.showText();
            this.resources.getChildren().add(resource.resource);
        }
    }

    /**
     * Create dice roller
     */
    private void makeDiceRoller(){
        Circle circle1 = new Circle(XOfDiceRoller+DICE_ROLLER_WIDTH/6,YOfDiceRoller+DICE_ROLLER_HEIGHT/4, DICE_ROLLER_RADIUS, Color.LIGHTGRAY);
        Circle circle2 = new Circle(XOfDiceRoller+DICE_ROLLER_WIDTH/2,YOfDiceRoller+DICE_ROLLER_HEIGHT/4, DICE_ROLLER_RADIUS, Color.LIGHTGRAY);
        Circle circle3 = new Circle(XOfDiceRoller+5*DICE_ROLLER_WIDTH/6,YOfDiceRoller+DICE_ROLLER_HEIGHT/4, DICE_ROLLER_RADIUS, Color.LIGHTGRAY);
        Circle circle4 = new Circle(XOfDiceRoller+DICE_ROLLER_WIDTH/6,YOfDiceRoller+3*DICE_ROLLER_HEIGHT/4, DICE_ROLLER_RADIUS, Color.LIGHTGRAY);
        Circle circle5 = new Circle(XOfDiceRoller+DICE_ROLLER_WIDTH/2,YOfDiceRoller+3*DICE_ROLLER_HEIGHT/4, DICE_ROLLER_RADIUS, Color.LIGHTGRAY);
        Circle circle6 = new Circle(XOfDiceRoller+5*DICE_ROLLER_WIDTH/6,YOfDiceRoller+3*DICE_ROLLER_HEIGHT/4, DICE_ROLLER_RADIUS, Color.LIGHTGRAY);
        circles.add(circle1);
        circles.add(circle2);
        circles.add(circle3);
        circles.add(circle4);
        circles.add(circle5);
        circles.add(circle6);
//        for(Circle resource:circles){
//            System.out.println("--------------resource-------------");
//            System.out.println("For layout, " + resource.getLayoutX()+", " +resource.getLayoutY());
//            System.out.println("For center, " + resource.getCenterX()+", " +resource.getCenterY());
//            System.out.println("-----------------------------");
//        }

        Rectangle rectangle = new Rectangle(XOfDiceRoller, YOfDiceRoller, DICE_ROLLER_WIDTH, DICE_ROLLER_HEIGHT);
        rectangle.setFill(null);
        rectangle.setStroke(Color.BLACK);
        Button button = new Button("Roll");
        button.setLayoutX(XOfDiceRoller);
        button.setLayoutY(YOfDiceRoller+DICE_ROLLER_HEIGHT);
        button.setAlignment(Pos.CENTER);
        button.setMinSize(DICE_ROLLER_WIDTH, 30);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //FIXME
            }
        });
        diceRoller.getChildren().addAll(rectangle, circle1,circle2,circle3,circle4,circle5,circle6,button);
    }







//    class Triangle extends Polygon{
//        private double x;
//        private double y;
//        private double side;
//        public Triangle(double x, double y, double side){
//            this.x = x;
//            this.y = y;
//            this.side = side;
//            this.setLayoutY(y);
//            this.setLayoutX(x);
//            this.getPoints().addAll(
//                    0.0, -this.side * Math.sqrt(3) / 4,
//                    -this.side/2, this.side * Math.sqrt(3) / 4,
//                    this.side/2, this.side * Math.sqrt(3) / 4
//            );
//
//            this.setFill(Color.LIGHTGRAY);
//        }
//
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception{
//        stage.setTitle("Board");
//        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
//        stage.setScene(scene);
//
//        for(int i = 0; i<5; i++){
//            for(int j = 0; j < 3; j++){
//                Triangle triangle = new Triangle(100+100*i, 86.6+173.2*j, 196);
//                triangles.add(triangle);
//                if(i%2 == 1){
//                    triangle.setRotate(180);
//                }
//                root.getChildren().add(triangle);
//            }
//        }
//
//        DraggableTriangle draggableTriangle = new DraggableTriangle(300, 260,200, this);
//        root.getChildren().add(draggableTriangle);
//        stage.show();
//    }
//


}
