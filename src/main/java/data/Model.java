package data;

import java.io.File;
import java.util.ArrayList;

import data.publication.Publication;
import javafx.scene.shape.Rectangle;
import offlineads.FileHandler;
import offlineads.PDFHandler;

public class Model {
	
	private File resources = new File("resources");
	private File dirNewPubs = new File(resources + "/NewPublications");
	private File[] filesNewPubs = dirNewPubs.listFiles();
	private File dirNewPubsImages = new File(resources + "/NewPublicationsImages");
	private File[] filesNewPubsImages = dirNewPubsImages.listFiles();
	private File dirNewAdsImages = new File(resources + "/NewAdsImages");
	private File[] filesNewAdsImages = dirNewAdsImages.listFiles();
	private File dirScratch = new File(resources + "/Scratch");
	private Publication[] pubsData;
	
//	private ArrayList<ArrayList<ArrayList<Rectangle>>> selections = new ArrayList<>();
	
	/**
	 * Test main method for quick model testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = new Model();
		model.initialiseResources();
		model.convertPdfs();
	}
	
	public Model() {
		initialiseResources();
		wipeScratch(); // in case application was force-quitted
		convertPdfs();
		populatePubsData();
	}
	
	public void wipeScratch() {
		for (File scratchFile : dirScratch.listFiles()) {
			scratchFile.delete();
		}
	}
	
	/**
	 * If folders don't exist, create them, e.g. first run
	 */
	public void initialiseResources() {
		if (!resources.exists()) {new File("resources").mkdir();}
		if (!dirNewPubs.exists()) {FileHandler.makeDirFromFile(dirNewPubs);}
		if (!dirNewPubsImages.exists()) {FileHandler.makeDirFromFile(dirNewPubsImages);}
		if (!dirNewAdsImages.exists()) {FileHandler.makeDirFromFile(dirNewAdsImages);}
		if (!dirScratch.exists()) {FileHandler.makeDirFromFile(dirScratch);}
	}
	
	/**
	 * convert new PDF files to JPEGs and store in NewPublicationsImages directory.
	 */
	public void convertPdfs() {
		if (filesNewPubs.length > 0) {
			for (File file : filesNewPubs) {
				PDFHandler.convertDocPagesToJPEG(file, dirNewPubsImages);
			}
		}
	}
	
	public void populatePubsData() {
		pubsData = new Publication[filesNewPubsImages.length];
		for (int i = 0; i < filesNewPubsImages.length - 1; i++) {
			pubsData[i] = new Publication(this, i);
		}
	}
	
	public File[] getFilesNewPubs() {
		return filesNewPubs;
	}
	
	public File[] getFilesNewPubsImages() {
		return filesNewPubsImages;
	}
	
	public File getDirNewAdsImages() {
		return dirNewAdsImages;
	}
	
	public File[] getFilesNewAdsImages() {
		return filesNewAdsImages;
	}
	
	public File getDirScratch() {
		return dirScratch;
	}
	
	public Publication[] getPubsData() {
		return pubsData;
	}
	
}
