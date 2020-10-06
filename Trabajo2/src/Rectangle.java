
import static java.lang.Math.*;

public class Rectangle {
	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String toString() {
		return "Rectangle{ x: " + this.x + " y: " + this.y + " width: " + this.width + " height: " + this.height + " }";
	}
	
	public static boolean intersection(Rectangle r1, Rectangle r2) {
		// Encuentra el x y el ancho de la intersección
		int xmin = Math.min(r1.x, r2.x);
		int xmax = Math.max(r1.x, r2.x);
		int xbound = Math.min((r1.x + r1.width), (r2.x + r2.width));
		int xdiff = xmax - xmin;
		int widthNewRect = xbound - xmin - xdiff;
		int xNewRect = xmax;
		
		// Encuentra el x y el alto de la intersección 		
		int ymin = Math.min(r1.y, r2.y);
		int ymax = Math.max(r1.y, r2.y);
		int ybound = Math.min((r1.y + r1.height), (r2.y + r2.height));
		int ydiff = ymax - ymin;
		int heightNewRect = ybound - ymin - ydiff;
		int yNewRect = ymax;
		
		// Si el ancho o el alto del nuevo rectangulo son negativos se encuentra una intersección
		if (widthNewRect < 0 || heightNewRect < 0) {
			// No se encontró ninguna intersección
			return false;
		} else {
			// Se encontró una intersección
			return true;
		}
	}
}