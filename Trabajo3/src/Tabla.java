import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Tabla {
	
	public String nombre;
	public HashSet<String> atrs;
	
	
	public Tabla(String nombre, String atrs) {
		this.nombre = nombre;
		
		atrs = atrs.replaceAll(" ", "").replaceAll("\\r", "");
		HashSet<String> listAtributos = new HashSet<String>(Arrays.asList(atrs.split("\n")));
		this.atrs = listAtributos;
	}

	public static String regla1(Tabla t1, Tabla t2, String S1, String S2) {
		S2 = S2.replaceAll(" ", "");
		S1 = S1.replaceAll(" ", "");
		ArrayList<String> s2 = new ArrayList<String>(Arrays.asList(S2.split("\n")));
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList(S1.split("\n")));		
		HashSet<String> pKey = new HashSet<String>(t1.atrs);
		pKey.retainAll(t2.atrs);
		
		//Intersección T'2(S2)
		ArrayList<String> t2s2 = new ArrayList<String>();
		for(int i = 0; i < s2.size(); i++) {
			HashSet<String> s2i = new HashSet<String>(Arrays.asList(s2.get(i).split(",")));
			s2i.retainAll(t2.atrs);
			t2s2.addAll(s2i);
		}
		System.out.println("T'2(s2) = " + Arrays.toString(t2s2.toArray()));
		
		//Hallar T2'
		HashSet<String> t2p = new HashSet<String>();
		HashSet<String> auxT2P = new HashSet<String>();
		HashSet<String> t2pp = new HashSet<String>();
		for(int i = 0; i < s1.size(); i++) {
			HashSet<String> s1i = new HashSet<String>(Arrays.asList(s1.get(i).split(",")));
			HashSet<String> auxS1i = new HashSet<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t2s2);
			if(!s1i.equals(auxS1i)) {
				auxT2P.addAll(s1i);
			}
			//Condición para hallar T''2
			auxS1i.retainAll(t2s2);
			if(auxS1i.isEmpty()) {
				t2pp.addAll(s1i);
				System.out.println("auxS1 Vacio");
			}
		}
		t2p.addAll(t2s2);
		t2p.addAll(auxT2P);
		t2p.removeAll(pKey);
		t2pp.removeAll(pKey);
		System.out.println("s1i-t2' = " + Arrays.toString(auxT2P.toArray()));
		System.out.println("T'2 = " + Arrays.toString(t2p.toArray()));
		
		System.out.println("T''2 = " + Arrays.toString(t2pp.toArray()));
		
		HashSet<String> t1i = new HashSet<String>();
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
	
	public static String regla4(Tabla t1, Tabla t2, Tabla t3, String S1, String S3){
		S3 = S3.replaceAll(" ", "");
		S1 = S1.replaceAll(" ", "");
		ArrayList<String> s3 = new ArrayList<String>(Arrays.asList(S3.split("\n")));
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList(S1.split("\n")));
		ArrayList<String> s1t2 = new ArrayList<String>();
		ArrayList<String> s1t3 = new ArrayList<String>();
		HashSet<String> pKey = new HashSet<String>(t1.atrs);
		pKey.retainAll(t2.atrs);
		
		//Clasificar S1
		for(int i = 0; i < s1.size(); i++) {
			HashSet<String> s1i = new HashSet<String>(Arrays.asList(s1.get(i).split(",")));
			if(t2.atrs.containsAll(s1i)) {
				s1t2.add(s1.get(i));
			}else if(t3.atrs.containsAll(s1i)) {
				s1t3.add(s1.get(i));
			};
		}
		
		//Hallar T'2
		//Intersección T'2(S3)
		ArrayList<String> t2s3 = new ArrayList<String>();
		for(int i = 0; i < s3.size(); i++) {
			HashSet<String> s3i = new HashSet<String>(Arrays.asList(s3.get(i).split(",")));
			s3i.retainAll(t2.atrs);
			t2s3.addAll(s3i);
		}
		System.out.println("T'2(s3) = " + Arrays.toString(t2s3.toArray()));
		
		//Hallar T'2 y T''2
		HashSet<String> t2p = new HashSet<String>();
		HashSet<String> auxT2P = new HashSet<String>();
		HashSet<String> t2pp = new HashSet<String>();
		for(int i = 0; i < s1t2.size(); i++) {
			HashSet<String> s1i = new HashSet<String>(Arrays.asList(s1t2.get(i).split(",")));
			HashSet<String> auxS1i = new HashSet<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t2s3);
			if(!s1i.equals(auxS1i)) {
				auxT2P.addAll(s1i);
			}
			//Condición para hallar T''2
			auxS1i.retainAll(t2s3);
			if(auxS1i.isEmpty()) {
				t2pp.addAll(s1i);
				System.out.println("auxS1T2 Vacio");
			}
		}
		t2p.addAll(t2s3);
		t2p.addAll(auxT2P);
		t2p.removeAll(pKey);
		t2pp.removeAll(pKey);
		System.out.println("s1i-t2' = " + Arrays.toString(auxT2P.toArray()));
		System.out.println("T'2 = " + Arrays.toString(t2p.toArray()));
		System.out.println("T''2 = " + Arrays.toString(t2pp.toArray()));
		
		//Hallar T'3
		//Intersección T'3(S3)
		HashSet<String> t3s3 = new HashSet<String>();
		for(int i = 0; i < s3.size(); i++) {
			HashSet<String> s3i = new HashSet<String>(Arrays.asList(s3.get(i).split(",")));
			s3i.retainAll(t3.atrs);
			t3s3.addAll(s3i);
		}
		System.out.println("T'3(s3) = " + Arrays.toString(t3s3.toArray()));
		
		//Hallar T'3 y T''3
		HashSet<String> t3p = new HashSet<String>();
		HashSet<String> auxT3P = new HashSet<String>();
		HashSet<String> t3pp = new HashSet<String>();
		for(int i = 0; i < s1t3.size(); i++) {
			ArrayList<String> s1i = new ArrayList<String>(Arrays.asList(s1t3.get(i).split(",")));
			System.out.println("S1i = " + Arrays.toString(s1i.toArray()));
			ArrayList<String> auxS1i = new ArrayList<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t3s3);
			if(!s1i.equals(auxS1i)) {
				auxT3P.addAll(s1i);
			}
			//Condición para hallar T''3
			System.out.println("auxS1i = " + Arrays.toString(auxS1i.toArray()));
			System.out.println("t3s3 = " + Arrays.toString(t3s3.toArray()));
			auxS1i.retainAll(t3s3);
			System.out.println("auxS1i" + Arrays.toString(auxS1i.toArray()));
			if(auxS1i.isEmpty()) {
				t3pp.addAll(s1i);
				System.out.println("auxS1T3 Vacio");
			}
		}
		t3p.addAll(t3s3);
		t3p.addAll(auxT3P);
		t3p.removeAll(pKey);
		t3pp.removeAll(pKey);
		System.out.println("s1i-t3' = " + Arrays.toString(auxT3P.toArray()));
		System.out.println("T'3 = " + Arrays.toString(t3p.toArray()));
		System.out.println("T''3 = " + Arrays.toString(t3pp.toArray()));
		
		ArrayList<String> t1i = new ArrayList<String>();
		t1i.addAll(t1.atrs);

		String strT1i = Arrays.toString(t1i.toArray()).replace("[", "").replace("]", "");
		String strT2p = Arrays.toString(t2p.toArray()).replace("[", "").replace("]", "");
		String Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
				+ strT2p + "}, ";
		if(!t2pp.isEmpty()) {
			String strT2pp = Arrays.toString(t2pp.toArray()).replace("[", "").replace("]", "");
			Qp += "T''2:{" + strT2pp+ "}, ";
		}
		String strT3p = Arrays.toString(t3p.toArray()).replace("[", "").replace("]", "");
		Qp += t3.nombre + "_of_" + t1.nombre + ": {"
				+ strT3p + "}";
		if(!t3pp.isEmpty()) {
			String strT3pp = Arrays.toString(t3pp.toArray()).replace("[", "").replace("]", "");
			Qp += ", T''3:{" + strT3pp + "}";
		}
		System.out.println(Qp + "}");
		return Qp + "}";
	}
	
	public static String regla5(Tabla t1, Tabla t2, Tabla t3, String S1, String S3) {
		S3 = S3.replaceAll(" ", "");
		S1 = S1.replaceAll(" ", "");
		ArrayList<String> s3 = new ArrayList<String>(Arrays.asList(S3.split("\n")));
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList(S1.split("\n")));
		ArrayList<String> s1t2 = new ArrayList<String>();
		ArrayList<String> s1t3 = new ArrayList<String>();
		HashSet<String> pKeyT1 = new HashSet<String>(t1.atrs);
		pKeyT1.retainAll(t2.atrs);
		HashSet<String> pKeyT2 = new HashSet<String>(t2.atrs);
		pKeyT2.retainAll(t3.atrs);
		
		//Clasificar S1
		for(int i = 0; i < s1.size(); i++) {
			HashSet<String> s1i = new HashSet<String>(Arrays.asList(s1.get(i).split(",")));
			if(t2.atrs.containsAll(s1i)) {
				s1t2.add(s1.get(i));
			}else if(t3.atrs.containsAll(s1i)) {
				s1t3.add(s1.get(i));
			};
		}
		
		//Hallar T'2
		//Intersección T'2(S3)
		ArrayList<String> t2s3 = new ArrayList<String>();
		for(int i = 0; i < s3.size(); i++) {
			HashSet<String> s3i = new HashSet<String>(Arrays.asList(s3.get(i).split(",")));
			s3i.retainAll(t2.atrs);
			t2s3.addAll(s3i);
		}
		System.out.println("T'2(s3) = " + Arrays.toString(t2s3.toArray()));
		
		//Hallar T'2 y T''2
		HashSet<String> t2p = new HashSet<String>();
		HashSet<String> auxT2P = new HashSet<String>();
		HashSet<String> t2pp = new HashSet<String>();
		for(int i = 0; i < s1t2.size(); i++) {
			HashSet<String> s1i = new HashSet<String>(Arrays.asList(s1t2.get(i).split(",")));
			HashSet<String> auxS1i = new HashSet<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t2s3);
			if(!s1i.equals(auxS1i)) {
				auxT2P.addAll(s1i);
			}
			//Condición para hallar T''2
			auxS1i.retainAll(t2s3);
			if(auxS1i.isEmpty()) {
				t2pp.addAll(s1i);
				System.out.println("auxS1T2 Vacio");
			}
		}
		t2p.addAll(t2s3);
		t2p.addAll(auxT2P);
		t2p.removeAll(pKeyT1);
		t2pp.removeAll(pKeyT1);
		System.out.println("s1i-t2' = " + Arrays.toString(auxT2P.toArray()));
		System.out.println("T'2 = " + Arrays.toString(t2p.toArray()));
		System.out.println("T''2 = " + Arrays.toString(t2pp.toArray()));
		
		//Hallar T'3
		//Intersección T'3(S3)
		HashSet<String> t3s3 = new HashSet<String>();
		for(int i = 0; i < s3.size(); i++) {
			HashSet<String> s3i = new HashSet<String>(Arrays.asList(s3.get(i).split(",")));
			s3i.retainAll(t3.atrs);
			t3s3.addAll(s3i);
		}
		System.out.println("T'3(s3) = " + Arrays.toString(t3s3.toArray()));
		
		//Hallar T'3 y T''3
		HashSet<String> t3p = new HashSet<String>();
		HashSet<String> auxT3P = new HashSet<String>();
		HashSet<String> t3pp = new HashSet<String>();
		for(int i = 0; i < s1t3.size(); i++) {
			ArrayList<String> s1i = new ArrayList<String>(Arrays.asList(s1t3.get(i).split(",")));
			System.out.println("S1i = " + Arrays.toString(s1i.toArray()));
			ArrayList<String> auxS1i = new ArrayList<String>(s1i);
			//Condicion para hallar T'2
			s1i.removeAll(t3s3);
			if(!s1i.equals(auxS1i)) {
				auxT3P.addAll(s1i);
			}
			//Condición para hallar T''3
			System.out.println("auxS1i = " + Arrays.toString(auxS1i.toArray()));
			System.out.println("t3s3 = " + Arrays.toString(t3s3.toArray()));
			auxS1i.retainAll(t3s3);
			System.out.println("auxS1i" + Arrays.toString(auxS1i.toArray()));
			if(auxS1i.isEmpty()) {
				t3pp.addAll(s1i);
				System.out.println("auxS1T3 Vacio");
			}
		}
		t3p.addAll(t3s3);
		t3p.addAll(auxT3P);
		t3p.removeAll(pKeyT2);
		t3pp.removeAll(pKeyT2);
		System.out.println("s1i-t3' = " + Arrays.toString(auxT3P.toArray()));
		System.out.println("T'3 = " + Arrays.toString(t3p.toArray()));
		System.out.println("T''3 = " + Arrays.toString(t3pp.toArray()));
		
		ArrayList<String> t1i = new ArrayList<String>();
		t1i.addAll(t1.atrs);

		String strT1i = Arrays.toString(t1i.toArray()).replace("[", "").replace("]", "");
		String strT2p = Arrays.toString(t2p.toArray()).replace("[", "").replace("]", "");
		String strT3p = Arrays.toString(t3p.toArray()).replace("[", "").replace("]", "");
		String Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
				+ strT2p + ", " + t3.nombre + "_of_" + t2.nombre + ": {" + strT3p + "}} ";
		if(!t2pp.isEmpty()) {
			String strT2pp = Arrays.toString(t2pp.toArray()).replace("[", "").replace("]", "");
			Qp += ", T''2:{" + strT2pp+ "}";
		}
		
//		Qp += t3.nombre + "_of_" + t1.nombre + ": {"
//				+ strT3p + "}";
		if(!t3pp.isEmpty()) {
			String strT3pp = Arrays.toString(t3pp.toArray()).replace("[", "").replace("]", "");
			Qp += ", T''3:{" + strT3pp + "}";
		}
		System.out.println(Qp + "}");
		return Qp + "}";
	}
}
