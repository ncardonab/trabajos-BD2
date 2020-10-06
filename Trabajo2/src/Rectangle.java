
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
		return "Rectangle\n{	x: " + this.x + "\n		y: " + this.y + "\n 	width: " + this.width + "\n 	height: " + this.height + "\n}";
	}
	
	public static boolean intersection(Rectangle r1, Rectangle r2) {
		
		// Encuentra el x y el ancho de la intersección
		int xmin = Math.min(r1.x, r2.x);
		int xmax = Math.max(r1.x, r2.x);
		int xbound = Math.min((r1.x + r1.width), (r2.x + r2.width));
		int xdiff = xmax - xmin;
		r1.width = xbound - xmin - xdiff;
		r1.x = xmax;
		
		// Encuentra el x y el alto de la intersección 		
		int ymin = Math.min(r1.y, r2.y);
		int ymax = Math.max(r1.y, r2.y);
		int ybound = Math.min((r1.y + r1.height), (r2.y + r2.height));
		int ydiff = ymax - ymin;
		r1.height = ybound - ymin - ydiff;
		r1.y = ymax;
		
		// Si el ancho o el alto son negativos se encuentra una intersección
		if (r1.width < 0 || r1.height < 0) {
			// No se encontró ninguna intersección
			return false;
		} else {
			// Se encontró una intersección
			return true;
		}
	}
}