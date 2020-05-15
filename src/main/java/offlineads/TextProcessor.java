package offlineads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/*
 * TextProcessor processes the text of an image and compares it against previously submitted ads taken from the database.
 * First OCR is performed on the image and the results are compared against the OCR results of the previously submitted ads.
 * Then the OCR results from the image are compared against the human entered version of the previous ads
 * Each comparison returns the number of matches, number of non-matches and the percentage of accuracy
 */
public class TextProcessor {
	
	public static final String[] OCR_LANGUAGES = {"eng", "deu"};
	
	public static String doTess4JOCR(File imageFile, String language) {
		ITesseract instance = new Tesseract();
		instance.setLanguage(language);
		instance.setDatapath("resources/tessdata");
		
		System.out.println("**************************************************");
		System.out.println("Doing OCR on " + imageFile.getName() + "\n");
		String rawResult = "";
		try {
			// assign raw results to an output string
			rawResult = instance.doOCR(imageFile);
			
			// print resulting OCR for current image to console
//			System.out.println(rawResult);
		} catch (TesseractException e) {
//			System.err.println(e.getMessage());
		}
//		TextProcessor.saveOCRResultToFile(rawResult, fileName, directory);
		return rawResult;
	}
	
	/**
	 * Credit to Scott YSN for method
	 * https://stackoverflow.com/questions/15356405/how-to-run-a-command-at-terminal-from-java-program
	 * @param imageFile
	 * @param folder
	 */
	public static void doTesseractOCR(File imageFile, String fileName, String directory) {
		System.out.println("TextProcessor: Doing OCR on " + imageFile.getPath());
        String command = "tesseract " + imageFile.getPath() + " " + directory + "/" + fileName + " -l eng";

        try {
	        Process proc = Runtime.getRuntime().exec(command);
	
	        // Read the output
	
	        BufferedReader reader =  
	              new BufferedReader(new InputStreamReader(proc.getInputStream()));
	
	        String line = "";
	        while((line = reader.readLine()) != null) {
	            System.out.print(line + "\n");
	        }
			proc.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static void saveOCRResultToFile(String rawResult, String fileName, String folder) {
		
		// Save rawResult string to text file
		PrintWriter out;
		try {
			out = new PrintWriter(folder + "/" + fileName);
			out.println(rawResult);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(folder + "/" + outString);
	}
	
	public static void compareOCRResults(String a, String b) {
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(a.split(" |\n|\r")));
		ArrayList<String> bList = new ArrayList<String>(Arrays.asList(b.split(" |\n|\r")));
		System.out.println("Textporcessor: compareOCRResults: prePrune: aList.size(): " + aList.size() + " bList.size(): " + bList.size());

		for (int i = 0; i < bList.size(); i++) {
			for (int j = 0; j < aList.size(); j++) {
				if (aList.get(j).equals(bList.get(i))) {
					bList.remove(i);
					aList.remove(j);
				}
			}
		}
		
		System.out.println("Textporcessor: compareOCRResults: postPrune: aList.size(): " + aList.size() + " bList.size(): " + bList.size());
		System.out.println();
	}
		
}