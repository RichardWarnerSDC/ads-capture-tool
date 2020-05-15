package guijavafx.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import guijavafx.ZoomerPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ImageExtractController extends Controller {

	@FXML protected BorderPane root;
	protected BorderPane leftBorderPane;
	protected BorderPane rightBorderPane;
	@FXML protected Button btnBackButton = new Button("Back");
	protected ObservableList<ImageView> olPvwImgVws = FXCollections.observableArrayList();
	protected VBox vBoxImages;
	private ScrollPane spPvwScroller;
	protected int currentlySelectedImgVw;
	protected ZoomerPane zoomerPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// left bar
		
		leftBorderPane = new BorderPane();
		
		leftBorderPane.setTop(txtTitleText);
		
		spPvwScroller = new ScrollPane();
		spPvwScroller.setFitToHeight(true);
		spPvwScroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		spPvwScroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		vBoxImages = new VBox(16);
	    vBoxImages.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		vBoxImages.setAlignment(Pos.CENTER);
				
		btnBackButton.setMinSize(128, 64);
		leftBorderPane.setBottom(btnBackButton);
	}
	
	public void createImagePreviews(File[] imageFiles) {
		
		for (int i = 0; i <= imageFiles.length - 1; i++) {
			int imgVwImageNum = i;
			ImageView imgVwImage = new ImageView(new Image("file:" + imageFiles[i].toString(), 128, 128, true, false));
			imgVwImage.setOnMouseClicked(e -> imgVwImage_Click(e, imgVwImageNum, imageFiles[imgVwImageNum]));
			olPvwImgVws.add(imgVwImage);
			vBoxImages.getChildren().add(imgVwImage);
			imgVwImage = new ImageView();
		}
		spPvwScroller.setContent(vBoxImages);
		leftBorderPane.setCenter(spPvwScroller);
		root.setLeft(leftBorderPane);
	}
	
	public void imgVwImage_Click(MouseEvent e, int nodeNum, File imageFile) {
		currentlySelectedImgVw = nodeNum;
		setCenterImage(imageFile);
	}
	
	public void setCenterImage(File imageFile) {
		zoomerPane = new ZoomerPane(this, imageFile, app.getPrimaryScreenBounds());
		root.setCenter(zoomerPane);
	}
	
	public void setSelections(Controller controller, ArrayList<Rectangle> selections) {
    	if (controller.getClass().equals(CutAdsController.class)) {
    		CutAdsController cutAdsController = (CutAdsController) controller;
    		cutAdsController.setSelections(selections);
    	} else if (controller.getClass().equals(EnterAdsController.class)) {
    		EnterAdsController enterAdsController = (EnterAdsController) controller;
    		enterAdsController.setSelections(selections);
    	}	
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
}
