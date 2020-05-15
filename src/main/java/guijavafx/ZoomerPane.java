package guijavafx;

import java.io.File;
import java.util.ArrayList;

import guijavafx.controllers.Controller;
import guijavafx.controllers.ImageExtractController;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import offlineads.MyMath;

public class ZoomerPane extends ScrollPane {
	
	ImageExtractController controller;
	int currentRectSelect;
    double imgHeight;
    double imgWidth;
    boolean isDragging = false;
    boolean keyCtrlIsPressed = false;
    double xOffsetFromSP;
    double yOffsetFromSP;
    ArrayList<Point2D> pntSelectOrigins = new ArrayList<>();
    Point2D pntImgVwOrigin;
    Point2D pntSelectOrigin;
    Rectangle rectSelect;
    Point2D rectSelectCenterPoint;
    Point2D rootCenterPoint;
	double rootHeight;
	double rootWidth;
    ArrayList<Rectangle> selections = new ArrayList<>();
    ArrayList<Double> selectionsScales = new ArrayList<>();

    int zoomLvl = 10;
    double zoomMin = 0.2;
    double zoomMax = 8;
    double zoomMultiplier;
    
	Pane pane;
	BorderPane paneImgPane;
    
    /**
     * TODO
     * Set ScrollPane (this)|VBox to be 2x page width&Height
     * Set imagePreview to be 1/4 page Width|Height and center in this|VBox
     * Set image not to zoom on scroll without modifier e.g. ctrl+scroll https://stackoverflow.com/questions/27461643/javafx-disable-scrolling-by-mousewheel-in-scrollpane
     * 
     * @param cutAdsScenesID
     * @param pageImage
     * @param primaryScreenBounds
     */
    public ZoomerPane(ImageExtractController controller, File imageFile , Rectangle2D primaryScreenBounds) {
    	this.controller = controller;
    	
    	// The content pane will hold the Image View and user generated Rectangle rectSelect
    	pane = new Pane();
    	
    	// Hosts the Image View
    	paneImgPane = new BorderPane();
    	
    	// Prepare provided image and create a new Image View from it.
    	Image pageImage = new Image("file:" + imageFile.toString());
    	imgWidth = pageImage.getWidth();
    	imgHeight = pageImage.getHeight();
        ImageView imgView = new ImageView(pageImage);
        
        // set the Image View to minimum zoom size // CHANGE TO JUST BELOW VIEWPORT FIT WITH WHITESPACE
        zoomMultiplier = zoomMin;
        imgView.setFitWidth(imgWidth * zoomMultiplier);
        imgView.setFitHeight(imgHeight * zoomMultiplier);
        
        pntImgVwOrigin = new Point2D((rootWidth - imgView.getFitWidth()) / 2, (rootHeight - imgView.getFitHeight()) /2);
        
        // calculate max width and height of this ScrollPane 
        rootWidth = imgWidth * zoomMax;
        rootHeight = imgHeight * zoomMax;
        rootCenterPoint = new Point2D(rootWidth / 2, rootHeight / 2);
        
        // calculate distance between this ScrollPane border and Image View
        xOffsetFromSP = rootWidth - imgView.getFitWidth() / 2;
        yOffsetFromSP = rootHeight - imgView.getFitHeight() / 2;
        
        // set this.scrollpane to center
        this.setHvalue(0.5);
        this.setVvalue(0.5);
        
        // mouse handler
        paneImgPane.setOnMousePressed(e-> {
        	selectionsScales.add(zoomMultiplier);
        	rectSelect = new Rectangle();
            rectSelect.setStroke(Color.BLUE);
            rectSelect.setFill(new Color(0, 0, 1, 0.5));
        	pntSelectOrigin = new Point2D(e.getX(), e.getY());
        	pntSelectOrigins.add(pntSelectOrigin);
        	rectSelect.setX(pntSelectOrigin.getX());
        	rectSelect.setY(pntSelectOrigin.getY());
        	selections.add(rectSelect);
        	pane.getChildren().add(rectSelect);
        });
        paneImgPane.setOnMouseReleased(e-> {
            System.out.printf("X: %.2f, Y: %.2f, Width: %.2f, Height: %.2f%n", 
                    rectSelect.getX(), rectSelect.getY(), rectSelect.getWidth(), rectSelect.getHeight());
        });
        paneImgPane.setOnMouseDragged(e-> {
            rectSelect.setWidth(Math.abs(e.getX() - pntSelectOrigin.getX()));
            rectSelect.setHeight(Math.abs(e.getY() - pntSelectOrigin.getY()));
        	if (e.getX() < pntSelectOrigin.getX()) {
        		rectSelect.setX(e.getX());
        	}
        	if (e.getY() < pntSelectOrigin.getY()) {
        		rectSelect.setY(e.getY());
        	}
            rectSelectCenterPoint = new Point2D(e.getX() + (rectSelect.getWidth() / 2), e.getY() + (rectSelect.getHeight() / 2));
        });
        
        paneImgPane.setOnScroll(e-> {
        	if (keyCtrlIsPressed) {
        		System.out.println("ctrl pressed");
        		e.consume();
        		
        		double oldScaleMultiplier = zoomMultiplier;
        		
        		// set new scaleMultiplier
        		onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
                imgView.setFitWidth(imgWidth * zoomMultiplier);
                imgView.setFitHeight(imgHeight * zoomMultiplier);
                
                for (int i = 0; i <= selections.size() - 1; i++) {
                	Rectangle selection = selections.get(i);
	                
                	selection = MyMath.scaleRectangleCenterRespecitingC(selection, rootCenterPoint, oldScaleMultiplier, zoomMultiplier);
	
	                System.out.printf("X: %.2f, Y: %.2f, Width: %.2f, Height: %.2f%n", 
	                        selection.getX(), rectSelect.getY(), rectSelect.getWidth(), rectSelect.getHeight());
	                
                }
        	}
        });
        
        this.setOnKeyPressed(e -> {
        	if (e.getCode() == KeyCode.CONTROL) {
        	keyCtrlIsPressed = true;
        }
        });
        this.setOnKeyReleased(e -> {
        	if (e.getCode() == KeyCode.CONTROL) {
        	keyCtrlIsPressed = false;
        }
        });
    	
    	paneImgPane.setMinWidth(rootWidth);
    	paneImgPane.setMinHeight(rootHeight);
    	paneImgPane.setCenter(imgView);
    	paneImgPane.setAlignment(imgView, Pos.CENTER);
    	paneImgPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, new Insets(0, 0, 0, 0)))); 
    	pane.getChildren().add(paneImgPane);
        this.setContent(pane);
        
    }
    
    public void onScroll(double mouseDelta, Point2D mousePoint) {
    	if (mouseDelta > 0 && zoomLvl < 15) {
    		zoomLvl += 1;
    	} else if (mouseDelta < 0 && zoomLvl > 3) {
    		zoomLvl -= 1;
    	}
		zoomMultiplier = zoomLvl / 10.0;
    	System.out.println(zoomMultiplier);
    }
    
	public void normaliseSelections() {
		double rootPaddingX = (rootWidth - imgWidth) / 2;
		double rootPaddingY = (rootHeight - imgHeight) / 2;
		
		ArrayList<Rectangle> normalisedSelections = new ArrayList<>();
		for (int i = 0; i <= selections.size() - 1; i++) {
			Rectangle normalisedSelection = new Rectangle();
			normalisedSelection.setX(selections.get(i).getX());
			normalisedSelection.setY(selections.get(i).getY());
			normalisedSelection.setWidth(selections.get(i).getWidth());
			normalisedSelection.setHeight(selections.get(i).getHeight());

        	normalisedSelection = MyMath.scaleRectangleCenterRespecitingC(normalisedSelection, rootCenterPoint, zoomMultiplier, 1.0);

        	normalisedSelection.setX(normalisedSelection.getX() - rootPaddingX);
        	normalisedSelection.setY(normalisedSelection.getY() - rootPaddingY);
        	normalisedSelections.add(normalisedSelection);
        	selections.get(i).setStroke(Color.MAGENTA);
        	selections.get(i).setFill(Color.WHITE);
		}
    	controller.setSelections(controller, normalisedSelections);
	}
	
}    