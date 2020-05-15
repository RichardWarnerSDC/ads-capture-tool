package offlineads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class Model {
	
	private File resources = new File("res");
	private File dirNewPubs = new File(resources + "/NewPublications");
	private File[] filesNewPubs = dirNewPubs.listFiles();
	private File dirNewPubsImages = new File(resources + "/NewPublicationsImages");
	private File[] filesNewPubsImages = dirNewPubsImages.listFiles();
	private File dirNewAdsImages = new File(resources + "/NewAdsImages");
	private File[] filesNewAdsImages = dirNewAdsImages.listFiles();
	
	/**
	 * Test main method for quick model testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Model model = new Model();
		model.initialiseResources();
		model.preparePdfs();
	}
	
	public Model() {

	}
	
	/**
	 * If folders don't exist, create them, e.g. first run
	 */
	public void initialiseResources() {
		if (!resources.exists()) {new File("res").mkdir();}
		if (!dirNewPubs.exists()) {FileHandler.makeDirFromFile(dirNewPubs);}
		if (!dirNewPubsImages.exists()) {FileHandler.makeDirFromFile(dirNewPubsImages);}
		if (!dirNewAdsImages.exists()) {FileHandler.makeDirFromFile(dirNewAdsImages);}
	}
	
	/**
	 * convert new PDF files to JPEGs and store in ProcessedPublications directory.
	 */
	public synchronized void preparePdfs() {
		for (File file : filesNewPubs) {
			PDFHandler.convertDocPagesToJPEG(file, dirNewPubsImages);
		}
	}
	
	public File[] getFilesNewPubs() {
		return filesNewPubs;
	}
	
	public File[] getFilesNewPubsImages() {
		return filesNewPubsImages;
	}
	
	public File[] getFilesNewAdsImages() {
		return filesNewAdsImages;
	}
	
}
