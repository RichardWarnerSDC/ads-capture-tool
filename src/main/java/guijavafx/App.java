package guijavafx;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import data.Model;
import guijavafx.controllers.CutAdsController;
import guijavafx.controllers.EnterAdsController;
import guijavafx.controllers.JobSelectController;
import guijavafx.controllers.PublicationSelectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

	private Model model = new Model();
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	private String strTitle = "Digital and Printed Ads Capture Tool";
	private Stage primaryStage;
	private Scene jobSelectScene;
	private Scene PublicationSelectScene;
	private Scene[] cutAdsScenes;
	private Scene enterAdsScene;
	private JobSelectController jobSelectController;
	private PublicationSelectController PublicationSelectController;
	private CutAdsController[] cutAdsControllers;
	private EnterAdsController enterAdsController;
	private File dirFxmlFilesDirectory = new File("/guijavafx/fxml");
	double primaryScreenWidth = primaryScreenBounds.getWidth();
	double primaryScreenHeight = primaryScreenBounds.getHeight();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {	
			this.primaryStage = primaryStage;
			
			createJobSelectScene();
			
			createCutAdsScenes();
			
			createPublicationSelectScene();
						
			primaryStage.setTitle(strTitle);
			primaryStage.setWidth(primaryScreenWidth);
			primaryStage.setHeight(primaryScreenHeight);
			primaryStage.setMaximized(true);
			primaryStage.setOnCloseRequest(e -> btnClose_Click(e));
			primaryStage.setScene(jobSelectScene);
			primaryStage.show();
	}
	
	public void createJobSelectScene() throws IOException {		
		FXMLLoader jobSelectFXMLLoader = new FXMLLoader(getClass().getResource(dirFxmlFilesDirectory + "/job_select_pane.fxml"));
		Parent jobSelectPane = jobSelectFXMLLoader.load();
		this.jobSelectController = (JobSelectController) jobSelectFXMLLoader.getController();		
		
		jobSelectController.setTitleText(strTitle);
		jobSelectController.setApp(this);
		
		jobSelectScene = new Scene(jobSelectPane, primaryScreenWidth, primaryScreenHeight);
	}
	
	public void createPublicationSelectScene() throws IOException {
		FXMLLoader publicationSelectFXMLLoader = new FXMLLoader(getClass().getResource(dirFxmlFilesDirectory + "/publication_select_pane.fxml"));
		Parent publicationSelectPane = publicationSelectFXMLLoader.load();
		this.PublicationSelectController = (PublicationSelectController) publicationSelectFXMLLoader.getController();
		
		PublicationSelectController.setApp(this);
		PublicationSelectController.getSPPubScroller().setMinWidth(primaryScreenWidth);
//		PublicationSelectController.populatePubInfo();
		PublicationSelectController.createPublicationPreviews();
		
		PublicationSelectScene = new Scene(publicationSelectPane, primaryScreenWidth, primaryScreenHeight);
	}
	
	public void createCutAdsScenes() throws IOException {
		cutAdsScenes = new Scene[model.getFilesNewPubs().length];
		cutAdsControllers = new CutAdsController[model.getFilesNewPubs().length];
		for (int i = 0; i <= cutAdsScenes.length - 1; i++) {
			if (cutAdsScenes[i] == null) {
				FXMLLoader cutAdsFXMLLoader = new FXMLLoader(getClass().getResource(dirFxmlFilesDirectory + "/cut_ads_pane.fxml"));
				Parent cutAdsPane = cutAdsFXMLLoader.load();
				this.cutAdsControllers[i] = (CutAdsController) cutAdsFXMLLoader.getController();
				
				cutAdsControllers[i].onCreated(this, i);
				
				cutAdsScenes[i] = new Scene(cutAdsPane, primaryScreenWidth, primaryScreenHeight);
			}
		}
	}
	
	public void createEnterAdsScene() throws IOException {
		FXMLLoader enterAdsFXMLLoader = new FXMLLoader(getClass().getResource(dirFxmlFilesDirectory + "/enter_ads_pane.fxml"));
		Parent enterAdsPane = enterAdsFXMLLoader.load();
		this.enterAdsController = (EnterAdsController) enterAdsFXMLLoader.getController();
		
		enterAdsController.onCreated(this);
		
		enterAdsScene = new Scene(enterAdsPane, primaryScreenWidth, primaryScreenHeight);
	}

	/**
	 * Confirms whether the user wishes to quit and safely exits JavaFX framework
	 * ending the execution of the Application's launch method.
	 */
	public void btnClose_Click(WindowEvent e) {
		Optional<ButtonType> quit;
		Alert confirmExit = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to exit?",
				ButtonType.YES, ButtonType.NO);
		quit = (Optional<ButtonType>) confirmExit.showAndWait(); // false IDE error
		if (quit != null && quit.get().getText().equals("Yes")) {
			System.out.println(quit.toString());
			// add any files, stream and etc to be closed and free resources
			primaryStage.close();
		} else {
			e.consume();
		}
	}
	
	public Model getModel() {
		return model;
	}
	
	public Rectangle2D getPrimaryScreenBounds() {
		return primaryScreenBounds;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Scene getJobSelectScene() {
		return jobSelectScene;
	}
	
	public JobSelectController getJobSelectController() {
		return jobSelectController;
	}

	public Scene getPublicationSelectScene() {
		return PublicationSelectScene;
	}
	
	public PublicationSelectController getPublicationSelectController() {
		return PublicationSelectController;
	}
	
	public Scene[] getCutAdsScenes() {
		return cutAdsScenes;
	}
	
	public CutAdsController[] getCutAdsControllers() {
		return cutAdsControllers;
	}
	
	public Scene getEnterAdsScene() {
		return enterAdsScene;
	}
	
	public EnterAdsController getEnterAdsController() {
		return enterAdsController;
	}
	
}
