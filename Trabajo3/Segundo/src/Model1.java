
public class Model1 extends Model {
	private int codigo;
	private String nombre;
	private int totalVentas;
	
	public Model1(int codigo, String nombre, int totalVentas) {
		super(nombre, totalVentas);
		this.codigo = codigo;
		this.nombre = nombre;
		this.totalVentas = totalVentas;
	}
	
	public String toString() {
		return "codigo: " + this.codigo + ", nombre: " + this.nombre + ", totalVentas: " + this.totalVentas;
	}
	
	public String[] getAttributesAsList() {
		String nombreTruncado = this.nombre.length() > 30 ? this.nombre.substring(0, Math.min(this.nombre.length(), 30))+"..." : this.nombre;
	
		return new String[] {Integer.toString(this.codigo), nombreTruncado, Integer.toString(this.totalVentas)};
	}
		
	public Integer getCodigo() { return this.codigo;}
	public String getNombre() { return this.nombre;}
	public Integer getTotalVentas() { return this.totalVentas;}
}
