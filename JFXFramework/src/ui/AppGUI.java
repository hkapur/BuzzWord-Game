package ui;

import apptemplate.AppTemplate;
import components.AppStyleArbiter;
import controller.FileController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import propertymanager.PropertyManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;
import static settings.AppPropertyType.*;
import static settings.InitializationParameters.APP_IMAGEDIR_PATH;

public class AppGUI implements AppStyleArbiter {
    protected FileController fileController;   // to react to file-related controls
    protected Stage primaryStage;     // the application window
    protected Scene primaryScene;     // the scene graph
    protected BorderPane appPane;          // the root node in the scene graph, to organize the containers
    protected BorderPane borderpane;
    public BorderPane hk;
    Button help;
    public GridPane copygrid;
    ScrollPane scrollPane;
    protected VBox toolbarPane;      // the top toolbar
    protected String applicationTitle; // the application title
    protected Button createprofile;
    protected Button selectmode;
    protected Button gotolevelselect;
    protected Button Login;
    protected Button Logout;
    protected HBox heading;
    String word;
    String guessed;
    protected Button b;
    protected Button Home;
    protected Button closeButton;
    protected BorderPane bp;
    protected Canvas cv;
    protected ChoiceBox cb;
    protected VBox v1;
    protected HBox time;
    protected VBox rightvbox;
    protected HBox combo;
    protected VBox target;
    protected HBox hb1;
    public Label level;
    int score;
    Label w8;
    int q=1;
    public Button replay;
    String guessed1 = "";
    String guessed2 = "";
    String guessed3 = "";
    String guessed4 = "";
    String guessed5 = "";
    Label label;
    public int m = 0;
    public boolean bul = true;
    public Button[] button = new Button[16];
    public String name;
    public String Pass;
    public int val = 1;
    public String k = "";
    public int l = 100;
    public int mit;
    public Button[] bt = new Button[4];
    public Button[] bt2 = new Button[4];
    public Button resume;
    final int[] timeSeconds = new int[1];
    public Timeline timeline;
    public String[] right = new String[1000];

    private int appWindowWidth;  // optional parameter for window width that can be set by the application
    private int appWindowHeight; // optional parameter for window height that can be set by the application

    public void setName(String name)
    {
        this.name = name;
    }
    public void setPass(String Pass)
    {
        this.Pass = Pass;
    }
    public Label lab() {
        Label l2 = new Label("English Dictionary");
        l2.setFont(new Font("Arial", 25));
        l2.setUnderline(true);
        l2.setTextFill(Color.web("#ffffff"));
        return l2;
    }
    public Label lab2(){
        Label l2 = new Label("Places");
        l2.setFont(new Font("Arial", 25));
        l2.setUnderline(true);
        l2.setTextFill(Color.web("#ffffff"));
        return l2;
    }

    public Label lab3(){
        Label l2 = new Label("Scenes");
        l2.setFont(new Font("Arial", 25));
        l2.setUnderline(true);
        l2.setTextFill(Color.web("#ffffff"));
        return l2;
    }

    public Label lab4(){
            Label l2 = new Label("Famous People");
            l2.setFont(new Font("Arial", 25));
            l2.setUnderline(true);
            l2.setTextFill(Color.web("#ffffff"));
            return l2;
        }

    public AppGUI(Stage initPrimaryStage, String initAppTitle, AppTemplate app) throws IOException, InstantiationException {
        this(initPrimaryStage, initAppTitle, app, -1, -1);
    }

    public AppGUI(Stage primaryStage, String applicationTitle, AppTemplate appTemplate, int appWindowWidth, int appWindowHeight) throws IOException, InstantiationException {
        this.appWindowWidth = appWindowWidth;
        this.appWindowHeight = appWindowHeight;
        this.primaryStage = primaryStage;
        this.applicationTitle = applicationTitle;
        initializeToolbar();                    // initialize the top toolbar
        initializeToolbarHandlers(appTemplate); // set the toolbar button handlers
        initializeWindow();                     // start the app window (without the application-specific workspace)
    }

    public FileController getFileController() {
        return this.fileController;
    }

    public VBox getToolbarPane() {
        return toolbarPane;
    }

    public BorderPane getAppPane() {
        return appPane;
    }

    public Label getHeading() {
        return getHeading();
    }

    public Scene getPrimaryScene() {
        return primaryScene;
    }

    public Stage getWindow() {
        return primaryStage;
    }

    public void initializeToolbar() throws IOException {
        toolbarPane = new VBox();
        cv = new Canvas(300, 150);
        GraphicsContext gc = cv.getGraphicsContext2D();
        initScreen(gc);
        heading = new HBox();
        Label l = new Label("!! BUZZWORD !!");
        heading.setPadding(new Insets(30));
        l.setFont(new Font("Arial", 25));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        heading.setAlignment(Pos.BASELINE_CENTER);
        toolbarPane.setSpacing(30);
        bp = new BorderPane();
        bp.setTop(heading);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        help = initializeButton(toolbarPane, "Help", CREATE_PROFILE_TOOLTIP.toString(), false);
        createprofile = initializeButton(toolbarPane, "CreateNewProfile", CREATE_PROFILE_TOOLTIP.toString(), false);
        Login = initializeButton(toolbarPane, "Login", LOGIN_TOOLTIP.toString(), false);
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }

