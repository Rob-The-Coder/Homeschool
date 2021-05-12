package Beans;
import java.util.ArrayList;
/***************************/
public class DomandaBean {
/***************************/
//Attributi
/***************************/
  private QuestionarioBean Questionario;
  private ArrayList<RispostaBean> Risposta;
  private String TestoDomanda;
  private String TipoDomanda;
  private float PunteggioSbagliato;
  private float PunteggioGiusto;
  private float PunteggioVuoto;
  private String ImmagineDomanda;
/***************************/
//Costruttore
/***************************/
  public DomandaBean(){
	  Questionario = new QuestionarioBean();
	  Risposta = new ArrayList<RispostaBean>();
  }
/***************************/
//Getter/Setter
/***************************/
  public QuestionarioBean getQuestionario() {
    return Questionario;
  }
  public void setQuestionario(QuestionarioBean questionario) {
    Questionario = questionario;
  }
  public ArrayList<RispostaBean> getRisposta() {
    return Risposta;
  }
  public void setRisposta(ArrayList<RispostaBean> risposta) {
    Risposta = risposta;
  }
  public String getTestoDomanda() {
    return TestoDomanda;
  }
  public void setTestoDomanda(String testoDomanda) {
    TestoDomanda = testoDomanda;
  }
  public float getPunteggioSbagliato() {
    return PunteggioSbagliato;
  }
  public void setPunteggioSbagliato(float punteggioSbagliato) {
    PunteggioSbagliato = punteggioSbagliato;
  }
  public float getPunteggioGiusto() {
    return PunteggioGiusto;
  }
  public void setPunteggioGiusto(float punteggioGiusto) {
    PunteggioGiusto = punteggioGiusto;
  }
  public float getPunteggioVuoto() {
    return PunteggioVuoto;
  }
  public void setPunteggioVuoto(float punteggioVuoto) {
    PunteggioVuoto = punteggioVuoto;
  }
  public String getImmagineDomanda() {
    return ImmagineDomanda;
  }
  public void setImmagineDomanda(String immagineDomanda) {
    ImmagineDomanda = immagineDomanda;
  }
/***************************/
public String getTipoDomanda() {
	return TipoDomanda;
}
public void setTipoDomanda(String tipoDomanda) {
	TipoDomanda = tipoDomanda;
}
}
