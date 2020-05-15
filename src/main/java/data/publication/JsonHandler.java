package data.publication;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import data.Model;
import offlineads.FileHandler;

/**
 * OfflineAdsJsonHandler provides methods for loading, saving and handling Offline Ads Data Json objects
 * @author richard
 */
public class JsonHandler {
	
	public static void main(String[] args) {
		JsonHandler jsonHandler = new JsonHandler();
		Publication[] pubsData = new Publication[1];
		Model model = new Model();
		int fileNum = 0;
		Publication pub = new Publication(model, fileNum);
		pubsData[0] = pub;
		PublicationsJson json = jsonHandler.new PublicationsJson(pubsData);
		jsonHandler.jsonString = jsonHandler.convertToJson(json);
		jsonHandler.saveJsonToFile("PubsJson");
	}
	
	private String jsonString = "";
	
	public JsonHandler() {
		
	}
	
	/**
	 * loadJson method reads in the Offline Ads Json object from project root
	 * @param jsonFile The .json object to be loaded
	 */
	public void loadJson(File jsonFile) {
		System.out.println("Loading json from file: " + jsonFile.getName());
		String jsonString = "";
		try {
	    	StringBuffer jsonStringBuffer = new StringBuffer();
	    	BufferedReader br = new BufferedReader(new FileReader(jsonFile));
	    	String line = br.readLine();
	    	while (line != null) {
	    		jsonStringBuffer.append(line);
	    		line = br.readLine();
	    	}
	    	this.jsonString = jsonStringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts Java Object to JSON object.
	 */
	public String convertToJson(Object obj) {
		 return new Gson().toJson(obj);
	}
	
	/**
	 * Write the current jsonString to a file
	 * @param fileID
	 */
	public void saveJsonToFile(String filename) {
		File outputDirectory = new File("resources/exportedJson");
		if (!outputDirectory.exists()) {
			FileHandler.makeDirFromFile(outputDirectory);
		}
		FileHandler.saveTextToFile(jsonString, outputDirectory, filename);
	}
	
	private class PublicationsJson {
		
		private Publication[] pubsData;
		
		public PublicationsJson(Publication[] pubsData) {
			this.pubsData = pubsData;
		}
		
	}
}
