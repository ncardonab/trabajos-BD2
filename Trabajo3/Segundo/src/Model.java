
public class Model {
	private String nombre;
	private int totalVentas;
	
	public Model(String nombre, int totalVentas) {
		super();
		this.nombre = nombre;
		this.totalVentas = totalVentas;
	}
	
	public String toString() {
		return "nombre: " + this.nombre + ", totalVentas: " + this.totalVentas;
	}
	
	public String[] getAttributesAsList() {
		String nombreTruncado = this.nombre.length() > 30 ? this.nombre.substring(0, Math.min(this.nombre.length(), 30))+"..." : this.nombre;
		return new String[] {nombreTruncado, Integer.toString(this.totalVentas)};
	}
	
	public String getNombre() { return this.nombre;}
	public Integer getTotalVentas() { return this.totalVentas;}
	public String getDescripcion() { return "";}
	public Integer getCodigo() { return 0;}
}
