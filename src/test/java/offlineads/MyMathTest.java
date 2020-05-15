package offlineads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class MyMathTest {
    
    @Test
    public void centerPoint2DShouldBeGreaterThanZero() {
    	Point2D o = new Point2D(100,100);
    	Point2D oz = new Point2D(0,0);
    	assertThrows(IllegalArgumentException.class, () -> {
    		MyMath.translatePointRespectingC(o, oz, 1.0, 2.0);
    	});
    }
    
    @Test
    public void inputPoint2DShouldBeGreaterThanOrEqualToZero() {
    	Point2D o = new Point2D(100,100);
    	Point2D p = new Point2D(-1,-1);
    	assertThrows(IllegalArgumentException.class, () -> {
    		MyMath.translatePointRespectingC(p, o, 1.0, 2.0);
    	});
    }
    
    @Test
    public void scaleMultipliersShouldBeGreaterThanZero() {
    	Point2D o = new Point2D(100,100);
    	Point2D p = new Point2D(0,0);
    	assertThrows(IllegalArgumentException.class, () -> {
    		MyMath.translatePointRespectingC(p, o, 0, 0.1);
    	});
    	assertThrows(IllegalArgumentException.class, () -> {
    		MyMath.translatePointRespectingC(p, o, 0.1, 0);
    	});
    }
    
    @Test
    public void scalePoint2DShouldEqualPoint2DScaled() {
    	Point2D o = new Point2D(100,100);
    	Point2D a = new Point2D(80,80); // NW
    	Point2D b = new Point2D(60,60); // NW
    	Point2D c = new Point2D(120,80); // NE
    	Point2D d = new Point2D(140,60); // NE
    	Point2D e = new Point2D(120,120); // SE
    	Point2D f = new Point2D(140,140); // SE
    	Point2D g = new Point2D(80,120); //SW
    	Point2D h = new Point2D(60,140); //SW

    	assertEquals(b.toString(), MyMath.translatePointRespectingC(a, o, 0.4, 0.3).toString());
    	assertEquals(d.toString(), MyMath.translatePointRespectingC(c, o, 0.5, 0.2).toString());

    }
	
}
