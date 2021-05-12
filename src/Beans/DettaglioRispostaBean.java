package Beans;
/***************************/
public class DettaglioRispostaBean {
/***************************/
//Attributi
/***************************/
	private StudenteBean Studente;
	private RispostaBean Risposta;
	private Boolean RispostaSelezionata;
/***************************/
//Costruttore
/***************************/
	public DettaglioRispostaBean() {
		Studente = new StudenteBean();
		Risposta = new RispostaBean();
	}
/***************************/
//Getter/Setter
/***************************/
	public StudenteBean getStudente() {
		return Studente;
	}
	public void setStudente(StudenteBean studente) {
		Studente = studente;
	}
	public Boolean getRispostaSelezionata() {
		return RispostaSelezionata;
	}
	public void setRispostaSelezionata(Boolean rispostaSelezionata) {
		RispostaSelezionata = rispostaSelezionata;
	}
	public RispostaBean getRisposta() {
		return Risposta;
	}
	public void setRisposta(RispostaBean risposta) {
		Risposta = risposta;
	}	
/***************************/	
}
