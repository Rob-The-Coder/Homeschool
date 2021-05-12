package Beans;
import java.util.ArrayList;
/***************************/
public class ClasseBean {
/***************************/
//Attributi
/***************************/
	private String NomeClasse;
	private ArrayList <StudenteBean> Studente;
/***************************/	
//Costruttore
/***************************/
	public ClasseBean() {
		Studente = new ArrayList<StudenteBean>();
	}
/***************************/	
//Getter/Setter
/***************************/	
	public String getNomeClasse() {
		return NomeClasse;
	}
	public void setNomeClasse(String nomeClasse) {
		NomeClasse = nomeClasse;
	}
	public ArrayList <StudenteBean> getStudente() {
		return Studente;
	}
	public void setStudente(ArrayList <StudenteBean> studente) {
		Studente = studente;
	}
/***************************/
}
