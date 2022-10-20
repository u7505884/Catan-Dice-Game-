package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.text.TextAlignment.CENTER;
import static javafx.scene.text.TextAlignment.LEFT;

/**
 * @author
 */
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
    //resource table
    private static final double RESOURCE_TABLE_LOCATION_WIDTH_ADAPTION = 250;
    private static final double RESOURCE_TABLE_LOCATION_HEIGHT_ADAPTION = 175;
    private static final double XOfResourceTable = 57;
    private static final double YOfResourceTable = 520;
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
    //nextRoundButton
    private static final double NEXT_ROUND_BUTTON_LOCATION_WIDTH_ADAPTION = 200;
    private static final double NEXT_ROUND_BUTTON_LOCATION_HEIGHT_ADAPTION = 50;
    private static final double XOfNextRoundButton = 600-NEXT_ROUND_BUTTON_LOCATION_WIDTH_ADAPTION*0.5;
    private static final double YOfNextRoundButton = 617;
    //console
    private static final double CONSOLE_LOCATION_WIDTH_ADAPTION = 300;
    private static final double CONSOLE_LOCATION_HEIGHT_ADAPTION = 4000;
    private static final double XOfConsole = 867;
    private static final double YOfConsole = 450;

    private static final Group root = new Group();// basic group
    private static final Group basicBoard = new Group();
    private static final Group roads = new Group();
    private static final Group settlements = new Group();
    private static final Group cities = new Group();
    private static final Group knights = new Group();
    private static final Group resourceTable = new Group();
    private static final Group controls = new Group();
    private static final Group recorder = new Group();
    private static final Group draggableResources = new Group();
    private static final HBox resourcesAndText = new HBox();
    private static final HBox tradeButtons = new HBox();
    private static final HBox nameLabels = new HBox();
    private static final Group diceRoller = new Group();
    private static final Group nextRoundButton = new Group();

    private TextField playerTextField;
    private TextField boardTextField;

    static Map<Integer, hexagon> hexagonsMap = new HashMap<>();
    static Map<Integer, road> roadsMap = new HashMap<>();
    static Map<Integer, settlement> settlementsMap = new HashMap<>();
    static Map<Integer, city> citiesMap = new HashMap<>();
    static Map<Integer, knight> knightsMap = new HashMap<>();
    static ArrayList<Circle> circles = new ArrayList<>();
    static ArrayList<Circle> currentCircles =  new ArrayList<>();
    static int[] resourcesNeedToBeRolled = new int[6];
    static int[] numberOfRollInEachRound = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    static ArrayList<BuildableStructures> buildableStructures = new ArrayList<>();
    static Resource[] resourceType = {Resource.Ore, Resource.Grain, Resource.Wool, Resource.Timber, Resource.Brick, Resource.Gold};

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

        private Road roadObject;

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
        public road(double x, double y, double rotation, Road roadObject){
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.roadObject = roadObject;
            this.setRotate(this.rotation);
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            Text text = new Text(x-3, y+3, "1");
            text.setFont(new Font(10));
            text.setTextAlignment(CENTER);
            road.getChildren().addAll(this,text);
            road.setOnMouseClicked(event -> {
                if(buildableStructures.contains(this.roadObject)){
                    board.build(roadObject);
                    highlight();
                    refreshDices(false);
                    refreshBoard();
                    refreshRecorder();
                }
            });
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
        private Settlement settlementObject;
        public settlement(double x, double y, int score, Settlement settlementObject){
            this.x = x;
            this.y = y;
            this.score = score;
            this.settlementObject = settlementObject;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            Text text = new Text(x-5, y+5, String.valueOf(score));
            text.setFont(new Font(10));
            text.setTextAlignment(CENTER);
            settlement.getChildren().addAll(this,text);
            settlement.setOnMouseClicked(event -> {
                if(buildableStructures.contains(this.settlementObject)){
                    board.build(settlementObject);
                    highlight();
                    refreshDices(false);
                    refreshBoard();
                    refreshRecorder();
                }
            });
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
        private City cityObject;
        public city(double x, double y, int score, City cityObject){
            this.x = x;
            this.y = y;
            this.score = score;
            this.cityObject = cityObject;
            this.setLayoutX(this.x);
            this.setLayoutY(this.y);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            Text text = new Text(x-5, y+5, String.valueOf(score));
            text.setFont(new Font(10));
            text.setTextAlignment(CENTER);
            city.getChildren().addAll(this,text);
            city.setOnMouseClicked(event -> {
                if(buildableStructures.contains(this.cityObject)){
                    board.build(cityObject);
                    highlight();
                    refreshDices(false);
                    refreshBoard();
                    refreshRecorder();
                }
            });
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

        private Knight knightObject;

        public knight(double x, double y, double radiusX, double radiusY, int score, Knight knightObject){
            this.x = x;
            this.y = y;
            this.radiusX = radiusX;
            this.radiusY = radiusY;
            this.score = score;
            this.knightObject = knightObject;
            this.setCenterX(x);
            this.setCenterY(y);
            this.setRadiusX(radiusX);
            this.setRadiusY(radiusY);
            this.setFill(Color.WHITE);
            this.setStroke(Color.BLACK);
            this.circle = new Circle(x,y-17,8, Color.WHITE);
            this.circle.setStroke(Color.BLACK);
            this.setAccessibleText("disdainful");
            Text text = new Text(x-3, y+5, String.valueOf(score));
            text.setFont(new Font(10));
            text.setTextAlignment(CENTER);
            knight.getChildren().addAll(this,circle,text);
            knight.setOnMouseClicked(event -> {
                if (!knightObject.getWhetherHaveSwapped()&&knightObject.getWhetherHaveBuilt()){//what we want when we click a built knight
                    openSwapStage(knightObject);
                    refreshBoard();
                }
                if(buildableStructures.contains(this.knightObject)){//what we want when we click an un-built knight
                    board.build(knightObject);
                    highlightJ();
                }
                refreshDices(false);
                refreshBoard();
                refreshRecorder();
            });
        }
        public void highlightJ(){
            this.circle.setFill(Color.RED);
            this.setFill(Color.RED);
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
     * use for create resource component, which represents resource we have currently
     */
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
            this.setFill(Color.rgb(0,0,0,0));// set color null, but the difference from null is this can be captured by click
        }
    }

    /**
     * use for create draggable resource component
     */
    class DraggableResource extends resource {
        private Game game;
        private double mouseX;
        private double mouseY;
        private Circle highlighted = null;
        private boolean lock = false;// lock draggable resource when it is in the position, unless right click
        private Circle destination; // record where will current node go, so that we can roll back when right click happens
        public DraggableResource(double x, double y, double radius, Resource currentResource, Game game) {
            super(x, y, radius, currentResource);
            this.game = game;
            this.setCenterX(x);
            this.setCenterY(y);
            Image draggableResourceImage = new Image(Viewer.class.getResource(URI_BASE + currentResource.getName() +".png").toString());
            ImageView draggableResourceView = new ImageView(draggableResourceImage);
            draggableResourceView.setFitWidth(2*radius+4);
            draggableResourceView.setFitHeight(2*radius+4);
            draggableResourceView.setLayoutX(x-radius-2);
            draggableResourceView.setLayoutY(y-radius-2);
            this.resource.getChildren().addAll(draggableResourceView, this);//add image first, so that resource component can be clicked
            //mouse action
            this.setOnMousePressed(event -> {
                if(event.getButton() == MouseButton.PRIMARY && lock == false){
                    this.mouseX = event.getSceneX();
                    this.mouseY = event.getSceneY();
                    this.toFront();
                }
            });
            this.setOnMouseClicked(event -> {
                if(event.getButton()== MouseButton.SECONDARY){
                    //back to initial position
                    this.setCenterX(x);
                    this.setCenterY(y);
                    draggableResourceView.setLayoutX(x-radius-2);
                    draggableResourceView.setLayoutY(y-radius-2);
                    this.toFront();
                    //roll back configuration
                    lock = false;
                    currentCircles.remove(destination);//set this circle available again
                    destination.setFill(Color.LIGHTGRAY);
                }
            });
            this.setOnMouseDragged(event->{
                if(lock == false){
                    if(findNearestCircle(mouseX,mouseY) == null){//no left position in dice roller
                        this.setCenterX(super.getCenterX());
                        this.setCenterY(super.getCenterY());
                        draggableResourceView.setLayoutX(super.getCenterX()-super.getRadius()-2);
                        draggableResourceView.setLayoutY(super.getCenterY()-super.getRadius()-2);
                    }else{
                        this.setCenterX(mouseX);
                        this.setCenterY(mouseY);
                        this.mouseX = event.getSceneX();
                        this.mouseY = event.getSceneY();
                        draggableResourceView.setLayoutX(mouseX-radius);
                        draggableResourceView.setLayoutY(mouseY-radius);
                        highlightNearestCircle(mouseX,mouseY);
                    }

                }
            });
            this.setOnMouseReleased(event->{
                if(lock == false){
                    Circle snapCircle = findNearestCircle(mouseX,mouseY);
                    if(snapCircle == null){//no left position in dice roller
                        this.setCenterX(super.getCenterX());
                        this.setCenterY(super.getCenterY());
                        draggableResourceView.setLayoutX(super.getCenterX()-super.getRadius()-2);
                        draggableResourceView.setLayoutY(super.getCenterY()-super.getRadius()-2);
                    }else{
                        this.destination = snapCircle;
                        this.setCenterX(snapCircle.getCenterX());
                        this.setCenterY(snapCircle.getCenterY());
                        draggableResourceView.setLayoutX(snapCircle.getCenterX()-radius-3);
                        draggableResourceView.setLayoutY(snapCircle.getCenterY()-radius-3);
                        this.mouseX = event.getSceneX();
                        this.mouseY = event.getSceneY();
                        lock = true;
                        //record which circles we have occupied
                        currentCircles.add(snapCircle);
                        //record which resource has been placed in roller
                        resourcesNeedToBeRolled[super.currentResource.getIndex()-1]++;
                    }

                }

            });
        }

        public Circle findNearestCircle(double x, double y){
            double minDistance = Integer.MAX_VALUE;
            Circle resCircle = null;
            for(Circle circle:circles){
                if(!currentCircles.contains(circle)){
                    double currentDistance = Math.sqrt(Math.pow((circle.getCenterX()-x),2)+Math.pow((circle.getCenterY()-y),2));
                    if(currentDistance<minDistance){
                        minDistance = currentDistance;
                        resCircle = circle;
                    }
                }
            }
            return resCircle;
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
        road road0 = new road(600-37.5*Math.sqrt(3), 350-37.5,-60, board.getRoads().get(0));
        road road1 = new road(600-75*Math.sqrt(3), 350.0,0, board.getRoads().get(1));
        road road2 = new road(600-37.5*Math.sqrt(3), 350+37.5,60, board.getRoads().get(2));
        road road3 = new road(600-37.5*Math.sqrt(3), 350+3*37.5,-60, board.getRoads().get(3));
        road road4 = new road(600-75*Math.sqrt(3), 350+150,0, board.getRoads().get(4));
        road road5 = new road(600-37.5*Math.sqrt(3), 500+37.5,60, board.getRoads().get(5));
        road road6 = new road(600, 575,0, board.getRoads().get(6));
        road road7 = new road(600+37.5*Math.sqrt(3), 500+37.5,-60, board.getRoads().get(7));
        road road8 = new road(600+37.5*Math.sqrt(3), 500-37.5,60, board.getRoads().get(8));
        road road9 = new road(600+37.5*Math.sqrt(3), 350+37.5,-60, board.getRoads().get(9));
        road road10 = new road(600+37.5*Math.sqrt(3), 350-37.5,60, board.getRoads().get(10));
        road road11 = new road(600+37.5*Math.sqrt(3), 350-112.5,-60, board.getRoads().get(11));
        road road12 = new road(600+75*Math.sqrt(3), 500,0.0, board.getRoads().get(12));
        road road13 = new road(600+112.5*Math.sqrt(3), 425+37.5,-60, board.getRoads().get(13));
        road road14 = new road(600+112.5*Math.sqrt(3), 425-37.5,60, board.getRoads().get(14));
        road road15 = new road(600+112.5*Math.sqrt(3), 275+37.5,-60, board.getRoads().get(15));

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
        settlement settlement3 = new settlement(600-75/Math.sqrt(3), 350-75, 3, board.getSettlements().get(3));
        settlement settlement4 = new settlement(600-75/Math.sqrt(3), 500-75,4, board.getSettlements().get(4));
        settlement settlement5 = new settlement(600-75/Math.sqrt(3), 500+75,5, board.getSettlements().get(5));
        settlement settlement7 = new settlement(600+50*Math.sqrt(3), 500,7, board.getSettlements().get(7));
        settlement settlement9 = new settlement(600+50*Math.sqrt(3), 350,9, board.getSettlements().get(9));
        settlement settlement11 = new settlement(600+50*Math.sqrt(3), 200,11, board.getSettlements().get(11));

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
        city city7 = new city(600-100*Math.sqrt(3), 350,7, board.getCities().get(7));
        city city12 = new city(600-100*Math.sqrt(3), 500, 12, board.getCities().get(12));
        city city20 = new city(600+125*Math.sqrt(3), 425, 20, board.getCities().get(20));
        city city30 = new city(600+125*Math.sqrt(3), 275, 30, board.getCities().get(30));

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
        knight knight1 = new knight(600-75*Math.sqrt(3), 275-35, 10, 15,1, board.getKnights().get(1));
        knight knight2 = new knight(600-75*Math.sqrt(3), 425-35, 10, 15,2, board.getKnights().get(2));
        knight knight3 = new knight(600, 500-35, 10, 15,3, board.getKnights().get(3));
        knight knight4 = new knight(600+75*Math.sqrt(3), 425-35, 10, 15,4, board.getKnights().get(4));
        knight knight5 = new knight(600+75*Math.sqrt(3), 275-35, 10, 15,5, board.getKnights().get(5));
        knight knight6 = new knight(600, 200-35, 10, 15,6, board.getKnights().get(6));
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
        Label finalScore = new Label(String.valueOf(currentFinalScore));
        finalScore.setLayoutX(3.65 * RECORDER_HORIZONTAL_SPACE_ADAPTION);
        finalScore.setLayoutY(4 * RECORDER_VERTICAL_SPACE_ADAPTION);
        finalScore.setFont(Font.font("TimesRomes", FontWeight.BOLD, 22));
        finalScore.setAlignment(Pos.CENTER);
        recorder.getChildren().add(finalScore);
    }

    /**
     * Create 6 circle to display 6 types of resource and text under them to display their amount
     */

    private void makeResourcesAndText(){
        // make circles
        ArrayList<Circle> backCircles = new ArrayList<>();
        Circle ore = new Circle(XOfResource - 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS);
        Circle grain = new Circle(XOfResource - 3*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS);
        Circle wool = new Circle(XOfResource - RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS);
        Circle timber = new Circle(XOfResource + RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS);
        Circle brick = new Circle(XOfResource + 3*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS);
        Circle gold = new Circle(XOfResource + 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS);
        backCircles.add(ore);
        backCircles.add(grain);
        backCircles.add(wool);
        backCircles.add(timber);
        backCircles.add(brick);
        backCircles.add(gold);
        int index =0;
        for(Circle circle :backCircles){
            Group resourceElement = new Group();
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            //make texts under circles, which indicate the amount of resource
            Text textAmount = new Text( circle.getCenterX()-0.3* circle.getRadius(),  circle.getCenterY()+3* circle.getRadius(), String.valueOf(board.getCurrentResource()[index]));
            textAmount.setFont(new Font(15));
            textAmount.setStyle("-fx-font-weight:bold");
            textAmount.setTextAlignment(CENTER);
            //make texts "EMPTY" in circles
            Text textNone = new Text( circle.getCenterX()-0.8* circle.getRadius(), circle.getCenterY(), "EMPTY");
            textNone.setFont(new Font(12));
            textNone.setTextAlignment(CENTER);
            resourceElement.getChildren().addAll(circle,textNone,textAmount);
            resourcesAndText.getChildren().add(resourceElement);
            index++;
        }
        //make name label
        Text oreLabel = new Text("Ore");
        oreLabel.setStyle("-fx-font-weight:bold");
        Text grainLabel = new Text("Grain");
        grainLabel.setStyle("-fx-font-weight:bold");
        Text woolLabel = new Text("Wool");
        woolLabel.setStyle("-fx-font-weight:bold");
        Text timberLabel = new Text("Timber");
        timberLabel.setStyle("-fx-font-weight:bold");
        Text brickLabel = new Text("Brick");
        brickLabel.setStyle("-fx-font-weight:bold");
        Text goldLabel = new Text("Gold");
        goldLabel.setStyle("-fx-font-weight:bold");
        nameLabels.getChildren().addAll(oreLabel, grainLabel, woolLabel, timberLabel, brickLabel, goldLabel);
        nameLabels.setSpacing(0.7*RESOURCE_HORIZONTAL_SPACE_ADAPTION);
        nameLabels.setLayoutX(XOfResource - 4.6*RESOURCE_HORIZONTAL_SPACE_ADAPTION-RESOURCE_RADIUS);
        nameLabels.setLayoutY(YOfResource - 2*RESOURCE_RADIUS);
        //make trade buttons
        Button oreButton = new Button("Trade");
        oreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.trade(Resource.Ore);
                refreshDices(false);
                refreshBoard();
            }
        });
        Button grainButton = new Button("Trade");
        grainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.trade(Resource.Grain);
                refreshDices(false);
                refreshBoard();
            }
        });
        Button woolButton = new Button("Trade");
        woolButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.trade(Resource.Wool);
                refreshDices(false);
                refreshBoard();
            }
        });
        Button timberButton = new Button("Trade");
        timberButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.trade(Resource.Timber);
                refreshDices(false);
                refreshBoard();
            }
        });
        Button brickButton = new Button("Trade");
        brickButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.trade(Resource.Brick);
                refreshDices(false);
                refreshBoard();
            }
        });
        tradeButtons.getChildren().addAll(oreButton, grainButton, woolButton, timberButton, brickButton);
        tradeButtons.setSpacing(0.12*RESOURCE_HORIZONTAL_SPACE_ADAPTION);
        tradeButtons.setLayoutX(XOfResource - 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION-RESOURCE_RADIUS);
        tradeButtons.setLayoutY(YOfResource + 1.2*RESOURCE_RADIUS);

        resourcesAndText.setLayoutX(XOfResource- 5.85*RESOURCE_HORIZONTAL_SPACE_ADAPTION);
        resourcesAndText.setLayoutY(YOfResource-RESOURCE_RADIUS);
        resourcesAndText.setSpacing(0.3*RESOURCE_HORIZONTAL_SPACE_ADAPTION);
    }

    /**
     * create draggable component based on their required amount
     */
    private void makeDraggableResources() {
        //make components based on their amount
        for(int i =0; i < 6; i++){
            int amount = board.getCurrentResource()[i];
            while(amount>0){
                DraggableResource draggableResource;
                switch (i){
                    case(0)-> draggableResource = new DraggableResource(XOfResource - 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Ore, game);
                    case(1)-> draggableResource = new DraggableResource(XOfResource - 3*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Grain, game);
                    case(2)-> draggableResource = new DraggableResource(XOfResource - RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Wool, game);
                    case(3)-> draggableResource = new DraggableResource(XOfResource + RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Timber, game);
                    case(4)-> draggableResource = new DraggableResource(XOfResource + 3*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Brick, game);
                    case(5)-> draggableResource = new DraggableResource(XOfResource + 5*RESOURCE_HORIZONTAL_SPACE_ADAPTION, YOfResource, RESOURCE_RADIUS, Resource.Gold, game);
                    default -> draggableResource = null;
                }
                this.draggableResources.getChildren().add(draggableResource.resource);
                amount--;
            }
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

        Rectangle rectangle = new Rectangle(XOfDiceRoller, YOfDiceRoller, DICE_ROLLER_WIDTH, DICE_ROLLER_HEIGHT);
        rectangle.setFill(null);
        rectangle.setStroke(Color.BLACK);
        Button button = new Button("Roll");
        button.setLayoutX(XOfDiceRoller);
        button.setLayoutY(YOfDiceRoller+DICE_ROLLER_HEIGHT);
        button.setAlignment(Pos.CENTER);
        button.setMinSize(DICE_ROLLER_WIDTH, 30);
        button.setFont(Font.font(15));
        if(numberOfRollInEachRound[board.getRound()]<3) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    board.roll(resourcesNeedToBeRolled);
                    refreshDices(true);
                }
            });
        }else {
            button.setDisable(true);//can not re-roll more than 3 times
        }
        diceRoller.getChildren().addAll(rectangle, circle1,circle2,circle3,circle4,circle5,circle6,button);
    }

    /**
     * create a button to enter next round
     */
    private void makeNextRoundButton(){
        Button button = new Button("NEXT ROUND");
        button.setAlignment(Pos.CENTER);
        button.setLayoutX(XOfNextRoundButton);
        button.setLayoutY(YOfNextRoundButton);
        button.setMinSize(NEXT_ROUND_BUTTON_LOCATION_WIDTH_ADAPTION, NEXT_ROUND_BUTTON_LOCATION_HEIGHT_ADAPTION);
        button.setFont(Font.font(18));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board.nextRound();
                refreshDices(false);
                refreshRecorder();
                refreshBoard();
            }
        });
        nextRoundButton.getChildren().add(button);
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
     * set image of resource table
     */
    void initializeResourceTable(){
        //set background
        Image resourceTableImage = new Image(Viewer.class.getResource(URI_BASE + "Resource Table.png").toString());
        ImageView resourceTableView = new ImageView(resourceTableImage);
        resourceTableView.setFitWidth(RESOURCE_TABLE_LOCATION_WIDTH_ADAPTION);
        resourceTableView.setFitHeight(RESOURCE_TABLE_LOCATION_HEIGHT_ADAPTION);
        resourceTableView.setLayoutX(XOfResourceTable);
        resourceTableView.setLayoutY(YOfResourceTable);
        this.resourceTable.getChildren().add(resourceTableView);
        this.root.getChildren().add(resourceTable);
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

        highlightBuildableStructures();

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
     * set draggable resources: add Group draggableResources to Group root
     */
    void initializeDraggableResources(){
        root.getChildren().add(draggableResources);
        this.makeDraggableResources();
    }

    /**
     * set resources and texts: add Group resourcesAndText to Group root
     */
    void initializeResourcesAndText(){
        root.getChildren().add(resourcesAndText);
        root.getChildren().add(tradeButtons);
        root.getChildren().add(nameLabels);
        this.makeResourcesAndText();
    }

    /**
     * set dice roller: add Group diceRoller to Group root
     */
    void initializeDiceRoller(){
        root.getChildren().add(diceRoller);
        this.makeDiceRoller();
    }

    /**
     * set nextRoundButton : add Group nextRoundButton to Group root
     */
    void initializeNextRoundButton(){
        root.getChildren().add(nextRoundButton);
        this.makeNextRoundButton();
    }

    /**
     * create a new console window
     */
    void initializeConsole() {
        TextArea console = new TextArea();
        console.setLayoutX(XOfConsole);
        console.setLayoutY(YOfConsole);
        console.setMaxSize(CONSOLE_LOCATION_WIDTH_ADAPTION, CONSOLE_LOCATION_HEIGHT_ADAPTION);
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b){
                String text = String.valueOf((char)b);
                Platform.runLater(() ->{
                    console.appendText(text);
                });
            }
            @Override
            public void write(byte[] b, int off, int len){
                String s = new String(b, off, len);
                Platform.runLater(()-> console.appendText(s));
            }
        }, true));
        System.setErr(System.out);
        root.getChildren().add(console);
    }

    /**
     * highlight all buildable structures in board
     */
    public void highlightBuildableStructures(){
        for (int index: roadsMap.keySet()){
            if(board.whetherCanBeBuilt(board.getRoads().get(index))){
                buildableStructures.add(roadsMap.get(index).roadObject);
                roadsMap.get(index).setFill(Color.YELLOW);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),roadsMap.get(index));
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0.3);
                fadeTransition.setCycleCount(Timeline.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
            }else if(board.getRoads().get(index).getWhetherHaveBuilt()){
                roadsMap.get(index).highlight();
            }

        }
        for (int index: knightsMap.keySet()){
            if(board.whetherCanBeBuilt(board.getKnights().get(index))){
                buildableStructures.add(knightsMap.get(index).knightObject);
                knightsMap.get(index).circle.setFill(Color.YELLOW);
                knightsMap.get(index).setFill(Color.YELLOW);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),knightsMap.get(index).knight);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0.3);
                fadeTransition.setCycleCount(Timeline.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
            }else if(board.getKnights().get(index).getWhetherHaveBuilt()){
                if(board.getKnights().get(index).getWhetherHaveSwapped()){
                    knightsMap.get(index).highlightK();
                }else {
                    knightsMap.get(index).highlightJ();
                }
            }
        }
        for (int index: settlementsMap.keySet()){
            if(board.whetherCanBeBuilt(board.getSettlements().get(index))){
                buildableStructures.add(settlementsMap.get(index).settlementObject);
                settlementsMap.get(index).setFill(Color.YELLOW);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),settlementsMap.get(index));
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0.3);
                fadeTransition.setCycleCount(Timeline.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
            }else if(board.getSettlements().get(index).getWhetherHaveBuilt()){
                settlementsMap.get(index).highlight();
            }
        }
        for (int index: citiesMap.keySet()){
            if(board.whetherCanBeBuilt(board.getCities().get(index))){
                buildableStructures.add(citiesMap.get(index).cityObject);
                citiesMap.get(index).setFill(Color.YELLOW);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7),citiesMap.get(index));
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0.3);
                fadeTransition.setCycleCount(Timeline.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
            }else if(board.getCities().get(index).getWhetherHaveBuilt()){
                citiesMap.get(index).highlight();
            }
        }
    }

    /**
     * refresh draggable resources, text of resources' number and dice roller
     *
     * @param whetherHaveRolled: identify whether we have roll dice(s), before refresh this
     */
    public void refreshDices(boolean whetherHaveRolled){
        resourcesAndText.getChildren().clear();
        nameLabels.getChildren().clear();
        tradeButtons.getChildren().clear();
        diceRoller.getChildren().clear();
        draggableResources.getChildren().clear();

        resourcesNeedToBeRolled = new int[6];
        currentCircles = new ArrayList<>();
        circles = new ArrayList<>();
        if(whetherHaveRolled)
            numberOfRollInEachRound[board.getRound()]++;

        makeDiceRoller();
        makeResourcesAndText();
        makeDraggableResources();
    }

    /**
     * refresh basic board
     */
    public void refreshBoard(){
        basicBoard.getChildren().clear();
        roads.getChildren().clear();
        settlements.getChildren().clear();
        cities.getChildren().clear();
        knights.getChildren().clear();

        hexagonsMap = new HashMap<>();
        roadsMap = new HashMap<>();
        settlementsMap = new HashMap<>();
        citiesMap = new HashMap<>();
        knightsMap = new HashMap<>();

        makeBasicBoard();
        makeRoads();
        makeSettlements();
        makeCities();
        makeKnights();

        highlightBuildableStructures();
    }

    /**
     * refresh score recorder
     */
    public void refreshRecorder(){
        recorder.getChildren().clear();
        makeRecorder();
    }

    /**
     * open a new stage to have the action of swap
     */
    public void openSwapStage(Knight knight){
        Stage swapStage = new Stage();
        swapStage.setTitle("Swap Action");

        Text swapText = new Text(220, 50, "What do you want to swapped out?");
        swapText.setFont(new Font(20));

        ComboBox<String> resourceSwapOut = new ComboBox<>();
        for(int indexOfResource = 1; indexOfResource<=6; indexOfResource++){
            if(board.getCurrentResource()[indexOfResource-1] >= 1 && knight.getScores()!=indexOfResource){
                resourceSwapOut.getItems().add(resourceType[indexOfResource-1].getName());
            }
        }

        Button button = new Button("Swap Out");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean whetherHaveCorrectChoice = true;
                try{
                    switch (resourceSwapOut.getValue()){
                        case("Ore")->board.swap(Resource.Ore, resourceType[knight.getScores()-1]);
                        case("Grain")->board.swap(Resource.Grain, resourceType[knight.getScores()-1]);
                        case("Wool")->board.swap(Resource.Wool, resourceType[knight.getScores()-1]);
                        case("Timber")->board.swap(Resource.Timber, resourceType[knight.getScores()-1]);
                        case("Brick")->board.swap(Resource.Brick, resourceType[knight.getScores()-1]);
                        case("Gold")->board.swap(Resource.Gold, resourceType[knight.getScores()-1]);
                        default -> whetherHaveCorrectChoice = false;
                    }
                }catch (NullPointerException ex){
                    whetherHaveCorrectChoice = false;
                }
                refreshDices(false);
                refreshRecorder();
                refreshBoard();
                if(whetherHaveCorrectChoice){
                    swapStage.close();
                }
            }
        });
        button.setFont(Font.font(15));

        VBox swapRoot = new VBox(swapText, resourceSwapOut, button);
        swapRoot.setAlignment(Pos.CENTER);
        swapRoot.setSpacing(70);
        Scene swapScene = new Scene(swapRoot, 600, 300);
        swapStage.setScene(swapScene);
        swapStage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //test
        board.setRound(7);
        board.setScoresRecorder(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
        board.setCurrentResource(new int[]{10,10,10,10,10,2});
        //set basic configuration of stage
        stage.setTitle("Game");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        //add different part of the game into stage
        initializeBackground();
        initializeResourceTable();
        initializeCatan();
        initializeBoard();
        initializeRecorder();
        initializeDiceRoller();
        initializeResourcesAndText();
        initializeDraggableResources();
        initializeNextRoundButton();
        initializeConsole();

        //show stage
        stage.setScene(scene);
        stage.show();
    }
}
