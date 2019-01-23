package gui;

import apptemplate.AppTemplate;
import components.AppWorkspaceComponent;
import controller.BuzzWordController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import propertymanager.PropertyManager;
import ui.AppGUI;

import java.io.IOException;

import static buzzword.BuzzWordProperties.*;

public class Workspace extends AppWorkspaceComponent {

    AppTemplate app; // the actual application
    AppGUI      gui; // the GUI inside which the application sits

    Label             guiHeadingLabel;   // workspace (GUI) heading label
    HBox              headPane;          // conatainer to display the heading
    HBox              bodyPane;          // container for the main game displays
    ToolBar           footToolbar;       // toolbar for game buttons
    BorderPane        figurePane;        // container to display the namesake graphic of the (potentially) hanging person
    VBox              gameTextsPane;     // container to display the text-related parts of the game
    BuzzWordController controller;

    public Workspace(AppTemplate initApp) throws IOException {
        app = initApp;
        gui = app.getGUI();
        controller = (BuzzWordController) gui.getFileController();    //new HangmanController(app, startGame); <-- THIS WAS A MAJOR BUG!??
        layoutGUI();     // initialize all the workspace (GUI) components including the containers and their layout
        setupHandlers(); // ... and set up event handling
    }

    private void layoutGUI() {
        PropertyManager propertyManager = PropertyManager.getManager();
        guiHeadingLabel = new Label(propertyManager.getPropertyValue(WORKSPACE_HEADING_LABEL));

        headPane = new HBox();
        headPane.getChildren().add(guiHeadingLabel);
        headPane.setAlignment(Pos.BASELINE_LEFT);

        figurePane = new BorderPane();
        gameTextsPane = new VBox();


        bodyPane = new HBox();
        bodyPane.getChildren().addAll( gameTextsPane, figurePane);

        HBox blankBoxLeft  = new HBox();
        HBox blankBoxRight = new HBox();
        HBox.setHgrow(blankBoxLeft, Priority.ALWAYS);
        HBox.setHgrow(blankBoxRight, Priority.ALWAYS);
        footToolbar = new ToolBar(blankBoxLeft, blankBoxRight);

        workspace = new VBox();
        workspace.getChildren().addAll(headPane, bodyPane, footToolbar);
    }

    private void setupHandlers() {

    }

    @Override
    public void initStyle() {
        PropertyManager propertyManager = PropertyManager.getManager();
        gui.getAppPane().setId(propertyManager.getPropertyValue(ROOT_BORDERPANE_ID));
        gui.getToolbarPane().getStyleClass().setAll(propertyManager.getPropertyValue(SEGMENTED_BUTTON_BAR));
        gui.getToolbarPane().setId(propertyManager.getPropertyValue(TOP_TOOLBAR_ID));
        workspace.getStyleClass().add(CLASS_BORDERED_PANE);
        guiHeadingLabel.getStyleClass().setAll(propertyManager.getPropertyValue(HEADING_LABEL));
    }

    /** This function reloads the entire workspace */
    @Override
    public void reloadWorkspace() {
        /* does nothing; use reinitialize() instead */
    }

    public void gamePlayScreen() throws IOException, InstantiationException {
        gui.initializegameToolbar();
        gui.initializegameToolbarHandlers(app);
        gui.initializegameWindow();
    }

    public VBox getGameTextsPane() {
        return gameTextsPane;
    }

    public BorderPane bp() {return figurePane;}

    public void reinitialize() {
        figurePane = new BorderPane();
        gameTextsPane = new VBox();
        bodyPane.getChildren().setAll(gameTextsPane, figurePane);
    }
}
