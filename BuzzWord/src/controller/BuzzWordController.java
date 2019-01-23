package controller;

import apptemplate.AppTemplate;
import data.GameData;
import gui.Workspace;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import propertymanager.PropertyManager;
import ui.YesNoCancelDialogSingleton;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static settings.AppPropertyType.*;

public class BuzzWordController implements FileController{

    private AppTemplate appTemplate;
    private Button GameButton;
    public String user;
    public String Pass;
    public GameData gamedata;
    public boolean b;
    public int val;

    public BuzzWordController(AppTemplate appTemplate)
    {
        this.appTemplate = appTemplate;
    }

    public void disableclose()
    {
        GameButton = appTemplate.getGUI().getCloseButton();
        GameButton.setDisable(true);
    }

    public void enableclose()
    {
        GameButton = appTemplate.getGUI().getCloseButton();
        GameButton.setDisable(false);
    }

    public void start()
    {
        System.out.print("Start");
    }

    @Override
    public void handleNewRequest() {

    }

    @Override
    public void handleSaveRequest() throws IOException {

    }

    @Override
    public void handleLogin() throws IOException {
        login();
        disableclose();
    }

    @Override
    public void handleLogout() {
        try {
            appTemplate.getGUI().home(appTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleCreateProfile()
    {
        createprofile();
    }

    public void login()
    {
        gamedata = (GameData) appTemplate.getDataComponent();
        Stage Stage2 = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40, 0, 0, 10));
        gridPane.setHgap(5); gridPane.setVgap(5);
        Scene scene = new Scene(gridPane, 300, 150);
        Label lbUser = new Label("PROFILE NAME");
        TextField tfUser = new TextField();
        tfUser.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        lbUser.setTextFill(Paint.valueOf("white"));
        Label lbPass = new Label("PROFILE PASSWORD");
        PasswordField tfPass = new PasswordField();
        lbPass.setTextFill(Paint.valueOf("white"));
        tfPass.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        tfUser.setStyle("-fx-text-fill: white");
        tfPass.setStyle("-fx-text-fill: white");

        gridPane.setStyle("-fx-background-color : black");
        gridPane.add(lbUser, 0, 0);
        gridPane.add(tfUser, 1, 0);
        gridPane.add(lbPass, 0, 1);
        gridPane.add(tfPass, 1, 1);
        Stage2.setTitle("Login");
        Stage2.setScene(scene);
        Stage2.show();
        tfPass.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    user = tfUser.getText();
                    gamedata.setLoguser(user);
                    Pass = tfPass.getText();
                    gamedata.setLogpass(Pass);
                    Path p = Paths.get("C:\\Users\\MAHE\\Documents\\My Games\\BuzzWordGame\\BuzzWord\\saved\\users.txt");
                    try {
                        prolog(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage2.hide();
                    enableclose();
                    if(b == true) {
                        try {
                            appTemplate.getGUI().setName(user);
                            appTemplate.getGUI().setPass(Pass);
                            appTemplate.getGUI().changinggui(appTemplate);
                            val = appTemplate.getFileComponent().getval();
                            setval(val);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                    else Stage2.hide();
                }
                if (ke.getCode().equals(KeyCode.ESCAPE))
                {
                    Stage2.hide();
                    enableclose();
                }
            }
        });
        tfUser.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ESCAPE))
                {
                    Stage2.hide();
                    enableclose();
                }
            }
        });
    }

    public void setval(int val)
    {
        this.val = val;
        appTemplate.getGUI().val=this.val;
    }

    public void createprofile()
    {
        gamedata = (GameData) appTemplate.getDataComponent();
        Stage Stage2 = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40, 0, 0, 10));
        gridPane.setHgap(5); gridPane.setVgap(5);
        Scene scene = new Scene(gridPane, 300, 150);
        Label lbUser = new Label("PROFILE NAME");
        TextField tfUser = new TextField();
        tfUser.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        lbUser.setTextFill(Paint.valueOf("white"));
        Label lbPass = new Label("PROFILE PASSWORD");
        TextField tfPass = new TextField();
        lbPass.setTextFill(Paint.valueOf("white"));
        tfPass.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        tfUser.setStyle("-fx-text-fill: white");
        tfPass.setStyle("-fx-text-fill: white");

        gridPane.setStyle("-fx-background-color : black");
        gridPane.add(lbUser, 0, 0);
        gridPane.add(tfUser, 1, 0);
        gridPane.add(lbPass, 0, 1);
        gridPane.add(tfPass, 1, 1);
        Stage2.setTitle("Create New Profile");
        Stage2.setScene(scene);
        Stage2.show();
        tfPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    user = tfUser.getText();
                    gamedata.setUser(user);
                    Pass = tfPass.getText();
                    gamedata.setPass(Pass);
                    appTemplate.getGUI().setName(user);
                    Path p = Paths.get("C:\\Users\\MAHE\\Documents\\My Games\\BuzzWordGame\\BuzzWord\\saved\\users.txt");
                    try {
                        newProfile(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        appTemplate.getGUI().changinggui(appTemplate);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                    Stage2.hide();
                }
            }
        });
    }

    @Override
    public void handleLevelSelect(){
        Workspace gameWorkspace = (Workspace) appTemplate.getWorkspaceComponent();
        try {
            gameWorkspace.gamePlayScreen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleSelectMode() {
        ChoiceBox cb = appTemplate.getGUI().getcb();
        String s = String.valueOf(cb.getValue());
        if(s=="English Dictionary")
        {
            try {
                appTemplate.getGUI().Englishlevel(appTemplate);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(s=="Places")
        {
            try {
                appTemplate.getGUI().Placeslevel(appTemplate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        else if(s=="Scenes")
        {
            try {
                appTemplate.getGUI().Sceneslevel(appTemplate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        else if(s=="Famous People")
        {
            try {
                appTemplate.getGUI().Famouslevel(appTemplate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleHelpRequest() {
        Stage Stage2 = new Stage();
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 300, 150);
        ScrollPane sp = new ScrollPane();
        VBox vb = new VBox();
        for(int i=0;i<15;i++)
        {
            Label l = new Label("Information related to the game");
            vb.getChildren().add(l);
        }
        sp.setContent(vb);
        sp.setPrefSize(300,150);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        gridPane.add(sp, 0,0);
        Stage2.setTitle("Help Screen");
        Stage2.setScene(scene);
        Stage2.show();
    }

    @Override
    public void handleHintRequest() {
        String u = appTemplate.getGUI().name;
        String v = appTemplate.getGUI().Pass;
        Stage Stage2 = new Stage();
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 220, 100);
        Label label = new Label("Your username is: " + u);
        Label label2 = new Label("Your Password is: " + v);
        Label label3 = new Label("You have unlocked: " + appTemplate.getGUI().val + " levels");
        gridPane.add(label,0,0);
        gridPane.add(label2,0,1);
        gridPane.add(label3,0,2);
        Stage2.setTitle("Your Profile");
        Stage2.setScene(scene);
        Stage2.show();
    }

    private void prolog(Path source) throws IOException {
        b = appTemplate.getFileComponent().loadData(appTemplate.getDataComponent(), source);
    }


    private void newProfile(Path target) throws IOException {
        appTemplate.getFileComponent().saveData(appTemplate.getDataComponent(), target);
    }

    @Override
    public void handleHomeRequest() {
        try {
            appTemplate.getGUI().timeline.stop();
            appTemplate.getGUI().changinggui(appTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleExitRequest() {
        try {
            promptToClose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void handleExitRequest2() {
        try {
            promptToClose2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void lost()
    {
            Stage Stage2 = new Stage();
            GridPane gridPane = new GridPane();
            Scene scene = new Scene(gridPane, 180, 100);
            Button b1 = new Button("YES");
            Button b2 = new Button("NO");
            Label l = new Label("Do you wish to retry?");
            gridPane.add(l,0,0);
            gridPane.add(b1,0,1);
            gridPane.add(b2,1,1);
            b1.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        req();
                        Stage2.hide();
                    }
                });
            b2.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        handleSelectMode();
                        Stage2.hide();
                    }
                });
            Stage2.setTitle("You Lost");
            Stage2.setScene(scene);
            Stage2.show();
    }

    @Override
    public void handleEnd()
    {
        appTemplate.getGUI().timeline.stop();
        int x;
        try {
            appTemplate.getGUI().val += 1;
            Path p =  Paths.get("C:\\Users\\MAHE\\Documents\\My Games\\BuzzWordGame\\BuzzWord\\saved\\users.txt");
            try {
                x = appTemplate.getGUI().val;
                appTemplate.getFileComponent().savelevel(appTemplate.getDataComponent(), p,x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            promptToEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean promptToEnd() throws IOException{
        PropertyManager propertyManager = PropertyManager.getManager();
        YesNoCancelDialogSingleton yesNoCancelDialog = YesNoCancelDialogSingleton.getSingleton();

        yesNoCancelDialog.show(propertyManager.getPropertyValue(Level_Completed),
                propertyManager.getPropertyValue(Level_MESSAGE));

        if (yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.NO)){
            System.exit(0);
        }
        else if(yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.YES))
        {
            handleSelectMode();
        }

        return yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.NO);
    }

    private boolean promptToClose() throws IOException {
        PropertyManager propertyManager = PropertyManager.getManager();
        YesNoCancelDialogSingleton yesNoCancelDialog = YesNoCancelDialogSingleton.getSingleton();

        yesNoCancelDialog.show(propertyManager.getPropertyValue(CLOSE_TITLE),
                propertyManager.getPropertyValue(CLOSE_MESSAGE));

        if (yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.YES))
            System.exit(0);

        return yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.NO);
    }

    private boolean promptToClose2() throws IOException {
        handlePauseRequest();
        PropertyManager propertyManager = PropertyManager.getManager();
        YesNoCancelDialogSingleton yesNoCancelDialog = YesNoCancelDialogSingleton.getSingleton();

        yesNoCancelDialog.show(propertyManager.getPropertyValue(CLOSE_TITLE),
                propertyManager.getPropertyValue(CLOSE_MESSAGE));

        if (yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.YES))
            System.exit(0);

        else if(yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.NO))
        {
            handleResumeRequest();
        }
        return yesNoCancelDialog.getSelection().equals(YesNoCancelDialogSingleton.NO);
    }

    @Override
    public void handlePauseRequest()
    {
        try {
            appTemplate.getGUI().initializegameToolbarp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            appTemplate.getGUI().initializegameToolbarHandlersscore(appTemplate);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try {
            appTemplate.getGUI().initializegameWindow2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void handlescorereq()
    {
        try {
            appTemplate.getGUI().initializegameToolbartimer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            appTemplate.getGUI().initializegameToolbarHandlersscore(appTemplate);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try {
            appTemplate.getGUI().initializegameWindowscore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResumeRequest()
    {
        try {
            appTemplate.getGUI().initializegameToolbartimer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            appTemplate.getGUI().initializegameToolbarHandlersscore(appTemplate);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try {
            appTemplate.getGUI().initializegameWindowscore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void req()
    {
        for(int i=0;i<100;i++)
        {
            appTemplate.getGUI().right[i]= null;
        }
        Workspace gameWorkspace = (Workspace) appTemplate.getWorkspaceComponent();
        try {
            appTemplate.getGUI().timeline.stop();
            gameWorkspace.gamePlayScreen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
