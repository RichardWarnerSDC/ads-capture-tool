package offlineads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.geometry.Bounds;

public class ImageProcessor {

	public static void writeSubImage(BufferedImage bi, Bounds bounds, String imageType, File outFile) {
		try {
			int x = (int) bounds.getMinX();
			int y = (int) bounds.getMinY();
			int w = (int) bounds.getWidth();
			int h = (int) bounds.getHeight();
			ImageIO.write(bi.getSubimage(x, y, w, h), imageType, new File(outFile.toString() + ".jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
