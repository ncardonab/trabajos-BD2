
public class Model2 extends Model{
	private String nombre;
	private String descripcion;
	private int totalVentas;
	
	public Model2(String nombre, String descripcion, int totalVentas) {
		super(nombre, totalVentas);
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.totalVentas = totalVentas;
	}
	
	public String toString() {
		return "nombre: " + this.nombre + ", descripcion: " + this.descripcion + ", totalVentas: " + this.totalVentas;
	}
	
	public String[] getAttributesAsList() {
		String nombreTruncado = this.nombre.length() > 6 ? this.nombre.substring(0, Math.min(this.nombre.length(), 6))+"..." : this.nombre;
		String descripcionTruncada = this.descripcion.length() > 30 ? this.descripcion.substring(0, Math.min(this.descripcion.length(), 30))+"..." : this.descripcion;
		
		return new String[] {nombreTruncado, descripcionTruncada, Integer.toString(this.totalVentas)};
	}

	public String getNombre() { return this.nombre;}
	public String getDescripcion() { return this.descripcion;}
	public Integer getTotalVentas() { return this.totalVentas;}
}
