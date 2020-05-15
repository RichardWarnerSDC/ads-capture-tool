package guijavafx.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

import guijavafx.App;
import guijavafx.ZoomerPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.text.Text;
import offlineads.FileHandler;
import offlineads.ImageProcessor;
import offlineads.PDFHandler;

public class CutAdsController extends ImageExtractController {
	
	private int cutAdsSceneID;
	private Button btnCutSelectedButton = new Button("Cut Selected");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		txtTitleText.setText("Cut Ads");
		btnBackButton.setOnAction(e -> btnBackButton_Click());
		
		// right bar
		
		rightBorderPane = new BorderPane();
		
		btnCutSelectedButton.setMinSize(128, 64);
		btnCutSelectedButton.setOnAction(e -> btnCutSelected_Click());
		
		rightBorderPane.setBottom(btnCutSelectedButton);
		
		root.setRight(rightBorderPane);
	}
	
	public void onCreated(App app, int cutAdsSceneID) {
		setApp(app);
		setCutAdsScenesID(cutAdsSceneID);
		File[] imageFiles = app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles();
		createImagePreviews(imageFiles);
		setCenterImage(imageFiles[0]);
	}
		
	public void setCutAdsScenesID(int id) {
		this.cutAdsSceneID = id;
	}
	
	@FXML protected void btnBackButton_Click() {
		app.getPrimaryStage().setScene(app.getPublicationSelectScene());
	}
	
	public void btnCutSelected_Click() {
		zoomerPane.normaliseSelections();
	}
	
	public void setSelections(ArrayList<Rectangle> selections) {
		File srcFile = app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles()[currentlySelectedImgVw];
		BufferedImage bi = PDFHandler.imageFileToBufferedImage(srcFile);
		for (Rectangle selection: selections) {
			String[] outDirStrings = {app.getModel().getDirNewAdsImages().toString(), app.getModel().getFilesNewPubsImages()[cutAdsSceneID].listFiles()[currentlySelectedImgVw].getName()};
			File outFile = new File(FileHandler.makeDirFromStrings(outDirStrings) + UUID.randomUUID());
			ImageProcessor.writeSubImage(bi, selection.getBoundsInLocal(), "jpeg", outFile);
		}
	}
}