    public void initializeToolbarHandlers(AppTemplate app) throws InstantiationException {
        try {
            Method getFileControllerClassMethod = app.getClass().getMethod("getFileControllerClass");
            String fileControllerClassName = (String) getFileControllerClassMethod.invoke(app);
            Class<?> klass = Class.forName("controller." + fileControllerClassName);
            Constructor<?> constructor = klass.getConstructor(AppTemplate.class);
            fileController = (FileController) constructor.newInstance(app);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            exit(1);
        }
        help.setOnAction(e-> fileController.handleHelpRequest());
        closeButton.setOnAction(e -> fileController.handleExitRequest());
        createprofile.setOnAction(e -> fileController.handleCreateProfile());
        Login.setOnAction(e -> {
            try {
                fileController.handleLogin();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
    public void initializeToolbarHandlers2(AppTemplate app) throws InstantiationException {
        try {
            Method getFileControllerClassMethod = app.getClass().getMethod("getFileControllerClass");
            String fileControllerClassName = (String) getFileControllerClassMethod.invoke(app);
            Class<?> klass = Class.forName("controller." + fileControllerClassName);
            Constructor<?> constructor = klass.getConstructor(AppTemplate.class);
            fileController = (FileController) constructor.newInstance(app);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            exit(1);
        }
        createprofile.setOnAction(e-> fileController.handleHintRequest());
        closeButton.setOnAction(e -> fileController.handleExitRequest());
        gotolevelselect.setOnAction(e -> fileController.handleSelectMode());
        Logout.setOnAction(e -> {
            fileController.handleLogout();
        });
    }
    public void initializeToolbarHandlers3(AppTemplate app) throws InstantiationException {
        try {
            Method getFileControllerClassMethod = app.getClass().getMethod("getFileControllerClass");
            String fileControllerClassName = (String) getFileControllerClassMethod.invoke(app);
            Class<?> klass = Class.forName("controller." + fileControllerClassName);
            Constructor<?> constructor = klass.getConstructor(AppTemplate.class);
            fileController = (FileController) constructor.newInstance(app);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            exit(1);
        }
        for(int i=0;i<4;i++) {
            int finalI = i;
            bt[i].setOnAction((event) -> {
                m = finalI + 1;
                fileController.handleLevelSelect();
            });
        }
        for(int j=0;j<4;j++) {
            int finalO = j+4;
            bt2[j].setOnAction((event) -> {
                m = finalO + 1;
                fileController.handleLevelSelect();
            });
        }
        createprofile.setOnAction(e->fileController.handleHintRequest());
        closeButton.setOnAction(e -> fileController.handleExitRequest());
        Home.setOnAction(e -> fileController.handleHomeRequest());
        Logout.setOnAction(e -> {
            fileController.handleLogout();
        });

    }
    public void initializegameToolbarHandlers(AppTemplate app) throws InstantiationException {
        try {
            Method getFileControllerClassMethod = app.getClass().getMethod("getFileControllerClass");
            String fileControllerClassName = (String) getFileControllerClassMethod.invoke(app);
            Class<?> klass = Class.forName("controller." + fileControllerClassName);
            Constructor<?> constructor = klass.getConstructor(AppTemplate.class);
            fileController = (FileController) constructor.newInstance(app);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            exit(1);
        }
        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

            Button cell = (Button) event.getSource();

            if( event.isPrimaryButtonDown()) {
                cell.setStyle("-fx-base:green");
            }
        };
        EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {

            PickResult pickResult = event.getPickResult();
            Node node = pickResult.getIntersectedNode();

            if( node instanceof Cell) {

                Button cell = (Button) node;

                if( event.isPrimaryButtonDown()) {
                    cell.setStyle("-fx-base:green");
                }

            }

        };
        EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
            for(int i=0;i<16;i++) {
                button[i].setStyle("-fx-background-color: rgb(96,96,96)");
            }
        };

        EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {

            Button cell = (Button) event.getSource();
            cell.startFullDrag();

        };

        EventHandler<MouseEvent> onMouseDragEnteredEventHandler = event -> {

            Button cell = (Button) event.getSource();

            if( event.isPrimaryButtonDown()) {
                cell.setStyle("-fx-base:green");
            }

        };
        if(bul==true) {
            resume.setOnAction((event) -> {
                bul = false;
                fileController.handlePauseRequest();
                k = "";
            });
        }
        else resume.setOnAction((event) -> {
            bul = true;
            fileController.handleResumeRequest();
            k = "";
        });
        score = 0;
        word = "";
        for(int i = 0;i<16;i++){
            int finalI = i;
            button[i].setOnAction((event) -> {
                word += button[finalI].getText();
                button[finalI].setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent ke)
                    {
                        if (ke.getCode().equals(KeyCode.ENTER))
                        {
                            for(int j=0;j<30;j++)
                            {
                                if((word.toLowerCase()).equals(right[j])&&word!="")
                                {
                                    right[j]=null;
                                    if(word.length()>=4) {
                                        score += 15;
                                    }
                                    else score +=10;
                                    guessed = word;
                                    guessed1 = guessed;
                                    System.out.println(score);
                                    fileController.handlescorereq();
                                }
                            }
                            word = "";
                        }
                    }
                });;
            });
            button[i].setOnMousePressed( onMousePressedEventHandler);
            button[i].setOnDragDetected( onDragDetectedEventHandler);
            button[i].setOnMouseDragEntered( onMouseDragEnteredEventHandler);
            button[i].setOnMouseReleased(onMouseReleasedEventHandler);
        }
        int j = 0;
        for(j=0;j<16;j++) {
            int finalJ = j;
            button[j].setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    if ((button[finalJ].getText().toLowerCase()).equals(ke.getCode().toString().toLowerCase())) {
                        button[finalJ].setStyle("-fx-base:green");
                    } else button[finalJ].setStyle("-fx-base:green");
                }
            });
        }
        replay.setOnAction(e -> fileController.req());
        closeButton.setOnAction(e -> fileController.handleExitRequest2());
        Home.setOnAction(e -> fileController.handleHomeRequest());
    }

    public void initializegameToolbarHandlersscore(AppTemplate app) throws InstantiationException {
        try {
            Method getFileControllerClassMethod = app.getClass().getMethod("getFileControllerClass");
            String fileControllerClassName = (String) getFileControllerClassMethod.invoke(app);
            Class<?> klass = Class.forName("controller." + fileControllerClassName);
            Constructor<?> constructor = klass.getConstructor(AppTemplate.class);
            fileController = (FileController) constructor.newInstance(app);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            exit(1);
        }
        if(bul==true) {
            resume.setOnAction((event) -> {
                bul = false;
                fileController.handlePauseRequest();
                k = "";
            });
        }
        else resume.setOnAction((event) -> {
            bul = true;
            fileController.handleResumeRequest();
            k = "";
        });
        word = "";
        for(int i = 0;i<16;i++){
            int finalI = i;
            button[i].setOnAction((event) -> {
                word += button[finalI].getText();
                button[finalI].setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    @Override
                    public void handle(KeyEvent ke)
                    {
                        if (ke.getCode().equals(KeyCode.ENTER))
                        {
                            for(int j=0;j<30;j++)
                            {
                                if((word.toLowerCase()).equals(right[j])&&word!="")
                                {
                                    guessed = word;
                                    right[j]=null;
                                    if(word.length()>=4){
                                        score+=15;
                                    }
                                    else score += 10;
                                    switch(q) {
                                        case 1: guessed2 = word;
                                            break;
                                        case 2: guessed3 = word;
                                            break;
                                        case 3: guessed4 = word;
                                            break;
                                        case 4: guessed5 = word;
                                    }
                                    q+=1;
                                    System.out.println(score);
                                    fileController.handlescorereq();
                                    if(score>=mit)
                                    {
                                        fileController.handleEnd();
                                    }
                                }
                            }
                            word = "";
                        }
                    }
                });;
            });
        }
        replay.setOnAction(e -> fileController.req());
        closeButton.setOnAction(e -> fileController.handleExitRequest2());
        Home.setOnAction(e -> fileController.handleHomeRequest());
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public void initScreen(GraphicsContext gc) {
        gc.setFill(Paint.valueOf("rgb(96,96,96)"));
        int y = 0;
        for (int j = 0; j < 4; j++) {
            gc.fillOval(0, y, 30, 30);
            gc.fillOval(60, y, 30, 30);
            gc.fillOval(120, y, 30, 30);
            gc.fillOval(180, y, 30, 30);
            y = y + 40;
        }
        gc.setFill(Paint.valueOf("White"));
        gc.fillText("B", 10, 20);
        gc.fillText("U", 70, 20);
        gc.fillText("Z", 10, 60);
        gc.fillText("Z", 70, 60);
        gc.fillText("W", 130, 100);
        gc.fillText("O", 190, 100);
        gc.fillText("R", 130, 140);
        gc.fillText("D", 190, 140);
    }

    public void initializeWindow() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        // SET THE WINDOW TITLE
        primaryStage.setTitle(applicationTitle);
        // add the toolbar to the constructed workspace
        appPane = new BorderPane();
        bp.setCenter(cv);
        appPane.setLeft(toolbarPane);
        appPane.setCenter(bp);
        appPane.setRight(closeButton);
        primaryScene = appWindowWidth < 1 || appWindowHeight < 1 ? new Scene(appPane)
                : new Scene(appPane,
                appWindowWidth,
                appWindowHeight);

        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resrouces folder does not exist.");
        try (InputStream appLogoStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(APP_LOGO)))) {
            primaryStage.getIcons().add(new Image(appLogoStream));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    public void initializeLevelWindow() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        // SET THE WINDOW TITLE
        primaryStage.setTitle(applicationTitle);
        // add the toolbar to the constructed workspace
        appPane = new BorderPane();
        appPane.setLeft(toolbarPane);
        appPane.setCenter(bp);
        appPane.setRight(closeButton);
        primaryScene = appWindowWidth < 1 || appWindowHeight < 1 ? new Scene(appPane)
                : new Scene(appPane,
                appWindowWidth,
                appWindowHeight);

        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resrouces folder does not exist.");
        try (InputStream appLogoStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(APP_LOGO)))) {
            primaryStage.getIcons().add(new Image(appLogoStream));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void initializegameWindow() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        // SET THE WINDOW TITLE
        primaryStage.setTitle(applicationTitle);
        // add the toolbar to the constructed workspace
        appPane = new BorderPane();
        appPane.setLeft(toolbarPane);
        appPane.setCenter(borderpane);
        appPane.setRight(closeButton);
        primaryScene = appWindowWidth < 1 || appWindowHeight < 1 ? new Scene(appPane)
                : new Scene(appPane,
                appWindowWidth,
                appWindowHeight);
        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resrouces folder does not exist.");
        try (InputStream appLogoStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(APP_LOGO)))) {
            primaryStage.getIcons().add(new Image(appLogoStream));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String filename = "C:\\Users\\MAHE\\Documents\\My Games\\BuzzWordGame\\BuzzWord\\resources\\words\\English.txt";
        Scanner sc = new Scanner(new File(filename));
        boolean f = false;
        l=0;
        String line= null;
        int i = 0;
        while(sc.hasNext()) {
                line = sc.nextLine();
                    if (k.toLowerCase().contains(line.toLowerCase())) {
                        right[i] = line;
                        f = true;
                        l += 1;
                        i++;
                    }
            };
            l=l*10;
            if(!f)
            {
                fileController.req();
            }
        System.out.println(l);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    public void initializegameWindowscore() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        // SET THE WINDOW TITLE
        primaryStage.setTitle(applicationTitle);
        // add the toolbar to the constructed workspace
        appPane = new BorderPane();
        appPane.setLeft(toolbarPane);
        appPane.setCenter(borderpane);
        appPane.setRight(closeButton);
        primaryScene = appWindowWidth < 1 || appWindowHeight < 1 ? new Scene(appPane)
                : new Scene(appPane,
                appWindowWidth,
                appWindowHeight);
        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resrouces folder does not exist.");
        try (InputStream appLogoStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(APP_LOGO)))) {
            primaryStage.getIcons().add(new Image(appLogoStream));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void initializegameWindow2() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        // SET THE WINDOW TITLE
        primaryStage.setTitle(applicationTitle);
        // add the toolbar to the constructed workspace
        appPane = new BorderPane();
        appPane.setLeft(toolbarPane);
        appPane.setCenter(borderpane);
        appPane.setRight(closeButton);
        primaryScene = appWindowWidth < 1 || appWindowHeight < 1 ? new Scene(appPane)
                : new Scene(appPane,
                appWindowWidth,
                appWindowHeight);
        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resrouces folder does not exist.");
        try (InputStream appLogoStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(APP_LOGO)))) {
            primaryStage.getIcons().add(new Image(appLogoStream));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void changinggui(AppTemplate appTemplate) throws IOException, InstantiationException {
        initializeToolbar2();
        scrollPane = new ScrollPane();
        guessed1 = "";
        guessed2 = "";
        guessed3 = "";
        guessed4 = "";
        guessed5 = "";
        score = 0;
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        initializeToolbarHandlers2(appTemplate);
        initializeWindow();
    }

    public void home(AppTemplate appTemplate) throws IOException, InstantiationException {
        initializeToolbar();
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        initializeToolbarHandlers(appTemplate);
        initializeWindow();
    }

    public void initializeToolbar2() throws IOException {
        toolbarPane = new VBox();
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        heading = new HBox();
        Label l = new Label("!! BUZZWORD !!");
        l.setFont(new Font("Arial", 25));
        heading.setPadding(new Insets(30));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        heading.setAlignment(Pos.BASELINE_CENTER);
        toolbarPane.setSpacing(30);
        bp = new BorderPane();
        bp.setTop(heading);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        createprofile = initializeButton(toolbarPane, name, CREATE_PROFILE_TOOLTIP.toString(), false);
        selectmode = initializeButton(toolbarPane, "Select Mode", CREATE_PROFILE_TOOLTIP.toString(), false);
        selectmode.setDisable(true);
        cb = new ChoiceBox();
        cb.getItems().addAll("English Dictionary", "Places", "Scenes", "Famous People");
        cb.setValue("English Dictionary");
        cb.setStyle("-fx-background-color: darkgray;-fx-text-base-color: darkred");
        toolbarPane.getChildren().addAll(cb);
        gotolevelselect = initializeButton(toolbarPane, "Go to level Selection", CREATE_PROFILE_TOOLTIP.toString(), false);
        Logout = initializeButton(toolbarPane, "Logout", CREATE_PROFILE_TOOLTIP.toString(), false);
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }

    public ChoiceBox getcb() { return cb; }

    public void Englishlevel(AppTemplate app) throws InstantiationException, IOException {
        Label w;
        w = lab();
        initializeToolbar3(w,app);
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        initializeToolbarHandlers3(app);
        initializeLevelWindow();
    }

    public void initializeToolbar3(Label l2,AppTemplate app) throws IOException {

        try {
            Method getFileControllerClassMethod = app.getClass().getMethod("getFileControllerClass");
            String fileControllerClassName = (String) getFileControllerClassMethod.invoke(app);
            Class<?> klass = Class.forName("controller." + fileControllerClassName);
            Constructor<?> constructor = klass.getConstructor(AppTemplate.class);
            fileController = (FileController) constructor.newInstance(app);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            exit(1);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        toolbarPane = new VBox();
        heading = new HBox();
        HBox h1;
        HBox h2;
        VBox v2;
        v1 = new VBox();
        v2 = new VBox();
        h1 = new HBox();
        h2 = new HBox();
        BorderPane bp4 = new BorderPane();
        BorderPane bp2 = new BorderPane();
        BorderPane bp3 = new BorderPane();
        heading.setPadding(new Insets(30));
        Label l = new Label("!! BUZZWORD !!");
        l.setFont(new Font("Arial", 25));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        heading.setAlignment(Pos.BASELINE_CENTER);
        v1.getChildren().addAll(l2);
        v1.setAlignment(Pos.BASELINE_CENTER);
        v2.setPadding(new Insets(50));
        int x = 1;
        for(int i =0;i<4;i++)
        {
            bt[i]= new Button(String.valueOf(x));
            bt[i].setShape(new Circle(10));
            bt[i].setDisable(true);
            x+=1;
        }
        h1.setSpacing(30);
        h1.setAlignment(Pos.BASELINE_CENTER);
        h1.getChildren().addAll(bt);
        int y = 5;
        for(int i =0;i<4;i++)
        {
            bt2[i]= new Button(String.valueOf(y));
            bt2[i].setShape(new Circle(10));
            bt2[i].setDisable(true);
            y+=1;
        }

        switch(val){
            case 1: bt[0].setDisable(false);
                break;
            case 2:
                bt[0].setDisable(false);
                bt[1].setDisable(false);
                break;
            case 3:
                bt[0].setDisable(false);
                bt[1].setDisable(false);
                bt[2].setDisable(false);
                break;
            case 4:
                bt[0].setDisable(false);
                bt[1].setDisable(false);
                bt[2].setDisable(false);
                bt[3].setDisable(false);
                break;
            case 5:
                for(int i =0;i<4;i++)
                {
                    bt[i].setDisable(false);
                }
                bt2[0].setDisable(false);
                break;
            case 6:
                for(int i =0;i<4;i++)
                {
                    bt[i].setDisable(false);
                }
                bt2[0].setDisable(false);
                bt2[1].setDisable(false);
                break;
            case 7:
                for(int i =0;i<4;i++)
                {
                    bt[i].setDisable(false);
                }
                bt2[0].setDisable(false);
                bt2[1].setDisable(false);
                bt2[2].setDisable(false);
                break;
            case 8:
                for(int i =0;i<4;i++)
                {
                    bt[i].setDisable(false);
                }
                bt2[0].setDisable(false);
                bt2[1].setDisable(false);
                bt2[2].setDisable(false);
                bt2[3].setDisable(false);
                break;
            default:
                bt[0].setDisable(false);
        }

        h2.setSpacing(30);
        h2.setPadding(new Insets(20));
        h2.setAlignment(Pos.BASELINE_CENTER);
        h2.getChildren().addAll(bt2);
        bp2.setTop(v1);
        bp2.setCenter(bp3);
        bp3.setTop(v2);
        bp3.setCenter(bp4);
        bp4.setTop(h1);
        bp4.setCenter(h2);
        toolbarPane.setSpacing(30);
        bp = new BorderPane();
        bp.setTop(heading);
        bp.setCenter(bp2);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        createprofile = initializeButton(toolbarPane, name, CREATE_PROFILE_TOOLTIP.toString(), false);
        Home = initializeButton(toolbarPane, "Home", CREATE_PROFILE_TOOLTIP.toString(), false);
        Logout = initializeButton(toolbarPane, "Logout", CREATE_PROFILE_TOOLTIP.toString(), false);
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }
    public void initializegameToolbarp() throws IOException {
        toolbarPane = new VBox();
        timeline.stop();
        VBox vel = new VBox();
        VBox v = new VBox();
        VBox vb = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(false);
        VBox content = new VBox();
        scrollPane.setContent(content);
        scrollPane.setPrefSize(120,120);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        hb1 = new HBox();
        Label w7 = new Label("TOTAL");w7.setTextFill(Paint.valueOf("white"));
        w8 = new Label(String.valueOf(score));w8.setTextFill(Paint.valueOf("white"));
        Label w9 = new Label("TARGET");w9.setTextFill(Paint.valueOf("white"));
        vb.getChildren().addAll(scrollPane);
        hb1.getChildren().addAll(w7,w8);
        hb1.setStyle("-fx-background-color: rgb(68,68,68)");
        target = new VBox();
        Label tg = new Label(String.valueOf(mit));
        tg.setTextFill(Paint.valueOf("white"));
        target.getChildren().addAll(w9,tg);
        target.setStyle("-fx-background-color: rgb(96,96,96)");
        toolbarPane.setSpacing(30);
        heading = new HBox();
        rightvbox = new VBox();
        time = new HBox();
        combo = new HBox();
        Label let = new Label();
        let.setTextFill(Paint.valueOf("white"));
        combo.getChildren().addAll(let);
        combo.setStyle("-fx-background-color: rgb(96,96,96)");
        heading.setPadding(new Insets(30));
        Label l = new Label("!! BUZZWORD !!");
        l.setFont(new Font("Arial", 25));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: gray");
        borderpane.setTop(heading);
        borderpane.setMargin(heading,new Insets(0,0,0,120));
        Label rt = new Label("TIME REMAINING: ");
        rt.setTextFill(Paint.valueOf("red"));
        time.setStyle("-fx-background-color: rgb(211,211,211);");
        v.getChildren().addAll(vb,hb1);
        rightvbox.getChildren().addAll(time,combo,v,target);
        rightvbox.setSpacing(30);
        rightvbox.setPadding(new Insets(20));
        borderpane.setRight(rightvbox);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        createprofile = initializeButton(toolbarPane, name, CREATE_PROFILE_TOOLTIP.toString(), false);
        createprofile.setDisable(true);
        Home = initializeButton(toolbarPane, "Home", CREATE_PROFILE_TOOLTIP.toString(), false);
        resume = initializeChildButton2(vel, RESUME_ICON.toString(), RESUME_TOOLTIP.toString(), false);
        borderpane.setBottom(vel);
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }

    public void initializegameToolbar() throws IOException {
        final Integer STARTTIME = 60;
        toolbarPane = new VBox();
        hk = new BorderPane();
        Label remains;
        VBox vel = new VBox();
        VBox v = new VBox();
        VBox vb = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(false);
        VBox content = new VBox();
        scrollPane.setContent(content);
        GridPane grid = new GridPane();
        int numRows = 4 ;
        int numColumns = 4 ;
        for (int row = 0 ; row < numRows ; row++ ){
            RowConstraints rc = new RowConstraints();
            grid.getRowConstraints().add(rc);
        }
        for (int col = 0 ; col < numColumns; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            grid.getColumnConstraints().add(cc);
        }

        for (int i = 0 ; i < 16 ; i++) {
            button[i] = createButton();
            button[i].setShape(new Circle(10));
            grid.add(button[i], i%4, i / 4);
        }
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(20);
        grid.setHgap(20);
        int x = 10;
        for (int i = 0; i < 4; i++)
        {
            Label label = new Label(word);
            content.setPrefHeight(content.getPrefHeight() + label.getPrefHeight());
            content.getChildren().add(label);
        }

        copygrid = grid;
        scrollPane.setPrefSize(120,120);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        hb1 = new HBox();
        Label w7 = new Label("TOTAL");w7.setTextFill(Paint.valueOf("white"));
        Label w8 = new Label(String.valueOf(score));w8.setTextFill(Paint.valueOf("white"));
        Label w9 = new Label("TARGET");w9.setTextFill(Paint.valueOf("white"));
        vb.getChildren().addAll(scrollPane);
        hb1.getChildren().addAll(w7,w8);
        hb1.setStyle("-fx-background-color: rgb(68,68,68)");
        target = new VBox();
        int p;
        switch(m){
            case 1:
                p = l/8;
                break;
            case 2:
                p = l/7;
                break;
            case 3:
                p = l/6;
                break;
            case 4:
                p = l/5;
                break;
            case 5:
                p = l/4;
                break;
            case 6:
                p = l/3;
                break;
            case 7:
                p = l/2;
                break;
            case 8:
                p = (int) (l/1.5);
                break;
            default:
                p = l;
                break;
        }
        Label tg = new Label(String.valueOf(p));
        mit = p;
        tg.setTextFill(Paint.valueOf("white"));
        target.getChildren().addAll(w9,tg);
        target.setStyle("-fx-background-color: rgb(96,96,96)");
        toolbarPane.setSpacing(30);
        heading = new HBox();
        rightvbox = new VBox();
        time = new HBox();
        combo = new HBox();
        Label let = new Label();
        let.setTextFill(Paint.valueOf("white"));
        combo.getChildren().addAll(let);
        combo.setStyle("-fx-background-color: rgb(96,96,96)");
        heading.setPadding(new Insets(30));
        Label l = new Label("!! BUZZWORD !!");
        l.setFont(new Font("Arial", 25));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: gray");
        borderpane.setTop(heading);
        borderpane.setMargin(heading,new Insets(0,0,0,120));
        remains = new Label();
            remains.setText(String.valueOf(STARTTIME));
            timeSeconds[0] = STARTTIME;
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),
                            new EventHandler() {
                                @Override
                                public void handle(Event event) {
                                    timeSeconds[0]--;
                                    remains.setText(String.valueOf(timeSeconds[0]));
                                    if (timeSeconds[0] <= 0) {
                                        timeline.stop();
                                        fileController.lost();
                                    }
                                }
                            }));
        timeline.stop();
            timeline.playFromStart();
        remains.setTextFill(Paint.valueOf("red"));
        Label rt = new Label("TIME REMAINING: ");
        rt.setTextFill(Paint.valueOf("red"));
        time.getChildren().addAll(rt,remains);
        time.setStyle("-fx-background-color: rgb(211,211,211);");
        v.getChildren().addAll(vb,hb1);
        borderpane.setCenter(grid);
        rightvbox.getChildren().addAll(time,combo,v,target);
        rightvbox.setSpacing(30);
        rightvbox.setPadding(new Insets(20));
        borderpane.setRight(rightvbox);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        createprofile = initializeButton(toolbarPane, name, CREATE_PROFILE_TOOLTIP.toString(), false);
        createprofile.setDisable(true);
        replay = initializeButton(toolbarPane, "Replay", CREATE_PROFILE_TOOLTIP.toString(), false);
        Home = initializeButton(toolbarPane, "Home", CREATE_PROFILE_TOOLTIP.toString(), false);
        resume = initializeChildButton2(vel, RESUME_ICON.toString(), RESUME_TOOLTIP.toString(), false);
        borderpane.setBottom(vel);
        hk = borderpane;
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }
    public void initializegameToolbartimer() throws IOException {
        toolbarPane = new VBox();
        hk = new BorderPane();
        Label remains;
        VBox vel = new VBox();
        VBox v = new VBox();
        VBox vb = new VBox();
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(false);
        VBox content = new VBox();
            label = new Label(guessed1);
            content.getChildren().add(label);
        Label label2 = new Label(guessed2);
        content.getChildren().add(label2);
        Label label3 = new Label(guessed3);
        content.getChildren().add(label3);
        Label label4 = new Label(guessed4);
        content.getChildren().add(label4);
        Label label5 = new Label(guessed5);
        content.getChildren().add(label5);

        scrollPane.setContent(content);
        scrollPane.setPrefSize(120,120);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        hb1 = new HBox();
        Label w7 = new Label("TOTAL");w7.setTextFill(Paint.valueOf("white"));
        Label w8 = new Label(String.valueOf(score));w8.setTextFill(Paint.valueOf("white"));
        Label w9 = new Label("TARGET");w9.setTextFill(Paint.valueOf("white"));
        vb.getChildren().addAll(scrollPane);
        hb1.getChildren().addAll(w7,w8);
        hb1.setStyle("-fx-background-color: rgb(68,68,68)");
        target = new VBox();
        int p;
        switch(m){
            case 1:
                p = l/8;
                break;
            case 2:
                p = l/7;
                break;
            case 3:
                p = l/6;
                break;
            case 4:
                p = l/5;
                break;
            case 5:
                p = l/4;
                break;
            case 6:
                p = l/3;
                break;
            case 7:
                p = l/2;
                break;
            case 8:
                p = (int) (l/1.5);
                break;
            default:
                p = l;
                break;
        }
        remains = new Label();
        int STARTTIME = timeSeconds[0];
        timeline.stop();
            remains.setText(String.valueOf(STARTTIME));
            timeSeconds[0] = STARTTIME;
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),
                            new EventHandler() {
                                @Override
                                public void handle(Event event) {
                                    timeSeconds[0]--;
                                    remains.setText(String.valueOf(timeSeconds[0]));
                                    if (timeSeconds[0] <= 0) {
                                        timeline.stop();
                                        fileController.lost();
                                    }
                                }
                            }));
            timeline.playFromStart();
        Label tg = new Label(String.valueOf(p));
        mit = p;
        tg.setTextFill(Paint.valueOf("white"));
        target.getChildren().addAll(w9,tg);
        target.setStyle("-fx-background-color: rgb(96,96,96)");
        toolbarPane.setSpacing(30);
        heading = new HBox();
        rightvbox = new VBox();
        time = new HBox();
        combo = new HBox();
        Label let = new Label(guessed);
        let.setTextFill(Paint.valueOf("white"));
        combo.getChildren().addAll(let);
        combo.setStyle("-fx-background-color: rgb(96,96,96)");
        heading.setPadding(new Insets(30));
        Label l = new Label("!! BUZZWORD !!");
        l.setFont(new Font("Arial", 25));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: gray");
        borderpane.setTop(heading);
        borderpane.setMargin(heading,new Insets(0,0,0,120));
        remains.setTextFill(Paint.valueOf("red"));
        Label rt = new Label("TIME REMAINING: ");
        rt.setTextFill(Paint.valueOf("red"));
        time.getChildren().addAll(rt,remains);
        time.setStyle("-fx-background-color: rgb(211,211,211);");
        v.getChildren().addAll(vb,hb1);
        borderpane.setCenter(copygrid);
        rightvbox.getChildren().addAll(time,combo,v,target);
        rightvbox.setSpacing(30);
        rightvbox.setPadding(new Insets(20));
        borderpane.setRight(rightvbox);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        createprofile = initializeButton(toolbarPane, name, CREATE_PROFILE_TOOLTIP.toString(), false);
        createprofile.setDisable(true);
        replay = initializeButton(toolbarPane, "Replay", CREATE_PROFILE_TOOLTIP.toString(), false);
        Home = initializeButton(toolbarPane, "Home", CREATE_PROFILE_TOOLTIP.toString(), false);
        resume = initializeChildButton2(vel, RESUME_ICON.toString(), RESUME_TOOLTIP.toString(), false);
        borderpane.setBottom(vel);
        hk = borderpane;
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }

    public void initializeresumeToolbar() throws IOException {
        toolbarPane = new VBox();
        hk = new BorderPane();
        Label remains;
        VBox vel = new VBox();
        VBox v = new VBox();
        VBox vb = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(false);
        VBox content = new VBox();
        scrollPane.setContent(content);
        int x = 10;
        for (int i = 0; i < 4; i++)
        {
            Label label = new Label("  word " +"          "+ x);
            content.setPrefHeight(content.getPrefHeight() + label.getPrefHeight());
            content.getChildren().add(label);
        }

        scrollPane.setPrefSize(120,120);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        hb1 = new HBox();
        Label w7 = new Label("TOTAL");w7.setTextFill(Paint.valueOf("white"));
        Label w8 = new Label("     40");w8.setTextFill(Paint.valueOf("white"));
        Label w9 = new Label("TARGET");w9.setTextFill(Paint.valueOf("white"));
        vb.getChildren().addAll(scrollPane);
        hb1.getChildren().addAll(w7,w8);
        hb1.setStyle("-fx-background-color: rgb(68,68,68)");
        target = new VBox();
        Label tg = new Label(String.valueOf(mit));
        tg.setTextFill(Paint.valueOf("white"));
        target.getChildren().addAll(w9,tg);
        target.setStyle("-fx-background-color: rgb(96,96,96)");
        toolbarPane.setSpacing(30);
        heading = new HBox();
        rightvbox = new VBox();
        time = new HBox();
        combo = new HBox();
        Label let = new Label("B U");
        let.setTextFill(Paint.valueOf("white"));
        combo.getChildren().addAll(let);
        combo.setStyle("-fx-background-color: rgb(96,96,96)");
        heading.setPadding(new Insets(30));
        Label l = new Label("!! BUZZWORD !!");
        l.setFont(new Font("Arial", 25));
        l.setTextFill(Color.web("#ffffff"));
        heading.getChildren().addAll(l);
        borderpane = new BorderPane();
        borderpane.setStyle("-fx-background-color: gray");
        borderpane.setTop(heading);
        borderpane.setMargin(heading,new Insets(0,0,0,120));
        remains = new Label();
        remains.setText(String.valueOf(timeSeconds[0]));
        Timeline tl;
        final int[] timeSeconds2 = new int[1];
        int s = timeSeconds[0];
        timeSeconds2[0] = s;
        tl = new Timeline();
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        timeSeconds2[0]--;
                        remains.setText(String.valueOf(timeSeconds2[0]));
                        if (timeSeconds2[0] <= 0) {
                            tl.stop();
                        }
                    }
                }));
        tl.playFromStart();
        remains.setTextFill(Paint.valueOf("red"));
        Label rt = new Label("TIME REMAINING: ");
        rt.setTextFill(Paint.valueOf("red"));
        time.getChildren().addAll(rt,remains);
        time.setStyle("-fx-background-color: rgb(211,211,211);");
        v.getChildren().addAll(vb,hb1);
        borderpane.setCenter(copygrid);
        rightvbox.getChildren().addAll(time,combo,v,target);
        rightvbox.setSpacing(30);
        rightvbox.setPadding(new Insets(20));
        borderpane.setRight(rightvbox);
        b = initializeButton(toolbarPane, "", "", false);
        b.setVisible(false);
        createprofile = initializeButton(toolbarPane, name, CREATE_PROFILE_TOOLTIP.toString(), false);
        Home = initializeButton(toolbarPane, "Home", CREATE_PROFILE_TOOLTIP.toString(), false);
        resume = initializeChildButton2(vel, RESUME_ICON.toString(), RESUME_TOOLTIP.toString(), false);
        borderpane.setBottom(vel);
        hk = borderpane;
        closeButton = initializeChildButton(toolbarPane, CLOSE_ICON.toString(), CLOSE_TOOLTIP.toString(), false);
    }

    public void Placeslevel(AppTemplate app) throws IOException, InstantiationException {
        Label w;
        w = lab2();
        initializeToolbar3(w,app);
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        initializeToolbarHandlers3(app);
        initializeLevelWindow();
    }

    private Button createButton2(){
        Button button = new Button(" ");
        button.setTextFill(Paint.valueOf("White"));
        button.setStyle("-fx-background-color: rgb(96,96,96)");
        return button ;
    }

    private Button createButton() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'A');
        Button button = new Button(String.valueOf(c));
        k += c;
        button.setTextFill(Paint.valueOf("White"));
        button.setStyle("-fx-background-color: rgb(96,96,96)");
        return button ;
    }

    public void Sceneslevel(AppTemplate app) throws IOException, InstantiationException {
        Label w;
        w = lab3();
        initializeToolbar3(w,app);
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        initializeToolbarHandlers3(app);
        initializeLevelWindow();
    }

    public void Famouslevel(AppTemplate app) throws IOException, InstantiationException {
        Label w;
        w = lab4();
        initializeToolbar3(w,app);
        toolbarPane.setStyle("-fx-font-size: 12pt; -fx-background-color: rgb(211,211,211); -fx-padding: .9em 0.416667em .9em 0.416667em");
        bp.setStyle("-fx-background-color: gray");
        initializeToolbarHandlers3(app);
        initializeLevelWindow();
    }

    public Button initializeButton(Pane toolbarPane,String name, String tooltip, boolean disabled)
    {
        PropertyManager propertyManager = PropertyManager.getManager();
        Button button = new Button(name);
        button.setTextFill(Paint.valueOf("white"));
        button.setStyle("-fx-base: #A8A8A8  ");
        toolbarPane.getChildren().add(button);
        Tooltip buttonTooltip = new Tooltip(propertyManager.getPropertyValue(tooltip));
        button.setTooltip(buttonTooltip);
        return button;
    }

    public Button initializeChildButton(Pane toolbarPane, String icon, String tooltip, boolean disabled) throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resources folder does not exist.");

        Button button = new Button();
        try (InputStream imgInputStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(icon)))) {
            Image buttonImage = new Image(imgInputStream);
            button.setDisable(disabled);
            button.setGraphic(new ImageView(buttonImage));
            Tooltip buttonTooltip = new Tooltip(propertyManager.getPropertyValue(tooltip));
            button.setTooltip(buttonTooltip);
            toolbarPane.getChildren().add(button);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            exit(1);
        }

        return button;
    }

    public Button initializeChildButton2(VBox vel, String icon, String tooltip, boolean disabled) throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();

        URL imgDirURL = AppTemplate.class.getClassLoader().getResource(APP_IMAGEDIR_PATH.getParameter());
        if (imgDirURL == null)
            throw new FileNotFoundException("Image resources folder does not exist.");

        Button button = new Button();
        try (InputStream imgInputStream = Files.newInputStream(Paths.get(imgDirURL.toURI()).resolve(propertyManager.getPropertyValue(icon)))) {
            Image buttonImage = new Image(imgInputStream);
            button.setDisable(disabled);
            button.setGraphic(new ImageView(buttonImage));
            Tooltip buttonTooltip = new Tooltip(propertyManager.getPropertyValue(tooltip));
            button.setTooltip(buttonTooltip);
            level = new Label();
            level.setText("Level number: "+String.valueOf(m));
            level.setStyle("-fx-text-fill: white");
            level.setFont(new Font("Arial", 20));
            vel.getChildren().addAll(level,button);
            vel.setMargin(button,new Insets(0,0,100,250));
            vel.setMargin(level,new Insets(0,0,20,200));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            exit(1);
        }

        return button;
    }
    @Override
    public void initStyle() {
        // currently, we do not provide any stylization at the framework-level
    }
}
