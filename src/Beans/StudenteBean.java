package Beans;
import java.util.ArrayList;
/***************************/
public class StudenteBean {
/***************************/
//Attributi
/***************************/
	private ClasseBean Classe;
	private ArrayList <DettaglioRispostaBean> DettaglioRisposta;
	private String Nome;
	private String Cognome;
	private String Città;
	private String Email;
	private String Password;
	private String PercorsoImmagineStudente;
	private String DataNascita;
/***************************/	
//Costruttore
/***************************/
	public StudenteBean() {
		Classe = new ClasseBean();
		DettaglioRisposta = new ArrayList<DettaglioRispostaBean>();
	}
/***************************/
//Getter/Setter
/***************************/
	public ClasseBean getClasse() {
		return Classe;
	}
	public void setClasse(ClasseBean classe) {
		Classe = classe;
	}
	public ArrayList <DettaglioRispostaBean> getDettaglioRisposta() {
		return DettaglioRisposta;
	}
	public void setDettaglioRisposta(ArrayList <DettaglioRispostaBean> dettaglioRisposta) {
		DettaglioRisposta = dettaglioRisposta;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public String getCittà() {
		return Città;
	}
	public void setCittà(String città) {
		Città = città;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getPercorsoImmagineStudente() {
		return PercorsoImmagineStudente;
	}
	public void setPercorsoImmagineStudente(String percorsoImmagineStudente) {
		PercorsoImmagineStudente = percorsoImmagineStudente;
	}
	public String getDataNascita() {
		return DataNascita;
	}
	public void setDataNascita(String dataNascita) {
		DataNascita = dataNascita;
	}
/***************************/
}
