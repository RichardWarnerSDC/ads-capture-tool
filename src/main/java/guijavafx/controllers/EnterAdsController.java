package guijavafx.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

import guijavafx.App;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import offlineads.FileHandler;
import offlineads.ImageProcessor;
import offlineads.PDFHandler;
import offlineads.TextProcessor;

public class EnterAdsController extends ImageExtractController {
	
	private Button btnCutSelectedButton = new Button("Cut Selected");
	
	TextField txtFldHeadline;
	TextArea txtAreaAdText;
	ComboBox<String> cbxOcrLanguage = new ComboBox<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);				
		txtTitleText.setText("Enter Ads");
		btnBackButton.setOnAction(e -> btnBackButton_Click());
		
		// right bar
		
		rightBorderPane = new BorderPane();
		
		GridPane rgp = new GridPane();
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHalignment(HPos.CENTER);
		
		Label lblHeadline = new Label("Headline");
		txtFldHeadline = new TextField();
		txtFldHeadline.setPromptText("Enter the headline");
		
		Label lblAdText = new Label("Ad Text");
		txtAreaAdText = new TextArea();
		txtAreaAdText.setPromptText("Enter all of the ad text");
		
		rgp.add(lblHeadline, 0, 0);
		rgp.add(txtFldHeadline, 0, 1);
		rgp.add(lblAdText, 0, 2);
		rgp.add(txtAreaAdText, 0, 3);
		
		rightBorderPane.setCenter(rgp);

		VBox rvb = new VBox(16);
		
		String[] ocrLanguages = TextProcessor.OCR_LANGUAGES;
		for (String language : ocrLanguages) {
			cbxOcrLanguage.getItems().add(language);
		}
		cbxOcrLanguage.getSelectionModel().select(0); //default to English
		rvb.getChildren().add(cbxOcrLanguage);
		
		Button btnCutSelectedAdsButton = new Button("Enter Current Ad");
		btnCutSelectedAdsButton.setMinSize(128, 64);
		btnCutSelectedAdsButton.setOnAction(e -> btnCutSelected_Click());
		rvb.getChildren().add(btnCutSelectedAdsButton);
		
		rightBorderPane.setBottom(rvb);
		
		root.setRight(rightBorderPane);
	}
	
	public void onCreated(App app) {
		setApp(app);
		File[] imageFiles = app.getModel().getDirNewAdsImages().listFiles();
		createImagePreviews(imageFiles);
		setCenterImage(imageFiles[0]);
	}
	
	@FXML protected void btnBackButton_Click() {
		app.getModel().wipeScratch();
		app.getPrimaryStage().setScene(app.getJobSelectScene());
	}
	
	public void btnCutSelected_Click() {
		zoomerPane.normaliseSelections();
		// do ocr on scratch files
		StringBuilder sb = new StringBuilder();
		for (File snippet : app.getModel().getDirScratch().listFiles()) {
			sb.append(TextProcessor.doTess4JOCR(snippet, cbxOcrLanguage.getSelectionModel().getSelectedItem()) + "\n");
		}
		txtAreaAdText.appendText(sb.toString());
	}
	
	public void setSelections(ArrayList<Rectangle> selections) {
		for (Rectangle selection: selections) {
			File imageFile = app.getModel().getFilesNewAdsImages()[currentlySelectedImgVw];
			System.out.println(imageFile.toString());
			BufferedImage bi = PDFHandler.imageFileToBufferedImage(imageFile);
			String[] outDirStrings = {app.getModel().getDirScratch().toString(), app.getModel().getFilesNewAdsImages()[currentlySelectedImgVw].getName()};
			File outFile = new File(FileHandler.makeDirFromStrings(outDirStrings) + UUID.randomUUID());
			ImageProcessor.writeSubImage(bi, selection.getBoundsInLocal(), "jpeg", outFile);
		}
	}
}
