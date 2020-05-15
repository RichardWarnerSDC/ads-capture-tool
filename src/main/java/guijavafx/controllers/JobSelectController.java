package guijavafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import guijavafx.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class JobSelectController extends Controller {
		
	@FXML protected void btnCutAdsButton_Click() {
		app.getPrimaryStage().setScene(app.getPublicationSelectScene());
	}
	
	@FXML protected void btnEnterAdsButton_Click() {
		try {
			app.createEnterAdsScene();
		} catch (IOException e) {
			e.printStackTrace();
		}
		app.getPrimaryStage().setScene(app.getEnterAdsScene());
	}
	
	@FXML protected void btnEditAdsButton_Click() {
		app.getPrimaryStage().setScene(app.getJobSelectScene());
	}
	
}
