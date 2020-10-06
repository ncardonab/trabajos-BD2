
public class XMLClass {
	
	public static String parseToXML(String str) {
		String xml = "<locales>";
		 
		String[] locales = str.split("\n");
		for (String l:locales) {
			xml += "<rectangulo>";
			String[] coordenadas = l.split(",");
			xml += "<a>" + coordenadas[0] + "</a>"+
				 "<b>" + coordenadas[1] + "</b>"+
				 "<c>" + coordenadas[2] + "</c>"+
				 "<d>" + coordenadas[3] + "</d>";
			xml += "</rectangulo>";
		}
		xml += "</locales>";
		
		return xml;
	}
	
	public static String parseMatrixToXML(int[][] matrix) {
		
		String xml = "<locales>";
		
		for (int i = 0; i < matrix.length; i++) {
			
			xml += "<rectangulo>";
			xml += "<a>" + matrix[i][0] + "</a>"+
				 "<b>" + matrix[i][1] + "</b>"+
				 "<c>" + matrix[i][2] + "</c>"+
				 "<d>" + matrix[i][3] + "</d>";
			xml += "</rectangulo>";
			
		}
		xml += "</locales>";
		
		return xml;
	}
}
