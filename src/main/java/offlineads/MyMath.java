package offlineads;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class MyMath {
	
	public static void main(String[] args) {

	}
	
	public static Rectangle scaleRectangleCenterRespecitingC(Rectangle inRect, Point2D c, double oldScaleMultiplier, double newScaleMultiplier) {
		
		inRect.setWidth((inRect.getWidth() / (oldScaleMultiplier * 10)) * (newScaleMultiplier * 10));
		inRect.setHeight((inRect.getHeight() / (oldScaleMultiplier * 10)) * (newScaleMultiplier * 10));
		
		double rectMinDistX = Math.abs(c.getX() - inRect.getX());
		double unscaledMinDistX = rectMinDistX / (oldScaleMultiplier * 10);
		double rescaledMinDistX = unscaledMinDistX * (newScaleMultiplier * 10);
		
		double rectMinDistY = Math.abs(c.getY() - inRect.getY());
		double unscaledMinDistY = rectMinDistY / (oldScaleMultiplier * 10);
		double rescaledMinDistY = unscaledMinDistY * (newScaleMultiplier * 10);
		
		
		if (inRect.getX() < c.getX()) {
			inRect.setX(c.getX() - rescaledMinDistX);
		} else {
			inRect.setX(c.getX() + rescaledMinDistX);
		}
		
		if (inRect.getY() < c.getY()) {
			inRect.setY(c.getY() - rescaledMinDistY);
		} else {
			inRect.setY(c.getY() + rescaledMinDistY);
		}
		
		return inRect;
	}
	
	/**
	 * 
	 * @param point
	 * @param c The center pivot point with which to translate around
	 * @param oldScale
	 * @param newScale
	 * @return
	 */
	public static Point2D translatePointRespectingC(Point2D point, Point2D c, double oldScale, double newScale) {
		double[] xy = {point.getX(), point.getY()};
		double[] cxy = {c.getX(), c.getY()};
		double scaleMultipliers[] = {oldScale, newScale};
		
		for (double iPoint : xy) {
			if (iPoint < 0) {
				System.out.println("Input point must not have a dimension less than 0");
				throw new IllegalArgumentException();
			}
		}
		for (double cPoint : cxy) {
			if (cPoint <= 0) {
				System.out.println("Center point must be greater than 0");
				throw new IllegalArgumentException();
			}
		}
		
		for ( double scaleMultiplier : scaleMultipliers) {
			if (scaleMultiplier <= 0) {
				System.out.println("Scale multiplier must be greater than 0");
				throw new IllegalArgumentException();
			}
		}
		
		for (int i = 0; i < 2; i++) {
			
			double distanceDiff = Math.abs(cxy[i] - xy[i]);
			System.out.println(distanceDiff);
			double unscaled = distanceDiff / (oldScale * 10);
			System.out.println(unscaled);
			double rescaled = unscaled * (newScale * 10);
			System.out.println(rescaled);
			if (xy[i] > cxy[i]) {
				xy[i] = cxy[i] + rescaled;
			} else if (xy[i] < cxy[i]) {
				xy[i] = cxy[i] - rescaled;
			}
		}
		
		Point2D newPoint = new Point2D(xy[0], xy[1]);
		return newPoint;
		
	}
	
}
