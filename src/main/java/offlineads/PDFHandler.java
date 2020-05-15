package offlineads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class PDFHandler {
	
	private static PDDocument pdDoc;
	private static PDPage pdPage;
	private static PDFRenderer pdfRenderer;
	private static BufferedImage currBuffImage;
	
	private static final int KINDAHALF1080_DPI = 60;
	private static final int KINDA1080_DPI = 120;
	private static final float METRO_DPI = 250.339738f; // Give or take
		
	public static void convertDocPagesToJPEG(File pdfFile, File processedPubsDir) {
		String outputDirString = processedPubsDir + "/" + pdfFile.getName();
		// check no project folder exists in output directory for current pdf
		if (!new File(outputDirString).exists()) {
			try {
				openPDF(pdfFile);
				createPdfRenderer();
				
				FileHandler.makeDirFromFile(new File(outputDirString));
				int pageNum = 0;
				// iterate over pages converting each page to jpeg
				for (PDPage page : pdDoc.getPages()) {
					String outputFileString = "/Page" + pageNum + ".jpeg";
					renderPage(pdfRenderer, pageNum, METRO_DPI);
					ImageIOUtil.writeImage(currBuffImage, outputDirString + outputFileString, (int) METRO_DPI);
					pageNum++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed converting document to JPEG: " + pdfFile.toString());
			} finally {
				closePDF();
			}
		}
	}
	
	public static void writeFXImageToFile(Image image, File file) {
		BufferedImage outBI = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(outBI, "jpeg", file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static BufferedImage imageFileToBufferedImage(File imgFile) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(imgFile.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}
	
	/**
	 * Gets the image equivalent to the first page of each pdf from the pdf's generated
	 * image folder.
	 * @param The root directory where the pdf files are located.
	 * @return images The images of each PDF's front page.
	 */
	public static ObservableList<Image> getFrontPageImages(File[] pdfFilesRoot) {
		ObservableList<Image> images = FXCollections.observableArrayList();
		for (File file : pdfFilesRoot) {
			if (file.isDirectory()) {
				System.out.println("Imaging");
				String strFilename = file.listFiles()[0].toString();
				Image image = new Image("file:" + strFilename);
				images.add(image);
			}
		}
		return images;
	}
	
	// static non-user methods
	
	/**
	 * Ready pdf file.
	 * @param pdfFile
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void openPDF(File pdfFile) throws IOException {
		// check pdfFile is not a directory and is valid pdf file
		if (!pdfFile.isDirectory() && FileHandler.hasValidExtension(pdfFile, "pdf")) {
			RandomAccessFile raFile = new RandomAccessFile(pdfFile, "rw");
			FileChannel fc = raFile.getChannel();
			try (fc; raFile; FileLock fileLock = fc.tryLock()) {
				if (fileLock != null) {
					InputStream in = Channels.newInputStream(raFile.getChannel());
					pdDoc = PDDocument.load(in);
				}
				fileLock.release();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.print("Unable to open pdf file, does it exist?");
			} finally {
				fc.close();
				raFile.close();
			}
		}
	}
	
	private static void closePDF() {
		if (pdDoc != null) {
			try {
				pdDoc.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Failed to close pdf file, perhaps it is already closed.");
			}
		}
	}
	
	private static void loadPDFPage(int pageNum) {
		pdPage = pdDoc.getPage(pageNum);
	}
	
	private static void createPdfRenderer() {
		pdfRenderer = new PDFRenderer(pdDoc);
	}
	
	/**
	 * Metro dpi 250.339738
	 * @param page
	 * @param dpi
	 */
	private static void renderPage(PDFRenderer pdfRenderer, int pageNum, float dpi) {
		try {
			currBuffImage = pdfRenderer.renderImageWithDPI(pageNum, dpi);
			System.out.println("Rendered image of" + currBuffImage.getWidth() + "x" +
					+ currBuffImage.getHeight());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dan, https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
	 * @return
	 */
	private static Image convertImageForFX() {
	    WritableImage wr = null;
	    if (currBuffImage != null) {
	        wr = new WritableImage(currBuffImage.getWidth(), currBuffImage.getHeight());
	        PixelWriter px = wr.getPixelWriter();
	        for (int x = 0; x < currBuffImage.getWidth(); x++) {
	            for (int y = 0; y < currBuffImage.getHeight(); y++) {
	                px.setArgb(x, y, currBuffImage.getRGB(x, y));
	            }
	        }
	    }
	    
	    return wr;
	}

	public void printDocInformation(PDDocument document) {
		System.out.println("The selected PDF document contains "
				+ document.getPages().getCount() + "pages.");
	}
	
}