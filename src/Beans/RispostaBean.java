package Beans;
import java.util.ArrayList;
/***************************/
public class RispostaBean {
/***************************/
//Attributi
/***************************/ 
	private DomandaBean Domanda;
	private ArrayList <DettaglioRispostaBean> DettaglioRisposta;
	private String TestoRisposta;
	private Boolean RispostaGiusta;
/***************************/		
//Costruttore
/***************************/
	public RispostaBean() {
		Domanda = new DomandaBean();
		DettaglioRisposta = new ArrayList<DettaglioRispostaBean>();
	}
/***************************/
//Getter/Setter
/***************************/
	public String getTestoRisposta() {
		return TestoRisposta;
	}
	public void setTestoRisposta(String testoRisposta) {
		TestoRisposta = testoRisposta;
	}
	public ArrayList <DettaglioRispostaBean> getDettaglioRisposta() {
    return DettaglioRisposta;
  }
  public void setDettaglioRisposta(ArrayList <DettaglioRispostaBean> dettaglioRisposta) {
	  DettaglioRisposta = dettaglioRisposta;
  }
	public Boolean getRispostaGiusta() {
		return RispostaGiusta;
	}
	public void setRispostaGiusta(Boolean rispostaGiusta) {
		RispostaGiusta = rispostaGiusta;
	}
	public DomandaBean getDomanda() {
		return Domanda;
	}
	public void setDomanda(DomandaBean domanda) {
		Domanda = domanda;
	}
/***************************/
}
