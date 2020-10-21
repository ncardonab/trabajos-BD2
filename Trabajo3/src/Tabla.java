import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Tabla {
	
	public String nombre;
	public ArrayList<String> atrs;
	
	
	public Tabla(String nombre, String atrs) {
		this.nombre = nombre;
		
		atrs = atrs.replaceAll(" ", "").replaceAll("\\r", "");
		ArrayList<String> listAtributos = new ArrayList<String>(Arrays.asList(atrs.split("\n")));
		this.atrs = listAtributos;
	}

	public static String regla1(Tabla t1, Tabla t2, String S1, String S2) {
		S2 = S2.replaceAll(" ", "");
		S1 = S1.replaceAll(" ", "");
		ArrayList<String> s2 = new ArrayList<String>(Arrays.asList(S2.split("\n")));
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList(S1.split("\n")));		
		
		//Intersección T'2(S2)
		ArrayList<String> t2s2 = new ArrayList<String>();
		for(int i = 0; i < s2.size(); i++) {
			ArrayList<String> s2i = new ArrayList<String>(Arrays.asList(s2.get(i).split(",")));
			s2i.retainAll(t2.atrs);
			t2s2.addAll(s2i);
		}
		System.out.println("T'2(s2) = " + Arrays.toString(t2s2.toArray()));
		
		//Hallar T2'
		ArrayList<String> t2p = new ArrayList<String>();
		ArrayList<String> auxT2P = new ArrayList<String>();
		ArrayList<String> t2pp = new ArrayList<String>();
		for(int i = 0; i < s1.size(); i++) {
			ArrayList<String> s1i = new ArrayList<String>(Arrays.asList(s1.get(i).split(",")));
			ArrayList<String> auxS1i = new ArrayList<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t2s2);
			if(!s1i.equals(auxS1i)) {
				auxT2P.addAll(s1i);
			}
			//Condición para hallar T''2
			auxS1i.retainAll(t2s2);
			if(auxS1i.isEmpty()) {
				t2pp.addAll(s1);
				System.out.println("auxS1 Vacio");
			}
		}
		t2p.addAll(t2s2);
		t2p.addAll(auxT2P);
		System.out.println("s1i-t2' = " + Arrays.toString(auxT2P.toArray()));
		System.out.println("T'2 = " + Arrays.toString(t2p.toArray()));
		
		System.out.println("T''2 = " + Arrays.toString(t2pp.toArray()));
		
		ArrayList<String> t1i = new ArrayList<String>();
		t1i.addAll(t1.atrs);
		
		String strT1i = Arrays.toString(t1i.toArray()).replace("[", "").replace("]", "");
		String strT2p = Arrays.toString(t2p.toArray()).replace("[", "").replace("]", "");
		String Qp;
		if(t2pp.isEmpty()) {
			Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
					+ strT2p + "}}";
		} else {
			String strT2pp = Arrays.toString(t2pp.toArray()).replace("[", "").replace("]", "");
			Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
					+ strT2p + "}, T''2:{"+ strT2pp + "}}";
		}
		System.out.println(Qp);
		return Qp;
	}
	
	public static String regla4(Tabla t1, Tabla t2, Tabla t3, String S1, String S3) {
		S3 = S3.replaceAll(" ", "");
		S1 = S1.replaceAll(" ", "");
		ArrayList<String> s3 = new ArrayList<String>(Arrays.asList(S2.split("\n")));
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList(S1.split("\n")));		
		
		//Intersección T'2(S2)
		ArrayList<String> t2s2 = new ArrayList<String>();
		for(int i = 0; i < s3.size(); i++) {
			ArrayList<String> s2i = new ArrayList<String>(Arrays.asList(s2.get(i).split(",")));
			s2i.retainAll(t2.atrs);
			t2s2.addAll(s2i);
		}
		System.out.println("T'2(s2) = " + Arrays.toString(t2s2.toArray()));
		
		//Hallar T2'
		ArrayList<String> t2p = new ArrayList<String>();
		ArrayList<String> auxT2P = new ArrayList<String>();
		ArrayList<String> t2pp = new ArrayList<String>();
		for(int i = 0; i < s1.size(); i++) {
			ArrayList<String> s1i = new ArrayList<String>(Arrays.asList(s1.get(i).split(",")));
			ArrayList<String> auxS1i = new ArrayList<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t2s2);
			if(!s1i.equals(auxS1i)) {
				auxT2P.addAll(s1i);
			}
			//Condición para hallar T''2
			auxS1i.retainAll(t2s2);
			if(auxS1i.isEmpty()) {
				t2pp.addAll(s1);
				System.out.println("auxS1 Vacio");
			}
		}
		t2p.addAll(t2s2);
		t2p.addAll(auxT2P);
		System.out.println("s1i-t2' = " + Arrays.toString(auxT2P.toArray()));
		System.out.println("T'2 = " + Arrays.toString(t2p.toArray()));
		
		System.out.println("T''2 = " + Arrays.toString(t2pp.toArray()));
		
		ArrayList<String> t1i = new ArrayList<String>();
		t1i.addAll(t1.atrs);
		
		String strT1i = Arrays.toString(t1i.toArray()).replace("[", "").replace("]", "");
		String strT2p = Arrays.toString(t2p.toArray()).replace("[", "").replace("]", "");
		String Qp;
		if(t2pp.isEmpty()) {
			Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
					+ strT2p + "}}";
		} else {
			String strT2pp = Arrays.toString(t2pp.toArray()).replace("[", "").replace("]", "");
			Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
					+ strT2p + "}, T''2:{"+ strT2pp + "}}";
		}
		System.out.println(Qp);
		return Qp;
	}
}
