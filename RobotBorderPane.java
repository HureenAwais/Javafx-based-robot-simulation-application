/**
 * The RobotBorderPane class represents a JavaFX application demonstrating a robot simulation.
 * It includes a graphical interface with buttons, canvas, and menus to control and visualize robot behaviors.
 *
 * @authorhureen
 */
package RobotGUI;

import java.io.File;
import javafx.stage.FileChooser;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The main application class for the Robot simulation.
 */
public class RobotBorderPane extends Application {
    private RobotCanvas mc;
    private AnimationTimer timer;          // timer used for animation
    private VBox rtPane;                    // vertical box for putting info
    private RobotArena arena;
    private FileChooser fileChooser;
    private boolean delete = false;
	private Stage primaryStage;
	private static final long THREE_MINUTES_IN_NANOS = 1 * 60 * 1_000_000_000L;
    /**
     * Function to show a message.
     *
     * @param TStr title of the message block
     * @param CStr content of the message
     */
    private void showMessage(String TStr, String CStr) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(TStr);
        alert.setHeaderText(null);
        alert.setContentText(CStr);

        alert.showAndWait();
    }
    /**
     * Shows a popup message with the specified title and content.
     *
     * @param title   The title of the popup.
     * @param content The content of the popup.
     */
    public void showPopupMessage(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.initOwner(primaryStage);  // Set the owner of the pop-up to the primaryStage
        alert.showAndWait();
    }
    /**
     * Shows a "Game Over" popup message and stops the animation.
     */
    private void showGameOver() {
    	 // Run the code on the JavaFX Application Thread
    	 Platform.runLater(() -> {
    	        showPopupMessage("Whiskers 've won", "All game Robots are Dead. Whiskers and beams robots are the winner!");
    	        timer.stop();  // Stop the animation
    	       
    	    });
    	
    	 timer.stop();  // Stop the animation
    	 //System.exit(0);
       
    }
    /**
     * Shows a "Game Over" popup message and stops the animation.
     */
    private void showTimeOver() {
    	 // Run the code on the JavaFX Application Thread
    	 Platform.runLater(() -> {
    	        showPopupMessage("Game Over!! ", "TIME OVER! !!! Game and ring Robots are the winner!");
    	        timer.stop();  // Stop the animation
    	       
    	    });
    	
    	 timer.stop();  // Stop the animation
    	 //System.exit(0);
       
    }


    /**
     * Function to show information about the program.
     */
    private void showAbout() {
        
     showMessage( "About", "Hureen's JavaFX Demonstrator");

       
    }
    /**
     * Function to show instructions about the program.
     */
    private void showHelp() {
        String title = "Help";
        String contentText = "Welcome to the Robot Simulation Game!\n\n"
                + "Objective:\n"
                + "Simulate different robots in the arena and achieve the highest score.\n\n"
                + "Game Controls:\n"
                + "- Press the Start/Stop Animation button to control the simulation.\n"
                + "- Click and drag on items to reposition them in the arena.\n"
                + "- Press the Add buttons to introduce various types of robots at random positions.\n"
                + "- Press the Delete Item button and click on an arena item to remove it.\n"
                + "- Use the File menu to save, load, and exit the game.\n\n"
                + "Time Over:\n"
                + "- The game ends if whiskers can't kill Game and Ring robots in a minute.\n"
                + "Game Over:\n"
                + "- Game and Ring robots have 3 lives. Game ends when they die.\n"
                + "- Keep an eye on the robot scores to determine the game status.";

        showMessage(title, contentText);
    }

    public void showScore (double x, double y, int score) {
		mc.showText(x, y, Integer.toString(score));
	} 

    /**
     * Set up the mouse event - when the mouse is dragged, put the robot there.
     *
     * @param canvas The canvas where the mouse events are handled.
     */
    void setMouseEvents(Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,    // for MOUSE PRESSED event
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        arena.setXY(e.getX(), e.getY());

                        drawWorld();                            // redraw the world
                        drawStatus();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,    // for MOUSE PRESSED event
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (delete) {
                            arena.dltRobot(e.getX(), e.getY());

                            drawWorld();                            // redraw the world
                            delete = false;
                        }
                    }
                });
        
    }

    /**
     * Set up the menu of commands for the GUI.
     *
     * @return The menu bar containing File and Help menus.
     */
   MenuBar setMenu() {
        // initially set up the file chooser to look for cfg files in the current directory
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Robot Files", "*.cfg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        MenuBar menuBar = new MenuBar();                    // create main menu

        Menu mFile = new Menu("File");                      // add File main menu
        MenuItem mExit = new MenuItem("Exit");              // sub-menu item for Exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {             // action on exit is
                timer.stop();                               // stop timer
                System.exit(0);                             // exit program
            }
        });
        MenuItem mLoad = new MenuItem("Load");             // sub-menu item for Load
        mLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {             // action on load
                timer.stop();                               // stop timer
                File selectedFile = fileChooser.showOpenDialog(null);       // ask the user for the file name
                if (selectedFile != null) {                                // if selected
                    if (arena.loadFile(selectedFile.getName()) > 0)       // try to load
                        showMessage("Error", "Could not load file");
                    else {
                        showMessage("Message", "File loaded ok");
                        drawWorld();
                        drawStatus();                                       // indicate where balls are
                    }
                }
            }
        });
        MenuItem mSave = new MenuItem("Save");             // sub-menu item for Save
        mSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {             // action on save
                timer.stop();                               // stop timer
                File selectedFile = fileChooser.showSaveDialog(null);       // user selects the file
                if (selectedFile != null) {
                    if (arena.saveFile(selectedFile.getName()) > 0)       // save the arena
                        showMessage("Error", "Could not save file");
                    else showMessage("Message", "File saved ok");
                }
            }
        });
        mFile.getItems().addAll(mLoad, mSave, mExit);                   // add exit to File menu

        Menu mHelp = new Menu("Help");  // create Help menu
        MenuItem msubHelp = new MenuItem("Help");
   		msubHelp.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
               	showHelp();				// show the help message
               }
       
        });
   	 MenuItem mAbout = new MenuItem("About");            // sub-menu item for About
     mAbout.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent actionEvent) {
             showAbout(); // and its action to print about
             
         }
         });
        
       	
     mHelp.getItems().addAll(mAbout, msubHelp);                   // add About to Help main item

        menuBar.getMenus().addAll(mFile, mHelp);            // set the main menu with File, Help
        return menuBar;                                     // return the menu
    }

    /**
     * Set up the horizontal box for the bottom with relevant buttons.
     *
     * @return The HBox containing buttons for controlling the simulation.
     */
    private HBox setButtons() {
        Button btnStart = new Button("Start");                        // create a button for starting
        btnStart.setOnAction(new EventHandler<ActionEvent>() {    // now define the event when it is pressed
            @Override
            public void handle(ActionEvent event) {
                timer.start();                                    // its action is to start the timer
            }
        });

        Button btnStop = new Button("Stop");                        // now button for stopping
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();                                    // and its action to stop the timer
            }
        });
        Button btnAddArena = new Button("Arena Items");                        // now button for stopping
        btnAddArena.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drawWorld();                                   // and its action to stop the timer
            }
        });

        Button btnWhisker = new Button("Whisker");                // now button for adding a whisker robot
        btnWhisker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addRobot();                                // and its action to stop the timer
                drawWorld();
            }
        });
        Button btnGmaeRobot = new Button("GameRobot");                // now button for adding an obstacle
        btnGmaeRobot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addGameRobot();                            // and its action to stop the timer
                drawWorld();
            }
        });

        Button btnObstacle = new Button("Blocker");                // now button for adding an obstacle
        btnObstacle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addObstacles();                            // and its action to stop the timer
                drawWorld();
            }
        });

        Button btnBeam = new Button("Beam");                // now button for adding a beam robot
        btnBeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addBeams();                                // and its action to stop the timer
                drawWorld();
            }
        });
        Button btnAvoid = new Button("Ring");                // now button for adding a beam robot
        btnAvoid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arena.addavoidRobot();                                // and its action to stop the timer
                drawWorld();
            }
        });


        Button btnDlt = new Button("Delete Item");                // now button for deleting
        btnDlt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                timer.stop();
                delete = true;  // and its action to stop the timer

            }
        });
        // now add these buttons + labels to an HBox
        return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "),btnAddArena,btnGmaeRobot, btnWhisker, btnObstacle, btnBeam, btnAvoid , new Label("Dlt: "),btnDlt);
    }
    
   /**
    *  * Show the score .. by writing it at position x,y
	 * @param x
	 * @param y
	 * @param score
	 */
	/**public void showScore (double x, double y, int score) {
		mc.showInt(x, y, score);
	} */

    /**
     * Draw the world with the robot in it.
     */
    public void drawWorld() {
        mc.clearCanvas();                        // set beige color
        arena.drawArena(mc);
    }

    /**
     * Show where the robot is, in a pane on the right.
     */
    public void drawStatus() {
        rtPane.getChildren().clear();                    // clear rtPane
        ArrayList<String> allRs = arena.describeAll();
        for (String s : allRs) {
            Label l = new Label(s);         // turn description into a label
            rtPane.getChildren().add(l);    // add label
        }
    }

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
		primaryStage.setTitle("Robot simulation");
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 20, 10, 20));

        bp.setTop(setMenu());                                            // put the menu at the top

        Group root = new Group();                                        // create a group with a canvas
        Canvas canvas = new Canvas(400, 500);
        root.getChildren().add(canvas);
        bp.setLeft(root);                                                // load the canvas to the left area

        mc = new RobotCanvas(canvas.getGraphicsContext2D(), 400, 500);

        setMouseEvents(canvas);                                            // set up mouse events

        arena = new RobotArena(400, 500);                                // set up the arena
        //drawWorld();

        timer = new AnimationTimer() { 
        	// set up the timer
        	 // Set up AnimationTimer
           
                private long startTime = -1;

                @Override
                public void start() {
                    super.start();
                    startTime = System.nanoTime();
                }

                @Override
                public void handle(long currentNanoTime) {
                    if (startTime < 0) {
                        startTime = currentNanoTime;
                    }

                    long elapsedTime = currentNanoTime - startTime;

                    if (elapsedTime >= THREE_MINUTES_IN_NANOS) {
                        stop(); // Stop the timer after three minutes
                        showTimeOver();
                    } else {
                        // Your existing animation logic
                        if (arena.getGameRobots().isEmpty()) {
                            showGameOver();
                        }
                        arena.checkRobots();
                        arena.adjustRobot();
                        drawWorld();
                        drawStatus();
                    }
                }
            };
       

        rtPane = new VBox();                                            // set VBox on the right to list items
        rtPane.setAlignment(Pos.TOP_LEFT);                                // set alignment
        rtPane.setPadding(new Insets(5, 75, 75, 5));                    // padding
        bp.setRight(rtPane);                                            // add rtPane to borderpane right

        bp.setBottom(setButtons());                                        // set bottom pane with buttons

        Scene scene = new Scene(bp, 700, 600);                            // set the overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    /**
     * The main method to launch the GUI.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(args);            // launch the GUI
        
    }

}
