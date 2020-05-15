package guijavafx.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import guijavafx.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class Controller implements Initializable {
	
	protected boolean isCreated = false;
	@FXML protected Text txtTitleText = new Text("");
	protected App app;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void onCreated() {
		
	}
	
	public boolean getIsCreated() {
		return isCreated;
	}
	
	public void setTitleText(String text) {
		txtTitleText.setText(text);
	}

	public void setApp(App app) {
		this.app = app;
	}
	
}
