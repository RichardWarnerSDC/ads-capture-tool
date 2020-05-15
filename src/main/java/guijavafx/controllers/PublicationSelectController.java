package guijavafx.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import data.publication.Publication;
import data.publication.Publication.Location;
import guijavafx.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class PublicationSelectController extends Controller {
		
	FlowPane flow;
	private int currentlySelectedPublication = 0;
	@FXML private BorderPane root;
	@FXML private FlowPane fpPubInfo;
	@FXML Button btnBackButton;
	private ScrollPane spPubScroller;
	@FXML private HBox hBoxPubImages;
	private ObservableList<ImageView> olPubImages = FXCollections.observableArrayList();
	ColorAdjust grayscale = new ColorAdjust();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		grayscale.setSaturation(-1);
		
		spPubScroller = new ScrollPane();
		spPubScroller.setFitToHeight(true);
		spPubScroller.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		spPubScroller.setVbarPolicy(ScrollBarPolicy.NEVER);
	
	}
	
	public void createPublicationPreviews() {
		File[] newPubsImages = app.getModel().getFilesNewPubsImages();
		for (int i = 0; i <= newPubsImages.length - 1; i++) {
			int pubNum = i;
			File convertedPub = newPubsImages[i];
			ImageView imgVwPubImage = new ImageView(new Image("file:" + convertedPub.listFiles()[0], 480, 480, true, false));
			imgVwPubImage.setEffect(grayscale);
			imgVwPubImage.setOnMouseClicked(e -> imgVwPubImage_Clicked(e, pubNum));
			olPubImages.add(imgVwPubImage);
			hBoxPubImages.getChildren().add(imgVwPubImage);
		}

		spPubScroller.setContent(hBoxPubImages);
		root.setCenter(spPubScroller);
	}
	
	public void imgVwPubImage_Clicked(MouseEvent e, int i) {
		if (e.getClickCount() == 2) {
			// double clicked
			// open?
//			publishSetInfo();
			app.getPrimaryStage().setScene(app.getCutAdsScenes()[i]);
			e.consume();
		} else {
			// single click
			olPubImages.get(currentlySelectedPublication).setEffect(grayscale);
			olPubImages.get(i).setEffect(null);
			currentlySelectedPublication = i;
		}
	}
	
	public ObservableList<ImageView> getOlPubImages() {
		return olPubImages;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	public ScrollPane getSPPubScroller() {
		return spPubScroller;
	}
	
	@FXML protected void btnBackButton_Click() {
		app.getPrimaryStage().setScene(app.getJobSelectScene());
	}
}
