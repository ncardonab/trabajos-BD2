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

	public static void regla1(Tabla t1, Tabla t2, String S1, String S2) {
		S2 = S2.replaceAll(" ", "");
		S1 = S1.replaceAll(" ", "");
		ArrayList<String> s2 = new ArrayList<String>(Arrays.asList(S2.split("\n")));
		ArrayList<String> s1 = new ArrayList<String>(Arrays.asList(S1.split("\n")));		
		
		//Intersección T2(S2)
		ArrayList<String> t2s2 = new ArrayList<String>();
		for(int i = 0; i < s2.size(); i++) {
			ArrayList<String> s2i = new ArrayList<String>(Arrays.asList(s2.get(i).split(",")));
			for(int j = s2i.size() - 1; j > -1; --j){
			    String str = s2i.get(j);
			    if(t2.atrs.contains(str))
			        t2s2.add(str);
			}
		}
		System.out.println("T'2(s2) = " + Arrays.toString(t2s2.toArray()));
//		s2.retainAll(t2.atrs);
//		System.out.println(Arrays.toString(s2.toArray()));
		
		//Hallar T2'
		ArrayList<String> t2p = new ArrayList<String>();
		ArrayList<String> auxT2P = new ArrayList<String>();
		for(int i = 0; i < s1.size(); i++) {
			ArrayList<String> s1i = new ArrayList<String>(Arrays.asList(s1.get(i).split(",")));
			for(int j = s1i.size() - 1; j > -1; --j){
			    String str = s1i.get(j);
			    if(!t2s2.contains(str))
			    	auxT2P.add(str);
			}
		}
		t2p.addAll(t2s2);
		t2p.addAll(auxT2P);
		
		System.out.println("T'2 = " + Arrays.toString(t2p.toArray()));
		
		//Hallar T2''
		ArrayList<String> t2pp = new ArrayList<String>();
		for(int i = 0; i < s1.size(); i++) {
			ArrayList<String> auxS1 = new ArrayList<String>(Arrays.asList(s1.get(i).split(",")));
			auxS1.retainAll(t2s2);
			if(auxS1.isEmpty()) {
				t2pp.addAll(s1);
				System.out.println("auxS1 Vacio");
			}
		}
//		System.out.println("auxs1 = " + Arrays.toString(auxS1.toArray()));
		System.out.println("T''2 = " + Arrays.toString(t2pp.toArray()));
		
		ArrayList<String> t1i = new ArrayList<String>();
		t1i.addAll(t1.atrs);
//		t1i.addAll(s1);
		
		String strT1i = Arrays.toString(t1i.toArray()).replace("[", "").replace("]", "");
		String strT2p = Arrays.toString(t2p.toArray()).replace("[", "").replace("]", "");
		String Qp;
		if(t2pp.isEmpty()) {
			Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
					+ strT2p + "}}";
		}
		else {
			String strT2pp = Arrays.toString(t2pp.toArray()).replace("[", "").replace("]", "");
			Qp = "Q'={" +  strT1i + ", " + t2.nombre + "_of_" + t1.nombre + ": {"
					+ strT2p + "}, T''2:{"+ strT2pp + "}}";
		}
		System.out.println(Qp);
	}
}
